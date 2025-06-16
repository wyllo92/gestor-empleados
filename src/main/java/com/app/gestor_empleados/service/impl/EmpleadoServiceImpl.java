package com.app.gestor_empleados.service.impl;

import com.app.gestor_empleados.entity.Empleado;
import com.app.gestor_empleados.model.EmpleadoDTO;
import com.app.gestor_empleados.repository.EmpleadoRepository;
import com.app.gestor_empleados.service.EmpleadoService;
import com.app.gestor_empleados.service.EmpleadoTiempoService;
import com.app.gestor_empleados.soap.model.EmpleadoRequest;
import com.app.gestor_empleados.soap.model.EmpleadoResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final WebServiceTemplate webServiceTemplate;
    private static final String SOAP_ENDPOINT = "http://localhost:8080/ws/empleados";
    private final EmpleadoRepository empleadoRepository;

    @Autowired
    private EmpleadoTiempoService empleadoTiempoService;

    public EmpleadoServiceImpl(Jaxb2Marshaller marshaller, EmpleadoRepository empleadoRepository) {
        this.webServiceTemplate = new WebServiceTemplate(marshaller);
        this.empleadoRepository = empleadoRepository;
    }

    public EmpleadoResponse crearEmpleado(EmpleadoDTO empleadoDTO) {
        try {
            EmpleadoRequest request = convertirARequest(empleadoDTO);
            return (EmpleadoResponse) webServiceTemplate.marshalSendAndReceive(SOAP_ENDPOINT, request);
        } catch (DatatypeConfigurationException e) {
            throw new IllegalArgumentException("Error al convertir fechas para el empleado", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear empleado vía SOAP", e);
        }
    }

    private EmpleadoRequest convertirARequest(EmpleadoDTO dto) throws DatatypeConfigurationException {
        EmpleadoRequest request = new EmpleadoRequest();
        request.setNumeroDocumento(dto.getNumeroDocumento());
        request.setNombres(dto.getNombres());
        request.setApellidos(dto.getApellidos());
        request.setTipoDocumento(dto.getTipoDocumento());
        request.setFechaNacimiento(convert(dto.getFechaNacimiento()));
        request.setFechaVinculacion(convert(dto.getFechaVinculacion()));
        request.setCargo(dto.getCargo());
        request.setSalario(dto.getSalario());
        return request;
    }

    @Override
    public EmpleadoDTO guardarEmpleado(EmpleadoDTO empleadoDTO) {
        if (empleadoRepository.existsByNumeroDocumento(empleadoDTO.getNumeroDocumento())) {
            throw new IllegalArgumentException("Ya existe un empleado con ese número de documento");
        }
        Empleado empleado = new Empleado();
        empleado.setNumeroDocumento(empleadoDTO.getNumeroDocumento());
        empleado.setNombres(empleadoDTO.getNombres());
        empleado.setApellidos(empleadoDTO.getApellidos());
        empleado.setTipoDocumento(empleadoDTO.getTipoDocumento());
        empleado.setFechaNacimiento(empleadoDTO.getFechaNacimiento());
        empleado.setFechaVinculacion(empleadoDTO.getFechaVinculacion());
        empleado.setCargo(empleadoDTO.getCargo());
        empleado.setSalario(empleadoDTO.getSalario());

        empleado = empleadoRepository.save(empleado);

        empleadoDTO.setId(empleado.getId());
        empleadoDTO.setEdad(empleadoTiempoService.calcularEdad(empleado.getFechaNacimiento()));
        empleadoDTO.setTiempoVinculacion(empleadoTiempoService.calcularTiempoVinculacion(empleado.getFechaVinculacion()));

        return empleadoDTO;
    }

    public static XMLGregorianCalendar convert(LocalDate localDate) throws DatatypeConfigurationException {
        if (localDate == null) return null;
        GregorianCalendar calendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }
}
