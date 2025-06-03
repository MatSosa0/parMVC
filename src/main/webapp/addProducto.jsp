<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!doctype html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión - Agregar producto</title>
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
            <form action="Controlador" method="POST">
                <div class="card border-info mb-4" style="width: 18rem;">
                    <div class="card-header">
                        AGREGAR PRODUCTO
                    </div>

                    <div class="card-body text-info">
                        <div class="form-group">
                            <label>NOMBRE</label>
                            <input type="text" name="txtNombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>DESCRIPCION</label>
                            <input type="text" name="txtDescripcion" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>UNIDADES</label>
                            <input type="text" name="txtUnidades" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>COSTO</label>
                            <input type="text" name="txtCosto" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>PRECIO</label>
                            <input type="text" name="txtPrecio" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>CATEGORIA</label>
                            <select name="txtCategoria" class="form-control">
                                <option value="Alimentos">Alimentos</option>
                                <option value="Bebidas">Bebidas</option>
                                <option value="Limpieza">Limpieza</option>
                            </select>
                        </div>
                    </div>
                    <div class="card-footer">
                        <input type="submit" value="Agregar" name="accion" class="btn btn-outline-success">
                        <a href="Controlador?accion=listar" class="btn-link ms-2">Volver</a>
                    </div>
                </div>
            </form>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>
