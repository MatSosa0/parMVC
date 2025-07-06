<%--  
    Document   : listadoCompras
    Created on : 22 jun 2025, 2:57:20 p.m.
    Author     : Matias
--%>

<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Listado de Compras</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <!-- DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
</head>
<body>
    
    <%
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

        if (usuario == null) {
            response.sendRedirect("../index.jsp");
        }
    %>
    
    <div class="container mt-4">
        <h1 class="h3">Listado de Compras</h1>

        <div class="d-flex justify-content-between mb-3">
            <div class="d-flex">
                <a class="btn btn-outline-primary" href="ControladorCompras?accion=listar">Listar</a>
                <a class="btn btn-outline-primary ms-2" href="ControladorCompras?accion=nueva">Nueva Compra</a>
                <a class="btn btn-outline-primary ms-2" href="<%= request.getContextPath() %>/Controlador?accion=listar">Volver</a>
            </div>
        </div>

        <hr>

        <div style="max-height: 427px; overflow-y: auto;">
            <table id="tablaCompras" class="table table-bordered table-striped">
                <thead class="table-dark">
                    <tr>
                        <th>N° Factura</th>
                        <th>Fecha</th>
                        <th>Proveedor</th>
                        <th>Forma Pago</th>
                        <th>Total</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="compra" items="${compras}">
                        <tr>
                            <td>${compra.numeroFactura}</td>
                            <td>${compra.fecha}</td>
                            <td>${compra.proveedorNombre}</td>
                            <td>${compra.formaPago}</td>
                            <td>Gs. ${compra.totalFactura}</td>
                            <td>
                                <a href="ControladorCompras?accion=detalleCompra&id=${compra.id}" class="btn btn-info btn-sm">Ver</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
       </div>
    </div>

    <!-- Scripts: jQuery, DataTables y Bootstrap Bundle -->
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
    <script src="Bootstrap/js/bootstrap.bundle.js"></script>

    <script>
        $(document).ready(function () {
            $('#tablaCompras').DataTable({
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
        });
    </script>
</body>
</html>
