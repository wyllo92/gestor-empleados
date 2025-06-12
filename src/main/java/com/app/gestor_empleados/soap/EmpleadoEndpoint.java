package com.app.gestor_empleados.soap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.app.gestor_empleados.model.EmpleadoDTO;
import com.app.gestor_empleados.service.EmpleadoService;
import com.app.gestor_empleados.soap.model.EmpleadoRequest;
import com.app.gestor_empleados.soap.model.EmpleadoResponse;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;

@Endpoint
public class EmpleadoEndpoint {

    private static final String NAMESPACE_URI = "http://www.gestor-empleados.com/soap";

    @Autowired
    private EmpleadoService empleadoService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "empleadoRequest")
    @ResponsePayload
    public EmpleadoResponse procesarEmpleado(@RequestPayload EmpleadoRequest request) {
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setNumeroDocumento(request.getNumeroDocumento());
        empleadoDTO.setNombres(request.getNombres());
        empleadoDTO.setApellidos(request.getApellidos());
        empleadoDTO.setTipoDocumento(request.getTipoDocumento());
        empleadoDTO.setFechaNacimiento(convertToLocalDate(request.getFechaNacimiento()));
        empleadoDTO.setFechaVinculacion(convertToLocalDate(request.getFechaVinculacion()));
        empleadoDTO.setCargo(request.getCargo());
        empleadoDTO.setSalario(request.getSalario());

        EmpleadoDTO empleadoGuardado = empleadoService.guardarEmpleado(empleadoDTO);

        EmpleadoResponse response = new EmpleadoResponse();
        response.setId(empleadoGuardado.getId());
        response.setMensaje("Empleado guardado exitosamente");
        response.setStatus(true);

        return response;
    }

    private LocalDate convertToLocalDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return LocalDate.of(
            calendar.getYear(),
            calendar.getMonth(),
            calendar.getDay()
        );
    }
} 