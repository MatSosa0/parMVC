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


public class ReporteProductosMasVendidos extends HttpServlet {

    ProductoDAO dao = new ProductoDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String, Object>> productos = dao.getProductosMasVendidos();

        request.setAttribute("productos", productos);
        request.getRequestDispatcher("reporteMasVendidos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String formato = request.getParameter("formato");
        List<Map<String, Object>> productos = dao.getProductosMasVendidos();

        if ("pdf".equalsIgnoreCase(formato)) {
            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=productos_mas_vendidos.pdf");

            try {
                com.lowagie.text.Document document = new com.lowagie.text.Document();
                com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

                document.open();
                document.add(new com.lowagie.text.Paragraph("Reporte de Productos Más Vendidos"));
                document.add(new com.lowagie.text.Paragraph(" "));

                com.lowagie.text.pdf.PdfPTable tabla = new com.lowagie.text.pdf.PdfPTable(3);
                tabla.setWidthPercentage(100);
                String[] headers = { "ID Producto", "Nombre", "Total Vendido" };
                for (String h : headers) {
                    tabla.addCell(h);
                }

                for (Map<String, Object> fila : productos) {
                    tabla.addCell(fila.get("id").toString());
                    tabla.addCell(fila.get("nombre").toString());
                    tabla.addCell(fila.get("total_vendido").toString());
                }

                document.add(tabla);
                document.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if ("excel".equalsIgnoreCase(formato)) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setHeader("Content-Disposition", "attachment; filename=productos_mas_vendidos.xlsx");

            try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
                org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Más Vendidos");

                String[] encabezados = { "ID Producto", "Nombre", "Total Vendido" };
                org.apache.poi.ss.usermodel.Row filaHeader = hoja.createRow(0);
                for (int i = 0; i < encabezados.length; i++) {
                    filaHeader.createCell(i).setCellValue(encabezados[i]);
                }

                int filaNum = 1;
                for (Map<String, Object> fila : productos) {
                    org.apache.poi.ss.usermodel.Row row = hoja.createRow(filaNum++);
                    row.createCell(0).setCellValue(fila.get("id").toString());
                    row.createCell(1).setCellValue(fila.get("nombre").toString());
                    row.createCell(2).setCellValue(fila.get("total_vendido").toString());
                }

                workbook.write(response.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("reporteMasVendidos.jsp").forward(request, response);
        }
    }

}
