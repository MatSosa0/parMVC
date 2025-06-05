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
</head>
<body class="container mt-4">
    <h3>Nueva Compra</h3>
    <form action="ControladorCompras" method="post">
        <input type="hidden" name="accion" value="AgregarCompra">
        
        <div class="mb-3">
            <label class="form-label">Proveedor:</label>
            <select name="proveedor_id" class="form-select" required>
                <option value="">Seleccione</option>
                <% for (Proveedor p : proveedores) { %>
                    <option value="<%= p.getId() %>"><%= p.getNombre() %></option>
                <% } %>
            </select>
        </div>
        
        <div class="mb-3">
            <label class="form-label">Forma de Pago:</label>
            <select name="forma_pago" class="form-select" required>
                <option value="Contado">Contado</option>
                <option value="Crédito">Crédito</option>
            </select>
        </div>

        <h5>Agregar Productos</h5>
        <table class="table table-bordered">
            <thead>
                <tr>
                    <th>Producto</th>
                    <th>Cantidad</th>
                    <th>Precio Unitario</th>
                </tr>
            </thead>
            <tbody>
                <% for (Producto prod : productos) { %>
                    <tr>
                        <td>
                            <input type="checkbox" name="producto_id" value="<%= prod.getId() %>"> <%= prod.getNombre() %>
                            <input type="hidden" name="id_producto_<%= prod.getId() %>" value="<%= prod.getId() %>">
                        </td>
                        <td><input type="number" name="cantidad_<%= prod.getId() %>" class="form-control" value="0" min="0"></td>
                        <td><input type="number" name="precio_<%= prod.getId() %>" class="form-control" value="<%= prod.getCosto() %>" step="0.01"></td>
                    </tr>
                <% } %>
            </tbody>
        </table>
        
        <button type="submit" class="btn btn-primary">Registrar Compra</button>
        <a href="ControladorCompras?accion=listar" class="btn btn-secondary">Cancelar</a>
    </form>
</body>
</html>
