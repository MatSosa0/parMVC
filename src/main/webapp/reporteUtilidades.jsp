<%-- 
    Document   : reporteUtilidades
    Created on : 15 jun 2025, 3:47:52 p.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Utilidades por Producto</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Utilidades por Producto</h2>

    <!-- Botones de exportación -->
    <form action="ReporteUtilidades" method="POST" class="mb-3">
        <button type="submit" name="formato" value="pdf" class="btn btn-danger">Exportar a PDF</button>
        <button type="submit" name="formato" value="excel" class="btn btn-success">Exportar a Excel</button>
    </form>

    <!-- Tabla de utilidades -->
    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Total Vendido</th>
                    <th>Costo Total</th>
                    <th>Ingreso Total</th>
                    <th>Utilidad</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> utilidades = (List<Map<String, Object>>) request.getAttribute("utilidades");
                    if (utilidades != null && !utilidades.isEmpty()) {
                        for (Map<String, Object> fila : utilidades) {
                %>
                <tr>
                    <td><%= fila.get("id") %></td>
                    <td><%= fila.get("nombre") %></td>
                    <td><%= fila.get("total_vendido") %></td>
                    <td>Gs. <%= fila.get("costo_total") %></td>
                    <td>Gs. <%= fila.get("ingreso_total") %></td>
                    <td><strong>Gs. <%= fila.get("utilidad") %></strong></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" class="text-center">No se encontraron datos.</td>
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
