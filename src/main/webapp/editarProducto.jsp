<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Sistema de Gestión - Editar producto</title>
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
                        ACTUALIZAR PRODUCTO
                    </div>

                    <div class="card-body text-info">
                        <div class="form-group">
                            <label>ID</label>
                            <input type="text" value="${producto.id}" name="txtId" readonly="" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>NOMBRE</label>
                            <input type="text" value="${producto.nombre}" name="txtNombre" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>DESCRIPCION</label>
                            <input type="text" value="${producto.descripcion}" name="txtDescripcion" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>UNIDADES</label>
                            <input type="text" value="${producto.unidades}" name="txtUnidades" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>COSTO</label>
                            <input type="text" value="${producto.costo}" name="txtCosto" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>PRECIO</label>
                            <input type="text" value="${producto.precio}" name="txtPrecio" class="form-control">
                        </div>
                        <div class="form-group">
                            <label>CATEGORIA</label>
                            <select name="txtCategoria" class="form-control">
                                <c:choose>
                                    <c:when test="${producto.categoria == 'Alimentos'}">
                                    <option selected="selected" value="Alimentos">Alimentos</option>
                                    <option value="Bebidas">Bebidas</option>
                                    <option value="Limpieza">Limpieza</option>
                                    </c:when>
                                    <c:when test="${producto.categoria == 'Bebidas'}">
                                    <option value="Alimentos">Alimentos</option>
                                    <option selected="selected" value="Bebidas">Bebidas</option>
                                    <option value="Limpieza">Limpieza</option>
                                    </c:when>
                                    <c:when test="${producto.categoria == 'Limpieza'}">
                                    <option value="Alimentos">Alimentos</option>
                                    <option value="Bebidas">Bebidas</option>
                                    <option selected="selected" value="Limpieza">Limpieza</option>
                                    </c:when>
                                </c:choose>  
                            </select>
                        </div>
                    </div>
                    <div class="card-footer">
                        <input type="submit" value="Actualizar" name="accion" class="btn btn-outline-success">
                        <a href="Controlador?accion=listar" class="btn-link ms-2">Volver</a>
                    </div>
                </div>
            </form>
        </div>
        <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
    </body>
</html>