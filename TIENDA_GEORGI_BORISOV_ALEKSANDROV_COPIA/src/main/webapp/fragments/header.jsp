<head>
    <!-- Importa el archivo CSS desde una carpeta de nivel superior -->
    <link rel="stylesheet" href="../css/header.css">
</head>

<!-- Asegúrate de tener Bootstrap y Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">

	<div class="container-fluid">
	    <div class="d-flex justify-content-center align-items-center py-3">
        <img src="${pageContext.request.contextPath}/img/tituloHeader.png" alt="Necromunda City" class="header-img">
    	</div>
    </div>

<nav class="navbar navbar-expand-lg navbar-dark rounded mx-5">
    <a class="navbar-brand d-flex align-items-center" href="${pageContext.request.contextPath}/">
        <img src="${pageContext.request.contextPath}/img/logo.png" alt="Logo TiendaOnline" style="height: 100px; width: auto; margin-right: 8px;">
        <span class="nav-link"><i class="fas fa-home"></i> Inicio</span>
    </a>

    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="#"><i class="fas fa-box-open"></i> Productos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"><i class="fas fa-info-circle"></i> Sobre Nosotros</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#"><i class="fas fa-phone-alt"></i> Contacto</a>
            </li>
        </ul>

        <form class="form-inline my-2 my-lg-0">
            <input class="form-control mr-sm-2" type="search" placeholder="Buscar productos..." aria-label="Buscar">
            <button class="btn btn-danger my-2 my-sm-0" type="submit"><i class="fas fa-search"></i></button>
        </form>

        <ul class="navbar-nav ml-auto">
			  	 <li class="nav-item">
			   		 <a class="nav-link" href="CarritoController"><i class="fas fa-shopping-cart"></i></a>
				</li>
            <% if (request.getSession().getAttribute("usuario") != null) { %>
                <li class="nav-item">
                    <form action="CerrarSesion" method="post">
                        <button type="submit" class="btn btn-outline-danger ml-2"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</button>
                    </form>
                </li>
            <% } else { %>
                <li class="nav-item">
                    <a class="nav-link" href="login.jsp"><i class="fas fa-sign-in-alt"></i>Login</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="registro.jsp"><i class="fas fa-user-plus"></i> Registro</a>
                </li>
            <% } %>
        </ul>
    </div>
</nav>

