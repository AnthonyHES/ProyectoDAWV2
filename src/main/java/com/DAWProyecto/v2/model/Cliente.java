package com.DAWProyecto.v2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cliente")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente")
    private int idCli ;

    @Column(name = "dni")
    private String dni;

    @Column(name = "nombres")
    private String nom;

    @Column(name = "apellidos")
    private String ape;

    @Column(name = "direccion")
    private String dir;

    @Column(name = "correo")
    private String correo;

    @Column(name = "telefono")
    private int tel;
}
