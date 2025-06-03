<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Objects"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión - Editar Usuario</title>
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
            <form action="ControladorUsuarios" method="POST">
                <div class="card border-info mb-4" style="width: 18rem;">
                    <div class="card-header">
                        ACTUALIZAR USUARIO
                    </div>

                    <div class="card-body text-info">
                        <div class="form-group">
                            <label>ID</label>
                            <input type="text" value="${usuario.id}" name="txtId" readonly="" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>NOMBRE</label>
                            <input type="text" value="${usuario.nombre}" name="txtNombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>CONTRASENIA</label>
                            <input type="password" value="${usuario.contrasenia}" name="txtContrasenia" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>ADMINISTRADOR</label>
                            <label>CATEGORIA</label>
                            
                            <select name="txtAdministrador" class="form-control">
                            <c:choose>
                                <c:when test="${usuario.administrador == 0}">
                                <option selected="selected" value="0">No</option>
                                <option value="1">Si</option>
                                </c:when>
                                <c:otherwise>
                                <option value="0">No</option>
                                <option selected="selected" value="1">Si</option>  
                                </c:otherwise>
                            </c:choose>
                            
                            </select>
                        </div>
                        
                    </div>
                    <div class="card-footer">
                        <input type="submit" value="Actualizar" name="accion" class="btn btn-outline-success">
                        <a href="ControladorUsuarios?accion=listar" class="btn-link ms-2">Volver</a>
                    </div>
                </div>
            </form>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>