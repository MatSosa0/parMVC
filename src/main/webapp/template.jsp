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
        /* Cursor pointer para los títulos colapsables */
        .collapsible-header {
            cursor: pointer;
            user-select: none;
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
    <div class="accordion" id="menuAccordion">

        <!-- Menú principal -->
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingMenu">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseMenu" aria-expanded="false" aria-controls="collapseMenu">
                    Menú Principal
                </button>
            </h2>
            <div id="collapseMenu" class="accordion-collapse collapse" aria-labelledby="headingMenu" data-bs-parent="#menuAccordion">
                <div class="accordion-body px-3">
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="ControladorProductos?accion=listar" class="nav-link">Productos</a></li>
                        <li class="nav-item"><a href="ControladorClientes?accion=listar" class="nav-link">Clientes</a></li>
                        <li class="nav-item"><a href="ControladorProveedores?accion=listar" class="nav-link">Proveedores</a></li>
                        <li class="nav-item"><a href="ControladorCompras?accion=listar" class="nav-link">Compras</a></li>
                        <li class="nav-item"><a href="ControladorVentas?accion=listar" class="nav-link">Ventas</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Reportes -->
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingReportes">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseReportes" aria-expanded="false" aria-controls="collapseReportes">
                    Reportes
                </button>
            </h2>
            <div id="collapseReportes" class="accordion-collapse collapse" aria-labelledby="headingReportes" data-bs-parent="#menuAccordion">
                <div class="accordion-body px-3">
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="ReporteInventario" class="nav-link">Inventario de productos</a></li>
                        <li class="nav-item"><a href="ReporteVentas" class="nav-link">Ventas por fecha</a></li>
                        <li class="nav-item"><a href="ReporteCompras" class="nav-link">Compras por fecha</a></li>
                        <li class="nav-item"><a href="ReporteProductosMasVendidos" class="nav-link">Productos más vendidos</a></li>
                        <li class="nav-item"><a href="ReporteTopClientes" class="nav-link">Top 15 Clientes</a></li>
                        <li class="nav-item"><a href="ReporteTopProveedores" class="nav-link">Top 15 Proveedores</a></li>
                        <li class="nav-item"><a href="ReporteUtilidades" class="nav-link">Utilidades por producto</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <!-- Administración (solo para admin) -->
        <% if (usuario.getAdministrador() == 1) { %>
        <div class="accordion-item">
            <h2 class="accordion-header" id="headingAdmin">
                <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse" data-bs-target="#collapseAdmin" aria-expanded="false" aria-controls="collapseAdmin">
                    Administración
                </button>
            </h2>
            <div id="collapseAdmin" class="accordion-collapse collapse" aria-labelledby="headingAdmin" data-bs-parent="#menuAccordion">
                <div class="accordion-body px-3">
                    <ul class="nav flex-column">
                        <li class="nav-item"><a href="ControladorUsuarios?accion=listar" class="nav-link">Usuarios</a></li>
                        <li class="nav-item"><a href="Auditoria?accion=listar" class="nav-link">Auditoría</a></li>
                    </ul>
                </div>
            </div>
        </div>
        <% } %>

        <!-- Salir -->
        <div class="mt-4 px-3">
            <a href="cerrarSesion" class="nav-link text-danger">Cerrar sesión</a>
        </div>

    </div>
</div>

<!-- Contenido principal -->
<div class="main-content">
    <jsp:include page="${contenido}" />
</div>

<script src="./Bootstrap/js/bootstrap.bundle.min.js"></script>
</body>
</html>
