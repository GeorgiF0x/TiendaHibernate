<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Producto" %> 

<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Catálogo de Productos</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css"> 	
    <link rel="stylesheet" href="./css/header.css"> 
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
</head>
<body>
    <%@ include file="./fragments/header.jsp" %>
	
	<div class="container mt-5">
        <h1 class="text-center">Catálogo de Productos</h1>
        <div class="row">
    <%
        // Obtener la lista de productos del request
        List<Producto> productos = (List<Producto>) request.getAttribute("productos");
        if (productos != null && !productos.isEmpty()) {
            for (Producto producto : productos) {
    %>
<div class="col-md-4 mb-4">
    <div class="card">
        <img src="./<%= producto.getImagen() %>" class="card-img-top img-fluid" alt="Imagen de Producto" style="object-fit: contain; height: 200px;">
        <div class="card-body">
            <h5 class="card-title"><%= producto.getNombre() %></h5>
            <p class="card-text">Descripción: <%= producto.getDescripcion() %></p>
            <p class="card-text">Precio: $<%= producto.getPrecio() %></p>
            <form action="CarritoController" method="post" class="d-flex align-items-center mt-3">
                <input type="hidden" name="productoId" value="<%= producto.getId() %>">
                <input type="hidden" name="productoNombre" value="<%= producto.getNombre() %>">
                <input type="number" name="cantidad" min="1" class="form-control me-2" placeholder="Cantidad" required style="width: 70px;">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-shopping-cart"></i> Añadir
                </button>
            </form>
        </div>
    </div>
</div>




    <%
            }
        } else {
    %>
            <div class="col-12">
                <p class="text-center">No hay productos disponibles en este momento.</p>
            </div>
    <%
        }
    %>
        </div> <!-- Cierre de .row -->
    </div> <!-- Cierre de .container -->

    <%@ include file="./fragments/footer.jsp" %>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>


