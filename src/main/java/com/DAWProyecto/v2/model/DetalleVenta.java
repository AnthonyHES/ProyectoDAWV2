package com.DAWProyecto.v2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double precio;
    private int cantidad;
    private String descripcionC;
    private double subtotal;
    @ManyToOne
    @JoinColumn(name = "id_venta")
    private Venta venta;

    @ManyToOne
    @JoinColumn(name = "id_carro")
    private Carro carro;


}
