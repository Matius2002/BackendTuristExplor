/*Objetivo: El propósito de este servicio es generar un reporte en formato Excel (archivo .xlsx) que contenga información sobre las 
visitas registradas en la base de datos. El reporte se genera dinámicamente y se envía al cliente como un archivo descargable a través 
de una respuesta HTTP.*/
package org.example.proyecturitsexplor.Servicios; /*Paquete*/

import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
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
import org.example.proyecturitsexplor.Entidades.Visita;
import org.example.proyecturitsexplor.Repositorios.VisitaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.commons.utils.Base64.InputStream;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.Rectangle;
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
import java.io.FileInputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.poi.ss.usermodel.HorizontalAlignment;

@Service
public class VisitaService {

    private static final Logger logger = LoggerFactory.getLogger(VisitaService.class);

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
        Sheet sheet = workbook.createSheet("Reporte de Visitas");

        // Estilos personalizados
        CellStyle headerStyle = crearEstiloHeader(workbook);
        CellStyle dataStyle = crearEstiloData(workbook);
        CellStyle dateStyle = crearEstiloFecha(workbook);

        // Crear encabezados
        Row headerRow = sheet.createRow(5);
        String[] columnHeaders = { "ID", "Ruta Visitada", "Fecha y Hora de Visita" };
        for (int i = 0; i < columnHeaders.length; i++) {
            org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
            cell.setCellStyle(headerStyle);
        }

        // Rellenar filas con los datos de las visitas
        int rowNum = 6;
        for (Visita visita : visitas) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(visita.getId());
            row.createCell(1).setCellValue(visita.getTipoTurismo());

            org.apache.poi.ss.usermodel.Cell fechaHoraCell = row.createCell(2);
            fechaHoraCell.setCellValue(visita.getFechaHoraVisita());
            fechaHoraCell.setCellStyle(dateStyle);

            // Aplicar estilo a cada fila
            for (int i = 0; i < columnHeaders.length; i++) {
                row.getCell(i).setCellStyle(dataStyle);
            }
        }

        // Autoajustar el tamaño de las columnas
        for (int i = 0; i < columnHeaders.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Configuración de la respuesta HTTP para descargar el archivo Excel
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_visitas.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private CellStyle crearEstiloHeader(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle crearEstiloData(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        return style;
    }

    private CellStyle crearEstiloFecha(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        style.setDataFormat(creationHelper.createDataFormat().getFormat("dd-MM-yyyy HH:mm"));
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setAlignment(org.apache.poi.ss.usermodel.HorizontalAlignment.CENTER);
        return style;
    }

    public void generarReportePDF(List<Visita> visitas, HttpServletResponse response) throws IOException {
        // Configuración de la respuesta HTTP para descargar el archivo PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=visitas.pdf");

        // Crear un documento PDF usando iText
        PdfWriter pdfWriter = new PdfWriter(response.getOutputStream());
        PdfDocument pdfDoc = new PdfDocument(pdfWriter);
        Document document = new Document(pdfDoc);

        // Añadir el manejador de eventos para la paginación en la parte superior
        // derecha
        pdfDoc.addEventHandler(PdfDocumentEvent.END_PAGE, new PaginationHandler(document));

        // Tabla para organizar el logo y el título
        Table headerTable = new Table(new float[] { 1, 4 });
        headerTable.setWidth(UnitValue.createPercentValue(100)); // Ocupa el 100% del ancho

        // Logo
        String logoPath = "src/main/images/escudo_alcaldia.jpeg"; // Asegúrate de ajustar la ruta del logo
        Image logo = new Image(ImageDataFactory.create(logoPath)).setWidth(80)
                .setHorizontalAlignment(HorizontalAlignment.LEFT);
        Cell logoCell = new Cell().add(logo).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.LEFT)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        headerTable.addCell(logoCell);

        // Título en una tabla separada
        Table titleTable = new Table(1);
        titleTable.setWidth(UnitValue.createPercentValue(100));
        Paragraph title = new Paragraph("Reporte de los tipos de turismos más visitados")
                .setFont(PdfFontFactory.createFont("Helvetica-Bold"))
                .setFontSize(20)
                .setBold()
                .setFontColor(new DeviceRgb(0, 102, 204))
                .setTextAlignment(TextAlignment.CENTER);
        Cell titleCell = new Cell().add(title).setBorder(Border.NO_BORDER)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE);
        titleTable.addCell(titleCell);

        // Añadir la tabla del encabezado al documento
        document.add(headerTable);
        document.add(titleTable);

        // Espacio entre el encabezado y la leyenda
        document.add(new Paragraph(" "));

        // Añadir la leyenda o descripción del reporte
        Paragraph legend = new Paragraph(
                "Este reporte detalla los tipos de turismos más visitados registrados en el sistema, mostrando información clave como la ruta visitada y la fecha de la visita.")
                .setFontSize(12)
                .setTextAlignment(TextAlignment.JUSTIFIED)
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
        table.addHeaderCell(new Cell().add(new Paragraph("ID").setBold()).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(
                new Cell().add(new Paragraph("Ruta Visitada").setBold()).setTextAlignment(TextAlignment.CENTER));
        table.addHeaderCell(new Cell().add(new Paragraph("Fecha y Hora de Visita").setBold())
                .setTextAlignment(TextAlignment.CENTER));

        // Rellenar tabla con los datos de las visitas
        for (Visita visita : visitas) {
            table.addCell(new Cell().add(new Paragraph(String.valueOf(visita.getId())))
                    .setTextAlignment(TextAlignment.CENTER));
            table.addCell(
                    new Cell().add(new Paragraph(visita.getTipoTurismo())).setTextAlignment(TextAlignment.CENTER));
            table.addCell(new Cell().add(new Paragraph(visita.getFechaHoraVisita().toString()))
                    .setTextAlignment(TextAlignment.CENTER));
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

    public class PaginationHandler implements IEventHandler {
        private final Document document;

        public PaginationHandler(Document document) {
            this.document = document;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            int pageNumber = docEvent.getDocument().getPageNumber(page);

            // Crear un PdfCanvas para la página
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamAfter(), page.getResources(),
                    docEvent.getDocument());

            // Obtener las dimensiones de la página
            Rectangle pageSize = page.getPageSize();

            // -------- Agregar número de página en la parte superior derecha --------
            float xTop = pageSize.getWidth() - 50; // Alineado a la derecha (50 px desde el borde)
            float yTop = pageSize.getTop() - 20; // Cerca de la parte superior (20 px desde el borde superior)

            pdfCanvas.beginText()
                    .setFontAndSize(document.getPdfDocument().getDefaultFont(), 10)
                    .moveText(xTop, yTop)
                    .showText("Página " + pageNumber)
                    .endText();

            // -------- Agregar el pie de página en la parte inferior izquierda --------
            float xBottom = pageSize.getLeft() + 30; // Alineado a la izquierda (30 px desde el borde izquierdo)
            float yBottom = pageSize.getBottom() + 20; // Alineado en la parte inferior (20 px desde el borde inferior)

            pdfCanvas.beginText()
                    .setFontAndSize(document.getPdfDocument().getDefaultFont(), 10)
                    .moveText(xBottom, yBottom) // Ajustar para alinear a la izquierda
                    .showText("Instituto Municipal de Turismo Cultura y Fomento")
                    .endText();

            // Liberar recursos
            pdfCanvas.release();
        }
    }
}