/**
 *
 * @author Matias
 */
package controlador;

import modeloDAO.ProductoDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ReporteUtilidades extends HttpServlet {

    ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> utilidades = dao.getUtilidadesPorProducto();

        request.setAttribute("utilidades", utilidades);
        request.setAttribute("contenido","reporteUtilidades.jsp");
        request.getRequestDispatcher("template.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String formato = request.getParameter("formato");
        List<Map<String, Object>> utilidades = dao.getUtilidadesPorProducto();

        if ("pdf".equalsIgnoreCase(formato)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=utilidades_productos.pdf");

            try {
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

                document.open();
                document.add(new com.lowagie.text.Paragraph("Reporte de Utilidades por Producto"));
                document.add(new com.lowagie.text.Paragraph(" "));

                com.lowagie.text.pdf.PdfPTable tabla = new com.lowagie.text.pdf.PdfPTable(6);
                tabla.setWidthPercentage(100);
                String[] headers = { "ID", "Nombre", "Total Vendido", "Costo Total", "Ingreso Total", "Utilidad" };
                for (String h : headers) {
                    tabla.addCell(h);
                }

                for (Map<String, Object> fila : utilidades) {
                    tabla.addCell(fila.get("id").toString());
                    tabla.addCell(fila.get("nombre").toString());
                    tabla.addCell(fila.get("total_vendido").toString());
                    tabla.addCell("Gs. " + fila.get("costo_total").toString());
                    tabla.addCell("Gs. " + fila.get("ingreso_total").toString());
                    tabla.addCell("Gs. " + fila.get("utilidad").toString());
                }

                document.add(tabla);
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("excel".equalsIgnoreCase(formato)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=utilidades_productos.xlsx");

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Utilidades");

                String[] encabezados = { "ID", "Nombre", "Total Vendido", "Costo Total", "Ingreso Total", "Utilidad" };
                org.apache.poi.ss.usermodel.Row filaHeader = hoja.createRow(0);
                for (int i = 0; i < encabezados.length; i++) {
                    filaHeader.createCell(i).setCellValue(encabezados[i]);
                }

                int filaNum = 1;
                for (Map<String, Object> fila : utilidades) {
                    org.apache.poi.ss.usermodel.Row row = hoja.createRow(filaNum++);
                    row.createCell(0).setCellValue(fila.get("id").toString());
                    row.createCell(1).setCellValue(fila.get("nombre").toString());
                    row.createCell(2).setCellValue(fila.get("total_vendido").toString());
                    row.createCell(3).setCellValue("Gs. " + fila.get("costo_total").toString());
                    row.createCell(4).setCellValue("Gs. " + fila.get("ingreso_total").toString());
                    row.createCell(5).setCellValue("Gs. " + fila.get("utilidad").toString());
                }

                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            request.setAttribute("utilidades", utilidades);
            request.setAttribute("contenido","reporteUtilidades.jsp");
            request.getRequestDispatcher("template.jsp").forward(request, response);
        }
    }

}

