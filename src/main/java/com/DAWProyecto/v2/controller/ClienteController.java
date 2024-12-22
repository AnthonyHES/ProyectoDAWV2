package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.repository.IClienteRepository;
import com.DAWProyecto.v2.model.Cliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {
    @Autowired
    private IClienteRepository repoCli;

    @GetMapping("/cliente")
    public String cargarPagCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("lstCliente", repoCli.findAll());
        return "crud-clientes";
    }
    @PostMapping("/cliente/agregar")
    public String agregarCarro(@ModelAttribute Cliente cliente, Model model) {
        // leer los datos ingresados
        System.out.println(cliente);
        try {
            repoCli.save(cliente);
            model.addAttribute("lstClientes", repoCli.findAll());
            model.addAttribute("mensaje", "Registro OK");
        } catch(Exception e) {
            model.addAttribute("mensaje", "No se Registró");
        }
        return "crud-clientes";
    }



    @PostMapping("/actualizarCli")
    public String actualizarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            repoCli.save(cliente);
            model.addAttribute("mensaje", "Registro actualizado correctamente");
        } catch(Exception e) {
            model.addAttribute("mensaje", "Error al actualizar el registro");
        }
        return "crud-clientes";
    }

    @GetMapping("/cargaActualizarCliente")
    public String cargaActualizarCarro(@ModelAttribute Cliente cliente, Model model) {
        //model.addAttribute("cliente", new Cliente());
        model.addAttribute("cliente", repoCli.findById(cliente.getIdCli()));
        System.out.println(cliente.getIdCli());
        model.addAttribute("lstCliente", repoCli.findAll());
        return "crud-clientes";
    }

    @GetMapping("/eliminarCliente")
    public String eliminarCliente(@ModelAttribute Cliente cliente, Model model) {
        try {
            repoCli.deleteById(cliente.getIdCli());
            model.addAttribute("lstCliente", repoCli.findAll());
            model.addAttribute("mensaje", "Eliminado: " + cliente.getIdCli());
        } catch(Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el registro");
        }
        return "crud-clientes";
    }
}
