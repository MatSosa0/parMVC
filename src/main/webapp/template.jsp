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
            width: 230px;
            background-color: #f8f9fa;
            padding-top: 20px;
            overflow-y: auto;
        }
        .main-content {
            margin-left: 240px;
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
        return;
    }
%>

<!-- Menú lateral -->
<div class="sidebar">
    <h5 class="text-center">Menú</h5>
    <ul class="nav flex-column px-3">

        <!-- Navegación principal -->
        <li class="nav-item"><a href="ControladorProductos?accion=listar" class="nav-link">Productos</a></li>
        <li class="nav-item"><a href="ControladorClientes?accion=listar" class="nav-link">Clientes</a></li>
        <li class="nav-item"><a href="ControladorProveedores?accion=listar" class="nav-link">Proveedores</a></li>
        <li class="nav-item"><a href="ControladorCompras?accion=listar" class="nav-link">Compras</a></li>
        <li class="nav-item"><a href="ControladorVentas?accion=listar" class="nav-link">Ventas</a></li>

        <!-- Reportes -->
        <li class="nav-item mt-3 fw-bold text-secondary">Reportes</li>
        <li class="nav-item"><a href="ReporteInventario" class="nav-link">Inventario de productos</a></li>
        <li class="nav-item"><a href="ReporteVentas" class="nav-link">Ventas por fecha</a></li>
        <li class="nav-item"><a href="ReporteCompras" class="nav-link">Compras por fecha</a></li>
        <li class="nav-item"><a href="ReporteProductosMasVendidos" class="nav-link">Productos más vendidos</a></li>
        <li class="nav-item"><a href="ReporteTopClientes" class="nav-link">Top 15 Clientes</a></li>
        <li class="nav-item"><a href="ReporteTopProveedores" class="nav-link">Top 15 Proveedores</a></li>
        <li class="nav-item"><a href="ReporteUtilidades" class="nav-link">Utilidades por producto</a></li>

        <!-- Admin -->
        <% if (usuario.getAdministrador() == 1) { %>
            <li class="nav-item mt-3 fw-bold text-secondary">Administración</li>
            <li class="nav-item"><a href="ControladorUsuarios?accion=listar" class="nav-link">Usuarios</a></li>
            <li class="nav-item"><a href="Auditoria?accion=listar" class="nav-link">Auditoría</a></li>
        <% } %>

        <!-- Salir -->
        <li class="nav-item mt-4"><a href="cerrarSesion" class="nav-link text-danger">Cerrar sesión</a></li>
    </ul>
</div>

<!-- Contenido principal -->
<div class="main-content">
    <jsp:include page="${contenido}" />
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
