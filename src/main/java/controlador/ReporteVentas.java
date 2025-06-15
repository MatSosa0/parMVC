package controlador;

import modeloDAO.VentaDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReporteVentas extends HttpServlet {

    VentaDAO dao = new VentaDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Si aún no se seleccionó rango de fechas, mostramos formulario vacío
        request.getRequestDispatcher("reporteVentas.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaInicioStr = request.getParameter("fecha_inicio");
        String fechaFinStr = request.getParameter("fecha_fin");
        String formato = request.getParameter("formato");

        if (fechaInicioStr == null || fechaFinStr == null) {
            request.setAttribute("error", "Debe seleccionar un rango de fechas.");
            request.getRequestDispatcher("reporteVentas.jsp").forward(request, response);
            return;
        }

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        List<Map<String, Object>> ventas = dao.getVentasPorFecha(fechaInicio, fechaFin);

        if ("pdf".equalsIgnoreCase(formato)) {
            // Exportar a PDF
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_ventas.pdf");

            try {
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

                document.open();
                document.add(new com.lowagie.text.Paragraph("Reporte de Ventas"));
                document.add(new com.lowagie.text.Paragraph("Rango: " + fechaInicio + " al " + fechaFin));
                document.add(new com.lowagie.text.Paragraph(" "));

                com.lowagie.text.pdf.PdfPTable tabla = new com.lowagie.text.pdf.PdfPTable(6);
                tabla.setWidthPercentage(100);
                String[] headers = { "ID", "Fecha", "Factura", "Cliente", "Forma de Pago", "Total" };
                for (String h : headers) {
                    tabla.addCell(h);
                }

                for (Map<String, Object> fila : ventas) {
                    tabla.addCell(fila.get("id").toString());
                    tabla.addCell(fila.get("fecha").toString());
                    tabla.addCell(fila.get("numero_factura").toString());
                    tabla.addCell(fila.get("cliente").toString());
                    tabla.addCell(fila.get("forma_pago").toString());
                    tabla.addCell("Gs. " + fila.get("total_factura").toString());
                }

                document.add(tabla);
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("excel".equalsIgnoreCase(formato)) {
            // Exportar a Excel
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_ventas.xlsx");

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Ventas");

                String[] encabezados = { "ID", "Fecha", "Factura", "Cliente", "Forma de Pago", "Total" };
                org.apache.poi.ss.usermodel.Row filaHeader = hoja.createRow(0);
                for (int i = 0; i < encabezados.length; i++) {
                    filaHeader.createCell(i).setCellValue(encabezados[i]);
                }

                int filaNum = 1;
                for (Map<String, Object> fila : ventas) {
                    org.apache.poi.ss.usermodel.Row row = hoja.createRow(filaNum++);
                    row.createCell(0).setCellValue(fila.get("id").toString());
                    row.createCell(1).setCellValue(fila.get("fecha").toString());
                    row.createCell(2).setCellValue(fila.get("numero_factura").toString());
                    row.createCell(3).setCellValue(fila.get("cliente").toString());
                    row.createCell(4).setCellValue(fila.get("forma_pago").toString());
                    row.createCell(5).setCellValue("Gs. " + fila.get("total_factura").toString());
                }

                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            // Mostrar en pantalla
            request.setAttribute("ventas", ventas);
            request.setAttribute("fecha_inicio", fechaInicioStr);
            request.setAttribute("fecha_fin", fechaFinStr);
            request.getRequestDispatcher("reporteVentas.jsp").forward(request, response);
        }
    }

}
