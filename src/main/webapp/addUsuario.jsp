<%-- 
    Document   : add
    Created on : 25 oct. 2022, 04:08:08
    Author     : Guiye
--%>

<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión - Agregar Usuario</title>
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
                        AGREGAR USUARIO
                    </div>

                    <div class="card-body text-info">
                        <div class="form-group">
                            <label>NOMBRE</label>
                            <input type="text" name="txtNombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>CONTRASEÑA</label>
                            <input type="password" name="txtContrasenia" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>ADMINISTRADOR</label>
                            <select name="txtAdministrador" class="btn btn-outline-primary ms-2">
                                <option value="0">No</option>
                                <option value="1">Sí</option>
                            </select>
                        </div>
                    </div>
                    <div class="card-footer">
                        <input type="submit" value="Agregar" name="accion" class="btn btn-outline-success">
                        <a href="ControladorUsuarios?accion=listar" class="btn-link ms-2">Volver</a>
                    </div>
                </div>
            </form>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>
