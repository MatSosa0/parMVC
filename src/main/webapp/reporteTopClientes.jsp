<%-- 
    Document   : reporteTopClientes
    Created on : 15 jun 2025, 10:13:14 a.m.
    Author     : Matias
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte Top 15 Clientes</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Top 15 Clientes</h2>

    <!-- Buscador y exportación -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div>
            <label>Buscar:</label>
            <input type="search" id="buscador" class="form-control form-control-sm" placeholder="Buscar cliente...">
        </div>
        <div>
            <form action="ReporteTopClientes" method="POST" class="d-flex gap-2">
                <button type="submit" name="formato" value="pdf" class="btn btn-danger btn-sm">Exportar a PDF</button>
                <button type="submit" name="formato" value="excel" class="btn btn-success btn-sm">Exportar a Excel</button>
            </form>
        </div>
    </div>

    <!-- Tabla -->
    <table id="tablaClientes" class="display table table-bordered">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Total Comprado</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="fila" items="${clientes}">
                <tr>
                    <td>${fila.id}</td>
                    <td>${fila.nombre}</td>
                    <td>Gs. ${fila.total_comprado}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
        const tabla = $('#tablaClientes').DataTable({
            pageLength: 7,
            lengthMenu: [ [5, 7, 10, 25, 50, -1], [5, 7, 10, 25, 50, "Todos"] ],
            language: {
                lengthMenu: "Filas por página: _MENU_",
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

        // Buscador global
        $('#buscador').on('keyup', function () {
            tabla.search(this.value).draw();
        });
    });
</script>

</body>
</html>




