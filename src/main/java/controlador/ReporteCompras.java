/**
 *
 * @author Matias
 */
package controlador;

import modeloDAO.CompraDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class ReporteCompras extends HttpServlet {

    CompraDAO dao = new CompraDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Mostrar formulario vacío
        
        request.setAttribute("contenido","reporteCompras.jsp");
        request.getRequestDispatcher("template.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String fechaInicioStr = request.getParameter("fecha_inicio");
        String fechaFinStr = request.getParameter("fecha_fin");
        String formato = request.getParameter("formato");

        if (fechaInicioStr == null || fechaFinStr == null) {
            request.setAttribute("error", "Debe seleccionar un rango de fechas.");
            request.setAttribute("contenido","reporteCompras.jsp");
            request.getRequestDispatcher("template.jsp").forward(request, response);
            return;
        }

        LocalDate fechaInicio = LocalDate.parse(fechaInicioStr);
        LocalDate fechaFin = LocalDate.parse(fechaFinStr);

        List<Map<String, Object>> compras = dao.getComprasPorFecha(fechaInicio, fechaFin);

        if ("pdf".equalsIgnoreCase(formato)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_compras.pdf");

            try {
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

                document.open();
                document.add(new com.lowagie.text.Paragraph("Reporte de Compras"));
                document.add(new com.lowagie.text.Paragraph("Rango: " + fechaInicio + " al " + fechaFin));
                document.add(new com.lowagie.text.Paragraph(" "));

                com.lowagie.text.pdf.PdfPTable tabla = new com.lowagie.text.pdf.PdfPTable(6);
                tabla.setWidthPercentage(100);
                String[] headers = { "ID", "Fecha", "Factura", "Proveedor", "Forma de Pago", "Total" };
                for (String h : headers) {
                    tabla.addCell(h);
                }

                for (Map<String, Object> fila : compras) {
                    tabla.addCell(fila.get("id").toString());
                    tabla.addCell(fila.get("fecha").toString());
                    tabla.addCell(fila.get("numero_factura").toString());
                    tabla.addCell(fila.get("proveedor").toString());
                    tabla.addCell(fila.get("forma_pago").toString());
                    tabla.addCell("Gs. " + fila.get("total_factura").toString());
                }

                document.add(tabla);
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("excel".equalsIgnoreCase(formato)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=reporte_compras.xlsx");

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Compras");

                String[] encabezados = { "ID", "Fecha", "Factura", "Proveedor", "Forma de Pago", "Total" };
                org.apache.poi.ss.usermodel.Row filaHeader = hoja.createRow(0);
                for (int i = 0; i < encabezados.length; i++) {
                    filaHeader.createCell(i).setCellValue(encabezados[i]);
                }

                int filaNum = 1;
                for (Map<String, Object> fila : compras) {
                    org.apache.poi.ss.usermodel.Row row = hoja.createRow(filaNum++);
                    row.createCell(0).setCellValue(fila.get("id").toString());
                    row.createCell(1).setCellValue(fila.get("fecha").toString());
                    row.createCell(2).setCellValue(fila.get("numero_factura").toString());
                    row.createCell(3).setCellValue(fila.get("proveedor").toString());
                    row.createCell(4).setCellValue(fila.get("forma_pago").toString());
                    row.createCell(5).setCellValue("Gs. " + fila.get("total_factura").toString());
                }

                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            request.setAttribute("compras", compras);
            request.setAttribute("fecha_inicio", fechaInicioStr);
            request.setAttribute("fecha_fin", fechaFinStr);
            request.setAttribute("contenido","reporteCompras.jsp");
            request.getRequestDispatcher("template.jsp").forward(request, response);
        }
    }

}

