<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.Map" %>
<%@ page import="modelo.ProductoAuxiliar" %>

<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Carrito de Compras</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="./css/header.css">
</head>
<body>
    <%@ include file="./fragments/header.jsp" %>

    <section class="h-100 h-custom" style="background-color: #eee;">
        <div class="container h-100 py-5">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col">
                    <div class="card shopping-cart" style="border-radius: 15px;">
                        <div class="card-body">
                            <div class="row">
                                <!-- Columna de productos -->
                                <div class="col-lg-6 px-5 py-4">
                                    <h3 class="mb-5 pt-2 text-center fw-bold text-uppercase">Tus Productos</h3>

                                    <% 
                                    Map<String, ProductoAuxiliar> productosCarrito = (Map<String, ProductoAuxiliar>) request.getAttribute("productosCarrito");

                                    if (productosCarrito != null && !productosCarrito.isEmpty()) {
                                        double totalGeneral = 0.0;
                                        for (ProductoAuxiliar productoAux : productosCarrito.values()) {
                                            double precioUnitario = productoAux.getProducto().getPrecio();
                                            int unidades = productoAux.getUnidades();
                                            double totalItem = precioUnitario * unidades;
                                            totalGeneral += totalItem;
                                    %>
                                    <div class="d-flex align-items-center mb-5">
                                        <div class="flex-shrink-0">
                                            <img src="<%= productoAux.getImagen() %>" class="img-fluid" style="width: 150px;" alt="Imagen del producto">
                                        </div>
                                        <div class="flex-grow-1 ms-3">
                                            <h5 class="text-primary"><%= productoAux.getProducto().getDescripcion() %></h5>
                                            <div class="d-flex align-items-center">
                                                <p class="fw-bold mb-0 me-5 pe-3"><%= String.format("%.2f", precioUnitario) %> €</p>
                                                <div class="def-number-input number-input safari_only">
                                                    <input class="quantity fw-bold bg-body-tertiary text-body" 
                                                           name="unidades_<%= productoAux.getProducto().getId() %>" 
                                                           value="<%= productoAux.getUnidades() %>" 
                                                           type="number" min="1">
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <% 
                                        }
                                    %>
                                    <hr class="mb-4" style="height: 2px; background-color: #1266f1; opacity: 1;">
                                    <div class="d-flex justify-content-between px-x">
                                        <p class="fw-bold">Descuento:</p>
                                        <p class="fw-bold">0€</p> <!-- Aquí podrías calcular el descuento si es necesario -->
                                    </div>
                                    <div class="d-flex justify-content-between p-2 mb-2 bg-primary text-white">
                                        <h5 class="fw-bold mb-0">Total:</h5>
                                        <h5 class="fw-bold mb-0"><%= String.format("%.2f", totalGeneral) %> €</h5>
                                    </div>
                                    <% 
                                    } else { 
                                    %>
                                        <div class="text-center text-muted">El carrito está vacío.</div>
                                    <% 
                                    } 
                                    %>
                                </div>

                                <!-- Columna de pago y finalizar compra -->
                                <div class="col-lg-6 px-5 py-4">
                                    <h3 class="mb-5 pt-2 text-center fw-bold text-uppercase">Pago</h3>
                                    <form action="CompraController" method="POST">
                                        <%-- Campos de entrada comentados temporalmente --%>
                                        <%-- 
                                        <div class="form-outline mb-5">
                                            <input type="text" id="nombre" class="form-control form-control-lg" name="nombre" required />
                                            <label class="form-label" for="nombre">Nombre Completo</label>
                                        </div>
                                        <div class="form-outline mb-5">
                                            <input type="email" id="email" class="form-control form-control-lg" name="email" required />
                                            <label class="form-label" for="email">Correo Electrónico</label>
                                        </div>
                                        <div class="form-outline mb-5">
                                            <input type="text" id="direccion" class="form-control form-control-lg" name="direccion" required />
                                            <label class="form-label" for="direccion">Dirección de Envío</label>
                                        </div>
                                        <div class="form-outline mb-5">
                                            <input type="tel" id="telefono" class="form-control form-control-lg" name="telefono" required />
                                            <label class="form-label" for="telefono">Teléfono de Contacto</label>
                                        </div>
                                        --%>
                                        <!-- Botón para finalizar la compra -->
                                        <button type="submit" class="btn btn-primary btn-block btn-lg">Comprar ahora</button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <%@ include file="./fragments/footer.jsp" %>
</body>
</html>
