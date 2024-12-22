package com.DAWProyecto.v2.repository;

import com.DAWProyecto.v2.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEmpleadoRepository extends JpaRepository<Empleado, Integer> {
    Empleado findByUserAndClave(String user, String clave);
}
