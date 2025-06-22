package controlador;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            case "listar":
                try {
                    List<Map<String, Object>> compras = daoCompra.getComprasConProveedor();
                    request.setAttribute("compras", compras);
                    request.setAttribute("titulo", "Listado de Compras");
                    request.setAttribute("contenido","listadoCompras.jsp");
                    request.getRequestDispatcher("template.jsp").forward(request, response);
                } catch (Exception e) {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "Error al listar las compras: " + e.getMessage());
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
                break;

            case "nueva":
                request.setAttribute("productos", daoProducto.getProductos());
                request.setAttribute("contenido","nuevaCompra.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);
                break;
                
            case "detalleCompra":
                try {
                    int idCompra = Integer.parseInt(request.getParameter("id"));

                    Compra compra = daoCompra.getCompraPorId(idCompra);
                    List<DetalleCompra> detalles = daoDetalle.getDetallesPorCompra(idCompra);

                    request.setAttribute("compra", compra);
                    request.setAttribute("detalles", detalles);
                    request.setAttribute("contenido","detalleCompra.jsp");
                    request.getRequestDispatcher("template.jsp").forward(request, response);

                } catch (Exception e) {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "Error al obtener los detalles de la compra: " + e.getMessage());
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
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
                    String[] productosSeleccionados = request.getParameterValues("producto_id[]"); // <-- importante: debe coincidir con el JSP
                    double totalFactura = 0.0;
                    List<DetalleCompra> detalles = new ArrayList<>();

                    if (productosSeleccionados != null && productosSeleccionados.length > 0) {
                        for (String idStr : productosSeleccionados) {
                            int idProducto = Integer.parseInt(idStr);

                            String cantidadStr = request.getParameter("cantidad_" + idProducto);
                            String precioStr = request.getParameter("precio_" + idProducto);

                            if (cantidadStr == null || precioStr == null) continue;

                            int cantidad = Integer.parseInt(cantidadStr);
                            double precio = Double.parseDouble(precioStr);

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

                    // Validación: al menos un producto con cantidad > 0
                    if (detalles.isEmpty()) {
                        request.setAttribute("config", "alert alert-warning");
                        request.setAttribute("mensaje", "Debe seleccionar al menos un producto con cantidad mayor a cero.");
                        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                        return;
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
