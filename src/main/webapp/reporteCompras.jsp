<%-- 
    Document   : reporteCompras
    Created on : 15 jun 2025, 9:19:36 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Compras por Fecha</title>
    <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2>Reporte de Compras por Rango de Fechas</h2>

    <!-- Formulario de fechas -->
    <form action="ReporteCompras" method="POST" class="row g-3 mb-4">
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

    <!-- Botones de exportación -->
    <%
        List<Map<String, Object>> compras = (List<Map<String, Object>>) request.getAttribute("compras");
        if (compras != null && !compras.isEmpty()) {
    %>
    <form action="ReporteCompras" method="POST" class="mb-3">
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
                    <th>Proveedor</th>
                    <th>Forma de Pago</th>
                    <th>Total Factura</th>
                </tr>
            </thead>
            <tbody>
                <%
                    if (compras != null && !compras.isEmpty()) {
                        for (Map<String, Object> compra : compras) {
                %>
                <tr>
                    <td><%= compra.get("id") %></td>
                    <td><%= compra.get("fecha") %></td>
                    <td><%= compra.get("numero_factura") %></td>
                    <td><%= compra.get("proveedor") %></td>
                    <td><%= compra.get("forma_pago") %></td>
                    <td>Gs. <%= compra.get("total_factura") %></td>
                </tr>
                <%
                        }
                    } else if (compras != null) {
                %>
                <tr>
                    <td colspan="6" class="text-center">No se encontraron compras en ese rango.</td>
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

