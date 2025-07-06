<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Sistema de Gestión - Registro de Auditoría</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
</head>
<body>

<%
    HttpSession sesion = request.getSession();
    Usuario usuario = (Usuario) sesion.getAttribute("usuarioLogueado");

    if (usuario == null){
        response.sendRedirect("index.jsp");
    } else if (usuario.getAdministrador() == 0){
        response.sendRedirect("listadoProductos.jsp");
    }
%>

<div class="container mt-4">
    <h2>Registro de Auditoría</h2>

    <!-- Botones -->
    <div class="mb-3">
        <a class="btn btn-outline-primary" href="Auditoria?accion=listar">Listar</a>
        <a class="btn btn-outline-primary ms-2" href="Controlador?accion=listar">Volver</a>
        <a class="btn btn-outline-primary ms-2" href="cerrarSesion">SALIR</a>
    </div>

    <!-- Tabla -->
    <table id="tablaAuditoria" class="display table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>#</th>
                <th>Nombres</th>
                <th>Descripción</th>
                <th>Unidades</th>
                <th>Costo</th>
                <th>Precio</th>
                <th>Categoría</th>
                <th>ID Usuario</th>
                <th>Usuario</th>
                <th>Acción</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="registro" items="${registros}">
                <tr>
                    <td>${registro.idAuditoria}</td>
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

<!-- Scripts -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="./Bootstrap/js/bootstrap.bundle.js"></script>

<script>
    $(document).ready(function () {
        $('#tablaAuditoria').DataTable({
            pageLength: 7,
            lengthMenu: [[5, 7, 10, 25, 50, -1], [5, 7, 10, 25, 50, "Todos"]],
            language: {
                lengthMenu: "Filas por página: _MENU_",
                zeroRecords: "No se encontraron resultados",
                info: "Mostrando _START_ a _END_ de _TOTAL_ registros",
                infoEmpty: "Mostrando 0 a 0 de 0 registros",
                infoFiltered: "(filtrado de _MAX_ registros totales)",
                search: "Buscar:",
                paginate: {
                    first: "Primero",
                    last: "Último",
                    next: "Siguiente",
                    previous: "Anterior"
                }
            },
            dom: 'rtip<"d-flex justify-content-end mt-2"l>'
        });
    });
</script>

</body>
</html>
