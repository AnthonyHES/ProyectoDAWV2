package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.model.Carro;
import com.DAWProyecto.v2.model.Marca;
import com.DAWProyecto.v2.repository.ICarroRepository;
import com.DAWProyecto.v2.repository.IMarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CarroController {
    @Autowired
    private ICarroRepository repoCar;

    @Autowired
    private IMarcaRepository repoMar;

    @GetMapping("/carro")
    public String cargarPagCarro(Model model){
        model.addAttribute("carro", new Carro());
        model.addAttribute("lstMarca", repoMar.findAll());
        model.addAttribute("lstCarros", repoCar.findAll());
        System.out.println("listaa");
        return "crud-carros";
    }

    /* Agregar carro (cambiar los mensajes para recibirlos desde el procedure /JSON: {message:0 o 1, Value:"Mensaje de confirmación"}/)
    @PostMapping("/carro/agregar")
    public String agregarCarro(@ModelAttribute Carro carro, Model model) {
        // leer los datos ingresados
        System.out.println(carro);
        try {
            repoCar.save(carro);
            model.addAttribute("lstMarca", repoMar.findAll());
            model.addAttribute("lstCarros", repoCar.findAll());
            model.addAttribute("mensaje", "Registro OK");
        } catch(Exception e) {
            model.addAttribute("mensaje", "No se Registró");
        }
        return "crud-carros";
    }*/

    @GetMapping("/cargaActualizarCarro")
    public String cargaActualizarCarro(@ModelAttribute Carro carro, Model model) {
        //model.addAttribute("carro", new Carro());
        model.addAttribute("carro", repoCar.findById(carro.getId()));
        model.addAttribute("lstMarca", repoMar.findAll());
        model.addAttribute("lstCarros", repoCar.findAll());
        return "crud-carros";
    }

    public static boolean validarDes(String texto){
        boolean valido = true;
        if(texto.isEmpty()) {
            valido = false;
        }
        else if(!texto.substring(0,texto.length()).matches("^[a-zA-Z0-9 ]*$")){
            valido = false;
        }
        return valido;
    }

    public static boolean validarOrigen(String texto){
        boolean valorg = true;
        if(texto.isEmpty()) {
            valorg = false;
        }
        else if(!texto.substring(0,texto.length()).matches("^[a-zA-Z ]*$")){
            valorg = false;
        }
        return valorg;
    }

    public static boolean validarCombustible(String texto){
        boolean valcomb = true;
        if(texto.isEmpty()) {
            valcomb = false;
        }
        else if(!texto.substring(0,texto.length()).matches("^[a-zA-Z ]*$")){
            valcomb = false;
        }
        return valcomb;
    }
    public static boolean validarPrecio(double precio){
        String prec = String.valueOf(precio);
        boolean validaPrecio = !prec.trim().isEmpty();

        if (!prec.matches("[0-9]*+([.][0-9]{1,2})?")) {
            validaPrecio = false;
        }

        return validaPrecio;
    }

    public static boolean validarStock(int stock){ {
        String stk = String.valueOf(stock);
        boolean validarStock = true;

        if (stk.trim().isEmpty()) {
            validarStock = false;
        }

        else if (!stk.matches("[1-9]+[0-9]*")) {
            validarStock = false;
        }

        return validarStock;
    }
    }

    @PostMapping("/carro/agregar")
    public String agregarCarro(@ModelAttribute Carro carro,
                               Model model) {
        // leer los datos ingresados
        System.out.println(carro);
        model.addAttribute("lstMarca", repoMar.findAll());
        model.addAttribute("lstCarros", repoCar.findAll());
        try {
            if (validarDes(carro.getDescripcion())) {
                carro.setDescripcion(carro.getDescripcion());
            }
            else {
                model.addAttribute("mensaje2", "Registrar un texto válido en la descripción: "+ carro.getDescripcion());
                return "crud-carros";
            }

            if (carro.getObjMarca().getIdmarca() != -1) {
                carro.getObjMarca().setIdmarca(carro.getObjMarca().getIdmarca());
            }
            else {
                model.addAttribute("mensaje2", "Ingrese un categoria válida: "+ carro.getObjMarca().getIdmarca());
                return "crud-carros";
            }

            if (validarOrigen(carro.getOrigen())) {
                carro.setOrigen(carro.getOrigen());
            }
            else {
                model.addAttribute("mensaje2", "Registrar un texto válido en el origen: "+ carro.getOrigen());
                return "crud-carros";
            }

            if (validarCombustible(carro.getCombustible())) {
                carro.setCombustible(carro.getCombustible());
            }
            else {
                model.addAttribute("mensaje2", "Registrar un texto válido en el combustible: "+ carro.getCombustible());
                return "crud-carros";
            }

            if (validarPrecio(carro.getPrecio())) {
                carro.setPrecio(carro.getPrecio());
            }
            else {
                model.addAttribute("mensaje2", "Ingrese un precio válido: "+ carro.getPrecio());
                return "crud-carros";
            }

            if (validarStock(carro.getStock())) {
                carro.setStock(carro.getStock());
            }
            else {
                model.addAttribute("mensaje2", "Ingrese un stock válido: "+ carro.getStock());
                return "crud-carros";
            }
            repoCar.save(carro);
            model.addAttribute("carro", new Carro());
            //Fijo esto debe mostrar despues de agregar
            model.addAttribute("lstMarca", repoMar.findAll());
            for(Marca marca : repoMar.findAll()){
                System.out.println(marca.getDescripcion());
            }
            model.addAttribute("lstCarros", repoCar.findAll());
            model.addAttribute("mensaje", "Se registro correctamente el carro con el código: "+ carro.getId());
            return "crud-carros";
        } catch(Exception e) {
            model.addAttribute("mensaje", e.getMessage());
        }
        return "crud-carros";
    }

    @PostMapping("/actualizar")
    public String actualizarCarro(@ModelAttribute Carro carro, Model model) {
        // leer los datos ingresados
        try {
            System.out.println("el carro : "+carro.getObjMarca().getIdmarca());
            repoCar.save(carro);
            model.addAttribute("lstMarca", repoMar.findAll());
            model.addAttribute("lstCarros", repoCar.findAll());
            model.addAttribute("mensaje", "Felicidades Actualización exitosa!!!: " + carro.getId());
        } catch(Exception e) {
            model.addAttribute("mensaje", "Lo sentimos, no se pudo actualizar :(");
        }
        return "crud-carros";
    }

    @GetMapping("/eliminarCarro")
    public String eliminarCarro(@ModelAttribute Carro carro, Model model) {
        // leer los datos ingresados
        try {
            repoCar.deleteById(carro.getId());
            model.addAttribute("lstMarca", repoMar.findAll());
            model.addAttribute("lstCarros", repoCar.findAll());
            model.addAttribute("mensaje", "Eliminado: " + carro.getId());
        } catch (Exception e) {
            model.addAttribute("mensaje", "Lo sentimos, no se pudo eliminar :(");
        }
        return "crud-carros";
    }



}
