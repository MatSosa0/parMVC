<%@page import="modelo.Proveedor"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Editar Proveedor</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("index.jsp");
    }

    Proveedor proveedor = (Proveedor) request.getAttribute("proveedor");
%>

<div class="container mt-4">
    <form action="ControladorProveedores" method="POST">
        <input type="hidden" name="id" value="<%= proveedor.getId() %>">
        <div class="card border-info" style="max-width: 600px; margin: auto;">
            <div class="card-header">EDITAR PROVEEDOR</div>
            <div class="card-body text-info">
                <div class="form-group mb-2">
                    <label>Nombre</label>
                    <input type="text" name="nombre" class="form-control" value="<%= proveedor.getNombre() %>" required>
                </div>
                <div class="form-group mb-2">
                    <label>RUC</label>
                    <input type="text" name="ruc" class="form-control" value="<%= proveedor.getRuc() %>">
                </div>
                <div class="form-group mb-2">
                    <label>Correo</label>
                    <input type="email" name="correo" class="form-control" value="<%= proveedor.getCorreo() %>">
                </div>
                <div class="form-group mb-2">
                    <label>Teléfono</label>
                    <input type="text" name="telefono" class="form-control" value="<%= proveedor.getTelefono() %>">
                </div>
                <div class="form-group mb-2">
                    <label>Dirección</label>
                    <input type="text" name="direccion" class="form-control" value="<%= proveedor.getDireccion() %>">
                </div>
            </div>
            <div class="card-footer">
                <input type="submit" value="Actualizar" name="accion" class="btn btn-outline-success">
                <a href="ControladorProveedores?accion=listar" class="btn btn-outline-primary ms-2">Volver</a>
            </div>
        </div>
    </form>
</div>

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
