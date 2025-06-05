<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Sistema de Gestión</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
    <style>
        .sidebar {
            height: 100vh;
            position: fixed;
            top: 0;
            left: 0;
            width: 220px;
            background-color: #f8f9fa;
            padding-top: 20px;
        }
        .main-content {
            margin-left: 230px;
            padding: 20px;
        }
    </style>
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
    }
%>

<!-- Menú lateral -->
<div class="sidebar">
    <h5 class="text-center">Menú</h5>
    <ul class="nav flex-column px-3">
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
        <li class="nav-item mt-3"><a href="cerrarSesion" class="nav-link text-danger">Cerrar sesión</a></li>
    </ul>
</div>

<!-- Contenido dinámico -->
<div class="main-content">
    <jsp:include page="${contenido}" />
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
