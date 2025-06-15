package controlador;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import modelo.Cliente;
import modeloDAO.ClienteDAO;
import modeloDAO.InterfazClienteDAO;


public class ControladorClientes extends HttpServlet {

    InterfazClienteDAO dao = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion == null) {
            response.sendRedirect("ControladorClientes?accion=listar");
            return;
        }

        switch (accion) {
            case "listar":
                List<Cliente> lista = dao.getClientes();
                request.setAttribute("listaClientes", lista);
                request.setAttribute("contenido", "listarClientes.jsp");
                request.getRequestDispatcher("template.jsp").forward(request, response);
                break;


            case "add":
                request.getRequestDispatcher("addCliente.jsp").forward(request, response);
                break;

            case "editar":
                int idEditar = Integer.parseInt(request.getParameter("id"));
                Cliente clienteEditar = dao.getId(idEditar);
                request.setAttribute("cliente", clienteEditar);
                request.getRequestDispatcher("editarCliente.jsp").forward(request, response);
                break;

            case "eliminar":
                int idEliminar = Integer.parseInt(request.getParameter("id"));
                dao.delete(idEliminar);
                response.sendRedirect("ControladorClientes?accion=listar");
                break;

            default:
                response.sendRedirect("ControladorClientes?accion=listar");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if (accion.equals("Agregar")) {
            Cliente nuevo = new Cliente();
            nuevo.setNombre(request.getParameter("nombre"));
            nuevo.setCedula(request.getParameter("cedula"));
            nuevo.setCorreo(request.getParameter("correo"));
            nuevo.setTelefono(request.getParameter("telefono"));
            nuevo.setDireccion(request.getParameter("direccion"));

            dao.add(nuevo);
            response.sendRedirect("ControladorClientes?accion=listar");
        }

        if (accion.equals("Actualizar")) {
            Cliente actualizado = new Cliente();
            actualizado.setId(Integer.parseInt(request.getParameter("id")));
            actualizado.setNombre(request.getParameter("nombre"));
            actualizado.setCedula(request.getParameter("cedula"));
            actualizado.setCorreo(request.getParameter("correo"));
            actualizado.setTelefono(request.getParameter("telefono"));
            actualizado.setDireccion(request.getParameter("direccion"));

            dao.update(actualizado);
            response.sendRedirect("ControladorClientes?accion=listar");
        }
    }
}
