package modelo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import conexion.HibernateUtil;
import modelo.Producto;

public class ProductoDao {

	// Método para agregar un nuevo producto
	public static boolean addProducto(Producto producto) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.persist(producto); // Usar persist para agregar el producto
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	// Método para obtener un producto por ID
	public static Producto getProductoById(int id) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.get(Producto.class, id); // Obtener el producto
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para obtener todos los productos
	public static List<Producto> getAllProductos() {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Producto", Producto.class).list(); // Obtener todos los productos
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// Método para actualizar un producto
	public static boolean updateProducto(Producto producto) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.merge(producto); // Usar merge en lugar de update
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	// Método para eliminar un producto por ID
	public static boolean deleteProducto(int id) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, id);
			if (producto != null) {
				session.remove(producto); // Usar remove en lugar de delete
				transaction.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	// metodo para comprobar el stock de un producto
	public static boolean verificarStock(int productoId, int cantidadDeseada) {
		Producto producto = getProductoById(productoId);
		return producto != null && producto.getStock() >= cantidadDeseada;
	}

	// reducir stock
	public static boolean reducirStock(int productoId, int cantidad) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Producto producto = session.get(Producto.class, productoId);
			if (producto != null && producto.getStock() >= cantidad) {
				producto.setStock(producto.getStock() - cantidad);
				session.merge(producto);
				transaction.commit();
				return true;
			}
			return false; // Stock insuficiente o producto no encontrado
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}
}
