/*Objetivo: Este servicio está diseñado para manejar operaciones relacionadas con la entidad Visita, 
como guardar y obtener todas las visitas.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import com.itextpdf.layout.element.Paragraph;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Repositorios.VisitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Service;
import javax.swing.text.Document;

/*Servicio y clase*/
@Service
public class ReporteVisitas {

    /*Dependencia y variable*/
    @Autowired
    private VisitaRepositorio visitaRepositorio;

    // Método para guardar una visita
    public Visita guardarVisita(Visita visita) {
        return visitaRepositorio.save(visita);
    }

    // Método para obtener todas las visitas
    public List<Visita> obtenerTodasLasVisitas() {
        return visitaRepositorio.findAll();
    }


}
