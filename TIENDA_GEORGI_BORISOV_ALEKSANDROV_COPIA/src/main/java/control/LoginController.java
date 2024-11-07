package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.servicios.ServiciosCarrito;
import control.servicios.ServiciosUsuario;
import modelo.Carrito;
import modelo.Usuario;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String email = request.getParameter("username");
		String clave = request.getParameter("password");

		// Validar usuario
		Usuario usuario = ServiciosUsuario.validarUsuario(email, clave);

		if (usuario != null) {
			// Iniciar sesión para el usuario
			request.getSession().setAttribute("usuario", usuario);
			System.out.println("Usuario validado: " + usuario);

			// Verificar si el carrito anónimo existe en la sesión antes de continuar
			List<Carrito> carritoAnonimo = (List<Carrito>) request.getSession().getAttribute("carritoAnonimo");
			List<Carrito> carritoUsuario = ServiciosCarrito.comprobarCarrito(usuario.getId());
			// si el usuario no tiene carritos crear uno vacio
			if (carritoUsuario == null && carritoUsuario.isEmpty()) {

			}
			if (carritoAnonimo != null && !carritoAnonimo.isEmpty()) {
				// Fusionar carritos: pasamos el usuarioId y las dos listas
				boolean fusionExito = ServiciosCarrito.fusionarCarritos(usuario.getId(), carritoAnonimo); // Cambio aquí

				if (fusionExito) {
					// Guardar el carrito del usuario después de la fusión
					ServiciosCarrito.guardarCarrito(carritoUsuario);

					// Actualizar la sesión con el carrito del usuario y eliminar el carrito anónimo
					request.getSession().setAttribute("carrito", carritoUsuario);
					request.getSession().removeAttribute("carritoAnonimo");
					System.out.println("Carrito anónimo migrado al usuario: " + usuario.getId());
				} else {
					System.out.println("Hubo un problema al fusionar los carritos.");
				}
			} else {
				// Si no hay carrito anónimo, solo cargamos el carrito del usuario desde la base
				// de datos
				request.getSession().setAttribute("carrito", carritoUsuario);
				System.out.println("Carrito cargado desde la base de datos para el usuario: " + usuario.getId());
			}

			// Redirigir al índice o página principal
			response.sendRedirect(request.getContextPath() + "/");

		} else {
			// Si la validación del usuario falla, mostrar mensaje de error
			request.setAttribute("errorMessage", "Usuario o contraseña incorrectos");
			System.out.println("Datos incorrectos");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

}
