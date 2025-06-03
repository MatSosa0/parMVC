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
        <link href="./css/css.css" rel="stylesheet">
    </head>
    <body>
        
        <% 
            HttpSession sesion = request.getSession();
            Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
            
            if (usuario == null){
                response.sendRedirect("index.jsp");
            }
            else if (usuario.getAdministrador() == 0){
                response.sendRedirect("listadoProductos.jsp");
            }
        %>
        
        <div class="container mt-4">
            <h1 class="h3">Usuarios del sistema</h1>
            <div class="d-flex justify-content-between">
                <div class="d-flex">
                    <a class="btn btn-outline-danger" href="ControladorUsuarios?accion=listar">Listar</a>
                    <a class="btn btn-outline-danger ms-2" href="ControladorUsuarios?accion=nuevo">Agregar</a>
                    <a class="btn btn-outline-danger ms-2" href="Controlador?accion=listar">Volver</a>
                    <a class="btn btn-outline-danger ms-2" href="cerrarSesion">SALIR</a>
                </div> 
                
            </div>
            <hr>
            <table class="table">
                <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nombres</th>
                        <th scope="col">Contraseña</th>
                        <th scope="col">Administrador</th>
                        <th scope="col">Acciones</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <c:forEach var = "usuario" items="${Usuarios}">
                        <tr>
                            <th scope="row">${usuario.id}</th>
                            <td>${usuario.nombre}</td>
                            <td class="hidetext">${usuario.contrasenia}</td>
                            <c:choose>
                                <c:when test="${usuario.administrador == 1}">
                                <td>Sí</td>
                                </c:when>
                                <c:otherwise>
                                <td>No</td>
                                </c:otherwise>
                            </c:choose>
                            <td>
                                <a href="ControladorUsuarios?accion=Editar&id=${usuario.id}" class="btn btn-outline-warning">Editar</a>
                                <a href="ControladorUsuarios?accion=Delete&id=${usuario.id}" class="btn btn-outline-danger">Eliminar</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>
