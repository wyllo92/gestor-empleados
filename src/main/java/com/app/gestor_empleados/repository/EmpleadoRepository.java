package com.app.gestor_empleados.repository;

import com.app.gestor_empleados.entity.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

}
