package com.app.gestor_empleados.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.app.gestor_empleados.entity.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    boolean existsByNumeroDocumento(String numeroDocumento);
}

