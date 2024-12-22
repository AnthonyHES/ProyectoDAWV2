package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.model.Empleado;
import com.DAWProyecto.v2.model.TipoEmpleado;
import com.DAWProyecto.v2.repository.IEmpleadoRepository;
import com.DAWProyecto.v2.repository.ITipoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EmpleadoController {
    @Autowired
    private IEmpleadoRepository repoEmple;

    @Autowired
    private ITipoRepository repoTipo;

    @GetMapping("/empleado")
    public String cargarPagEmpleado(Model model){
        model.addAttribute("empleado", new Empleado());
        model.addAttribute("lstTipo", repoTipo.findAll());
        model.addAttribute("lstEmpleado", repoEmple.findAll());
        return "crud-empleados";
    }

    @PostMapping("/empleado/agregar")
    public String agregarEmpleado(@ModelAttribute Empleado empleado, Model model) {
        // leer los datos ingresados
        System.out.println(empleado);
        try {
            repoEmple.save(empleado);
            model.addAttribute("empleado", new Empleado());
            model.addAttribute("lstTipo", repoTipo.findAll());
            for(TipoEmpleado tipo : repoTipo.findAll()){
                System.out.println(tipo.getDescrip());
            }
            model.addAttribute("lstEmpleado", repoEmple.findAll());
            model.addAttribute("mensaje", "Registro OK");
        } catch(Exception e) {
            model.addAttribute("mensaje", "No se Registró");
        }
        return "crud-empleados";
    }

    @GetMapping("/cargaActualizarEmpleado")
    public String cargaActualizarEmpleado(@ModelAttribute Empleado empleado, Model model) {
       model.addAttribute("empleado", repoEmple.findById(empleado.getIdEmpleado()));
        model.addAttribute("lstTipo", repoTipo.findAll());
        model.addAttribute("lstEmpleado", repoEmple.findAll());
        return "crud-empleados";
    }

    @GetMapping("/eliminarEmpleado")
    public String eliminarEmpleado(@ModelAttribute Empleado empleado, Model model) {
        try {
            repoEmple.deleteById(empleado.getIdEmpleado());
            model.addAttribute("lstEmpleado", repoEmple.findAll());
            model.addAttribute("lstTipo", repoTipo.findAll());
            model.addAttribute("mensaje", "Eliminado: " + empleado.getIdEmpleado());
        } catch(Exception e) {
            model.addAttribute("mensaje", "Error al eliminar el registro");
        }
        return "crud-empleados";
    }


}
