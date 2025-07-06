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
    <!-- ✅ DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
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
    <h1 class="h3">Listado de Productos</h1>
    <div class="d-flex justify-content-between mb-3">
        <form class="d-flex" action="Controlador" method="POST">
            <select name="txtCategoria" class="form-select me-2">
                <option value="Todos">Todos</option>
                <option value="Alimentos">Alimentos</option>
                <option value="Bebidas">Bebidas</option>
                <option value="Limpieza">Limpieza</option>
            </select>
            <button type="submit" class="btn btn-outline-primary" value="listar" name="accion">Listar</button>
            <a class="btn btn-outline-primary ms-2" href="Controlador?accion=nuevo">Agregar</a>
            <a class="btn btn-outline-primary ms-2" href="cerrarSesion">SALIR</a>
        </form>
    </div>

    <div class="table-responsive">
        <table id="tablaProductos" class="table table-striped table-hover align-middle">
            <thead class="table-dark text-center">
                <tr>
                    <th>#</th>
                    <th>Nombre</th>
                    <th>Descripción</th>
                    <th>Unidades</th>
                    <th>Costo</th>
                    <th>Precio</th>
                    <th>Categoría</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="producto" items="${Productos}">
                    <tr>
                        <td class="text-center">${producto.id}</td>
                        <td>${producto.nombre}</td>
                        <td>${producto.descripcion}</td>
                        <td class="text-center">${producto.unidades}</td>
                        <td class="text-center">${producto.costo}</td>
                        <td class="text-center">${producto.precio}</td>
                        <td class="text-center">${producto.categoria}</td>
                        <td class="text-nowrap text-center">
                            <a href="Controlador?accion=Editar&id=${producto.id}" class="btn btn-sm btn-warning me-1">Editar</a>
                            <a href="Controlador?accion=Delete&id=${producto.id}" class="btn btn-sm btn-danger">Eliminar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<!-- ✅ Scripts necesarios -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script>
    $(document).ready(function () {
    $('#tablaProductos').DataTable({
        pageLength: 7,
            lengthMenu: [ [5, 7, 10, 25, 50, -1], [5, 7, 10, 25, 50, "Todos"] ],
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

<script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
