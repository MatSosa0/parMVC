<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión - Listado de productos</title>
        <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
        
        <% 
            HttpSession sesion = request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
            
            if (usuario == null){
                response.sendRedirect("index.jsp");
            }
        %>
        
        <div class="container mt-4">
            <h1 class="h3">Sistema de Gestión</h1>
            <div class="d-flex justify-content-between">
                <form class="d-flex" action="Controlador" method="POST">
                    <select name="txtCategoria" class="btn btn-outline-primary ms-2">
                        <option value="Todos">Todos</option>
                        <option value="Alimentos">Alimentos</option>
                        <option value="Bebidas">Bebidas</option>
                        <option value="Limpieza">Limpieza</option>
                    </select>
                    <button type="submit" class="btn btn-outline-primary" value="listar" name="accion">Listar</button>
                    <a class="btn btn-outline-primary ms-2" href="Controlador?accion=nuevo">Agregar</a>
                    <a class="btn btn-outline-primary ms-2" href="cerrarSesion">SALIR</a>
                </form>
                <% if (usuario != null){
                    if (usuario.getAdministrador() == 1){
                %>
                <div>
                    <a class="btn btn-outline-danger" href="ControladorCompras?accion=nueva">Compras</a>
                    <a class="btn btn-outline-danger" href="ControladorVentas?accion=listar">Ventas</a> <!-- Botón nuevo agregado aquí -->
                    <a class="btn btn-outline-danger" href="ControladorProveedores?accion=listar">Proveedores</a>
                    <a class="btn btn-outline-danger" href="ControladorClientes?accion=listar">Clientes</a>
                    <a class="btn btn-outline-danger" href="ControladorUsuarios?accion=listar">Usuarios</a>
                    <a class="btn btn-outline-danger" href="Auditoria?accion=listar">Auditoria</a>
                    
                    <a class="btn btn-outline-dark mt-1" href="ReporteInventario">Reporte: Inventario</a><!-- Más adelante agregaremos otros botones aquí -->
                    <a class="btn btn-outline-dark mt-1" href="ReporteVentas">Reporte: Ventas por fecha</a>
                    <a class="btn btn-outline-dark mt-1" href="ReporteCompras">Reporte: Compras por fecha</a>
                    <a class="btn btn-outline-dark mt-1" href="ReporteProductosMasVendidos">Reporte: Productos más vendidos</a>
                    <a class="btn btn-outline-dark mt-1" href="ReporteTopClientes">Reporte: Top 15 Clientes</a>
                    <a class="btn btn-outline-dark mt-1" href="ReporteTopProveedores">Reporte: Top 15 Proveedores</a>
                    <a class="btn btn-outline-dark mt-1" href="ReporteUtilidades">Reporte: Utilidades por producto</a>

                </div>
                <% } } %>
            </div>
            <hr>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nombres</th>
                        <th scope="col">Descripción</th>
                        <th scope="col">Unidades</th>
                        <th scope="col">Costo</th>
                        <th scope="col">Precio</th>
                        <th scope="col">Categoría</th>
                        <th scope="col">Acciones</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <c:forEach var = "producto" items="${Productos}">
                        <tr>
                            <th scope="row">${producto.id}</th>
                            <td>${producto.nombre}</td>
                            <td>${producto.descripcion}</td>
                            <td>${producto.unidades}</td>
                            <td>${producto.costo}</td>
                            <td>${producto.precio}</td>
                            <td>${producto.categoria}</td>
                            <td>
                                <a href="Controlador?accion=Editar&id=${producto.id}" class="btn btn-outline-warning">Editar</a>
                                <a href="Controlador?accion=Delete&id=${producto.id}" class="btn btn-outline-danger">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>