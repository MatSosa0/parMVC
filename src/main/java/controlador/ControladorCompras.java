package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import modelo.Compra;
import modelo.DetalleCompra;
import modelo.Producto;
import modeloDAO.CompraDAO;
import modeloDAO.DetalleCompraDAO;
import modeloDAO.ProductoDAO;

@WebServlet(name = "ControladorCompras", urlPatterns = {"/ControladorCompras"})
public class ControladorCompras extends HttpServlet {

    ProductoDAO daoProducto = new ProductoDAO();
    CompraDAO daoCompra = new CompraDAO();
    DetalleCompraDAO daoDetalle = new DetalleCompraDAO();

    List<DetalleCompra> carrito = new ArrayList<>();
    double total = 0.0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        switch (accion) {
            case "nueva":
                request.setAttribute("productos", daoProducto.getProductos());
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            case "agregarAlCarrito":
                int idProducto = Integer.parseInt(request.getParameter("productoId"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));

                Producto p = daoProducto.getProductoPorId(idProducto);
                DetalleCompra dc = new DetalleCompra();
                dc.setProductoId(idProducto);
                dc.setCantidad(cantidad);
                dc.setPrecioUnitario(p.getCosto());
                dc.setTotalArticulo(p.getCosto() * cantidad);

                carrito.add(dc);
                total += dc.getTotalArticulo();

                request.getSession().setAttribute("carritoCompra", carrito);
                request.getSession().setAttribute("totalCompra", total);
                request.setAttribute("productos", daoProducto.getProductos());
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            case "registrarCompra":
                String numeroFactura = request.getParameter("numeroFactura");
                int proveedorId = Integer.parseInt(request.getParameter("proveedorId"));
                String formaPago = request.getParameter("formaPago");

                Compra compra = new Compra();
                compra.setNumeroFactura(numeroFactura);
                compra.setProveedorId(proveedorId);
                compra.setFormaPago(formaPago);
                compra.setTotalFactura(total);

                int idCompra = daoCompra.add(compra);

                for (DetalleCompra item : carrito) {
                    item.setCompraId(idCompra);
                    daoDetalle.add(item);

                    Producto prod = daoProducto.getProductoPorId(item.getProductoId());
                    int nuevoStock = prod.getUnidades() + item.getCantidad();
                    daoProducto.actualizarStock(item.getProductoId(), nuevoStock);
                }

                carrito.clear();
                total = 0.0;
                request.getSession().removeAttribute("carritoCompra");
                request.getSession().removeAttribute("totalCompra");

                request.setAttribute("config", "alert alert-success");
                request.setAttribute("mensaje", "¡Compra registrada exitosamente!");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;

            case "vaciarCarrito":
                carrito.clear();
                total = 0.0;
                request.getSession().removeAttribute("carritoCompra");
                request.getSession().removeAttribute("totalCompra");

                request.setAttribute("productos", daoProducto.getProductos());
                request.getRequestDispatcher("nuevaCompra.jsp").forward(request, response);
                break;

            default:
                throw new AssertionError("Acción no reconocida: " + accion);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}
