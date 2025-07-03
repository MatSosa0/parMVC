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
import modelo.Proveedor; // ¡Importar la clase Proveedor!
import modeloDAO.CompraDAO;
import modeloDAO.DetalleCompraDAO;
import modeloDAO.ProductoDAO;
import modeloDAO.ProveedorDAO; // ¡Importar ProveedorDAO!

@WebServlet(name = "ControladorCompras", urlPatterns = {"/ControladorCompras"})
public class ControladorCompras extends HttpServlet {

    ProductoDAO daoProducto = new ProductoDAO();
    CompraDAO daoCompra = new CompraDAO();
    DetalleCompraDAO daoDetalle = new DetalleCompraDAO();
    ProveedorDAO daoProveedor = new ProveedorDAO(); // ¡Añadir instancia de ProveedorDAO!

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
                // Aquí es donde necesitamos cargar los proveedores
                List<Proveedor> proveedores = daoProveedor.getProveedores(); // Obtener la lista de proveedores
                request.setAttribute("proveedores", proveedores); // Poner la lista en el request scope para la JSP

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

            case "Agregar": // Este caso en tu JSP está con 'Agregar', no 'registrarCompra'
                try {
                    String numeroFactura = request.getParameter("numeroFactura");
                    int proveedorId = Integer.parseInt(request.getParameter("proveedorId"));
                    String formaPago = request.getParameter("formaPago");

                    Compra compra = new Compra();
                    compra.setNumeroFactura(numeroFactura);
                    compra.setProveedorId(proveedorId);
                    compra.setFormaPago(formaPago);

                    // Obtener productos seleccionados del formulario dinámico
                    String[] productoIdsStr = request.getParameterValues("productoId[]"); // Nombres de campos JSP
                    String[] cantidadesStr = request.getParameterValues("cantidad[]");
                    String[] costosUnitariosStr = request.getParameterValues("costoUnitario[]");

                    double totalFactura = 0.0;
                    List<DetalleCompra> detalles = new ArrayList<>();

                    if (productoIdsStr != null && productoIdsStr.length > 0) {
                        for (int i = 0; i < productoIdsStr.length; i++) {
                            int idProducto = Integer.parseInt(productoIdsStr[i]);
                            int cantidad = Integer.parseInt(cantidadesStr[i]);
                            double costo = Double.parseDouble(costosUnitariosStr[i]);

                            if (cantidad > 0) { // Solo agregar si la cantidad es mayor a cero
                                DetalleCompra detalle = new DetalleCompra();
                                detalle.setProductoId(idProducto);
                                detalle.setCantidad(cantidad);
                                detalle.setPrecioUnitario(costo); // 'precioUnitario' en DetalleCompra es el costo de compra
                                detalle.setTotalArticulo(costo * cantidad);

                                totalFactura += detalle.getTotalArticulo();
                                detalles.add(detalle);
                            }
                        }
                    }

                    // Validación: al menos un producto con cantidad > 0
                    if (detalles.isEmpty()) {
                        request.setAttribute("config", "alert alert-warning");
                        request.setAttribute("mensaje", "Debe agregar al menos un producto con cantidad mayor a cero.");
                        // Vuelve a cargar las listas para que el usuario no pierda el contexto
                        request.setAttribute("proveedores", daoProveedor.getProveedores());
                        request.setAttribute("productos", daoProducto.getProductos());
                        request.setAttribute("contenido","nuevaCompra.jsp");
                        request.getRequestDispatcher("template.jsp").forward(request, response);
                        return;
                    }

                    compra.setTotalFactura(totalFactura);
                    int idCompra = daoCompra.add(compra); // Guarda la cabecera de la compra

                    // Guardar cada detalle y actualizar stock
                    for (DetalleCompra item : detalles) {
                        item.setCompraId(idCompra);
                        daoDetalle.add(item);

                        // Actualizar stock del producto
                        Producto prod = daoProducto.getProductoPorId(item.getProductoId());
                        int nuevoStock = prod.getUnidades() + item.getCantidad(); // Sumar la cantidad comprada al stock
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
                // Este caso podría ser menos relevante con el nuevo diseño dinámico.
                // Si ya no usas un "carrito" persistente en sesión de esta forma, podrías eliminarlo.
                // Lo mantengo por si tienes otra lógica que lo use.
                carrito.clear();
                total = 0.0;
                request.getSession().removeAttribute("carritoCompra");
                request.getSession().removeAttribute("totalCompra");

                // Asegúrate de recargar proveedores y productos al "vaciar" y volver a la página de nueva compra
                request.setAttribute("proveedores", daoProveedor.getProveedores());
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