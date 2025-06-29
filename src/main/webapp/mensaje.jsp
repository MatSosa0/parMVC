<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <title>Mensaje - Sistema de Gestión</title>
    <link href="./Bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
        }
        .alert-container {
            max-width: 600px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 12px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
            background-color: white;
        }
        .btn-volver {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container alert-container text-center">
        <div class="${config} fs-5 py-3" role="alert">
            <strong>${mensaje}</strong>
        </div>
        <a href="Controlador?accion=listar" class="btn btn-primary btn-volver">Volver al inicio</a>
    </div>

    <script src="./Bootstrap/js/bootstrap.bundle.js"></script>
</body>
</html>
