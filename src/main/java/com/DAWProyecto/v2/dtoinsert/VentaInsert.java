package com.DAWProyecto.v2.dtoinsert;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
@Getter
@Setter

public class VentaInsert {

    private int idCliente;
    private int idEmpleado;
    private String Numserie;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private double monto;

    public VentaInsert(Date fecha){
        this.fecha = fecha;
    }
    public VentaInsert(){

    }

}
