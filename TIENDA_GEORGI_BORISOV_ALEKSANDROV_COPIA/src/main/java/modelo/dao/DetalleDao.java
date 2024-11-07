package modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexion.HibernateUtil;
import modelo.Detalle;

public class DetalleDao {

	// Método para agregar un nuevo detalle
	public static boolean addDetalle(Detalle detalle) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.save(detalle); // Guarda el detalle en la base de datos
			transaction.commit(); // Realiza la transacción
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback(); // Revierte si ocurre algún error
			}
			e.printStackTrace(); // Muestra el error
			return false;
		}
	}

	// Método para obtener un detalle por ID
	public static Detalle getDetalleById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Detalle.class, id); // Obtiene el detalle por ID
		}
	}

	// Método para obtener todos los detalles
	public static List<Detalle> getAllDetalles() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Detalle", Detalle.class).list(); // Obtiene todos los detalles
		}
	}

	// Método para actualizar o guardar un detalle
	public static boolean updateDetalle(Detalle detalle) {
		return executeTransaction(session -> {
			session.merge(detalle); // Usa merge para manejar tanto entidades nuevas como ya persistidas
			return true;
		});
	}

	// Método para eliminar un detalle por ID
	public static boolean deleteDetalle(int id) {
		return executeTransaction(session -> {
			Detalle detalle = session.get(Detalle.class, id); // Obtiene el detalle por ID
			if (detalle != null) {
				session.delete(detalle); // Elimina el detalle
			}
			return detalle != null; // Si no existe, devuelve false
		});
	}

	// Método para ejecutar una transacción genérica
	private static boolean executeTransaction(TransactionCallback callback) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			boolean result = callback.execute(session);
			transaction.commit();
			return result;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	// Interfaz funcional para ejecutar transacciones genéricas
	private interface TransactionCallback {
		boolean execute(Session session);
	}
}
