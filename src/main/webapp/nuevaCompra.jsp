<%@page import="java.util.List"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.Proveedor"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Nueva Compra</title>
        <link href="Bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
        <style>
            .table-container {
                max-height: 400px; /* Altura máxima para la tabla de detalles */
                overflow-y: auto; /* Habilitar scroll si el contenido excede la altura */
            }
        </style>
    </head>
    <body>
        <div class="container mt-4">
            <h1>Nueva Compra</h1>

            <form action="ControladorCompras?accion=Agregar" method="post">

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Nº Factura:</label>
                        <input type="text" name="numeroFactura" class="form-control" required>
                    </div>

                    <div class="col-md-6">
                        <label class="form-label">Proveedor:</label>
                        <select name="proveedorId" class="form-control" required>
                            <c:forEach items="${proveedores}" var="proveedor">
                                <option value="${proveedor.id}">${proveedor.nombre}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="row mb-3">
                    <div class="col-md-6">
                        <label class="form-label">Forma de Pago:</label>
                        <select name="formaPago" class="form-control" required>
                            <option value="Contado">Contado</option>
                            <option value="Crédito">Crédito</option>
                        </select>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label">Total Factura:</label>
                        <input type="number" name="totalFactura" id="totalFactura" class="form-control" readonly>
                    </div>
                </div>

                <h3>Detalles de la Compra</h3>
                <div class="table-container mb-3">
                    <table class="table" id="tablaDetalles">
                        <thead class="table-dark">
                            <tr>
                                <th>Producto</th>
                                <th>Cantidad</th>
                                <th>Costo Unitario</th>
                                <th>Total</th>
                                <th>Acciones</th>
                            </tr>
                        </thead>
                        <tbody>
                            </tbody>
                    </table>
                </div>

                <button type="button" class="btn btn-primary" id="agregarFila">Agregar Producto</button>
                <button type="submit" class="btn btn-success">Guardar Compra</button>
                <a href="ControladorCompras?accion=listar" class="btn btn-secondary">Cancelar</a>
            </form>
        </div>

        <table style="display: none;">
            <tbody id="plantillaFila">
                <tr>
                    <td>
                        <select name="productoId[]" class="form-control producto">
                            <c:forEach items="${productos}" var="p">
                                <option value="${p.id}" data-costo="${p.costo}">
                                    ${p.nombre}
                                </option>
                            </c:forEach>
                        </select>
                    </td>
                    <td><input type="number" name="cantidad[]" class="form-control cantidad" value="1" min="1"></td>
                    <td><input type="number" name="costoUnitario[]" class="form-control costo" step="0.01" min="0"></td>
                    <td><input type="number" name="totalArticulo[]" class="form-control totalArticulo" readonly></td>
                    <td><button type="button" class="btn btn-danger eliminarFila">Eliminar</button></td>
                </tr>
            </tbody>
        </table>

        <script>
            $(document).ready(function () {
                // Función para agregar una nueva fila a la tabla de detalles
                $('#agregarFila').click(function () {
                    var fila = $('#plantillaFila tr').clone();
                    $('#tablaDetalles tbody').append(fila);
                });

                // Cuando se selecciona un producto en una fila, cargar su costo automáticamente
                $(document).on('change', '.producto', function () {
                    var costo = $(this).find('option:selected').data('costo');
                    var fila = $(this).closest('tr');
                    fila.find('.costo').val(costo); // Cambiado a 'costo' para reflejar el precio de compra
                    calcularFila(fila);
                });

                // Recalcular el total de la fila cuando cambia la cantidad o el costo
                $(document).on('input', '.cantidad, .costo', function () {
                    var fila = $(this).closest('tr');
                    calcularFila(fila);
                });

                // Eliminar fila de la tabla
                $(document).on('click', '.eliminarFila', function () {
                    $(this).closest('tr').remove();
                    calcularTotal(); // Recalcular el total general después de eliminar una fila
                });

                // Función para calcular el total de una fila específica
                function calcularFila(fila) {
                    var cantidad = parseFloat(fila.find('.cantidad').val()) || 0;
                    var costo = parseFloat(fila.find('.costo').val()) || 0;
                    var total = cantidad * costo;
                    fila.find('.totalArticulo').val(total.toFixed(2));
                    calcularTotal(); // Recalcular el total general cada vez que una fila cambia
                }

                // Función para calcular el total general de la factura
                function calcularTotal() {
                    var total = 0;
                    $('.totalArticulo').each(function () {
                        total += parseFloat($(this).val()) || 0;
                    });
                    $('#totalFactura').val(total.toFixed(2));
                }
            });
        </script>
    </body>
</html>