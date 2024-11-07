package control.servicios;

import java.util.List;

import modelo.Carrito;
import modelo.Producto;
import modelo.dao.CarritoDao;
import modelo.dao.ProductoDao;

public class ServiciosCompra {

	// Método para tramitar una compra
	public static boolean tramitarCompra(int usuarioId) {
		// Paso 1: Verificar stock de todos los productos en el carrito
		List<Carrito> carritoUsuario = CarritoDao.getCarritoPorUsuario(usuarioId);
		boolean stockDisponible = true;

		for (Carrito item : carritoUsuario) {
			Producto producto = ProductoDao.getProductoById(item.getProductoId());
			if (producto == null || producto.getStock() < item.getUnidades()) {
				System.out.println("Stock insuficiente para el producto: " + producto.getNombre());
				stockDisponible = false;
				break;
			}
		}

		// Si no hay stock disponible, abortar la compra
		if (!stockDisponible) {
			return false;
		}

		// Paso 2: Actualizar el stock de los productos en el carrito
		for (Carrito item : carritoUsuario) {
			Producto producto = ProductoDao.getProductoById(item.getProductoId());
			if (producto != null) {
				// Actualizar el stock
				int nuevoStock = producto.getStock() - item.getUnidades();
				producto.setStock(nuevoStock);
				boolean actualizado = ProductoDao.updateProducto(producto);
				if (!actualizado) {
					System.out.println("Error al actualizar el stock para el producto: " + producto.getNombre());
					return false;
				}
			}
		}

		// Paso 3: Vaciar el carrito
		boolean carritoVaciado = CarritoDao.deleteCarrito(usuarioId);
		if (!carritoVaciado) {
			System.out.println("Error al vaciar el carrito.");
			return false;
		}

		// Si todo salió bien, la compra ha sido procesada correctamente
		System.out.println("Compra realizada con éxito.");
		return true;
	}
}
