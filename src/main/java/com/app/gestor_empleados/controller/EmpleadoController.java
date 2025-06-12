package com.app.gestor_empleados.controller;

import com.app.gestor_empleados.model.EmpleadoDTO;
import com.app.gestor_empleados.service.EmpleadoService;
import com.app.gestor_empleados.soap.model.EmpleadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @PostMapping
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@Valid @RequestBody EmpleadoDTO empleadoDTO) {
        empleadoDTO.calcularTiempos();
        EmpleadoDTO empleadoGuardado = empleadoService.guardarEmpleado(empleadoDTO);
        return ResponseEntity.ok(empleadoGuardado);
    }

    @PostMapping(value = "/soap")
    public ResponseEntity<EmpleadoResponse> crearEmpleadoSoap(@Valid @RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        empleadoDTO.calcularTiempos();
        EmpleadoResponse empleadoGuardado = empleadoService.crearEmpleado(empleadoDTO);
        return ResponseEntity.ok(empleadoGuardado);
    }
}
