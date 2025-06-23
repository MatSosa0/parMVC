<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Cliente"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Nueva Venta</title>
        <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .table-container {
                max-height: 400px;
                overflow-y: auto;
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1>Nueva Venta</h1>

            <form action="ControladorVentas?accion=Agregar" method="post">

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Nº Factura:</label>
                        <input type="text" name="numeroFactura" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Cliente:</label>
                        <select name="clienteId" class="form-control" required>
                            <c:forEach items="${clientes}" var="cliente">
                                <option value="${cliente.id}">${cliente.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Forma de Pago:</label>
                        <select name="formaPago" class="form-control" required>
                            <option value="Efectivo">Efectivo</option>
                            <option value="Tarjeta">Tarjeta</option>
                            <option value="Transferencia">Transferencia</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Total Factura:</label>
                        <input type="number" name="totalFactura" id="totalFactura" class="form-control" readonly>
                    </div>
                </div>

                <h3>Detalles</h3>
                <div class="table-container mb-3">
                    <table class="table" id="tablaDetalles">
                        <thead class="table-dark">
                            <tr>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Precio</th>
                                <th>Total</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            <!-- Filas se agregan con JS -->
                        </tbody>
                    </table>
                </div>

                <button type="button" class="btn btn-primary" id="agregarFila">Agregar Producto</button>
                <button type="submit" class="btn btn-success">Guardar Venta</button>
                <a href="ControladorVentas?accion=listar" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>

        <!-- Plantilla para filas -->
        <table style="display: none;">
            <tbody id="plantillaFila">
                <tr>
                    <td>
                        <select name="productoId[]" class="form-control producto">
                            <c:forEach items="${productos}" var="p">
                                <option value="${p.id}" data-precio="${p.precio}">
                                    ${p.nombre} 
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="number" name="cantidad[]" class="form-control cantidad" value="1" min="1"></td>
                    <td><input type="number" name="precioUnitario[]" class="form-control precio" step="0.01" min="0"></td>
                    <td><input type="number" name="totalArticulo[]" class="form-control totalArticulo" readonly></td>
                    <td><button type="button" class="btn btn-danger eliminarFila">Eliminar</button></td>
                </tr>
            </tbody>
        </table>

        <script>
            $(document).ready(function () {
                // Agregar nueva fila al hacer clic
                $('#agregarFila').click(function () {
                    var fila = $('#plantillaFila tr').clone();
                    $('#tablaDetalles tbody').append(fila);
                });

                // Al seleccionar producto, cargar precio automáticamente
                $(document).on('change', '.producto', function () {
                    var precio = $(this).find('option:selected').data('precio');
                    var fila = $(this).closest('tr');
                    fila.find('.precio').val(precio);
                    calcularFila(fila);
                });

                // Al cambiar cantidad o precio
                $(document).on('input', '.cantidad, .precio', function () {
                    var fila = $(this).closest('tr');
                    calcularFila(fila);
                });

                // Eliminar fila
                $(document).on('click', '.eliminarFila', function () {
                    $(this).closest('tr').remove();
                    calcularTotal();
                });

                // Calcular total por fila
                function calcularFila(fila) {
                    var cantidad = parseFloat(fila.find('.cantidad').val()) || 0;
                    var precio = parseFloat(fila.find('.precio').val()) || 0;
                    var total = cantidad * precio;
                    fila.find('.totalArticulo').val(total.toFixed(2));
                    calcularTotal();
                }

                // Calcular total general de factura
                function calcularTotal() {
                    var total = 0;
                    $('.totalArticulo').each(function () {
                        total += parseFloat($(this).val()) || 0;
                    });
                    $('#totalFactura').val(total.toFixed(2));
                }
            });
        </script>

