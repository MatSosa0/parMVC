<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Cliente"%>
<%@page import="modelo.Producto"%>
<%@page import="modelo.DetalleVenta"%>
<%@page import="java.util.List"%>

<h2>Nueva Venta</h2>

<!-- Formulario para seleccionar cliente y agregar producto -->
<form action="ControladorVentas" method="GET" class="mb-4">
    <input type="hidden" name="accion" value="agregar" />

    <div class="row g-3 align-items-end">
        <div class="col-md-3">
            <label>Producto</label>
            <select name="idProducto" class="form-control" required>
                <option value="">Seleccionar</option>
                <%
                    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
                    if (productos != null) {
                        for (Producto p : productos) {
                %>
                <option value="<%= p.getId() %>"><%= p.getNombre() %> - Gs. <%= p.getPrecio() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="col-md-2">
            <label>Cantidad</label>
            <input type="number" name="cantidad" class="form-control" value="1" min="1" required />
        </div>

        <div class="col-md-2">
            <button type="submit" class="btn btn-outline-primary">Agregar al carrito</button>
        </div>
    </div>
</form>

<!-- Carrito actual -->
<h4>Productos seleccionados</h4>
<table class="table table-bordered">
    <thead class="table-light">
        <tr>
            <th>Producto</th>
            <th>Cantidad</th>
            <th>Precio Unitario</th>
            <th>Subtotal</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<DetalleVenta> carrito = (List<DetalleVenta>) request.getAttribute("carrito");
            double total = (Double) request.getAttribute("total");
            List<Producto> productosRef = (List<Producto>) request.getAttribute("productos");

            for (DetalleVenta d : carrito) {
                String nombreProducto = "";
                for (Producto p : productosRef) {
                    if (p.getId() == d.getIdProducto()) {
                        nombreProducto = p.getNombre();
                        break;
                    }
                }
        %>
        <tr>
            <td><%= nombreProducto %></td>
            <td><%= d.getCantidad() %></td>
            <td>Gs. <%= d.getPrecioUnitario() %></td>
            <td>Gs. <%= d.getSubtotal() %></td>
        </tr>
        <%
            }
        %>
        <tr class="table-dark">
            <td colspan="3" class="text-end"><strong>Total:</strong></td>
            <td>Gs. <%= total %></td>
        </tr>
    </tbody>
</table>

<!-- Confirmación de venta -->
<form action="ControladorVentas" method="GET">
    <input type="hidden" name="accion" value="confirmar" />
    <div class="row g-3">
        <div class="col-md-4">
            <label>Cliente</label>
            <select name="idCliente" class="form-control" required>
                <option value="">Seleccionar cliente</option>
                <%
                    List<Cliente> clientes = (List<Cliente>) request.getAttribute("clientes");
                    if (clientes != null) {
                        for (Cliente c : clientes) {
                %>
                <option value="<%= c.getId() %>"><%= c.getNombre() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>
        <div class="col-md-4 d-flex align-items-end">
            <button type="submit" class="btn btn-success me-2">Confirmar Venta</button>
            <a href="ControladorVentas?accion=cancelar" class="btn btn-danger">Cancelar</a>
        </div>
    </div>
</form>
