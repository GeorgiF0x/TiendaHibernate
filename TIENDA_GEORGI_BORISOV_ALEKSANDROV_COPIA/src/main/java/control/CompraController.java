package control;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.servicios.ServiciosCompra;
import modelo.Carrito;
import modelo.Usuario;

@WebServlet("/CompraController")
public class CompraController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompraController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verifica si el usuario está logueado
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario != null) {
			// Aquí puedes agregar la lógica de visualización o pre-procesamiento antes de
			// la compra
			// Puedes cargar los detalles del carrito, procesar la compra, etc.
			request.getRequestDispatcher("compra.jsp").forward(request, response);
		} else {
			// Si no hay sesión activa, redirige al login
			request.setAttribute("error", "Por favor, inicie sesión para realizar la compra.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verifica si el usuario está logueado
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario != null) {
			// Obtén el carrito del usuario
			List<Carrito> carrito = (List<Carrito>) request.getSession().getAttribute("carrito");

			if (carrito == null || carrito.isEmpty()) {
				request.setAttribute("error", "Tu carrito está vacío.");
				request.getRequestDispatcher("carrito.jsp").forward(request, response);
				return;
			}

			// Aquí se puede hacer el procesamiento de la compra
			// Esto es un ejemplo, puedes implementar la lógica necesaria como restar el
			// stock, realizar el pago, etc.
			boolean compraExitosa = ServiciosCompra.tramitarCompra(usuario.getId());

			if (compraExitosa) {
				// Si la compra fue exitosa, redirige a una página de confirmación
				request.setAttribute("mensaje", "Compra realizada con éxito.");
				request.getRequestDispatcher("confirmacionCompra.jsp").forward(request, response);
			} else {
				// Si hubo un error en el proceso de compra
				request.setAttribute("error", "Hubo un error al procesar tu compra.");
				request.getRequestDispatcher("carrito.jsp").forward(request, response);
			}
		} else {
			// Si no hay sesión activa, redirige al login
			request.setAttribute("error", "Por favor, inicie sesión para realizar la compra.");
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}
	}
}
