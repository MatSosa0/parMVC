<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, modelo.*" %>
<jsp:useBean id="DaoProveedor" class="modeloDAO.ProveedorDAO" scope="page" />
<jsp:useBean id="DaoProducto" class="modeloDAO.ProductoDAO" scope="page" />
<%
    List<Proveedor> proveedores = DaoProveedor.getProveedores();
    List<Producto> productos = DaoProducto.getProductos();
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Nueva Compra</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

    <script>
        function calcularTotal() {
            let total = 0;
            const checkboxes = document.querySelectorAll("input[name='producto_id[]']");
            checkboxes.forEach(function (checkbox) {
                if (checkbox.checked) {
                    const id = checkbox.value;
                    const cantidadInput = document.querySelector(`input[name='cantidad_${id}']`);
                    const precioInput = document.querySelector(`input[name='precio_${id}']`);

                    if (cantidadInput && precioInput) {
                        const cantidad = parseFloat(cantidadInput.value) || 0;
                        const precio = parseFloat(precioInput.value) || 0;
                        total += cantidad * precio;
                    }
                }
            });

            const totalInput = document.getElementById("totalEstimado");
            if (totalInput) {
                totalInput.value = total.toFixed(2);
            }
        }

        // Usar esto para asegurar que todos los campos estén renderizados antes de añadir eventos
        window.addEventListener("load", function () {
            document.querySelectorAll("input[type='number']").forEach(function (input) {
                input.addEventListener("input", calcularTotal);
            });

            document.querySelectorAll("input[name='producto_id[]']").forEach(function (checkbox) {
                checkbox.addEventListener("change", calcularTotal);
            });
        });
    </script>
</head>
<body class="container mt-4">
    <h3 class="mb-4">Registrar Nueva Compra</h3>

    <form action="ControladorCompras" method="post">
        <input type="hidden" name="accion" value="registrarCompra">

        <!-- Número de factura -->
        <div class="mb-3">
            <label for="numeroFactura" class="form-label">Número de Factura:</label>
            <input type="text" class="form-control" name="numeroFactura" id="numeroFactura" required>
        </div>

        <!-- Proveedor -->
        <div class="mb-3">
            <label for="proveedor_id" class="form-label">Proveedor:</label>
            <select name="proveedorId" id="proveedor_id" class="form-select" required>
                <option value="">Seleccione un proveedor</option>
                <% for (Proveedor p : proveedores) { %>
                    <option value="<%= p.getId() %>"><%= p.getNombre() %></option>
                <% } %>
            </select>
        </div>

        <!-- Forma de pago -->
        <div class="mb-3">
            <label for="formaPago" class="form-label">Forma de Pago:</label>
            <select name="formaPago" id="formaPago" class="form-select" required>
                <option value="Contado">Contado</option>
                <option value="Crédito">Crédito</option>
            </select>
        </div>

        <!-- Total estimado -->
        <div class="mb-3">
            <label for="totalEstimado" class="form-label">Total Estimado:</label>
            <input type="text" class="form-control" id="totalEstimado" readonly>
        </div>

        <!-- Productos -->
        <h5 class="mb-3">Seleccionar Productos</h5>
        <table class="table table-bordered table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>Seleccionar</th>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                </tr>
            </thead>
            <tbody>
                <% for (Producto prod : productos) { %>
                    <tr>
                        <td class="text-center">
                            <input type="checkbox" name="producto_id[]" value="<%= prod.getId() %>">
                        </td>
                        <td><%= prod.getNombre() %></td>
                        <td>
                            <input type="number" name="cantidad_<%= prod.getId() %>" class="form-control" value="0" min="0">
                        </td>
                        <td>
                            <input type="number" name="precio_<%= prod.getId() %>" class="form-control" value="<%= prod.getCosto() %>" step="0.01" min="0">
                        </td>
                    </tr>
                <% } %>
            </tbody>
        </table>

        <!-- Botones -->
        <div class="mt-4">
            <button type="submit" class="btn btn-success">Registrar Compra</button>
            <a href="ControladorCompras?accion=listar" class="btn btn-secondary ms-2">Cancelar</a>
        </div>
    </form>
</body>
</html>
