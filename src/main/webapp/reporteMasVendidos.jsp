<%-- 
    Document   : reporteMasVendidos
    Created on : 15 jun 2025, 9:47:26 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Productos Más Vendidos</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Productos Más Vendidos</h2>

    <!-- Botones de exportación -->
    <form action="ReporteProductosMasVendidos" method="POST" class="mb-3">
        <button type="submit" name="formato" value="pdf" class="btn btn-danger">Exportar a PDF</button>
        <button type="submit" name="formato" value="excel" class="btn btn-success">Exportar a Excel</button>
    </form>

    <!-- Tabla de productos -->
    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID Producto</th>
                    <th>Nombre</th>
                    <th>Total Vendido</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> productos = (List<Map<String, Object>>) request.getAttribute("productos");
                    if (productos != null && !productos.isEmpty()) {
                        for (Map<String, Object> fila : productos) {
                %>
                <tr>
                    <td><%= fila.get("id") %></td>
                    <td><%= fila.get("nombre") %></td>
                    <td><%= fila.get("total_vendido") %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="text-center">No hay productos vendidos registrados.</td>
                </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
