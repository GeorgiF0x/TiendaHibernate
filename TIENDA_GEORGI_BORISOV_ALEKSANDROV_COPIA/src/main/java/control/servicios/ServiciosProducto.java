package control.servicios;

import java.util.List;

import modelo.Producto;
import modelo.dao.ProductoDao;

public class ServiciosProducto {

	// Método para obtener el catálogo de productos
	public static List<Producto> obtenerCatalogo() {
		return ProductoDao.getAllProductos(); // Llama al DAO para obtener la lista de productos
	}

	// Método para agregar un nuevo producto
	public static boolean agregarProducto(Producto producto) {
		return ProductoDao.addProducto(producto); // Llama al DAO para agregar un nuevo producto
	}

	// Método para obtener un producto por ID
	public static Producto obtenerProductoPorId(int id) {
		Producto producto = ProductoDao.getProductoById(id); // Llama al DAO para obtener un producto por ID
		if (producto == null) {
			System.out.println("Producto con ID " + id + " no encontrado.");
		}
		return producto;
	}

	// Método para actualizar un producto
	public static boolean actualizarProducto(Producto producto) {
		return ProductoDao.updateProducto(producto); // Llama al DAO para actualizar el producto
	}

	// Método para eliminar un producto por ID
	public static boolean eliminarProducto(int id) {
		return ProductoDao.deleteProducto(id); // Llama al DAO para eliminar el producto por ID
	}
}
