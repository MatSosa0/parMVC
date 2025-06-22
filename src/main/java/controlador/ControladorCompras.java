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
                request.setAttribute("contenido","nuevaCompra.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);
                break;

            case "registrarCompra":
                try {
                    String numeroFactura = request.getParameter("numeroFactura");
                    int proveedorId = Integer.parseInt(request.getParameter("proveedorId"));
                    String formaPago = request.getParameter("formaPago");

                    Compra compra = new Compra();
                    compra.setNumeroFactura(numeroFactura);
                    compra.setProveedorId(proveedorId);
                    compra.setFormaPago(formaPago);

                    // Obtener productos seleccionados
                    String[] productosSeleccionados = request.getParameterValues("producto_id");
                    double totalFactura = 0.0;
                    List<DetalleCompra> detalles = new ArrayList<>();

                    if (productosSeleccionados != null) {
                        for (String idStr : productosSeleccionados) {
                            int idProducto = Integer.parseInt(idStr);

                            int cantidad = Integer.parseInt(request.getParameter("cantidad_" + idProducto));
                            double precio = Double.parseDouble(request.getParameter("precio_" + idProducto));

                            if (cantidad > 0) {
                                DetalleCompra detalle = new DetalleCompra();
                                detalle.setProductoId(idProducto);
                                detalle.setCantidad(cantidad);
                                detalle.setPrecioUnitario(precio);
                                detalle.setTotalArticulo(precio * cantidad);

                                totalFactura += detalle.getTotalArticulo();
                                detalles.add(detalle);
                            }
                        }
                    }

                    compra.setTotalFactura(totalFactura);
                    int idCompra = daoCompra.add(compra);

                    // Guardar cada detalle y actualizar stock
                    for (DetalleCompra item : detalles) {
                        item.setCompraId(idCompra);
                        daoDetalle.add(item);

                        Producto prod = daoProducto.getProductoPorId(item.getProductoId());
                        int nuevoStock = prod.getUnidades() + item.getCantidad();
                        daoProducto.actualizarStock(item.getProductoId(), nuevoStock);
                    }

                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "¡Compra registrada exitosamente!");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);

                } catch (Exception e) {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "Error al registrar la compra: " + e.getMessage());
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
                break;

            case "vaciarCarrito":
                carrito.clear();
                total = 0.0;
                request.getSession().removeAttribute("carritoCompra");
                request.getSession().removeAttribute("totalCompra");

                request.setAttribute("productos", daoProducto.getProductos());
                request.setAttribute("contenido","nuevaCompra.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);
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
