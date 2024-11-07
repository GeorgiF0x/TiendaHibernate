package modelo.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import conexion.HibernateUtil;
import modelo.Carrito;
import modelo.Producto;

public class CarritoDao {
	private static SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

	// Método para agregar un producto al carrito
	public static boolean addProductoAlCarrito(Carrito carrito) {
		Transaction transaction = null;
		Session session = null;
		try {
			// Abrir sesión y comenzar transacción
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// Guardar el objeto Carrito en la base de datos
			session.save(carrito);

			// Commit de la transacción solo si todo se completó correctamente
			transaction.commit();
			return true;
		} catch (Exception e) {
			// Revertir la transacción en caso de error
			if (transaction != null && transaction.getStatus().canRollback()) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			// Cerrar la sesión después de finalizar la transacción o el rollback
			if (session != null) {
				session.close();
			}
		}
	}

	// Método para obtener los productos del carrito de un usuario
	public static List<Carrito> getCarritoPorUsuario(int usuarioId) {
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			return session.createQuery("FROM Carrito WHERE usuarioId = :usuarioId", Carrito.class)
					.setParameter("usuarioId", usuarioId).list();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deleteCarrito(int usuarioId) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			// Obtener todos los productos del carrito para el usuario
			List<Carrito> carritoUsuario = session
					.createQuery("FROM Carrito WHERE usuarioId = :usuarioId", Carrito.class)
					.setParameter("usuarioId", usuarioId).list();

			// Eliminar todos los productos del carrito
			for (Carrito item : carritoUsuario) {
				session.delete(item);
			}

			// Confirmar la transacción
			transaction.commit();
			return true;
		} catch (Exception e) {
			// Si ocurre un error, revertir la transacción
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	// Método para actualizar las unidades de un producto en el carrito
	public static boolean updateUnidadesCarrito(int usuarioId, int productoId, int nuevasUnidades) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Carrito carrito = session
					.createQuery("FROM Carrito WHERE usuarioId = :usuarioId AND productoId = :productoId",
							Carrito.class)
					.setParameter("usuarioId", usuarioId).setParameter("productoId", productoId).uniqueResult();
			if (carrito != null) {
				carrito.setUnidades(nuevasUnidades);
				session.update(carrito);
				transaction.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	// Método para guardar o actualizar un solo carrito
	public static boolean saveOrUpdateCarrito(Carrito carrito) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			session.saveOrUpdate(carrito);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		}
	}

	// Método para guardar o actualizar una lista de carritos
	public static boolean saveOrUpdateCarrito(List<Carrito> carrito) {
		Session session = null;
		Transaction transaction = null;
		try {
			// Abrir la sesión y comenzar la transacción
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// Iterar sobre cada carrito para verificar si el producto existe antes de
			// guardarlo
			for (Carrito c : carrito) {
				// Verifica si el producto existe en la base de datos
				Producto producto = session.get(Producto.class, c.getProductoId());
				if (producto == null) {
					System.out.println("Producto con ID " + c.getProductoId() + " no encontrado.");
					return false; // Si el producto no existe, no se guarda el carrito
				}
				// Si el producto es válido, guardamos el carrito
				c.setProductoId(producto.getId()); // Establecer el producto correcto en el carrito
				session.saveOrUpdate(c); // Guardar o actualizar el carrito
			}

			// Confirmar la transacción
			transaction.commit();
			return true;
		} catch (Exception e) {
			// Si ocurre un error, hacer rollback
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			// Asegúrate de cerrar la sesión al final
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}

	// Método para fusionar el carrito del usuario con el carrito anónimo
	public static boolean fusionarCarritosDao(List<Carrito> carritoUsuario, List<Carrito> carritoAnonimo) {
		Session session = null;
		Transaction transaction = null;
		try {
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();

			// Usar un mapa para manejar los productos del carrito del usuario
			Map<Integer, Carrito> mapaCarrito = new HashMap<>();
			for (Carrito item : carritoUsuario) {
				mapaCarrito.put(item.getProductoId(), item);
			}

			// Fusionar el carrito anónimo con el carrito del usuario
			for (Carrito itemAnonimo : carritoAnonimo) {
				int productoId = itemAnonimo.getProductoId();

				// Verificar si el producto existe en la base de datos antes de proceder
				Producto producto = ProductoDao.getProductoById(productoId);
				if (producto == null) {
					System.out.println(
							"Producto con ID " + productoId + " no existe. El producto no se añadirá al carrito.");
					continue; // Saltamos al siguiente producto si el producto no existe
				}

				if (mapaCarrito.containsKey(productoId)) {
					// Si ya existe el producto, actualizar las unidades
					Carrito itemExistente = mapaCarrito.get(productoId);
					itemExistente.setUnidades(itemExistente.getUnidades() + itemAnonimo.getUnidades());
				} else {
					// Si no existe, asignar el usuario_id solo si carritoUsuario no está vacío
					if (!carritoUsuario.isEmpty()) {
						itemAnonimo.setUsuarioId(carritoUsuario.get(0).getUsuarioId()); // Asignar el usuario_id
					}
					mapaCarrito.put(productoId, itemAnonimo);
				}
			}

			// Guardar o actualizar los carritos fusionados
			return saveOrUpdateCarrito(new ArrayList<>(mapaCarrito.values()));

		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
			return false;
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
	}
}
