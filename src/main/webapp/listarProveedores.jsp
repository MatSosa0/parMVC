<%@page import="java.util.List"%>
<%@page import="modelo.Proveedor"%>
<%@page import="modelo.Usuario"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Listado de Proveedores</title>
    <link rel="stylesheet" href="./Bootstrap/css/bootstrap.min.css">
    <!-- ✅ DataTables CSS -->
    <link rel="stylesheet" href="https://cdn.datatables.net/1.13.4/css/jquery.dataTables.min.css">
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
    <h2 class="mb-3">Listado de Proveedores</h2>
    <form class="mb-3">
        <a href="ControladorProveedores?accion=nuevo" class="btn btn-outline-primary ms-2">Agregar Proveedor</a>
        <a href="<%= request.getContextPath() %>/Controlador?accion=listar" class="btn btn-outline-primary ms-2">Volver</a>
    </form>

    <div class="table-responsive">
        <table id="tablaProveedores" class="table table-bordered table-striped">
            <thead class="table-dark text-center">
                <tr>
                    <th>ID</th>
                    <th>Nombre</th>
                    <th>RUC</th>
                    <th>Correo</th>
                    <th>Teléfono</th>
                    <th>Dirección</th>
                    <th>Acciones</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Proveedor> lista = (List<Proveedor>) request.getAttribute("listaProveedores");
                    if (lista != null) {
                        for (Proveedor p : lista) {
                %>
                <tr>
                    <td class="text-center"><%= p.getId() %></td>
                    <td><%= p.getNombre() %></td>
                    <td class="text-center"><%= p.getRuc() %></td>
                    <td><%= p.getCorreo() %></td>
                    <td class="text-center"><%= p.getTelefono() %></td>
                    <td><%= p.getDireccion() %></td>
                    <td class="text-center">
                        <a href="ControladorProveedores?accion=Editar&id=<%= p.getId() %>" class="btn btn-warning btn-sm">Editar</a>
                        <a href="ControladorProveedores?accion=Delete&id=<%= p.getId() %>" class="btn btn-danger btn-sm" onclick="return confirm('¿Eliminar proveedor?')">Eliminar</a>
                    </td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<!-- ✅ Scripts necesarios -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.datatables.net/1.13.4/js/jquery.dataTables.min.js"></script>
<script src="./Bootstrap/js/bootstrap.bundle.js"></script>

<script>
    $(document).ready(function () {
        $('#tablaProveedores').DataTable({
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

</body>
</html>
