<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Gestión</title>
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

<div class="container-fluid">
    <div class="row">
        <!-- Menú lateral -->
        <div class="col-md-2 bg-light vh-100 p-3">
            <h5>Menú</h5>
            <ul class="nav flex-column">
                <li class="nav-item"><a href="Controlador?accion=listar" class="nav-link">Productos</a></li>
                <li class="nav-item"><a href="ControladorClientes?accion=listar" class="nav-link">Clientes</a></li>
                <li class="nav-item"><a href="ControladorProveedores?accion=listar" class="nav-link">Proveedores</a></li>
                <li class="nav-item"><a href="Compras?accion=listar" class="nav-link">Compras</a></li>
                <li class="nav-item"><a href="Ventas?accion=listar" class="nav-link">Ventas</a></li>
                <li class="nav-item"><a href="Reportes.jsp" class="nav-link">Reportes</a></li>
                <% if (usuario.getAdministrador() == 1) { %>
                <li class="nav-item"><a href="ControladorUsuarios?accion=listar" class="nav-link">Usuarios</a></li>
                <li class="nav-item"><a href="Auditoria?accion=listar" class="nav-link">Auditoría</a></li>
                <% } %>
                <li class="nav-item"><a href="cerrarSesion" class="nav-link text-danger">Cerrar sesión</a></li>
            </ul>
        </div>

        <!-- Contenido dinámico -->
        <div class="col-md-10 p-4">
            <h2>Bienvenido, <%= usuario.getNombre() %></h2>
            <p>Seleccioná una opción del menú lateral para comenzar.</p>
        </div>
    </div>
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
