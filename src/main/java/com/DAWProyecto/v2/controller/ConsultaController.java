package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.model.Carro;
import com.DAWProyecto.v2.model.Cliente;
import com.DAWProyecto.v2.model.Empleado;
import com.DAWProyecto.v2.repository.ICarroRepository;
import com.DAWProyecto.v2.repository.IClienteRepository;
import com.DAWProyecto.v2.repository.IEmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConsultaController {

    @Autowired
    private ICarroRepository repoCar;

    @Autowired
    private IEmpleadoRepository repoEm;

    @Autowired
    private IClienteRepository repoCli;

    @GetMapping("/consultas/carros")
    public String cargarPagConsultaCarro(Model model){
        model.addAttribute("carro", new Carro());
        model.addAttribute("lstCarros", repoCar.findAll());
        return "consulta-carros";
    }

    @GetMapping("/consultas/empleados")
    public String cargarPagConsultaEmpleado(Model model){
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("lstEmpleados", repoEm.findAll());
        return "consulta-empleados";
    }

    @GetMapping("/consultas/clientes")
    public String cargarPagConsultaCliente(Model model){
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("lstCliente", repoCli.findAll());
        return "consulta-clientes";
    }
}
