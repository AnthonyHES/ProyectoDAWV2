package com.DAWProyecto.v2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "Carro")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Carro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private int id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "origen")
    private String origen;

    @Column(name = "combustible")
    private String Combustible;

    @Column(name = "precio")
    private double precio;

    @Column(name = "stock")
    private int stock;
    @ManyToOne
    @JoinColumn(name = "id_marca")
    Marca objMarca;



}
