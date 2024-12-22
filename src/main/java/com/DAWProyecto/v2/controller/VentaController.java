package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.model.*;
import com.DAWProyecto.v2.repository.IClienteRepository;
import com.DAWProyecto.v2.repository.IDetalleVentaRepository;
import com.DAWProyecto.v2.carrito.Carrito;
import com.DAWProyecto.v2.carrito.CarroForm;
import com.DAWProyecto.v2.dtoinsert.VentaInsert;
import com.DAWProyecto.v2.repository.ICarroRepository;
import com.DAWProyecto.v2.repository.IVentaRepository;
import com.DAWProyecto.v2.utils.GenerarSerie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.sql.Date;
import java.util.List;

@Controller
@SessionAttributes({"carrito","empleado"})
public class VentaController {

    @Autowired
    private IVentaRepository repoVen;

    @Autowired
    private IClienteRepository repoCli;

    @Autowired
    private ICarroRepository repoCar;

    Cliente clienteG = new Cliente();
    Venta ventaG = new Venta();

    Carro carroG = new Carro();

    LocalDate localDate = LocalDate.now();
    Date fecha = Date.valueOf(localDate);
    VentaInsert ventaIns = new VentaInsert(fecha);
    CarroForm carroFormG = new CarroForm();
    GenerarSerie generarSerie = new GenerarSerie();

    @Autowired
    private IDetalleVentaRepository detalleVentaRepository;

    @GetMapping("/registro/venta")
    public String cargarPagRegistroVenta(Model model){
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        ventaIns.setNumserie(serie);
        Empleado empleado = (Empleado)model.getAttribute("empleado");
        if(empleado==null){
            model.addAttribute("serie",serie);
            model.addAttribute("carroForm", carroFormG);
            model.addAttribute("cliente", clienteG);
            model.addAttribute("venta",ventaG);
            model.addAttribute("ventainsert",ventaIns);
            model.addAttribute("carro",carroG);
            List<Carrito> carritoList = (List<Carrito>) model.getAttribute("carrito");
            model.addAttribute("carrito", carritoList);

            System.out.println();
            return "registro-ventas";
        }
        ventaIns.setIdEmpleado(empleado.getIdEmpleado());
        model.addAttribute("serie",serie);
        model.addAttribute("carroForm", carroFormG);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta",ventaG);
        model.addAttribute("ventainsert",ventaIns);
        model.addAttribute("carro",carroG);
        List<Carrito> carritoList = (List<Carrito>) model.getAttribute("carrito");
        model.addAttribute("carrito", carritoList);

        System.out.println();
        return "registro-ventas";
    }

    @GetMapping("/busquedacliente")
    public String cargarBusquedaCliente(@RequestParam(name = "dni") String dni, Model model) {

        System.out.println(dni);
        Cliente cliente = repoCli.findByDni(dni);
        System.out.println(cliente.getNom());
        clienteG = cliente;
        ventaIns.setIdCliente(cliente.getIdCli());

        List<Carrito> carritosList = (List<Carrito>) model.getAttribute("carrito");
        model.addAttribute("carrito", carritosList);
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        model.addAttribute("serie",serie);
        model.addAttribute("carroForm", carroFormG);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta",ventaG);
        model.addAttribute("ventainsert",ventaIns);
        model.addAttribute("carro",carroG);
        return "registro-ventas";
    }

    @GetMapping("/busquedacarro")
    public String cargarBusquedaCarro(@RequestParam(name = "carro") String carro, Model model) {

        Carro carroObtenido = repoCar.findById(Integer.parseInt(carro)).orElse(null);
        assert carroObtenido != null;
        System.out.println(carroObtenido.getId());
        carroG = carroObtenido;
        carroFormG.setIdCarro(carroG.getId());
        List<Carrito> carritosList = (List<Carrito>) model.getAttribute("carrito");
        model.addAttribute("carrito", carritosList);
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        model.addAttribute("serie",serie);
        model.addAttribute("carroForm",carroFormG);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta",ventaG);
        model.addAttribute("ventainsert",ventaIns);
        model.addAttribute("carro",carroG);
        return "registro-ventas";
    }

    @PostMapping("/agregarcarro")
    public String cargarAgregadoCarro(@ModelAttribute CarroForm carroForm, Model model) {
        System.out.println("Ingreeeeeeeeeeeeeeeeeeeeeeee" + carroForm.getIdCarro() + " " + carroForm.getCantidad());
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        model.addAttribute("serie", serie);
        Carro carro = repoCar.findById(carroForm.getIdCarro()).orElse(null);
        if(carro==null){
            List<Carrito> carritosList = (List<Carrito>) model.getAttribute("carrito");
            model.addAttribute("carroForm", carroForm);
            model.addAttribute("carrito", carritosList);
            model.addAttribute("cliente", clienteG);
            model.addAttribute("venta",ventaG);
            model.addAttribute("ventainsert",ventaIns);
            model.addAttribute("carro",carroG);
            return "registro-ventas";
        }
        Carrito carrito = new Carrito();
        carrito.setId(obtenerUltimoId(model)+1);
        carrito.setCarro(carro);
        carrito.setCantidad(carroForm.getCantidad());
        carrito.setSubtotal(carroForm.getCantidad() * carro.getPrecio());
        List<Carrito> carritoList = (List<Carrito>) model.getAttribute("carrito");
        if(carritoList==null){
            List<Carrito> carritosList = (List<Carrito>) model.getAttribute("carrito");
            model.addAttribute("carrito", carritosList);
            model.addAttribute("carroForm", carroForm);
            model.addAttribute("cliente", clienteG);
            model.addAttribute("venta",ventaG);
            model.addAttribute("ventainsert",ventaIns);
            model.addAttribute("carro",carroG);
            return "registro-ventas";
        }
        //Dentro carro
        boolean isrepeat = false;
        for(int i=0; i<carritoList.size(); i++){
            if(carritoList.get(i).getCarro().getId() == carrito.getCarro().getId()){
                carritoList.get(i).setCantidad(carritoList.get(i).getCantidad() + carrito.getCantidad());
                System.out.println("Subtotal -> "+carritoList.get(i).getSubtotal());
                ventaIns.setMonto(ventaIns.getMonto()+carrito.getSubtotal());
                isrepeat = true;
            }
        }
        if(!isrepeat){
            carritoList.add(carrito);
            ventaIns.setMonto(ventaIns.getMonto()+carrito.getSubtotal());
        }
        ventaIns.setIdCliente(clienteG.getIdCli());
        ventaIns.setNumserie(serie);
        System.out.println(carrito.getCarro().getId());
        System.out.println("Intenta agregar");
        model.addAttribute("carroForm", carroForm);
        model.addAttribute("carrito", carritoList);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta",ventaG);
        model.addAttribute("ventainsert",ventaIns);
        model.addAttribute("carro",new Carro());
        return "registro-ventas";
    }

    @PostMapping("/agregarVenta")
    public String cargarAgregadoVenta(@ModelAttribute VentaInsert ventaInsert, Model model){
        Empleado empleado = new Empleado();
        empleado.setIdEmpleado(ventaInsert.getIdEmpleado());
        Venta venta = new Venta();
        venta.setMonto(ventaIns.getMonto());
        venta.setNumserie(ventaIns.getNumserie());
        venta.setCliente(clienteG);
        venta.setEmpleado(empleado);
        venta.setFecha(ventaInsert.getFecha());
        System.out.println(ventaInsert);
        System.out.println("hasdfhkasdhfaskhdf"); //Cambiar
        clienteG = new Cliente();
        ventaG = new Venta();
        ventaIns = new VentaInsert();
        carroG = new Carro();
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        model.addAttribute("carroForm", new CarroForm());
        model.addAttribute("serie",serie);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta", ventaG);
        model.addAttribute("carro", carroG);
        model.addAttribute("ventainsert", ventaIns);
        try {
            repoVen.save(venta);
            List<Carrito> carritoList = (List<Carrito>) model.getAttribute("carrito");
            assert carritoList != null;
            for(Carrito car : carritoList){
                DetalleVenta detalleVenta = new DetalleVenta();
                detalleVenta.setVenta(venta);
                detalleVenta.setCantidad(car.getCantidad());
                detalleVenta.setCarro(car.getCarro());
                detalleVenta.setDescripcionC(car.getCarro().getDescripcion());
                detalleVenta.setPrecio(car.getCarro().getPrecio());
                detalleVentaRepository.save(detalleVenta);
                System.out.println(car.getCarro().getDescripcion());
            }
            model.addAttribute("carrito", new ArrayList<Carrito>());
            model.addAttribute("lstVenta", repoVen.findAll());
        } catch(Exception e) {
            model.addAttribute("carrito", new ArrayList<Carrito>());
            model.addAttribute("mensaje", "No se Registró");
        }

        return "registro-ventas";
    }

    @GetMapping("/cancelarVenta")
    public String cancelarVenta(Model model){
        clienteG = new Cliente();
        ventaG = new Venta();
        ventaIns = new VentaInsert();
        carroG = new Carro();
        String serie = generarSerie.numeroSerie(repoVen.findFirstByOrderByIdDesc().orElse(new Venta()).getId());
        model.addAttribute("carroForm", new CarroForm());
        model.addAttribute("serie",serie);
        model.addAttribute("cliente", clienteG);
        model.addAttribute("venta", ventaG);
        model.addAttribute("carro", carroG);
        model.addAttribute("ventainsert", ventaIns);
        model.addAttribute("carrito", new ArrayList<Carrito>());

        return "registro-ventas";
    }

    public int obtenerUltimoId(Model model){
        List<Carrito> carritoList = (List<Carrito>) model.getAttribute("carrito");
        if(carritoList==null || carritoList.size()==0){
            model.addAttribute("carrito",new ArrayList<Carrito>());
            return 0;
        }
        List<Carrito> newList = carritoList;
        newList.sort(Comparator.comparing(Carrito::getId, Comparator.reverseOrder()));
        return newList.get(0).getId();
    }



}
