/*Objetivo: El propósito de este servicio es generar un reporte en formato Excel (archivo .xlsx) que contenga información sobre las 
visitas registradas en la base de datos. El reporte se genera dinámicamente y se envía al cliente como un archivo descargable a través 
de una respuesta HTTP.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

/*Importaciones*/
import jakarta.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Repositorios.VisitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
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
import java.io.IOException;
import java.util.List;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.Event;

@Service
public class VisitaService {

    @Autowired
    private VisitaRepositorio visitaRepositorio;

    public List<Visita> obtenerTodasLasVisitas() {
        return visitaRepositorio.findAll(); // Este método recupera todas las visitas desde el repositorio
    }

    // Método para registrar una nueva visita
    public void registrarVisita(Visita visita) {
        visitaRepositorio.save(visita);
    }

    // Método para generar el reporte de los tipos de turismo más visitados
    public List<Object[]> obtenerReporteVisitasPorRuta() {
        return visitaRepositorio.contarVisitasPorRuta();
    }

    // ***Método para generar el archivo Excel***
    public void generarReporteExcel(List<Visita> visitas, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Visitas");
    
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
    
        CellStyle dataStyle = workbook.createCellStyle();
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
    
        CellStyle dateStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm"));
        dateStyle.setBorderBottom(BorderStyle.THIN);
        dateStyle.setBorderTop(BorderStyle.THIN);
        dateStyle.setBorderRight(BorderStyle.THIN);
        dateStyle.setBorderLeft(BorderStyle.THIN);
    
        // Crear título
        Row titleRow = sheet.createRow(0);
        org.apache.poi.ss.usermodel.Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Los Tipos de Turismos Más Visitados");
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setFont(titleFont);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2)); // Unir celdas para el título
    
        // Leyenda del reporte
        Row legendRow = sheet.createRow(1);
        org.apache.poi.ss.usermodel.Cell legendCell = legendRow.createCell(0);
        legendCell.setCellValue("Este reporte detalla los tipos de turismos más visitados, mostrando información clave como la ruta visitada y la fecha y hora de la visita.");
        CellStyle legendStyle = workbook.createCellStyle();
        legendStyle.setWrapText(true); // Permitir ajuste de texto
        legendCell.setCellStyle(legendStyle);
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 2)); // Unir celdas para la leyenda
    
        // Fila en blanco para dar espacio entre la leyenda y la tabla
        Row emptyRow = sheet.createRow(2); // Fila vacía para espacio
        emptyRow.createCell(0).setCellValue(""); // Solo se necesita para crear la fila
    
        // Crear encabezados
        Row headerRow = sheet.createRow(3);
        String[] columnHeaders = { "ID", "Ruta Visitada", "Fecha y Hora de Visita" };
        for (int i = 0; i < columnHeaders.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(headerStyle);
        }
    
        // Rellenar filas con los datos de las visitas
        int rowNum = 4;
        for (Visita visita : visitas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(visita.getId());
            row.createCell(1).setCellValue(visita.getRutaVisitada());
    
            org.apache.poi.ss.usermodel.Cell fechaHoraCell = row.createCell(2);
            fechaHoraCell.setCellValue(visita.getFechaHoraVisita());
            fechaHoraCell.setCellStyle(dateStyle);
        }
    
        // Autoajustar el tamaño de las columnas
        for (int i = 0; i < columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }
    
        // Configuración de la respuesta HTTP para descargar el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=visitas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }    

    public void generarReportePDF(List<Visita> visitas, HttpServletResponse response) throws IOException {
        // Configuración de la respuesta HTTP para descargar el archivo PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=visitas.pdf");

        // Crear un documento PDF usando iText
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Añadir el manejador de eventos para la paginación
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PaginationHandler(document));

        // Tabla para organizar el logo y el título en la misma línea
        Table headerTable = new Table(new float[] { 1, 2 }); // Dos columnas: 1 para el logo y 1 para el título
        headerTable.setWidth(UnitValue.createPercentValue(100)); // Ocupa el 100% del ancho

        // Logo
        String logoPath = "src/main/images/escudo_alcaldia.jpeg"; // Asegúrate de cambiar esta ruta
        Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(60)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(logoCell);

        // Título centrado en la segunda columna
        Paragraph title = new Paragraph("Reporte de los tipos de turismos más visitados")
                .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                .setFontSize(20)
                .setBold()
                .setFontColor(new DeviceRgb(0, 102, 204))
                .setTextAlignment(TextAlignment.CENTER);
        Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(titleCell);

        // Añadir la tabla con el logo y el título al documento
        document.add(headerTable);

        // Espacio entre el encabezado y la tabla de datos
        document.add(new Paragraph(" "));

        // Añadir la leyenda o descripción del reporte
        Paragraph legend = new Paragraph(
                "Este reporte detalla los tipos de turismos más visitados registrados en el sistema, mostrando información clave como la ruta visitada y la fecha de la visita.")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.LEFT)
                .setMarginTop(10)
                .setFontColor(ColorConstants.DARK_GRAY);
        document.add(legend);

        // Espacio entre la leyenda y la tabla de datos
        document.add(new Paragraph(" "));

        // Crear tabla con encabezados
        float[] columnWidths = { 1, 5, 5 };
        Table table = new Table(columnWidths);
        table.setWidth(UnitValue.createPercentValue(100));

        // Encabezado de la tabla
        table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Ruta Visitada").setBold()));
        table.addHeaderCell(new Cell().add(new Paragraph("Fecha y Hora de Visita").setBold()));

        // Rellenar tabla con los datos de las visitas
        for (Visita visita : visitas) {
            table.addCell(new Paragraph(String.valueOf(visita.getId())));
            table.addCell(new Paragraph(visita.getRutaVisitada()));
            table.addCell(new Paragraph(visita.getFechaHoraVisita().toString()));
        }

        // Añadir la tabla de datos al documento
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
    }

    // Clase para manejar la numeración de páginas
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

            // Texto con la numeración de páginas
            String pageStr = String.format("Página %d de %d", pageNumber, totalPages);
            Paragraph pageParagraph = new Paragraph(pageStr)
                    .setFontSize(10)
                    .setTextAlignment(TextAlignment.RIGHT);

            // Definir posición para la numeración de páginas
            float x = page.getPageSize().getWidth() - document.getRightMargin();
            float y = document.getBottomMargin() / 2;

            // Añadir la numeración de páginas en el pie de página
            Canvas canvas = new Canvas(new PdfCanvas(page), page.getPageSize());
            canvas.showTextAligned(pageParagraph, x, y, TextAlignment.RIGHT);
        }
    }
}