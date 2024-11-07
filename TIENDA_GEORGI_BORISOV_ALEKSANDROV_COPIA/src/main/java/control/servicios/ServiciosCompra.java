package control.servicios;

import java.util.List;

import modelo.Carrito;
import modelo.Detalle;
import modelo.Pedido;
import modelo.Producto;
import modelo.dao.CarritoDao;
import modelo.dao.DetalleDao;
import modelo.dao.PedidoDao;
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

		// Paso 2: Crear el pedido
		Pedido nuevoPedido = new Pedido();
		nuevoPedido.setUsuarioId(usuarioId);
		nuevoPedido.setFecha(new java.util.Date());
		nuevoPedido.setMetodoPago("Método de pago no especificado"); // Puedes agregar un campo para el pago
		nuevoPedido.setNumFactura("Factura_" + System.currentTimeMillis()); // Generar un número de factura único
		nuevoPedido.setTotal(calcularTotalCompra(carritoUsuario));

		boolean pedidoCreado = PedidoDao.guardarPedido(nuevoPedido);
		if (!pedidoCreado) {
			System.out.println("Error al crear el pedido.");
			return false;
		}

		// Paso 3: Actualizar el stock de los productos en el carrito y crear los
		// detalles de pedido
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

				// Crear detalle del pedido
				Detalle detalle = new Detalle();
				detalle.setPedidoId(nuevoPedido.getId());
				detalle.setProductoId(producto.getId());
				detalle.setUnidades(item.getUnidades());
				detalle.setPrecioUnidad(producto.getPrecio());
				detalle.setImpuesto(calcularImpuesto(producto));
				detalle.setTotal(calcularTotalProducto(item, producto));

				boolean detalleCreado = DetalleDao.addDetalle(detalle);
				if (!detalleCreado) {
					System.out.println("Error al agregar el detalle del pedido.");
					return false;
				}
			}
		}

		// Paso 4: Vaciar el carrito
		boolean carritoVaciado = CarritoDao.deleteCarrito(usuarioId);
		if (!carritoVaciado) {
			System.out.println("Error al vaciar el carrito.");
			return false;
		}

		// Si todo salió bien, la compra ha sido procesada correctamente
		System.out.println("Compra realizada con éxito.");
		return true;
	}

	// Método para calcular el total de la compra
	private static double calcularTotalCompra(List<Carrito> carritoUsuario) {
		double total = 0.0;
		for (Carrito item : carritoUsuario) {
			Producto producto = ProductoDao.getProductoById(item.getProductoId());
			if (producto != null) {
				total += producto.getPrecio() * item.getUnidades();
			}
		}
		return total;
	}

	// Método para calcular el impuesto de un producto (puedes personalizar la
	// lógica)
	private static double calcularImpuesto(Producto producto) {
		double impuesto = 0.21; // Ejemplo: 21% de IVA
		return producto.getPrecio() * impuesto;
	}

	// Método para calcular el total por producto
	private static double calcularTotalProducto(Carrito item, Producto producto) {
		return producto.getPrecio() * item.getUnidades() + calcularImpuesto(producto);
	}
}
