<%@page import="java.util.List"%>
<%@page import="modelo.Proveedor"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Proveedores</title>
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
    <h2>Listado de Proveedores</h2>
    <form class="mb-3">
        <a href="ControladorProveedores?accion=nuevo" class="btn btn-outline-primary ms-2">Agregar Proveedor</a>
        <a href="<%= request.getContextPath() %>/Controlador?accion=listar" class="btn btn-outline-primary ms-2">Volver</a>
    </form>
    <div style="max-height: 427px; overflow-y: auto;">
        <table class="table table-bordered">
            <thead class="table-dark">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>RUC</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Proveedor> lista = (List<Proveedor>) request.getAttribute("listaProveedores");
                    if (lista != null) {
                        for (Proveedor p : lista) {
                %>
                    <tr>
                        <td><%= p.getId() %></td>
                        <td><%= p.getNombre() %></td>
                        <td><%= p.getRuc() %></td>
                        <td><%= p.getCorreo() %></td>
                        <td><%= p.getTelefono() %></td>
                        <td><%= p.getDireccion() %></td>
                        <td>
                            <a href="ControladorProveedores?accion=Editar&id=<%= p.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                            <a href="ControladorProveedores?accion=Delete&id=<%= p.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar proveedor?')">Eliminar</a>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
