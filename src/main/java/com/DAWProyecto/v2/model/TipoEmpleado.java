package com.DAWProyecto.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "tipoempleado")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoEmpleado {
    @Id
    @Column(name = "id_tipo")
    private int idTipo;

    @Column(name = "descripcion")
    private String descrip;

    @OneToMany(mappedBy = "objTipo")
    @JsonIgnore
    Set<Empleado> empleado;
}
