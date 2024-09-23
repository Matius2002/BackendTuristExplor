package org.example.proyecturitsexplor.Controlador;

import jakarta.servlet.http.HttpServletResponse;
import org.example.proyecturitsexplor.Servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/reportes/usuarios/excel")
    public void generarReporteUsuariosExcel(HttpServletResponse response) throws IOException {
        try {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=usuarios.xlsx";
            response.setHeader(headerKey, headerValue);

            reporteService.exportarUsuariosExcel(response);
        } catch (IOException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Error generando reporte de usuarios en Excel");
        }
    }

    @GetMapping("/reportes/usuarios/pdf")
    public ResponseEntity<byte[]> generarReporteUsuariosPDF() {
        ByteArrayInputStream bis = reporteService.exportarUsuariosPDF();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=Reporte_Usuarios.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(bis.readAllBytes());
    }

    @GetMapping("/reportes/comentarios/{format}")
    public ResponseEntity<Resource> generarReporteComentarios(@PathVariable String format) {
        String fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
        String hora = new SimpleDateFormat("HH-mm-ss").format(new Date());
        String filename = "Reporte_de_Comentarios_" + fecha + "_" + hora + "." + format;

        InputStreamResource file;

        if (format.equals("pdf")) {
            file = new InputStreamResource(reporteService.generarReporteComentariosPDF());
        } else if (format.equals("excel")) {
            file = new InputStreamResource(reporteService.generarReporteComentariosExcel());
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }

        MediaType mediaType = format.equals("pdf")
                ? MediaType.APPLICATION_PDF
                : MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

        return ResponseEntity.ok()
                .contentType(mediaType)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(file);
    }
}
