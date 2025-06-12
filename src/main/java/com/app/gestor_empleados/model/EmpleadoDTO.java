package com.app.gestor_empleados.model;

import java.time.LocalDate;
import java.time.Period;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmpleadoDTO {

    private Long id;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "Los nombres son obligatorios")
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    private String apellidos;

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotNull(message = "La fecha de vinculación es obligatoria")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaVinculacion;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotNull(message = "El salario es obligatorio")
    @DecimalMin(value = "0.0", message = "El salario debe ser mayor a 0")
    private Double salario;

    private String edad;
    private String tiempoVinculacion;

    @AssertTrue(message = "La fecha de vinculación debe ser posterior a la fecha de nacimiento")
    public boolean isFechaVinculacionValida() {
        if (fechaNacimiento == null || fechaVinculacion == null) {
            return true;
        }
        return fechaVinculacion.isAfter(fechaNacimiento);
    }

    @AssertTrue(message = "El empleado debe ser mayor de edad (18 años o más)")
    public boolean isMayorEdad() {
        if (fechaNacimiento == null) {
            return true;
        }
        LocalDate fechaActual = LocalDate.now();
        return Period.between(fechaNacimiento, fechaActual).getYears() >= 18;
    }

    public void calcularTiempos() {
        if (fechaNacimiento != null) {
            Period periodo = Period.between(fechaNacimiento, LocalDate.now());
            edad = formatearPeriodo(periodo);
        }
        if (fechaVinculacion != null) {
            Period periodo = Period.between(fechaVinculacion, LocalDate.now());
            tiempoVinculacion = formatearPeriodo(periodo);
        }
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

