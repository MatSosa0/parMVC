package controlador;

import modeloDAO.ProductoDAO;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;
import java.io.OutputStream;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ReporteInventario extends HttpServlet {

    ProductoDAO dao = new ProductoDAO();

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    ProductoDAO dao = new ProductoDAO();
    List<Map<String, Object>> inventario = dao.getInventario();

    String formato = request.getParameter("formato");

    if ("pdf".equalsIgnoreCase(formato)) {
        // 👉 Exportar PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_inventario.pdf");

        try {
            Document document = new Document();
            OutputStream out = response.getOutputStream();
            PdfWriter.getInstance(document, out);

            document.open();
            document.add(new Paragraph("Reporte de Inventario de Productos"));
            document.add(new Paragraph(" "));

            PdfPTable tabla = new PdfPTable(7); // 7 columnas
            tabla.setWidthPercentage(100);

            // Encabezados
            String[] headers = { "ID", "Nombre", "Descripción", "Categoría", "Unidades", "Precio", "Total en stock" };
            for (String h : headers) {
                tabla.addCell(new PdfPCell(new Phrase(h)));
            }

            // Datos
            for (Map<String, Object> fila : inventario) {
                tabla.addCell(fila.get("id").toString());
                tabla.addCell(fila.get("nombre").toString());
                tabla.addCell(fila.get("descripcion").toString());
                tabla.addCell(fila.get("categoria").toString());
                tabla.addCell(fila.get("unidades").toString());
                tabla.addCell("Gs. " + fila.get("precio").toString());
                tabla.addCell("Gs. " + fila.get("total_stock").toString());
            }

            document.add(tabla);
            document.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    } else if ("excel".equalsIgnoreCase(formato)) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=reporte_inventario.xlsx");

        try (org.apache.poi.ss.usermodel.Workbook workbook = new org.apache.poi.xssf.usermodel.XSSFWorkbook()) {
            org.apache.poi.ss.usermodel.Sheet hoja = workbook.createSheet("Inventario");

            // Encabezados
            String[] encabezados = { "ID", "Nombre", "Descripción", "Categoría", "Unidades", "Precio", "Total en stock" };
            org.apache.poi.ss.usermodel.Row filaEncabezado = hoja.createRow(0);
            for (int i = 0; i < encabezados.length; i++) {
                filaEncabezado.createCell(i).setCellValue(encabezados[i]);
            }

            // Filas de datos
            int filaNum = 1;
            for (Map<String, Object> fila : inventario) {
                org.apache.poi.ss.usermodel.Row row = hoja.createRow(filaNum++);
                row.createCell(0).setCellValue(fila.get("id").toString());
                row.createCell(1).setCellValue(fila.get("nombre").toString());
                row.createCell(2).setCellValue(fila.get("descripcion").toString());
                row.createCell(3).setCellValue(fila.get("categoria").toString());
                row.createCell(4).setCellValue(fila.get("unidades").toString());
                row.createCell(5).setCellValue("Gs. " + fila.get("precio").toString());
                row.createCell(6).setCellValue("Gs. " + fila.get("total_stock").toString());
            }

            workbook.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }else {
            // 👉 Vista normal en tabla
            request.setAttribute("inventario", inventario);
            request.getRequestDispatcher("reporteInventario.jsp").forward(request, response);
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // por si se usa con formulario
    }
}
