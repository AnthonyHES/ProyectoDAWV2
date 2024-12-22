package com.DAWProyecto.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "marca")
@Data
public class Marca {
    @Id
    @Column(name = "id_marca")
    private int idmarca;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "objMarca")
    @JsonIgnore
    Set<Carro> carro;

}
