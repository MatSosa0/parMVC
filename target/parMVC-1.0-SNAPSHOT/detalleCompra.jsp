<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.*, java.util.*" %>
<jsp:useBean id="DaoCompra" class="modeloDAO.CompraDAO" scope="page" />
<jsp:useBean id="DaoDetalleCompra" class="modeloDAO.DetalleCompraDAO" scope="page" />
<jsp:useBean id="DaoProveedor" class="modeloDAO.ProveedorDAO" scope="page" />
<%
    int idCompra = Integer.parseInt(request.getParameter("id"));
    Compra compra = DaoCompra.getCompraPorId(idCompra);
    List<DetalleCompra> detalles = DaoDetalleCompra.getDetallesPorCompra(idCompra);
    Proveedor proveedor = DaoProveedor.getId(compra.getProveedorId());
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Detalle de Compra</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="container mt-4">
    <h3>Detalle de Compra</h3>
    <p><strong>Proveedor:</strong> <%= proveedor.getNombre() %></p>
    <p><strong>Fecha:</strong> <%= compra.getFecha() %></p>
    <p><strong>Forma de Pago:</strong> <%= compra.getFormaPago() %></p>
    <p><strong>Total Factura:</strong> $<%= compra.getTotalFactura() %></p>

    <h5>Productos Comprados</h5>
    <table class="table table-bordered">
        <thead>
            <tr>
                <th>Producto</th>
                <th>Cantidad</th>
                <th>Precio Unitario</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody>
            <% for (DetalleCompra d : detalles) { %>
                <tr>
                    <td><%= d.getNombreProducto() %></td>
                    <td><%= d.getCantidad() %></td>
                    <td>$<%= d.getPrecioUnitario() %></td>
                    <td>$<%= d.getTotalArticulo() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    <a href="ControladorCompras?accion=listar" class="btn btn-secondary">Volver</a>
</body>
</html>
