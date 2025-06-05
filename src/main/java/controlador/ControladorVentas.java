package controlador;

import modelo.*;
import modeloDAO.*;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.*;

@WebServlet("/ControladorVentas")
public class ControladorVentas extends HttpServlet {

    ProductoDAO productoDAO = new ProductoDAO();
    VentaDAO ventaDAO = new VentaDAO();
    ClienteDAO clienteDAO = new ClienteDAO();

    List<DetalleVenta> carrito = new ArrayList<>();
    int idVentaGenerada = 0;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nueva":
                List<Cliente> clientes = clienteDAO.getClientes();
                List<Producto> productos = productoDAO.getProductos();

                request.setAttribute("clientes", clientes);
                request.setAttribute("productos", productos);
                request.setAttribute("carrito", carrito);
                request.setAttribute("total", calcularTotal());

                request.setAttribute("contenido", "nuevaVenta.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);
                break;

            case "agregar":
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));

                Producto prod = productoDAO.getId(idProducto);
                DetalleVenta detalle = new DetalleVenta();
                detalle.setIdProducto(prod.getId());
                detalle.setCantidad(cantidad);
                detalle.setPrecioUnitario(prod.getPrecio());
                detalle.setSubtotal(cantidad * prod.getPrecio());
                carrito.add(detalle);

                response.sendRedirect("ControladorVentas?accion=nueva");
                break;

            case "confirmar":
                int idCliente = Integer.parseInt(request.getParameter("idCliente"));
                Venta venta = new Venta();
                venta.setIdCliente(idCliente);
                venta.setTotal(calcularTotal());

                idVentaGenerada = ventaDAO.registrarVenta(venta);

                // Insertar detalles
                for (DetalleVenta d : carrito) {
                    d.setIdVenta(idVentaGenerada);
                }

                boolean guardado = ventaDAO.registrarDetalle(carrito);
                carrito.clear();

                if (guardado) {
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "¡Venta registrada con éxito!");
                } else {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "Error al registrar la venta");
                }

                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;

            case "cancelar":
                carrito.clear();
                response.sendRedirect("ControladorVentas?accion=nueva");
                break;

            default:
                response.sendRedirect("ControladorVentas?accion=nueva");
                break;
        }
    }

    private double calcularTotal() {
        double total = 0;
        for (DetalleVenta d : carrito) {
            total += d.getSubtotal();
        }
        return total;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response); // usamos solo doGet para todas las acciones
    }
}
