<%@page import="modelo.Usuario"%>
<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

        <% 
            HttpSession sesion = request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
            
            if (usuario == null){
                response.sendRedirect("index.jsp");
            }
        %>

<h2>Listado de Clientes</h2>
<form class="mb-3">
    <a href="ControladorClientes?accion=add" class="btn btn-outline-primary ms-2">Agregar Cliente</a>
    <a href="<%= request.getContextPath() %>/Controlador?accion=listar" class="btn btn-outline-primary ms-2">Volver</a>
</form>  

    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>Cédula</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Cliente> lista = (List<Cliente>) request.getAttribute("listaClientes");
                    if (lista != null) {
                        for (Cliente c : lista) {
                %>
                    <tr>
                        <td><%= c.getId() %></td>
                        <td><%= c.getNombre() %></td>
                        <td><%= c.getCedula() %></td>
                        <td><%= c.getCorreo() %></td>
                        <td><%= c.getTelefono() %></td>
                        <td><%= c.getDireccion() %></td>
                        <td>
                            <a href="ControladorClientes?accion=editar&id=<%= c.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                            <a href="ControladorClientes?accion=eliminar&id=<%= c.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Estás seguro?')">Eliminar</a>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
  </div>
