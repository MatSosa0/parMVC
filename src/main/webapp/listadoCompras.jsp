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

        <table class="table table-bordered table-striped">
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

    <script src="Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>