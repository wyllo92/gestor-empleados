package com.app.gestor_empleados.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class EmpleadoTiempoService {

    public String calcularEdad(LocalDate fechaNacimiento) {
        if (fechaNacimiento == null) return null;
        Period periodo = Period.between(fechaNacimiento, LocalDate.now());
        return formatearPeriodo(periodo);
    }

    public String calcularTiempoVinculacion(LocalDate fechaVinculacion) {
        if (fechaVinculacion == null) return null;
        Period periodo = Period.between(fechaVinculacion, LocalDate.now());
        return formatearPeriodo(periodo);
    }

    private String formatearPeriodo(Period periodo) {
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