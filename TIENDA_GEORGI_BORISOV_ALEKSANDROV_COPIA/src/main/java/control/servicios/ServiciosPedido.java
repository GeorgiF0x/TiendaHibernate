package control.servicios;

import java.util.List;

import modelo.Pedido;
import modelo.dao.PedidoDao;

public class ServiciosPedido {

	private static PedidoDao pedidoDao = new PedidoDao();

	// Método para obtener todos los pedidos
	public static List<Pedido> obtenerTodosLosPedidos() {
		return pedidoDao.listarPedidos();
	}

	// Método para agregar un nuevo pedido
	public static boolean agregarPedido(Pedido pedido) {
		try {
			pedidoDao.guardarPedido(pedido);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Método para obtener un pedido por ID
	public static Pedido obtenerPedidoPorId(int id) {
		Pedido pedido = pedidoDao.obtenerPedidoPorId(id);
		if (pedido == null) {
			System.out.println("Pedido con ID " + id + " no encontrado.");
		}
		return pedido;
	}

	// Método para actualizar un pedido
	public static boolean actualizarPedido(Pedido pedido) {
		try {
			pedidoDao.actualizarPedido(pedido);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// Método para eliminar un pedido por ID
	public static boolean eliminarPedido(int id) {
		try {
			pedidoDao.eliminarPedido(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
