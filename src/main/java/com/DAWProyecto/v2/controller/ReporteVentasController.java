package com.DAWProyecto.v2.controller;

import com.DAWProyecto.v2.model.DetalleVenta;
import com.DAWProyecto.v2.model.Venta;
import com.DAWProyecto.v2.repository.*;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.sql.DataSource;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReporteVentasController {

    private final IVentaRepository ventaRepository;

    @Autowired
    private IClienteRepository repoCliente;

    @Autowired
    private IEmpleadoRepository repoEmpleado;

    @Autowired
    private ICarroRepository repoCarro;

    @Autowired
    private IDetalleVentaRepository repoDetalle;

    public ReporteVentasController(IVentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }


    @GetMapping("/report/ventas")
    public String cargarPagReporteVenta(Model model){
        model.addAttribute("venta", new Venta());
        return "reporte-ventas";
    }

    @PostMapping("/reporte/ventas")
    public ModelAndView generarReporteVentas(@RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                                             @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFin, @RequestParam("accion") String accion,
                                             @ModelAttribute DetalleVenta detalle, Model model) {
        ModelAndView modelAndView = new ModelAndView("reporte-ventas");
        if (accion.equals("Consultar")) {
            List<Venta> ventas = ventaRepository.findVentaByFechaBetween(fechaInicio, fechaFin);

            // Obtener los detalles de venta para cada venta
            Map<Integer, List<DetalleVenta>> detallesPorVenta = new HashMap<>();
            for (Venta venta : ventas) {
                List<DetalleVenta> detallesVenta = repoDetalle.findByVentaId(venta.getId());
                detallesPorVenta.put(venta.getId(), detallesVenta);
            }
            modelAndView.addObject("ventas", ventas);
            modelAndView.addObject("detallesPorVenta", detallesPorVenta);
        } else if (accion.equals("Descargar")) {
            // Lógica para descargar el PDF
        }

        return modelAndView;

    }

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private DataSource dataSource;

    //controlador que genere el pdf(POST) si no funciona probar con un script
    @PostMapping("/reporte/fechas")
    public void generarVentaFecha(HttpServletResponse response, @ModelAttribute Venta venta,
                                  @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha1,
                                  @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fecha2) {
        response.setHeader("Content-Disposition", "attachment; filename=\"reporte_ventas.pdf\";");
        response.setContentType("application/pdf");
        try {
            String ruta = resourceLoader.getResource("classpath:reportes/ReporteVentas.jasper").getURI().getPath();
            HashMap<String, Object> parametros = new HashMap<>();
            parametros.put("fecha_inicio", fecha1);
            parametros.put("fecha_fin", fecha2);
            JasperPrint jasperPrint = JasperFillManager.fillReport(ruta, parametros, dataSource.getConnection());
            OutputStream outStream = response.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outStream);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
