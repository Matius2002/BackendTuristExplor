package org.example.proyecturitsexplor.Servicios;

import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.parser.clipper.Paths;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.formula.functions.Index;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.ltgfmt.TestCase.Files;
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
import java.io.FileInputStream;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
//import com.itextpdf.layout.properties.HorizontalAlignment;  // iTextPDF

@Service
public class ReporteService {

    private static final Logger logger = LoggerFactory.getLogger(ReporteService.class);
    @Autowired
    private UserRepositorio userRepositorio;
    @Autowired
    private ExperienciaRepositorio experienciaRepositorio;

    // Estilo del reporte de usuarios en excel
    public void exportarUsuariosExcel(HttpServletResponse response) throws IOException {
        List<Usuarios> usuarios = userRepositorio.findAll();
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Usuarios");

        Drawing<?> drawing = sheet.createDrawingPatriarch();
        ClientAnchor anchor = workbook.getCreationHelper().createClientAnchor();
        anchor.setCol1(2);
        anchor.setRow1(0);
        anchor.setCol2(3);
        anchor.setRow2(2);

        CellStyle headerStyle = workbook.createCellStyle();
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 12);
        headerStyle.setFont(headerFont);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);

        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);

        Row titleRow = sheet.createRow(3);
        org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Reporte de Usuarios Registrados");
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 0, 3));

        Row legendRow = sheet.createRow(4);
        org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
        legendCell.setCellValue(
                "Este reporte detalla los usuarios registrados en el sistema, mostrando información clave como el nombre de usuario, el correo electrónico y la fecha de registro.");
        CellStyle legendStyle = workbook.createCellStyle();
        legendStyle.setWrapText(true);
        legendCell.setCellStyle(legendStyle);
        sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 3));

        Row emptyRow = sheet.createRow(5);
        emptyRow.createCell(0).setCellValue("");

        Row header = sheet.createRow(6);
        String[] columnHeaders = { "ID", "Nombre de Usuario", "Email", "Fecha de Registro" };
        for (int i = 0; i < columnHeaders.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        int rowIdx = 7;
        for (Usuarios usuario : usuarios) {
            Row row = sheet.createRow(rowIdx++);

            org.apache.poi.ss.usermodel.Cell idCell = row.createCell(0);
            idCell.setCellValue(usuario.getId());
            idCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell nameCell = row.createCell(1);
            nameCell.setCellValue(usuario.getNombreUsuario());
            nameCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell emailCell = row.createCell(2);
            emailCell.setCellValue(usuario.getEmail());
            emailCell.setCellStyle(dataStyle);

            org.apache.poi.ss.usermodel.Cell dateCell = row.createCell(3);
            if (usuario.getFechaRegistro() != null) {
                dateCell.setCellValue(usuario.getFechaRegistro());
                dateCell.setCellStyle(dateStyle);
            }
        }

        for (int i = 0; i < columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.xlsx");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            workbook.write(outputStream);
        } finally {
            workbook.close();
        }
    }

    public ByteArrayInputStream exportarUsuariosPDF() {
        List<Usuarios> usuarios = userRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE,
                    (com.itextpdf.kernel.events.IEventHandler) new PaginationHandler(document));

            Table headerTable = new Table(new float[] { 1, 2 });
            headerTable.setWidth(UnitValue.createPercentValue(100));

            String logoPath = "src/main/images/escudo_alcaldia.jpeg";
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60)
                    .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(logoCell);

            Paragraph title = new Paragraph("Reporte de Usuarios Registrados")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                    .setFontSize(20)
                    .setBold()
                    .setFontColor(new DeviceRgb(0, 102, 204))
                    .setTextAlignment(TextAlignment.CENTER);
            Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);

            headerTable.addCell(titleCell);
            document.add(headerTable);
            document.add(new Paragraph(" "));

            Paragraph legend = new Paragraph(
                    "Este reporte detalla los usuarios registrados en el sistema, mostrando información clave como el nombre de usuario, el correo electrónico y la fecha de registro.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setMarginTop(10)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);

            document.add(new Paragraph(" "));

            Table table = new Table(new float[] { 2, 4, 4, 4 });
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Nombre de Usuario").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Email").setBold()));
            table.addHeaderCell(new Cell().add(new Paragraph("Fecha de Registro").setBold()));

            for (Usuarios usuario : usuarios) {
                table.addCell(new Paragraph(String.valueOf(usuario.getId())));
                table.addCell(new Paragraph(usuario.getNombreUsuario()));
                table.addCell(new Paragraph(usuario.getEmail()));
                table.addCell(new Paragraph(usuario.getFechaRegistro().toString()));
            }

            document.add(table);

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

    class PaginationHandler implements IEventHandler {
        protected Document document;

        public PaginationHandler(Document document) {
            this.document = document;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfPage page = docEvent.getPage();

            int pageNumber = pdfDoc.getPageNumber(page);
            int totalPages = pdfDoc.getNumberOfPages();

            String pageStr = String.format("Página %d de %d", pageNumber, totalPages);
            Paragraph pageParagraph = new Paragraph(pageStr)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT);

            float xPage = page.getPageSize().getWidth() - document.getRightMargin();
            float yPage = page.getPageSize().getHeight() - document.getTopMargin() + 10;

            Canvas canvas = new Canvas(new PdfCanvas(page), page.getPageSize());
            canvas.showTextAligned(pageParagraph, xPage, yPage, TextAlignment.RIGHT);

            String footerText = "Instituto Municipal de Turismo Cultura y Fomento";
            Paragraph footerParagraph = new Paragraph(footerText)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.LEFT);

            float xFooter = document.getLeftMargin();
            float yFooter = document.getBottomMargin() / 2;

            canvas.showTextAligned(footerParagraph, xFooter, yFooter, TextAlignment.LEFT);
        }
    }

    public InputStream generarReporteComentarios(String format) {
        if (format.equals("pdf")) {
            return generarReporteComentariosPDF();
        } else if (format.equals("excel")) {
            return generarReporteComentariosExcel();
        } else {
            throw new IllegalArgumentException("Formato no soportado: " + format);
        }
    }

    public ByteArrayInputStream generarReporteComentariosExcel() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();
    
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Reporte de Comentarios");
    
            // Agregar logo
            int logoColumnStart = 7; // Columna donde comenzará el logo
            int logoRowStart = 0; // Fila donde comenzará el logo
            int logoColumnEnd = 9; // Columna donde terminará el logo
            int logoRowEnd = 7; // Fila donde terminará el logo
    
            try (InputStream inputStreamLogo = new FileInputStream("src/main/images/escudo_alcaldia.jpeg")) {
                byte[] logoBytes = IOUtils.toByteArray(inputStreamLogo);
                int logoIndex = workbook.addPicture(logoBytes, Workbook.PICTURE_TYPE_JPEG);
    
                Drawing<?> drawing = sheet.createDrawingPatriarch();
                CreationHelper helper = workbook.getCreationHelper();
                ClientAnchor anchor = helper.createClientAnchor();
    
                // Posicionar la imagen
                anchor.setCol1(logoColumnStart);
                anchor.setRow1(logoRowStart);
                anchor.setCol2(logoColumnEnd);
                anchor.setRow2(logoRowEnd);
    
                Picture pict = drawing.createPicture(anchor, logoIndex);
                pict.resize(1); // Ajusta la escala de la imagen (modifica el valor según sea necesario)
            } catch (IOException e) {
                logger.error("Error al agregar el logo al Excel", e);
            }
    
            // Estilos personalizados
            CellStyle headerStyle = workbook.createCellStyle();
            Font headerFont = workbook.createFont();
            headerFont.setBold(true);
            headerFont.setFontHeightInPoints((short) 12);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headerStyle.setBorderBottom(BorderStyle.THIN);
            headerStyle.setBorderTop(BorderStyle.THIN);
            headerStyle.setBorderRight(BorderStyle.THIN);
            headerStyle.setBorderLeft(BorderStyle.THIN);
            headerStyle.setAlignment(HorizontalAlignment.CENTER);
    
            CellStyle dataStyle = workbook.createCellStyle();
            dataStyle.setBorderBottom(BorderStyle.THIN);
            dataStyle.setBorderTop(BorderStyle.THIN);
            dataStyle.setBorderRight(BorderStyle.THIN);
            dataStyle.setBorderLeft(BorderStyle.THIN);
            dataStyle.setAlignment(HorizontalAlignment.CENTER);
    
            CellStyle dateStyle = workbook.createCellStyle();
            CreationHelper creationHelper = workbook.getCreationHelper();
            dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy"));
            dateStyle.setBorderBottom(BorderStyle.THIN);
            dateStyle.setBorderTop(BorderStyle.THIN);
            dateStyle.setBorderRight(BorderStyle.THIN);
            dateStyle.setBorderLeft(BorderStyle.THIN);
            dateStyle.setAlignment(HorizontalAlignment.CENTER);
    
            // Título del reporte
            Row titleRow = sheet.createRow(0);
            org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
            titleCell.setCellValue("Reporte de Comentarios de Experiencias");
            Font titleFont = workbook.createFont();
            titleFont.setBold(true);
            titleFont.setFontHeightInPoints((short) 16);
            CellStyle titleStyle = workbook.createCellStyle();
            titleStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            titleStyle.setFont(titleFont);
            titleStyle.setAlignment(HorizontalAlignment.CENTER);
            titleCell.setCellStyle(titleStyle);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5)); // El título se extiende de la columna 2 a la 5
    
            // Leyenda explicativa
            Row legendRow = sheet.createRow(2);
            org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
            legendCell.setCellValue(
                    "Reporte de comentarios registrados en el sistema (nueva-experiencia). Con Destino, Usuario, Calificación, Comentario y Fecha. Esto con el fin de poder saber que es lo que piensan los turistas y locales que visitan nuestras zonas que hacen parte del turismo del municipio de Girardot, Cundinamarca.");
            CellStyle legendStyle = workbook.createCellStyle();
            legendStyle.setWrapText(true);
            legendCell.setCellStyle(legendStyle);
            sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
    
            // Encabezados de la tabla
            Row header = sheet.createRow(4);
            String[] columnHeaders = { "ID", "Destino", "Usuario", "Calificación", "Comentario", "Fecha" };
            for (int i = 0; i < columnHeaders.length; i++) {
                org.apache.poi.ss.usermodel.Cell cell = header.createCell(i);
                cell.setCellValue(columnHeaders[i]);
                cell.setCellStyle(headerStyle);
            }
    
            // Rellenar los datos
            int rowIdx = 5;
            for (Experiencia experiencia : experiencias) {
                Row row = sheet.createRow(rowIdx++);
    
                org.apache.poi.ss.usermodel.Cell idCell = row.createCell(0);
                idCell.setCellValue(experiencia.getId());
                idCell.setCellStyle(dataStyle);
    
                org.apache.poi.ss.usermodel.Cell destinoCell = row.createCell(1);
                destinoCell.setCellValue(experiencia.getDestino().getDestinoName());
                destinoCell.setCellStyle(dataStyle);
    
                org.apache.poi.ss.usermodel.Cell usuarioCell = row.createCell(2);
                usuarioCell.setCellValue(experiencia.getUsuario().getNombreUsuario());
                usuarioCell.setCellStyle(dataStyle);
    
                org.apache.poi.ss.usermodel.Cell calificacionCell = row.createCell(3);
                calificacionCell.setCellValue(experiencia.getCalificacion());
                calificacionCell.setCellStyle(dataStyle);
    
                org.apache.poi.ss.usermodel.Cell comentarioCell = row.createCell(4);
                comentarioCell.setCellValue(experiencia.getComentario());
                comentarioCell.setCellStyle(dataStyle);
    
                org.apache.poi.ss.usermodel.Cell fechaCell = row.createCell(5);
                if (experiencia.getFecha() != null) {
                    fechaCell.setCellValue(experiencia.getFecha());
                    fechaCell.setCellStyle(dateStyle);
                }
            }
    
            // Ajustar el tamaño de las columnas
            for (int i = 0; i < columnHeaders.length; i++) {
                sheet.autoSizeColumn(i);
            }
    
            // Escritura en el ByteArrayOutputStream
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                workbook.write(out);
                return new ByteArrayInputStream(out.toByteArray());
            }
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en Excel", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }    

    public ByteArrayInputStream generarReporteComentariosPDF() {
        List<Experiencia> experiencias = experienciaRepositorio.findAll();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);

            PaginationHandler paginationHandler = new PaginationHandler(document);
            pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PaginationHandler(document));

            Table headerTable = new Table(new float[] { 1, 4 });
            headerTable.setWidth(UnitValue.createPercentValue(100));

            String logoPath = "src/main/images/escudo_alcaldia.jpeg";
            Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(80)
                    .setHorizontalAlignment(com.itextpdf.layout.properties.HorizontalAlignment.LEFT);
            Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.LEFT)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            headerTable.addCell(logoCell);
            document.add(headerTable);

            Table titleTable = new Table(1);
            titleTable.setWidth(UnitValue.createPercentValue(100));

            Paragraph title = new Paragraph("Reporte de Comentarios")
                    .setFont(PdfFontFactory.createFont("Helvetica-Bold")).setFontSize(20).setBold()
                    .setFontColor(new DeviceRgb(0, 102, 204)).setTextAlignment(TextAlignment.CENTER);
            Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setVerticalAlignment(VerticalAlignment.MIDDLE);
            titleTable.addCell(titleCell);
            document.add(titleTable);
            document.add(new Paragraph(" "));

            Paragraph legend = new Paragraph(
                    "Reporte de comentarios registrados en el sistema (nueva-experiencia). Con Destino, Usuario, Calificación, Comentario y Fecha. Esto con el fin de poder saber que es lo que piensan los turistas y locales que visitan nuestras zonas que hacen parte del turismo del municipio de Girardot, Cundinamarca.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.JUSTIFIED)
                    .setMarginTop(10)
                    .setFontColor(ColorConstants.DARK_GRAY);
            document.add(legend);
            document.add(new Paragraph(" "));

            Table table = new Table(new float[] { 1, 3, 2, 1, 5, 2 });
            table.setWidth(UnitValue.createPercentValue(100));

            table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Destino").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Usuario").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Calificación").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Comentario").setBold()).setTextAlignment(TextAlignment.CENTER));
            table.addHeaderCell(
                    new Cell().add(new Paragraph("Fecha").setBold()).setTextAlignment(TextAlignment.CENTER));

            for (Experiencia experiencia : experiencias) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(experiencia.getId())))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(
                        experiencia.getDestino() != null ? experiencia.getDestino().getDestinoName() : "Sin destino"))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(
                        experiencia.getUsuario() != null ? experiencia.getUsuario().getNombreUsuario() : "Anónimo"))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(experiencia.getCalificacion())))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(
                        experiencia.getComentario() != null ? experiencia.getComentario() : "Sin comentario"))
                        .setTextAlignment(TextAlignment.CENTER));
                table.addCell(new Cell().add(new Paragraph(
                        experiencia.getFecha() != null ? experiencia.getFecha().toString() : "Fecha desconocida"))
                        .setTextAlignment(TextAlignment.CENTER));
            }

            document.add(table);

            Paragraph footer = new Paragraph("Reporte generado el: " + java.time.LocalDate.now())
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT)
                    .setMarginTop(30)
                    .setFontColor(ColorConstants.GRAY);
            document.add(footer);

            document.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            logger.error("Error al generar el reporte de comentarios en PDF", e);
            return new ByteArrayInputStream(new byte[0]);
        }
    }
}