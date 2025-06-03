package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.Usuario;
import modeloDAO.UsuarioDAO;

/**
 *
 * @author Guiye
 */
public class ControladorUsuarios extends HttpServlet {

    UsuarioDAO DaoUsuario;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        List<Usuario> usuarios = new ArrayList<>();
        HttpSession session = request.getSession();
        String nombreUsuario = (String) session.getAttribute("nombreUsuario");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");
            
        switch (accion) {
            case "listar":
                DaoUsuario = new UsuarioDAO();
                usuarios = DaoUsuario.getUsuarios();
                request.setAttribute("Usuarios", usuarios);
                request.getRequestDispatcher("listadoUsuarios.jsp").forward(request, response);
                break;
            case "nuevo":
                request.getRequestDispatcher("addUsuario.jsp").forward(request, response);
                break;
            case "Agregar":
                int resultado;
                String nombre = request.getParameter("txtNombre");
                String contrasenia = request.getParameter("txtContrasenia");
                Integer administrador = Integer.valueOf(request.getParameter("txtAdministrador"));
                Usuario usuario = new Usuario(nombre, contrasenia, administrador);
                resultado = DaoUsuario.add(usuario);
                if (resultado != 0){
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "EL USUARIO SE HA AGREGADO CON ÉXITO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }else{
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO GUARDAR EL USUARIO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
                break;
                case "Editar":
                int id = Integer.valueOf(request.getParameter("id"));
                Usuario u = DaoUsuario.getId(id);
                request.setAttribute("usuario", u);
                request.getRequestDispatcher("editarUsuario.jsp").forward(request, response);
                break;
                case "Actualizar":
                int idUsuario1 = Integer.valueOf(request.getParameter("txtId"));
                String nombre1 = request.getParameter("txtNombre");
                String contrasenia1 = request.getParameter("txtContrasenia");
                Integer Administrador1 = Integer.valueOf(request.getParameter("txtAdministrador"));
                Usuario usuario1 = new Usuario(idUsuario1, nombre1, contrasenia1, Administrador1);
                int respuesta = DaoUsuario.update(usuario1);
                if (respuesta != 0){
                    request.setAttribute("config", "alert alert-success");
                    request.setAttribute("mensaje", "EL PRODUCTO SE HA ACTUALIZADO CON ÉXITO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }else{
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO ACTUALIZAR EL PRODUCTO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
                break;
                case "Delete":
                int idUsu = Integer.valueOf(request.getParameter("id"));
                Usuario usuarioABorrar = DaoUsuario.getId(idUsu);
                int res = DaoUsuario.delete(idUsu);
                if (res != 0){
                    request.setAttribute("config", "alert alert-warning");
                    request.setAttribute("mensaje", "EL USUARIO SE HA ELIMINADO CON ÉXITO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }else{
                    request.setAttribute("config", "alert alert-danger");
                    request.setAttribute("mensaje", "NO SE HA PODIDO ELIMINAR EL USUARIO");
                    request.getRequestDispatcher("mensaje.jsp").forward(request, response);
                }
                break;
            default:
                throw new AssertionError();
        }
        
        /*response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
             TODO output your page here. You may use following sample code. 
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorUsuarios</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorUsuarios at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
        */
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
