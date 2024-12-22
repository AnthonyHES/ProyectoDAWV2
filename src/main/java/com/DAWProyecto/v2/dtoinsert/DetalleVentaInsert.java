package com.DAWProyecto.v2.dtoinsert;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetalleVentaInsert {

    private double precio;
    private int cantidad;
    private String descripcionC;
    private double subtotal;
    private int idVenta;
    private int idCarro;

}
