<%-- 
    Document   : reporteTopClientes
    Created on : 15 jun 2025, 10:13:14 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte Top 15 Clientes</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Top 15 Clientes</h2>

    <!-- Botones de exportación -->
    <form action="ReporteTopClientes" method="POST" class="mb-3">
        <button type="submit" name="formato" value="pdf" class="btn btn-danger">Exportar a PDF</button>
        <button type="submit" name="formato" value="excel" class="btn btn-success">Exportar a Excel</button>
    </form>

    <!-- Tabla de resultados -->
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
                    List<Map<String, Object>> clientes = (List<Map<String, Object>>) request.getAttribute("clientes");
                    if (clientes != null && !clientes.isEmpty()) {
                        for (Map<String, Object> fila : clientes) {
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

