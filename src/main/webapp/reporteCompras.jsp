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
    <link href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css" rel="stylesheet">
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

    <!-- Tabla con resultados -->
    <div class="table-responsive">
        <table id="tablaCompras" class="table table-bordered table-striped">
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

<!-- JS DataTables -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        $('#tablaCompras').DataTable({
            pageLength: 7,
            lengthMenu: [[5, 7, 10, 25, -1], [5, 7, 10, 25, "Todos"]],
            language: {
                lengthMenu: "Mostrar _MENU_ registros por página",
                zeroRecords: "No se encontraron resultados",
                info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
                infoEmpty: "Mostrando 0 a 0 de 0 registros",
                infoFiltered: "(filtrado de _MAX_ registros totales)",
                search: "Buscar:",
                paginate: {
                    first: "Primero",
                    last: "Último",
                    next: "Siguiente",
                    previous: "Anterior"
                }
            },
            dom: 'rtip<"d-flex justify-content-end mt-2"l>'
        });
    });
</script>

</body>
</html>


