<%-- 
    Document   : reporteVentas
    Created on : 15 jun 2025, 8:52:35 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Ventas por Fecha</title>
    <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2>Reporte de Ventas por Rango de Fechas</h2>

    <!-- Formulario de búsqueda -->
    <form action="ReporteVentas" method="POST" class="row g-3 mb-4">
        <div class="col-md-4">
            <label>Fecha desde:</label>
            <input type="date" name="fecha_inicio" class="form-control" value="${fecha_inicio != null ? fecha_inicio : ''}" required>
        </div>
        <div class="col-md-4">
            <label>Fecha hasta:</label>
            <input type="date" name="fecha_fin" class="form-control" value="${fecha_fin != null ? fecha_fin : ''}" required>
        </div>
        <div class="col-md-4 d-flex align-items-end">
            <button type="submit" class="btn btn-primary me-2">Filtrar</button>
        </div>
    </form>

    <!-- Botones de exportación (se activan solo si hay ventas) -->
    <%
        List<Map<String, Object>> ventas = (List<Map<String, Object>>) request.getAttribute("ventas");
        if (ventas != null && !ventas.isEmpty()) {
    %>
        <form action="ReporteVentas" method="POST" class="mb-3">
            <input type="hidden" name="fecha_inicio" value="<%= request.getAttribute("fecha_inicio") %>">
            <input type="hidden" name="fecha_fin" value="<%= request.getAttribute("fecha_fin") %>">

            <button type="submit" name="formato" value="pdf" class="btn btn-danger">Exportar a PDF</button>
            <button type="submit" name="formato" value="excel" class="btn btn-success">Exportar a Excel</button>
        </form>
    <%
        }
    %>

    <!-- Tabla de resultados -->
    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Fecha</th>
                    <th>Nº Factura</th>
                    <th>Cliente</th>
                    <th>Forma de Pago</th>
                    <th>Total Factura</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (ventas != null && !ventas.isEmpty()) {
                        for (Map<String, Object> venta : ventas) {
                %>
                    <tr>
                        <td><%= venta.get("id") %></td>
                        <td><%= venta.get("fecha") %></td>
                        <td><%= venta.get("numero_factura") %></td>
                        <td><%= venta.get("cliente") %></td>
                        <td><%= venta.get("forma_pago") %></td>
                        <td>Gs. <%= venta.get("total_factura") %></td>
                    </tr>
                <%
                        }
                    } else if (ventas != null) {
                %>
                    <tr>
                        <td colspan="6" class="text-center">No se encontraron ventas en ese rango.</td>
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

