package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.carrito.Carrito;
import com.DAWProyecto.v2.model.Empleado;
import com.DAWProyecto.v2.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping(path = "/login")
@SessionAttributes({"carrito","empleado"})
public class LoginController {
    @Autowired
    private IEmpleadoRepository emRep;
    @GetMapping("/cargar")
    public String cargarPag(Model model) {
        model.addAttribute("empleado", new Empleado());
        return "index";
    }
    @GetMapping("/cargaEmpleado")
    public String cargaEmpleado(@ModelAttribute Empleado empleado, Model model) {
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("empleado", emRep.findById(empleado.getIdEmpleado()));
        model.addAttribute("lstEmpleado", emRep.findAll());
        return "menu-principal";
    }

    @PostMapping("/validar")
    public String grabarPag(@ModelAttribute Empleado empleado, Model model) {
        System.out.println(empleado);
        Empleado e = emRep.findByUserAndClave(empleado.getUser(), empleado.getClave());
        System.out.println(e);
        if (e == null) {
            model.addAttribute("empleado", new Empleado());
            model.addAttribute("mensaje", "Usuario o clave incorrecto");
            return "index";
        } else {

            model.addAttribute("carrito", new ArrayList<Carrito>());
            model.addAttribute("empleado", e);
            return "menu-principal";
        }
    }
}
