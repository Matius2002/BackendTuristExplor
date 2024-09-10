/*Objetivo: Cuyo objetivo es generar y exportar reportes en diferentes formatos (PDF y Excel) basados en datos de usuarios 
y experiencias almacenados en la base de datos.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
/*Importaciones*/
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.proyecturitsexplor.Entidades.Experiencia;
import org.example.proyecturitsexplor.Entidades.Usuarios;
import org.example.proyecturitsexplor.Repositorios.ExperienciaRepositorio;
import org.example.proyecturitsexplor.Repositorios.UserRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*Servicio y clase*/
@Service
public class ReporteService {

    /*Variables y dependencias*/
    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);
    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    /*Método de reporte de usuarios Excel*/
    public void exportarUsuariosExcel(HttpServletResponse response) throws IOException {
        List<Usuarios> usuarios = userRepositorio.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("ID");
        header.createCell(1).setCellValue("Nombre de Usuario");
        header.createCell(2).setCellValue("Email");
        header.createCell(3).setCellValue("Fecha de Registro");

        int rowIdx = 1;
        for (Usuarios usuario : usuarios) {
            Row row = sheet.createRow(rowIdx++);
            row.createCell(0).setCellValue(usuario.getId());
            row.createCell(1).setCellValue(usuario.getNombreUsuario());
            row.createCell(2).setCellValue(usuario.getEmail());
            row.createCell(3).setCellValue(usuario.getFechaRegistro().toString());
        }

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            logger.error("Error al exportar usuarios a Excel", e);
            throw e;
        }
    }

    //Método de reporte para usuarios en PDF
    public ByteArrayInputStream exportarUsuariosPDF() {
        List<Usuarios> usuarios = userRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            //Configurar el documento PDF
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            //Encabezado personalizado
            Paragraph title = new Paragraph("Reporte de usuarios")
                .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                .setFontSize(20)
                .setTextAlignment(TextAlignment.CENTER)
                .setBold()
                .setFontColor(new DeviceRgb(0,102,204));
            document.add(title);



            for (Usuarios usuario : usuarios) {
                document.add(new Paragraph("ID: " + usuario.getId()));
                document.add(new Paragraph("Nombre de Usuario: " + usuario.getNombreUsuario()));
                document.add(new Paragraph("Email: " + usuario.getEmail()));
                document.add(new Paragraph("Fecha de Registro: " + usuario.getFechaRegistro().toString()));
                document.add(new Paragraph(" "));
            }

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al exportar usuarios a PDF", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    //Método para comentarios en PDF y Excel
    public InputStream generarReporteComentarios(String format) {
        if (format.equals("pdf")) {
            return generarReporteComentariosPDF();
        } else if (format.equals("excel")) {
            return generarReporteComentariosExcel();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }

    //Método para comentarios en Excel
    public ByteArrayInputStream generarReporteComentariosExcel() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Comentarios");

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Destino");
            header.createCell(2).setCellValue("Usuario");
            header.createCell(3).setCellValue("Calificación");
            header.createCell(4).setCellValue("Comentario");
            header.createCell(5).setCellValue("Fecha");

            int rowIdx = 1;
            for (Experiencia experiencia : experiencias) {
                Row row = sheet.createRow(rowIdx++);
                row.createCell(0).setCellValue(experiencia.getId());
                row.createCell(1).setCellValue(experiencia.getDestino().getDestinoName());
                row.createCell(2).setCellValue(experiencia.getUsuario().getNombreUsuario());
                row.createCell(3).setCellValue(experiencia.getCalificacion());
                row.createCell(4).setCellValue(experiencia.getComentario());
                row.createCell(5).setCellValue(experiencia.getFecha().toString());
            }

            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en Excel", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    //Método para comentarios en PDF
    public ByteArrayInputStream generarReporteComentariosPDF() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            document.add(new Paragraph("Reporte de Comentarios"));

            for (Experiencia experiencia : experiencias) {
                document.add(new Paragraph("ID: " + experiencia.getId()));
                document.add(new Paragraph("Destino: " + experiencia.getDestino().getDestinoName())); // Asumiendo que Destino tiene un campo 'nombre'
                document.add(new Paragraph("Usuario: " + experiencia.getUsuario().getNombreUsuario())); // Asumiendo que Usuario tiene un campo 'nombreUsuario'
                document.add(new Paragraph("Calificación: " + experiencia.getCalificacion()));
                document.add(new Paragraph("Comentario: " + experiencia.getComentario()));
                document.add(new Paragraph("Fecha: " + experiencia.getFecha().toString()));
                document.add(new Paragraph(" "));
            }

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en PDF", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }
}
