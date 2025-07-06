<%@page import="modelo.Usuario"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Usuarios del sistema</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
    <link rel="stylesheet" href="./css/css.css">
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
    <h2 class="mb-4">Usuarios del sistema</h2>

    <!-- Botones principales -->
    <div class="mb-3">
        <a class="btn btn-outline-danger" href="ControladorUsuarios?accion=listar">Listar</a>
        <a class="btn btn-outline-danger ms-2" href="ControladorUsuarios?accion=nuevo">Agregar</a>
        <a class="btn btn-outline-danger ms-2" href="<%= request.getContextPath() %>/Controlador?accion=listar">Volver</a>
        <a class="btn btn-outline-danger ms-2" href="cerrarSesion">SALIR</a>
    </div>

    <!-- Tabla -->
    <table id="tablaUsuarios" class="display table table-bordered table-striped">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Contraseña</th>
                <th>Administrador</th>
                <th>Acciones</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="usuario" items="${Usuarios}">
                <tr>
                    <td>${usuario.id}</td>
                    <td>${usuario.nombre}</td>
                    <td class="hidetext">${usuario.contrasenia}</td>
                    <td>
                        <c:choose>
                            <c:when test="${usuario.administrador == 1}">Sí</c:when>
                            <c:otherwise>No</c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <a href="ControladorUsuarios?accion=Editar&id=${usuario.id}" class="btn btn-outline-warning btn-sm">Editar</a>
                        <a href="ControladorUsuarios?accion=Delete&id=${usuario.id}" class="btn btn-outline-danger btn-sm">Eliminar</a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>

<!-- Scripts necesarios -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="./Bootstrap/js/bootstrap.bundle.js"></script>

<script>
    $(document).ready(function () {
        $('#tablaUsuarios').DataTable({
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
            dom: 'rtip<"d-flex justify-content-end mt-2"l>' // Buscador arriba, paginación y "filas por página" abajo
        });
    });
</script>

</body>
</html>
