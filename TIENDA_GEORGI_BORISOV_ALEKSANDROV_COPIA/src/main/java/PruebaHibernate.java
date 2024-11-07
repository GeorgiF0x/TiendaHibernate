import java.util.List;

import modelo.Carrito;
import modelo.dao.CarritoDao;
import modelo.dao.ProductoDao;

public class PruebaHibernate {

	public static void main(String[] args) {
		ProductoDao productoService = new ProductoDao();
		CarritoDao carritoService = new CarritoDao();

//		// Crear un nuevo producto
//		Producto nuevoProducto = Producto.builder().nombre("Prueba").descripcion("Descripción del producto 1")
//				.precio(100.0).impuesto(21.0).stock(50).baja(false).imagen("img/producto1.jpg").build();
//
//		// Guardar el producto
//		boolean resultadoProducto = productoService.addProducto(nuevoProducto);
//		if (resultadoProducto) {
//			System.out.println("Producto guardado con éxito.");
//		} else {
//			System.out.println("Error al guardar el producto.");
//		}
//
//		// Obtener todos los productos y mostrarlos
//		List<Producto> productos = productoService.getAllProductos();
//		productos.forEach(System.out::println);
//
//		// Crear un nuevo carrito
//		Carrito nuevoCarrito = new Carrito(1, 1, 1, 3);
//		boolean resultadoCarrito = carritoService.addProductoAlCarrito(nuevoCarrito);
//		if (resultadoCarrito) {
//			System.out.println("Producto agregado al carrito con éxito.");
//		} else {
//			System.out.println("Error al agregar el producto al carrito.");
//		}
//
//		int productoId = 1; // ID del producto que deseas verificar
//		int cantidadDeseada = 3; // Cantidad que deseas reducir del stock
//
//		// Verificar si hay suficiente stock
//		boolean hayStock = productoService.verificarStock(productoId, cantidadDeseada);

//		if (hayStock) {
//			// Si hay stock suficiente, reducirlo
//			boolean resultadoStock = productoService.reducirStock(productoId, cantidadDeseada);
//			if (resultadoStock) {
//				System.out.println("Stock reducido con éxito.");
//			} else {
//				System.out.println("Error al reducir el stock.");
//			}
//		} else {
//			System.out.println("Stock insuficiente para la cantidad deseada.");
//		}

		// Obtener carrito por usuario y mostrarlo
		List<Carrito> listaCarritos = carritoService.getCarritoPorUsuario(1);
		listaCarritos.forEach(System.out::println);
	}
}
