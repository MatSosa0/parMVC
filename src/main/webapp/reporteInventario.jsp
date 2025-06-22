<%-- 
    Document   : reporteInventario
    Created on : 15 jun 2025, 7:40:08 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Inventario</title>
    <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<div class="container mt-4">
    <h2>Reporte de Inventario de Productos</h2>

    <!-- ✅ Botones de exportación -->
    <div class="mb-3">
        <a href="ReporteInventario?formato=pdf" class="btn btn-danger">Exportar a PDF</a>
        <a href="ReporteInventario?formato=excel" class="btn btn-success">Exportar a Excel</a>
    </div>

    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered table-striped">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Categoría</th>
                    <th>Unidades</th>
                    <th>Precio</th>
                    <th>Total en stock</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Map<String, Object>> inventario = (List<Map<String, Object>>) request.getAttribute("inventario");
                    if (inventario != null && !inventario.isEmpty()) {
                        for (Map<String, Object> fila : inventario) {
                %>
                <tr>
                    <td><%= fila.get("id") %></td>
                    <td><%= fila.get("nombre") %></td>
                    <td><%= fila.get("descripcion") %></td>
                    <td><%= fila.get("categoria") %></td>
                    <td><%= fila.get("unidades") %></td>
                    <td>Gs. <%= fila.get("precio") %></td>
                    <td>Gs. <%= fila.get("total_stock") %></td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="7" class="text-center">No hay datos para mostrar.</td>
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

