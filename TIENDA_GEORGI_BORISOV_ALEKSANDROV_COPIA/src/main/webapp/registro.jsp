<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registro</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/header.css"> 
</head>
<body>
    <%@ include file="./fragments/header.jsp" %>

    <div class="container mt-5">
        <h2 class="text-center">Registro de Usuario</h2>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="RegistroController" method="post">
                    <div class="form-group">
                        <label for="nombre">Nombre</label>
                        <input type="text" class="form-control" id="nombre" name="nombre" required>
                    </div>
                    <div class="form-group">
                        <label for="apellidos">Apellidos</label>
                        <input type="text" class="form-control" id="apellidos" name="apellidos" required>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="email" class="form-control" id="email" name="email" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Registrar</button>
                </form>
              
                <% 
                    String errorMessage = request.getParameter("errorMessage"); 
                    if (errorMessage != null) { 
                %>
                    <div class="alert alert-danger mt-3">
                        <%= errorMessage %>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <%@ include file="./fragments/footer.jsp" %>
</body>
</html>

