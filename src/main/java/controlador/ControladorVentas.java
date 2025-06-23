package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Cliente;
import modelo.Venta;
import modelo.DetalleVenta;
import modelo.Producto;
import modeloDAO.ClienteDAO;
import modeloDAO.VentaDAO;
import modeloDAO.DetalleVentaDAO;
import modeloDAO.ProductoDAO;

public class ControladorVentas extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        HttpSession session = request.getSession();

        if (accion == null) {
            accion = "listar";
        }

        switch (accion) {
            case "listar":
                listarVentas(request, response);
                break;
            case "nuevo":
                mostrarFormularioNuevo(request, response);
                break;
            case "Agregar":
                agregarVenta(request, response);
                break;
            case "Editar":
                mostrarFormularioEditar(request, response);
                break;
            case "Actualizar":
                actualizarVenta(request, response);
                break;
            case "Delete":
                eliminarVenta(request, response);
                break;
            case "verDetalles":
                verDetallesVenta(request, response);
                break;
            case "eliminarDetalle":
                eliminarDetalleAjax(request, response);
                break;

            default:
                listarVentas(request, response);
        }
    }

    private void eliminarDetalleAjax(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int idDetalle = Integer.parseInt(request.getParameter("idDetalle"));
        DetalleVentaDAO detalleDAO = new DetalleVentaDAO();
        ProductoDAO productoDAO = new ProductoDAO();

        // Obtener detalle para saber qué producto y cantidad eliminar
        DetalleVenta detalle = detalleDAO.getById(idDetalle);
        if (detalle != null) {
            // Actualizar stock: devolver la cantidad al stock
            Producto producto = productoDAO.getId(detalle.getProductoId());
            int nuevoStock = producto.getUnidades() + detalle.getCantidad();
            productoDAO.actualizarStock(producto.getId(), nuevoStock);

            // Eliminar el detalle
            detalleDAO.deleteById(idDetalle);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().write("Detalle eliminado con éxito");
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Detalle no encontrado");
        }
    }

    private void listarVentas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VentaDAO daoVenta = new VentaDAO();
        List<Venta> ventas = daoVenta.getVentas();
        request.setAttribute("ventas", ventas);
        request.setAttribute("contenido", "listadoVentas.jsp");
        request.getRequestDispatcher("template.jsp").forward(request, response);
    }

    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Aquí deberías cargar clientes y productos para los selects
        // request.setAttribute("clientes", clienteDAO.listarClientes());
        // request.setAttribute("productos", productoDAO.listarProductosConStock());
        request.setAttribute("contenido", "addVenta.jsp");
        request.getRequestDispatcher("template.jsp").forward(request, response);
    }

    private void agregarVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VentaDAO daoVenta = new VentaDAO();
        DetalleVentaDAO daoDetalle = new DetalleVentaDAO();

        try {
            // Obtener datos básicos de la venta
            String numeroFactura = request.getParameter("numeroFactura");
            Integer clienteId = Integer.parseInt(request.getParameter("clienteId"));
            String formaPago = request.getParameter("formaPago");

            // Procesar detalles de la venta
            String[] productos = request.getParameterValues("productoId");
            String[] cantidades = request.getParameterValues("cantidad");
            String[] precios = request.getParameterValues("precioUnitario");

            List<DetalleVenta> detalles = new ArrayList<>();
            double totalFactura = 0;

            if (productos != null && productos.length > 0) {
                for (int i = 0; i < productos.length; i++) {
                    int productoId = Integer.parseInt(productos[i]);
                    int cantidad = Integer.parseInt(cantidades[i]);
                    double precioUnitario = Double.parseDouble(precios[i]);
                    double totalArticulo = cantidad * precioUnitario;

                    // Verificar stock disponible
                    if (!daoDetalle.verificarStockDisponible(productoId, cantidad)) {
                        request.setAttribute("config", "alert alert-danger");
                        request.setAttribute("mensaje", "Stock insuficiente para el producto ID: " + productoId);
                        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                        return;
                    }

                    DetalleVenta detalle = new DetalleVenta();
                    detalle.setProductoId(productoId);
                    detalle.setCantidad(cantidad);
                    detalle.setPrecioUnitario(precioUnitario);
                    detalle.setTotalArticulo(totalArticulo);

                    detalles.add(detalle);
                    totalFactura += totalArticulo;
                }
            }

            // Crear y guardar la venta
            Venta venta = new Venta();
            venta.setNumeroFactura(numeroFactura);
            venta.setFecha(new Date());
            venta.setClienteId(clienteId);
            venta.setFormaPago(formaPago);
            venta.setTotalFactura(totalFactura);

            int resultado = daoVenta.add(venta);

            if (resultado > 0) {
                // Guardar detalles de la venta
                boolean detallesGuardados = daoDetalle.addDetalles(detalles, venta.getId());

                if (detallesGuardados) {
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "VENTA REGISTRADA CON ÉXITO");
                } else {
                    request.setAttribute("config", "alert alert-warning");
                    request.setAttribute("mensaje", "VENTA REGISTRADA PERO HUBO PROBLEMAS CON LOS DETALLES");
                }
            } else {
                request.setAttribute("config", "alert alert-danger");
                request.setAttribute("mensaje", "NO SE PUDO REGISTRAR LA VENTA");
            }
        } catch (Exception e) {
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ERROR: " + e.getMessage());
        }

        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

    private void mostrarFormularioEditar(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("ID recibido: " + id);
            VentaDAO daoVenta = new VentaDAO();
            ClienteDAO daoCliente = new ClienteDAO(); // 👈 Asegurate de importar esto
            DetalleVentaDAO daoDetalle = new DetalleVentaDAO(); // Opcional si mostrás detalles
            ProductoDAO daoProducto = new ProductoDAO();
            Venta venta = daoVenta.getId(id);

            if (venta != null) {
                // Obtener el cliente asociado a la venta
                Cliente cliente = daoCliente.getId(venta.getClienteId());

                // Opcional: si necesitás también los detalles
                List<DetalleVenta> detalles = daoDetalle.getDetallesPorVenta(id);

                for (DetalleVenta detalle : detalles) {
                    Producto producto = daoProducto.getId(detalle.getProductoId());
                    detalle.setNombreProducto(producto.getNombre());
                }
                for (DetalleVenta d : detalles) {
                    System.out.println("Producto: " + d.getNombreProducto());
                }

                List<Producto> productos = daoProducto.getProductosConStock(); // o listarConStock() si filtrás
                // Cargar productos con stock

                request.setAttribute("productos", productos);

                venta.setDetalles(detalles);
                // Enviamos todo a la vista

                request.setAttribute("venta", venta);

                // También cargar detalles de la venta si es necesario
                request.setAttribute("contenido", "editarVenta.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);

                request.setAttribute("cliente", cliente); // 👈 Mandamos el cliente a la vista
                request.setAttribute("productos", productos);

                request.getRequestDispatcher("editarVenta.jsp").forward(request, response);

            } else {
                request.setAttribute("config", "alert alert-danger");
                request.setAttribute("mensaje", "VENTA NO ENCONTRADA");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ID DE VENTA INVÁLIDO");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
    }

    private void actualizarVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        VentaDAO daoVenta = new VentaDAO();
        DetalleVentaDAO detalleVentaDAO = new DetalleVentaDAO();
        ProductoDAO daoProducto = new ProductoDAO();

        try {
            int idVenta = Integer.parseInt(request.getParameter("id"));
            String numeroFactura = request.getParameter("numeroFactura");
            String formaPago = request.getParameter("formaPago");
            int clienteId = Integer.parseInt(request.getParameter("clienteId"));

            double totalFactura = 0;

            // Recuperar detalles antiguos para devolver el stock
            List<DetalleVenta> detallesAntiguos = detalleVentaDAO.getDetallesPorVenta(idVenta);
            for (DetalleVenta detalle : detallesAntiguos) {
                Producto prod = daoProducto.getId(detalle.getProductoId());
                int nuevoStock = prod.getUnidades() + detalle.getCantidad(); // devolvemos stock
                daoProducto.actualizarStock(prod.getId(), nuevoStock);
            }

            // Eliminar detalles antiguos
            detalleVentaDAO.deleteDetalles(idVenta); // Asegúrate que este método exista y funcione

            // Leer arrays del formulario (sin los corchetes [] en los nombres)
            String[] productos = request.getParameterValues("productoId");
            String[] cantidades = request.getParameterValues("cantidad");
            String[] precios = request.getParameterValues("precioUnitario");

            if (productos != null && cantidades != null && precios != null) {
                for (int i = 0; i < productos.length; i++) {
                    int idProducto = Integer.parseInt(productos[i]);
                    int cantidad = Integer.parseInt(cantidades[i]);
                    double precioUnitario = Double.parseDouble(precios[i]);
                    double totalArticulo = cantidad * precioUnitario;

                    // Guardar detalle
                    DetalleVenta detalle = new DetalleVenta(idVenta, idProducto, cantidad, precioUnitario, totalArticulo);
                    detalleVentaDAO.agregar(detalle);

                    // Sumar al total
                    totalFactura += totalArticulo;

                    // Actualizar stock (restar la cantidad vendida)
                    Producto producto = daoProducto.getId(idProducto);
                    int nuevoStock = producto.getUnidades() - cantidad;
                    daoProducto.actualizarStock(idProducto, nuevoStock);
                }
            }

            // Actualizar la cabecera de la venta con el total calculado
            Venta venta = new Venta(idVenta, numeroFactura, new Date(), formaPago, totalFactura, clienteId);
            daoVenta.update(venta);

            // Redireccionar a listado o donde quieras
            response.sendRedirect("ControladorVentas?accion=listar");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ERROR AL ACTUALIZAR LA VENTA: " + e.getMessage());
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
        }
    }

    private void eliminarVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            VentaDAO daoVenta = new VentaDAO();
            int resultado = daoVenta.delete(id);

            if (resultado > 0) {
                request.setAttribute("config", "alert alert-success");
                request.setAttribute("mensaje", "VENTA ELIMINADA CON ÉXITO");
            } else {
                request.setAttribute("config", "alert alert-danger");
                request.setAttribute("mensaje", "NO SE PUDO ELIMINAR LA VENTA");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ID DE VENTA INVÁLIDO");
        }

        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
    }

    private void verDetallesVenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            System.out.println("ID recibido: " + id);
            VentaDAO daoVenta = new VentaDAO();
            ClienteDAO daoCliente = new ClienteDAO(); // 👈 Asegurate de importar esto
            DetalleVentaDAO daoDetalle = new DetalleVentaDAO(); // Opcional si mostrás detalles
            ProductoDAO daoProducto = new ProductoDAO();
            Venta venta = daoVenta.getId(id);

            if (venta != null) {
                // Obtener el cliente asociado a la venta
                Cliente cliente = daoCliente.getId(venta.getClienteId());

                // Opcional: si necesitás también los detalles
                List<DetalleVenta> detalles = daoDetalle.getDetallesPorVenta(id);

                for (DetalleVenta detalle : detalles) {
                    Producto producto = daoProducto.getId(detalle.getProductoId());
                    detalle.setNombreProducto(producto.getNombre());
                }
                for (DetalleVenta d : detalles) {
                    System.out.println("Producto: " + d.getNombreProducto());
                }

                List<Producto> productos = daoProducto.getProductos(); // o listarConStock() si filtrás
                // Cargar productos con stock

                request.setAttribute("productos", productos);

                venta.setDetalles(detalles);
                // Enviamos todo a la vista

                request.setAttribute("venta", venta);
                request.setAttribute("cliente", cliente); // 👈 Mandamos el cliente a la vista
                request.setAttribute("productos", productos);

                // También cargar detalles de la venta si es necesario
                request.setAttribute("contenido", "verVenta.jsp");

                request.getRequestDispatcher("template.jsp").forward(request, response);

            } else {
                request.setAttribute("config", "alert alert-danger");
                request.setAttribute("mensaje", "VENTA NO ENCONTRADA");
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
            }
        } catch (NumberFormatException e) {
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ID DE VENTA INVÁLIDO");
            request.getRequestDispatcher("mensaje.jsp").forward(request, response);
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

    @Override
    public String getServletInfo() {
        return "Controlador de Ventas";
    }
}
