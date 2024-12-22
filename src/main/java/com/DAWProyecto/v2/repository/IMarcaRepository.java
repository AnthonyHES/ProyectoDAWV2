package com.DAWProyecto.v2.repository;

import com.DAWProyecto.v2.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMarcaRepository extends JpaRepository<Marca, Integer> {
}
