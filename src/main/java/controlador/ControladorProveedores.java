package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import modelo.Proveedor;
import modeloDAO.ProveedorDAO;
import modeloDAO.InterfazProveedorDAO;

public class ControladorProveedores extends HttpServlet {

    InterfazProveedorDAO daoProveedor = new ProveedorDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        List<Proveedor> proveedores = new ArrayList<>();

        HttpSession session = request.getSession();
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        switch (accion) {
            case "listar":
                proveedores = daoProveedor.getProveedores();
                request.setAttribute("listaProveedores", proveedores);
                request.getRequestDispatcher("listarProveedores.jsp").forward(request, response);
                break;

            case "nuevo":
                request.getRequestDispatcher("addProveedores.jsp").forward(request, response);
                break;

            case "Agregar":
                Proveedor nuevo = new Proveedor();
                nuevo.setNombre(request.getParameter("nombre"));
                nuevo.setRuc(request.getParameter("ruc"));
                nuevo.setCorreo(request.getParameter("correo"));
                nuevo.setTelefono(request.getParameter("telefono"));
                nuevo.setDireccion(request.getParameter("direccion"));

                int resultadoAdd = daoProveedor.add(nuevo);
                if (resultadoAdd != 0) {
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "EL PROVEEDOR SE HA AGREGADO CON ÉXITO");
                } else {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO AGREGAR EL PROVEEDOR");
                }
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;

            case "Editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Proveedor proveedorEditar = daoProveedor.getId(idEditar);
                request.setAttribute("proveedor", proveedorEditar);
                request.getRequestDispatcher("editarProveedores.jsp").forward(request, response);
                break;

            case "Actualizar":
                Proveedor actualizado = new Proveedor();
                actualizado.setId(Integer.parseInt(request.getParameter("id")));
                actualizado.setNombre(request.getParameter("nombre"));
                actualizado.setRuc(request.getParameter("ruc"));
                actualizado.setCorreo(request.getParameter("correo"));
                actualizado.setTelefono(request.getParameter("telefono"));
                actualizado.setDireccion(request.getParameter("direccion"));

                int resultadoUpd = daoProveedor.update(actualizado);
                if (resultadoUpd != 0) {
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "EL PROVEEDOR SE HA ACTUALIZADO CON ÉXITO");
                } else {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO ACTUALIZAR EL PROVEEDOR");
                }
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;

            case "Delete":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                int resultadoDel = daoProveedor.delete(idEliminar);
                if (resultadoDel != 0) {
                    request.setAttribute("config", "alert alert-warning");
                    request.setAttribute("mensaje", "EL PROVEEDOR SE HA ELIMINADO CON ÉXITO");
                } else {
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO ELIMINAR EL PROVEEDOR");
                }
                request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                break;

            default:
                throw new ServletException("Acción no soportada: " + accion);
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
        return "Controlador para CRUD de proveedores";
    }
}
