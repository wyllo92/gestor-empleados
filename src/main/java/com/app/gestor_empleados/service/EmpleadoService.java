package com.app.gestor_empleados.service;

import com.app.gestor_empleados.model.EmpleadoDTO;
import com.app.gestor_empleados.soap.model.EmpleadoResponse;

public interface EmpleadoService {

    EmpleadoResponse crearEmpleado(EmpleadoDTO empleadoDTO) throws Exception;

    EmpleadoDTO guardarEmpleado(EmpleadoDTO empleado);

}
