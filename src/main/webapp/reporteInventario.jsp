<%-- 
    Document   : reporteInventario
    Created on : 15 jun 2025, 7:40:08 a.m.
    Author     : Matias
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@page import="java.util.Set"%>
<%
    Set<String> categorias = (Set<String>) request.getAttribute("categorias");
%>

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Reporte de Inventario</title>
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<div class="container mt-4">
    <h2>Reporte de Inventario</h2>

    <!-- Filtros y botones arriba -->
    <div class="d-flex justify-content-between align-items-center mb-3">
        <div class="d-flex gap-2 align-items-end">
            <!-- 🔍 Filtro por categoría -->
            <div>
                <label for="filtroCategoria">Filtrar por categoría:</label>
                <select id="filtroCategoria" class="form-select form-select-sm">
                    <option value="">Todas</option>
                    <%
                        if (categorias != null) {
                            for (String cat : categorias) {
                    %>
                        <option value="<%= cat %>"><%= cat %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <!-- 🔍 Buscador global -->
            <div>
                <label>Buscar:</label>
                <input type="search" id="buscador" class="form-control form-control-sm">
            </div>
        </div>

        <!-- 📦 Botones de exportación -->
        <div>
            <a href="ReporteInventario?formato=pdf" class="btn btn-sm btn-danger">Exportar PDF</a>
            <a href="ReporteInventario?formato=excel" class="btn btn-sm btn-success">Exportar Excel</a>
            <!--<a href="Controlador?accion=listar" class="btn btn-sm btn-secondary">Volver</a>-->
        </div>
    </div>

    <!-- 🧾 Tabla -->
    <table id="tablaInventario" class="display table table-bordered">
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
            <c:forEach var="fila" items="${inventario}">
                <tr>
                    <td>${fila.id}</td>
                    <td>${fila.nombre}</td>
                    <td>${fila.descripcion}</td>
                    <td>${fila.categoria}</td>
                    <td>${fila.unidades}</td>
                    <td>Gs. ${fila.precio}</td>
                    <td>Gs. ${fila.total_stock}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script>
        $(document).ready(function () {
            const tabla = $('#tablaInventario').DataTable({
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

            // Filtro por categoría
            $('#filtroCategoria').on('change', function () {
                tabla.column(3).search(this.value).draw(); // columna 3 = categoría
            });

            // Buscador general0
            $('#buscador').on('keyup', function () {
                tabla.search(this.value).draw();
            });
        });
    </script>
</body>
</html>


