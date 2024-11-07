package control;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.servicios.ServiciosCarrito;
import control.servicios.ServiciosProducto;
import modelo.Carrito;
import modelo.Producto;
import modelo.ProductoAuxiliar;
import modelo.Usuario;

/**
 * Servlet implementation class CarritoController
 */
@WebServlet("/CarritoController")
public class CarritoController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CarritoController() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// *******************************PARA MOSTRAR CARRITO************************S
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Verifica si el usuario está logueado
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");
		List<Carrito> carrito = null;

		// Usamos un HashMap para enviar los productos con sus unidades y la imagen
		HashMap<String, ProductoAuxiliar> productosMap = new HashMap<>();

		if (usuario == null) {
			// Carrito anónimo si el usuario no está logueado
			carrito = (List<Carrito>) request.getSession().getAttribute("carritoAnonimo");
			if (carrito == null) {
				carrito = new ArrayList<>(); // Inicializa un carrito vacío si aún no existe
				request.getSession().setAttribute("carritoAnonimo", carrito);
			}
		} else {
			// Carrito del usuario logueado
			carrito = (List<Carrito>) request.getSession().getAttribute("carrito");
			if (carrito == null) {
				int usuarioId = usuario.getId();
				carrito = ServiciosCarrito.comprobarCarrito(usuarioId);
				request.getSession().setAttribute("carrito", carrito);
			}
		}

		// Obtener detalles completos de cada producto en el carrito y asociar la
		// cantidad
		for (Carrito item : carrito) {
			Producto producto = ServiciosProducto.obtenerProductoPorId(item.getProductoId());
			if (producto != null) {
				// Crear un objeto que contenga el producto y la cantidad de unidades
				ProductoAuxiliar productoAuxiliar = new ProductoAuxiliar(producto, item.getUnidades());
				// Usar el nombre del producto como clave para el HashMap
				productosMap.put(producto.getNombre(), productoAuxiliar);
			}
		}

		// Pasar el HashMap a la vista para mostrar los detalles
		request.setAttribute("productosCarrito", productosMap);

		// Redirige a carrito.jsp
		request.getRequestDispatcher("/carrito.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	// ****************PARA AÑADIR AL CARRITO******************************
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String idProductoCadena = request.getParameter("productoId");
		String cantidadCadena = request.getParameter("cantidad");

		// Verificar que los parámetros no son nulos antes de convertirlos
		if (idProductoCadena == null || cantidadCadena == null) {
			System.out.println("Error: uno o más parámetros son nulos");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Faltan parámetros necesarios");
			return;
		}

		int idProducto;
		int cantidadAddCarrito;

		try {
			idProducto = Integer.parseInt(idProductoCadena);
			cantidadAddCarrito = Integer.parseInt(cantidadCadena);
		} catch (NumberFormatException e) {
			System.out.println("Error: los parámetros no son números válidos");
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos");
			return;
		}

		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		try {
			// Verificar si el usuario está logueado
			if (usuario == null) {
				// Gestión del carrito anónimo
				List<Carrito> carritoAnonimo = (List<Carrito>) request.getSession().getAttribute("carritoAnonimo");

				if (carritoAnonimo == null) {
					carritoAnonimo = new ArrayList<>();
					request.getSession().setAttribute("carritoAnonimo", carritoAnonimo);
				}

				boolean productoExistente = false;
				for (Carrito item : carritoAnonimo) {
					if (item.getProductoId() == idProducto) {
						int cantidadAnterior = item.getUnidades();
						item.setUnidades(cantidadAnterior + cantidadAddCarrito);
						productoExistente = true;

						System.out.println("Producto actualizado en el carrito anónimo:");
						System.out.println("ID Producto: " + idProducto);
						System.out.println("Cantidad anterior: " + cantidadAnterior);
						System.out.println("Cantidad añadida: " + cantidadAddCarrito);
						System.out.println("Nueva cantidad: " + item.getUnidades());
						break;
					}
				}

				if (!productoExistente) {
					Carrito nuevoItem = new Carrito(0, 0, idProducto, cantidadAddCarrito); // Se eliminó new Date()
					carritoAnonimo.add(nuevoItem);

					System.out.println("Producto añadido al carrito anónimo:");
					System.out.println("ID Producto: " + idProducto);
					System.out.println("Cantidad añadida: " + cantidadAddCarrito);
				}
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				// Gestión del carrito del usuario logueado
				int usuarioId = usuario.getId();
				List<Carrito> carritoUsuario = (List<Carrito>) request.getSession().getAttribute("carrito");

				if (carritoUsuario == null) {
					// Si no hay carrito en sesión, obtén el carrito desde la base de datos
					carritoUsuario = ServiciosCarrito.comprobarCarrito(usuarioId);
					request.getSession().setAttribute("carrito", carritoUsuario);
				}

				boolean productoExistente = false;
				for (Carrito item : carritoUsuario) {
					if (item.getProductoId() == idProducto) {
						int cantidadAnterior = item.getUnidades();
						item.setUnidades(cantidadAnterior + cantidadAddCarrito);
						ServiciosCarrito.actualizarUnidadesProducto(usuarioId, idProducto, item.getUnidades());
						productoExistente = true;

						System.out.println("Producto actualizado en el carrito del usuario:");
						System.out.println("ID Producto: " + idProducto);
						System.out.println("Cantidad anterior: " + cantidadAnterior);
						System.out.println("Cantidad añadida: " + cantidadAddCarrito);
						System.out.println("Nueva cantidad: " + item.getUnidades());

						break;
					}
				}

				if (!productoExistente) {
					Carrito nuevoItem = new Carrito(0, usuarioId, idProducto, cantidadAddCarrito);

					carritoUsuario.add(nuevoItem);
					ServiciosCarrito.guardarCarrito(nuevoItem);

					System.out.println("Producto añadido al carrito del usuario:");
					System.out.println("ID Producto: " + idProducto);
					System.out.println("Cantidad añadida: " + cantidadAddCarrito);
				}
				response.sendRedirect(request.getContextPath() + "/");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno del servidor");
		}
	}
}
