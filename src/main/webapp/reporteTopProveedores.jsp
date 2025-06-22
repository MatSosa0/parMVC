<%-- 
    Document   : reporteTopProveedores
    Created on : 15 jun 2025, 3:34:01 p.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte Top 15 Proveedores</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Top 15 Proveedores</h2>

    <!-- Botones de exportación -->
    <form action="ReporteTopProveedores" method="POST" class="mb-3">
        <button type="submit" name="formato" value="pdf" class="btn btn-danger">Exportar a PDF</button>
        <button type="submit" name="formato" value="excel" class="btn btn-success">Exportar a Excel</button>
    </form>

    <!-- Tabla de proveedores -->
    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Total Comprado</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> proveedores = (List<Map<String, Object>>) request.getAttribute("proveedores");
                    if (proveedores != null && !proveedores.isEmpty()) {
                        for (Map<String, Object> fila : proveedores) {
                %>
                <tr>
                    <td><%= fila.get("id") %></td>
                    <td><%= fila.get("nombre") %></td>
                    <td>Gs. <%= fila.get("total_comprado") %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="3" class="text-center">No se encontraron datos.</td>
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

