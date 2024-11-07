package control.servicios;

import java.util.List;

import modelo.Carrito;
import modelo.dao.CarritoDao;

public class ServiciosCarrito {

	// Método para comprobar si el carrito del usuario existe y, si no, crear uno
	// vacío
	public static List<Carrito> comprobarCarrito(int idCliente) {
		List<Carrito> carrito = CarritoDao.getCarritoPorUsuario(idCliente);
		// Si no hay carrito, creamos uno vacío
		if (carrito == null || carrito.isEmpty()) {
			Carrito carritoVacio = new Carrito(idCliente); // Creamos un carrito vacío para el usuario
			guardarCarrito(carritoVacio); // Guardamos el carrito vacío
			carrito.add(carritoVacio); // Añadimos el carrito vacío a la lista
		}
		return carrito;
	}

	// Método para fusionar el carrito del usuario con el carrito anónimo
	public static boolean fusionarCarritos(int usuarioId, List<Carrito> carritoAnonimo) {
		// Obtener el carrito del usuario
		List<Carrito> carritoUsuario = CarritoDao.getCarritoPorUsuario(usuarioId);

		// Si no existe carrito del usuario, no se puede fusionar
		if (carritoUsuario == null) {
			System.out.println("El carrito del usuario no existe.");
			return false;
		}

		// Llamamos al DAO para fusionar los carritos
		return CarritoDao.fusionarCarritosDao(carritoUsuario, carritoAnonimo);
	}

	// Método para guardar o actualizar un carrito (un solo carrito)
	public static boolean guardarCarrito(Carrito carrito) {
		return CarritoDao.saveOrUpdateCarrito(carrito);
	}

	// Método para guardar o actualizar una lista de carritos
	public static boolean guardarCarrito(List<Carrito> carrito) {
		// Aquí llamas a un DAO para guardar el carrito
		return CarritoDao.saveOrUpdateCarrito(carrito);
	}

	public static boolean eliminarProductoDelCarrito(int carritoId) {
		return CarritoDao.deleteCarrito(carritoId);
	}

	// Método para actualizar las unidades de un producto en el carrito
	public static boolean actualizarUnidadesProducto(int usuarioId, int productoId, int nuevasUnidades) {
		if (nuevasUnidades < 0) {
			System.out.println("La cantidad de unidades no puede ser negativa.");
			return false;
		}
		return CarritoDao.updateUnidadesCarrito(usuarioId, productoId, nuevasUnidades);
	}
}
