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

import java.time.Period;
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
        response.setId(empleadoGuardado.getId().toString());
        response.setNumeroDocumento(request.getNumeroDocumento());
        response.setNombres(request.getNombres());
        response.setApellidos(request.getApellidos());
        response.setTipoDocumento(request.getTipoDocumento());
        response.setFechaNacimiento(request.getFechaNacimiento());
        response.setFechaVinculacion(request.getFechaVinculacion());
        response.setCargo(request.getCargo());
        response.setSalario(request.getSalario());
        response.setEdad(calcularTiemposSoap(empleadoDTO.getFechaNacimiento()));
        response.setTiempoVinculacion(calcularTiemposSoap(empleadoDTO.getFechaVinculacion()));

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

    private String calcularTiemposSoap(LocalDate fechaInicial) {
        Period periodo = Period.between(fechaInicial, LocalDate.now());
        return formatearPeriodoSoap(periodo);
    }

    private String formatearPeriodoSoap(Period periodo) {
        StringBuilder resultado = new StringBuilder();
        
        if (periodo.getYears() > 0) {
            resultado.append(periodo.getYears()).append(" año");
            if (periodo.getYears() != 1) resultado.append("s");
        }
        
        if (periodo.getMonths() > 0) {
            if (resultado.length() > 0) resultado.append(", ");
            resultado.append(periodo.getMonths()).append(" mes");
            if (periodo.getMonths() != 1) resultado.append("es");
        }
        
        if (periodo.getDays() > 0 || resultado.length() == 0) {
            if (resultado.length() > 0) resultado.append(" y ");
            resultado.append(periodo.getDays()).append(" día");
            if (periodo.getDays() != 1) resultado.append("s");
        }
        
        return resultado.toString();
    }
} 