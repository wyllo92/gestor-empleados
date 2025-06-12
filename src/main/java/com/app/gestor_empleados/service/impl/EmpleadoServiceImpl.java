package com.app.gestor_empleados.service.impl;

import com.app.gestor_empleados.entity.Empleado;
import com.app.gestor_empleados.model.EmpleadoDTO;
import com.app.gestor_empleados.repository.EmpleadoRepository;
import com.app.gestor_empleados.service.EmpleadoService;
import com.app.gestor_empleados.soap.model.EmpleadoRequest;
import com.app.gestor_empleados.soap.model.EmpleadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    private final WebServiceTemplate webServiceTemplate;
    private static final String SOAP_ENDPOINT = "http://localhost:8080/ws/empleados";

    @Autowired
    private EmpleadoRepository empleadoRepository;

    public EmpleadoServiceImpl(Jaxb2Marshaller marshaller) {
        this.webServiceTemplate = new WebServiceTemplate(marshaller);
    }

    public EmpleadoResponse crearEmpleado(EmpleadoDTO empleadoDTO) throws Exception {
        EmpleadoRequest request = convertirARequest(empleadoDTO);
        return (EmpleadoResponse) webServiceTemplate.marshalSendAndReceive(SOAP_ENDPOINT, request);
    }

    private EmpleadoRequest convertirARequest(EmpleadoDTO dto) throws Exception {
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
        empleadoDTO.calcularTiempos();

        return empleadoDTO;
    }

    public static XMLGregorianCalendar convert(LocalDate localDate) throws Exception {
        GregorianCalendar calendar = GregorianCalendar.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
    }
}
