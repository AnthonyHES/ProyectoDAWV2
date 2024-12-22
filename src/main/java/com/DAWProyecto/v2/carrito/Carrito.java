package com.DAWProyecto.v2.carrito;

import com.DAWProyecto.v2.model.Carro;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Carrito {


    private int id;
    private Carro carro;
    private int cantidad;
    private double subtotal;

}
