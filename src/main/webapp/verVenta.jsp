<%@page import="java.text.SimpleDateFormat"%>
<%@page import="modelo.Cliente"%>
<%@page import="modelo.Producto"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Venta"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Ver Detalles de Venta</title>
        <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <style>
            .table-container {
                max-height: 400px;
                overflow-y: auto;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1>Detalle de Venta</h1>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Nº Factura:</label>
                    <input type="text" class="form-control" value="${venta.numeroFactura}" readonly>
                </div>

                <div class="col-md-6">
                    <label class="form-label">Fecha:</label>
                    <input type="text" class="form-control" value="<%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(((Venta) request.getAttribute("venta")).getFecha())%>" readonly>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Cliente:</label>
                    <input type="text" class="form-control" value="${cliente.nombre}" readonly>
                    <input type="hidden" name="clienteId" value="${cliente.id}">
                </div>

                <div class="col-md-6">
                    <label class="form-label">Forma de Pago:</label>
                    <input type="text" class="form-control" value="${venta.formaPago}" readonly>
                </div>
            </div>

            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Total Factura:</label>
                    <input type="number" class="form-control" value="${venta.totalFactura}" readonly>
                </div>
            </div>

            <h3>Detalles de Venta</h3>
            <div class="table-container mb-3">
                <table class="table">
                    <thead class="table-dark">
                        <tr>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Total Artículo</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="detalle" items="${venta.detalles}">
                            <tr>
                                <td>
                                    <input type="text" class="form-control" value="${detalle.nombreProducto}" readonly>
                                </td>
                                <td>
                                    <input type="number" class="form-control" value="${detalle.cantidad}" readonly>
                                </td>
                                <td>
                                    <input type="number" class="form-control" value="${detalle.precioUnitario}" readonly>
                                </td>
                                <td>
                                    <input type="number" class="form-control" value="${detalle.totalArticulo}" readonly>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div class="mb-3">
                <a href="ControladorVentas?accion=listar" class="btn btn-secondary">Volver</a>
            </div>
        </div>
        <script src="Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>
