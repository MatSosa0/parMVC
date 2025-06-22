<%-- 
    Document   : detallesVentas
    Created on : 15 jun 2025, 4:46:45 p.m.
    Author     : Matias
--%>

<%@page import="modelo.Venta"%>
<%@page import="modelo.DetalleVenta"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Detalle de Venta</title>
    <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("../index.jsp");
    }

    Venta venta = (Venta) request.getAttribute("venta");
    List<DetalleVenta> detalles = venta.getDetalles();
%>

<div class="container mt-4">
    <h2>Detalles de la Venta</h2>
    <div class="mb-3">
        <strong>N° Factura:</strong> <%= venta.getNumeroFactura() %><br>
        <strong>Fecha:</strong> <%= venta.getFecha() %><br>
        <strong>Cliente ID:</strong> <%= venta.getClienteId() %><br>
        <strong>Forma de Pago:</strong> <%= venta.getFormaPago() %><br>
        <strong>Total:</strong> Gs. <%= venta.getTotalFactura() %>
    </div>

    <table class="table table-bordered">
        <thead class="table-dark">
            <tr>
                <th>ID Producto</th>
                <th>Cantidad</th>
                <th>Precio Unitario</th>
                <th>Total Artículo</th>
            </tr>
        </thead>
        <tbody>
            <%
                if (detalles != null && !detalles.isEmpty()) {
                    for (DetalleVenta d : detalles) {
            %>
            <tr>
                <td><%= d.getProductoId() %></td>
                <td><%= d.getCantidad() %></td>
                <td>Gs. <%= d.getPrecioUnitario() %></td>
                <td>Gs. <%= d.getTotalArticulo() %></td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr>
                <td colspan="4" class="text-center">No hay productos registrados en esta venta.</td>
            </tr>
            <%
                }
            %>
        </tbody>
    </table>

    <a href="ControladorVentas?accion=listar" class="btn btn-secondary">Volver</a>
</div>

<script src="Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>

