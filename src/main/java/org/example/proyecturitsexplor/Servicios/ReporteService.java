/*Objetivo: Cuyo objetivo es generar y exportar reportes en diferentes formatos (PDF y Excel) basados en datos de usuarios 
y experiencias almacenados en la base de datos.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;

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

    /* Variables y dependencias */
    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);
    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    

    /* Método de reporte de usuarios Excel */
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

    // Método de reporte para usuarios en PDF
    public ByteArrayInputStream exportarUsuariosPDF() {
        List<Usuarios> usuarios = userRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // Configurar el documento PDF
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Tabla para organizar el logo y el título en la misma línea
            Table headerTable = new Table(new float[]{1, 2}); // Dos columnas: 1 para el logo y 1 para el título
            headerTable.setWidth(UnitValue.createPercentValue(100)); // Ocupa el 100% del ancho

            //Logo
        String logoPath = "src/main/images/simboloalcaldia.png"; // Asegúrate de cambiar esta ruta
        Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60).setHorizontalAlignment(HorizontalAlignment.LEFT);
        Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE); // Alineación vertical al centro
        headerTable.addCell(logoCell);

            // Título centrado en la segunda columna
        Paragraph title = new Paragraph("Reporte de Usuarios Registrados").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(20).setBold().setFontColor(new DeviceRgb(0, 102, 204)).setTextAlignment(TextAlignment.CENTER);
        Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE); // Alineación vertical al centro
        headerTable.addCell(titleCell);

        // Añadir la tabla con el logo y el título al documento
        document.add(headerTable);

        // Espacio entre el encabezado y la tabla de datos
        document.add(new Paragraph(" "));

            // Añadir la leyenda o descripción del reporte
        Paragraph legend = new Paragraph("Este reporte detalla los usuarios registrados en el sistema, mostrando información clave como el nombre de usuario, el correo electrónico y la fecha de registro.").setFontSize(12).setTextAlignment(TextAlignment.LEFT).setMarginTop(10).setFontColor(ColorConstants.DARK_GRAY);
        document.add(legend);

        // Espacio entre la leyenda y la tabla de datos
        document.add(new Paragraph(" "));

        // Tabla para organizar los datos de los usuarios
        Table table = new Table(new float[] { 2, 4, 4, 4 }); // Define el número de columnas y sus anchos relativos
        table.setWidth(UnitValue.createPercentValue(100));

        //Encabezado de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Nombre de Usuario").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Email").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Fecha de Registro").setBold()));
            
            //Filas de la tabla con los datos de los usuarios
        for (Usuarios usuario : usuarios) {
            table.addCell(new Paragraph(String.valueOf(usuario.getId())));
            table.addCell(new Paragraph(usuario.getNombreUsuario()));
            table.addCell(new Paragraph(usuario.getEmail()));
            table.addCell(new Paragraph(usuario.getFechaRegistro().toString()));
        }
        
        //Añadir la tabla al documento
        document.add(table);

            // Pie de página
            Paragraph footer = new Paragraph("Reporte generado el: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al generar reporte en PDF de usuarios", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }

    // Método para comentarios en PDF y Excel
    public InputStream generarReporteComentarios(String format) {
        if (format.equals("pdf")) {
            return generarReporteComentariosPDF();
        } else if (format.equals("excel")) {
            return generarReporteComentariosExcel();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }

    // Método para comentarios en Excel
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

    // Método para comentarios en PDF
    public ByteArrayInputStream generarReporteComentariosPDF() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            // Tabla para organizar el logo y el título en la misma línea
            Table headerTable = new Table(new float[]{1, 4}); // Más espacio para la columna del título
            headerTable.setWidth(UnitValue.createPercentValue(100)); // Ocupa el 100% del ancho

            // Logo en la primera columna
            String logoPath = "src/main/images/simboloalcaldia.png"; // Cambia la ruta si es necesario
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60).setHorizontalAlignment(HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT).setVerticalAlignment(VerticalAlignment.MIDDLE); // Alineación vertical al centro
            headerTable.addCell(logoCell);

            // Título centrado en la segunda columna
        Paragraph title = new Paragraph("Reporte de Comentarios").setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(20).setBold().setFontColor(new DeviceRgb(0, 102, 204)).setTextAlignment(TextAlignment.CENTER);
        Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setVerticalAlignment(VerticalAlignment.MIDDLE); // Alineación vertical al centro
        headerTable.addCell(titleCell);
            
            // Añadir la tabla con el logo y el título al documento
            document.add(headerTable);

            // Espacio entre el encabezado y la tabla de datos
            document.add(new Paragraph(" "));

            // Añadir la leyenda o descripción del reporte
            Paragraph legend = new Paragraph("Este reporte detalla los comentarios y calificaciones realizados por los usuarios sobre sus experiencias en los diferentes destinos.")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(10)
                .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);

            // Espacio entre la leyenda y la tabla de datos
            document.add(new Paragraph(" "));

            // Tabla para organizar los datos de los comentarios
            Table table = new Table(new float[]{1, 3, 2, 1, 5, 2}); // Define el número de columnas y sus anchos relativos
            table.setWidth(UnitValue.createPercentValue(100));
                // Encabezado de la tabla
            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Destino").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Usuario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Calificación").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Comentario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha").setBold()));

        // Filas de la tabla con los datos de los comentarios
        for (Experiencia experiencia : experiencias) {
            table.addCell(new Paragraph(String.valueOf(experiencia.getId())));
            table.addCell(new Paragraph(experiencia.getDestino() != null ? experiencia.getDestino().getDestinoName() : "Sin destino"));
            table.addCell(new Paragraph(experiencia.getUsuario() != null ? experiencia.getUsuario().getNombreUsuario() : "Anónimo"));
            table.addCell(new Paragraph(String.valueOf(experiencia.getCalificacion())));
            table.addCell(new Paragraph(experiencia.getComentario() != null ? experiencia.getComentario() : "Sin comentario"));
            table.addCell(new Paragraph(experiencia.getFecha() != null ? experiencia.getFecha().toString() : "Fecha desconocida"));
        }

        // Añadir la tabla al documento
        document.add(table);

        // Pie de página
        Paragraph footer = new Paragraph("Reporte generado el: " + java.time.LocalDate.now())
                .setFontSize(10)
                .setTextAlignment(TextAlignment.RIGHT)
                .setMarginTop(30)
                .setFontColor(ColorConstants.GRAY);
        document.add(footer);

        // Cerrar el documento
        document.close();
        return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
        logger.error("Error al generar el reporte de comentarios en PDF", e);
        return new ByteArrayInputStream(new byte[0]);
    }
    }
}
