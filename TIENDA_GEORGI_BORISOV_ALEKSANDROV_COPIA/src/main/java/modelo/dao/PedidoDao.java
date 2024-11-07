package modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexion.HibernateUtil;
import modelo.Pedido;

public class PedidoDao {
	// Guardar un nuevo pedido
	public static boolean guardarPedido(Pedido pedido) {
		Transaction transaction = null;
		Session session = null; // Declaramos la sesión aquí para gestionarla manualmente
		try {
			session = HibernateUtil.getSessionFactory().openSession(); // Abrimos la sesión manualmente
			transaction = session.beginTransaction(); // Iniciamos la transacción
			session.save(pedido); // Guardamos el pedido
			transaction.commit(); // Confirmamos la transacción
			return true; // Si todo va bien, devolvemos true
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback(); // Si algo falla, revertimos la transacción
			}
			e.printStackTrace(); // Mostramos el error
			return false; // En caso de error, devolvemos false
		} finally {
			if (session != null && session.isOpen()) {
				session.close(); // Cerramos la sesión manualmente en el bloque finally
			}
		}
	}

	// Obtener un pedido por ID
	public static Pedido obtenerPedidoPorId(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Pedido.class, id);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Actualizar un pedido
	public static void actualizarPedido(Pedido pedido) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.update(pedido);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	// Eliminar un pedido
	public static void eliminarPedido(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Pedido pedido = session.get(Pedido.class, id);
			if (pedido != null) {
				session.delete(pedido);
				System.out.println("Pedido eliminado correctamente.");
			}
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}

	// Listar todos los pedidos
	public static List<Pedido> listarPedidos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("from Pedido", Pedido.class).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
