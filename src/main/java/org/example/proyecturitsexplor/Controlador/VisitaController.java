package org.example.proyecturitsexplor.Controlador;

import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Servicios.VisitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/reportes/visitas")
public class VisitaController {

    @Autowired
    private VisitaService visitaService;

    // Método para registrar una nueva visita
    @PostMapping
    public ResponseEntity<?> registrarVisita(@RequestBody Visita visita) {
        visitaService.registrarVisita(visita);
        return ResponseEntity.ok("Visita registrada exitosamente");
    }

    // Método para descargar el reporte de visitas en formato Excel
    @GetMapping("/excel")
    public void descargarReporteVisitasExcel(HttpServletResponse response) throws IOException {
        List<Visita> visitas = visitaService.obtenerTodasLasVisitas(); 
        visitaService.generarReporteExcel(visitas, response);
    }

    // Método para descargar el reporte de visitas en formato PDF
    @GetMapping("/pdf")
    public void descargarReporteVisitasPDF(HttpServletResponse response) throws IOException {
        List<Visita> visitas = visitaService.obtenerTodasLasVisitas();
        visitaService.generarReportePDF(visitas, response);
    }
}
