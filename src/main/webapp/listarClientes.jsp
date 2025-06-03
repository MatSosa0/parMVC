<%@page import="java.util.List"%>
<%@page import="modelo.Cliente"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="modelo.Usuario"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Clientes</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
    }
%>

<div class="container mt-4">
    <h2>Listado de Clientes</h2>
    <a href="ControladorClientes?accion=add" class="btn btn-primary mb-3">Agregar Cliente</a>

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

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
