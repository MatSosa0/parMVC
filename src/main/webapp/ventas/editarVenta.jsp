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
    <title>Editar Venta</title>
    <link href="../Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
        .table-container {
            max-height: 400px;
            overflow-y: auto;
        }
    </style>
    <script>
        $(document).ready(function() {
            // Lógica para agregar productos dinámicamente
            $("#agregarProducto").click(function() {
                var fila = '<tr>' +
                    '<td><select name="productoId" class="form-control producto">' +
                        '<c:forEach items="${productos}" var="producto">' +
                        '<option value="${producto.id}" data-precio="${producto.precioVenta}">${producto.nombre} (Stock: ${producto.stock})</option>' +
                        '</c:forEach>' +
                    '</select></td>' +
                    '<td><input type="number" name="cantidad" class="form-control cantidad" min="1" value="1"></td>' +
                    '<td><input type="number" name="precioUnitario" class="form-control precio" step="0.01" min="0"></td>' +
                    '<td><input type="number" name="totalArticulo" class="form-control totalArticulo" readonly></td>' +
                    '<td><button type="button" class="btn btn-danger btn-eliminar">Eliminar</button></td>' +
                '</tr>';
                
                $("#detalles tbody").append(fila);
                actualizarTotal();
            });
            
            $(document).on("change", ".producto", function() {
                var precio = $(this).find("option:selected").data("precio");
                $(this).closest("tr").find(".precio").val(precio);
                calcularTotalArticulo($(this).closest("tr"));
            });
            
            $(document).on("input", ".cantidad, .precio", function() {
                calcularTotalArticulo($(this).closest("tr"));
            });
            
            $(document).on("click", ".btn-eliminar", function() {
                $(this).closest("tr").remove();
                actualizarTotal();
            });
            
            function calcularTotalArticulo(fila) {
                var cantidad = parseFloat(fila.find(".cantidad").val()) || 0;
                var precio = parseFloat(fila.find(".precio").val()) || 0;
                var totalArticulo = cantidad * precio;
                fila.find(".totalArticulo").val(totalArticulo.toFixed(2));
                actualizarTotal();
            }
            
            function actualizarTotal() {
                var total = 0;
                $(".totalArticulo").each(function() {
                    total += parseFloat($(this).val()) || 0;
                });
                $("#totalFactura").val(total.toFixed(2));
            }
            
            // Cargar detalles existentes al cargar la página
            <c:forEach items="${venta.detalles}" var="detalle" varStatus="loop">
                var filaExistente = '<tr>' +
                    '<td><select name="productoId" class="form-control producto">' +
                        '<c:forEach items="${productos}" var="producto">' +
                        '<option value="${producto.id}" data-precio="${producto.precioVenta}" ' +
                        '${producto.id == detalle.productoId ? 'selected' : ''}>${producto.nombre} (Stock: ${producto.stock})</option>' +
                        '</c:forEach>' +
                    '</select></td>' +
                    '<td><input type="number" name="cantidad" class="form-control cantidad" min="1" value="${detalle.cantidad}"></td>' +
                    '<td><input type="number" name="precioUnitario" class="form-control precio" step="0.01" min="0" value="${detalle.precioUnitario}"></td>' +
                    '<td><input type="number" name="totalArticulo" class="form-control totalArticulo" readonly value="${detalle.totalArticulo}"></td>' +
                    '<td><button type="button" class="btn btn-danger btn-eliminar">Eliminar</button></td>' +
                '</tr>';
                
                $("#detalles tbody").append(filaExistente);
                actualizarTotal();
            </c:forEach>
        });
    </script>
</head>
<body>
    <div class="container mt-4">
        <h1>Editar Venta</h1>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <form action="ControladorVentas?accion=Actualizar" method="post">
            <input type="hidden" name="id" value="${venta.id}">
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">N° Factura:</label>
                    <input type="text" name="numeroFactura" class="form-control" value="${venta.numeroFactura}" required>
                </div>
                
                <div class="col-md-6">
                    <label class="form-label">Fecha:</label>
                    <input type="text" class="form-control" 
                           value="<%= new SimpleDateFormat("dd/MM/yyyy HH:mm").format(((Venta)request.getAttribute("venta")).getFecha()) %>" 
                           readonly>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Cliente:</label>
                    <select name="clienteId" class="form-control" required>
                        <option value="">Seleccione un cliente</option>
                        <c:forEach items="${clientes}" var="cliente">
                            <option value="${cliente.id}" ${venta.clienteId == cliente.id ? 'selected' : ''}>${cliente.nombre}</option>
                        </c:forEach>
                    </select>
                </div>
                
                <div class="col-md-6">
                    <label class="form-label">Forma de Pago:</label>
                    <select name="formaPago" class="form-control" required>
                        <option value="Efectivo" ${venta.formaPago == 'Efectivo' ? 'selected' : ''}>Efectivo</option>
                        <option value="Tarjeta" ${venta.formaPago == 'Tarjeta' ? 'selected' : ''}>Tarjeta</option>
                        <option value="Transferencia" ${venta.formaPago == 'Transferencia' ? 'selected' : ''}>Transferencia</option>
                    </select>
                </div>
            </div>
            
            <div class="row mb-3">
                <div class="col-md-6">
                    <label class="form-label">Total Factura:</label>
                    <input type="number" id="totalFactura" name="totalFactura" class="form-control" value="${venta.totalFactura}" readonly>
                </div>
            </div>
            
            <h3>Detalles de Venta</h3>
            <div class="table-container mb-3">
                <table class="table" id="detalles">
                    <thead class="table-dark">
                        <tr>
                            <th>Producto</th>
                            <th>Cantidad</th>
                            <th>Precio Unitario</th>
                            <th>Total Artículo</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <!-- Las filas se agregarán dinámicamente con JavaScript -->
                    </tbody>
                </table>
            </div>
            
            <div class="mb-3">
                <button type="button" id="agregarProducto" class="btn btn-primary">Agregar Producto</button>
                <button type="submit" class="btn btn-success">Actualizar Venta</button>
                <a href="ControladorVentas?accion=listar" class="btn btn-secondary">Cancelar</a>
            </div>
        </form>
    </div>
    
    <script src="../Bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>