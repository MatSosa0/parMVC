<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Agregar Proveedor</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
    }
%>

<div class="container mt-4">
    <form action="ControladorProveedores" method="POST">
        <div class="card border-info" style="max-width: 600px; margin: auto;">
            <div class="card-header">AGREGAR PROVEEDOR</div>
            <div class="card-body text-info">
                <div class="form-group mb-2">
                    <label>Nombre</label>
                    <input type="text" name="nombre" class="form-control" required>
                </div>
                <div class="form-group mb-2">
                    <label>RUC</label>
                    <input type="text" name="ruc" class="form-control" required>
                </div>
                <div class="form-group mb-2">
                    <label>Correo</label>
                    <input type="email" name="correo" class="form-control">
                </div>
                <div class="form-group mb-2">
                    <label>Teléfono</label>
                    <input type="text" name="telefono" class="form-control">
                </div>
                <div class="form-group mb-2">
                    <label>Dirección</label>
                    <input type="text" name="direccion" class="form-control">
                </div>
            </div>
            <div class="card-footer">
                <input type="submit" value="Agregar" name="accion" class="btn btn-outline-success">
                <a href="ControladorProveedores?accion=listar" class="btn btn-outline-primary ms-2">Volver</a>
            </div>
        </div>
    </form>
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
