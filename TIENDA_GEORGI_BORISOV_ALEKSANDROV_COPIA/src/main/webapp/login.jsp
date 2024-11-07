<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
	<link rel="stylesheet" href="./css/header.css"> 
</head>
<body>
    <%@ include file="./fragments/header.jsp" %>

    <div class="container mt-5">
        <h2 class="text-center">Iniciar Sesión</h2>
        <div class="row justify-content-center">
            <div class="col-md-6">
                <form action="LoginController" method="post">
                    <div class="form-group">
                        <label for="username">Usuario</label>
                        <input type="text" class="form-control" id="username" name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Contraseña</label>
                        <input type="password" class="form-control" id="password" name="password" required>
                    </div>
                    <button type="submit" class="btn btn-primary btn-block">Entrar</button>
                </form>
              
                <% String error = request.getParameter("error"); 
                   if (error != null) { %>
                    <div class="alert alert-danger mt-3">
                        Usuario o contraseña incorrectos. Inténtalo de nuevo.
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <%@ include file="./fragments/footer.jsp" %>
</body>
</html>
