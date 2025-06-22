<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Listado de Ventas</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    
    <% 
        HttpSession sesion = request.getSession();
        Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
        
        if (usuario == null){
            response.sendRedirect("../index.jsp");
        }
    %>
    
    <div class="container mt-4">
        <h1 class="h3">Listado de Ventas</h1>
        <div class="d-flex justify-content-between">
            <div class="d-flex">
                <a class="btn btn-outline-primary" href="ControladorVentas?accion=listar">Listar</a>
                <a class="btn btn-outline-primary ms-2" href="ControladorVentas?accion=nuevo">Nueva Venta</a>
                <a class="btn btn-outline-primary ms-2" href="<%= request.getContextPath() %>/Controlador?accion=listar">Volver</a>
                <a class="btn btn-outline-danger ms-2" href="../cerrarSesion">SALIR</a>
            </div>
        </div>
        <hr>
        <div style="max-height: 427px; overflow-y: auto;">
            <table class="table">
                <thead class="table-dark">
                    <tr>
                        <th>N° Factura</th>
                        <th>Fecha</th>
                        <th>Cliente</th>
                        <th>Forma Pago</th>
                        <th>Total</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="venta" items="${ventas}">
                        <tr>
                            <td>${venta.numeroFactura}</td>
                            <td>${venta.fecha}</td>
                            <td>${venta.clienteNombre}</td>
                            <td>${venta.formaPago}</td>
                            <td>${venta.totalFactura}</td>
                            <td>
                                <a href="ControladorVentas?accion=verDetalles&id=${venta.id}" class="btn btn-info btn-sm">Ver</a>
                                <a href="ControladorVentas?accion=Editar&id=${venta.id}" class="btn btn-warning btn-sm">Editar</a>
                                <a href="ControladorVentas?accion=Delete&id=${venta.id}" class="btn btn-danger btn-sm">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <script src="Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>