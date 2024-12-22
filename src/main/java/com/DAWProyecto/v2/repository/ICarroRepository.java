package com.DAWProyecto.v2.repository;

import com.DAWProyecto.v2.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarroRepository extends JpaRepository<Carro, Integer> {
}
