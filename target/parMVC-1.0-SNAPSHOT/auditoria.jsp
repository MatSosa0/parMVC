<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión, registro de auditoría</title>
        <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
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
            <h1 class="h3">Registro de cambios</h1>
            <div class="d-flex">
                <a class="btn btn-outline-primary" href="Auditoria?accion=listar">Listar</a>
                <a class="btn btn-outline-primary ms-2" href="Controlador?accion=listar">Volver</a>
                <a class="btn btn-outline-primary ms-2" href="cerrarSesion">SALIR</a>
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
                        <th scope="col">Identificador usuario</th>
                        <th scope="col">Usuario</th>
                        <th scope="col">Accion</th>
                    </tr>
                </thead>
                <tbody class="table-group-divider">
                    <c:forEach var = "registro" items="${registros}">
                        <tr>
                            <th scope="row">${registro.idAuditoria}</th>
                            <td>${registro.nombreProducto}</td>
                            <td>${registro.descripcionProducto}</td>
                            <td>${registro.unidadesProducto}</td>
                            <td>${registro.costoProducto}</td>
                            <td>${registro.precioProducto}</td>
                            <td>${registro.categoriaProducto}</td>
                            <td>${registro.idUsuario}</td>
                            <td>${registro.nombreUsuario}</td>
                            <td>${registro.descripcionAccion}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>
