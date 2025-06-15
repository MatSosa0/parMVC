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
import modelo.Venta;
import modelo.DetalleVenta;
import modeloDAO.VentaDAO;
import modeloDAO.DetalleVentaDAO;

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
            default:
                listarVentas(request, response);
        }
    }
    
    private void listarVentas(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        VentaDAO daoVenta = new VentaDAO();
        List<Venta> ventas = daoVenta.getVentas();
        request.setAttribute("ventas", ventas);
        request.getRequestDispatcher("/ventas/listadoVentas.jsp").forward(request, response);
    }
    
    private void mostrarFormularioNuevo(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Aquí deberías cargar clientes y productos para los selects
        // request.setAttribute("clientes", clienteDAO.listarClientes());
        // request.setAttribute("productos", productoDAO.listarProductosConStock());
        request.getRequestDispatcher("/ventas/addVenta.jsp").forward(request, response);
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
            VentaDAO daoVenta = new VentaDAO();
            Venta venta = daoVenta.getId(id);
            
            if (venta != null) {
                request.setAttribute("venta", venta);
                // También cargar detalles de la venta si es necesario
                request.getRequestDispatcher("/ventas/editarVenta.jsp").forward(request, response);
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
        
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String numeroFactura = request.getParameter("numeroFactura");
            Integer clienteId = Integer.parseInt(request.getParameter("clienteId"));
            String formaPago = request.getParameter("formaPago");
            Double totalFactura = Double.parseDouble(request.getParameter("totalFactura"));
            
            Venta venta = new Venta();
            venta.setId(id);
            venta.setNumeroFactura(numeroFactura);
            venta.setFecha(new Date());
            venta.setClienteId(clienteId);
            venta.setFormaPago(formaPago);
            venta.setTotalFactura(totalFactura);
            
            int resultado = daoVenta.update(venta);
            
            if (resultado > 0) {
                request.setAttribute("config", "alert alert-success");
                request.setAttribute("mensaje", "VENTA ACTUALIZADA CON ÉXITO");
            } else {
                request.setAttribute("config", "alert alert-danger");
                request.setAttribute("mensaje", "NO SE PUDO ACTUALIZAR LA VENTA");
            }
        } catch (Exception e) {
            request.setAttribute("config", "alert alert-danger");
            request.setAttribute("mensaje", "ERROR: " + e.getMessage());
        }
        
        request.getRequestDispatcher("mensaje.jsp").forward(request, response);
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
            VentaDAO daoVenta = new VentaDAO();
            DetalleVentaDAO daoDetalle = new DetalleVentaDAO();
            
            Venta venta = daoVenta.getId(id);
            List<DetalleVenta> detalles = daoDetalle.getDetallesPorVenta(id);
            
            if (venta != null) {
                venta.setDetalles(detalles);
                request.setAttribute("venta", venta);
                request.getRequestDispatcher("/ventas/detallesVenta.jsp").forward(request, response);
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