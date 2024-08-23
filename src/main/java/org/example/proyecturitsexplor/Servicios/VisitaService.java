/*Objetivo: El propósito de este servicio es generar un reporte en formato Excel (archivo .xlsx) que contenga información sobre las 
visitas registradas en la base de datos. El reporte se genera dinámicamente y se envía al cliente como un archivo descargable a través 
de una respuesta HTTP.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
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

@Service
public class VisitaService {

    /*Dependencias*/
    @Autowired
    private VisitaRepositorio visitaRepositorio;

    /*Método*/
    public void generarReporteExcel(List<Visita> visitas, HttpServletResponse response) throws IOException, IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Visitas");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("ID");
        headerRow.createCell(1).setCellValue("Ruta Visitada");
        headerRow.createCell(2).setCellValue("Fecha y Hora de Visita");

        int rowNum = 1;
        for (Visita visita : visitas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(visita.getId());
            row.createCell(1).setCellValue(visita.getRutaVisitada());
            row.createCell(2).setCellValue(visita.getFechaHoraVisita().toString());
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=visitas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
