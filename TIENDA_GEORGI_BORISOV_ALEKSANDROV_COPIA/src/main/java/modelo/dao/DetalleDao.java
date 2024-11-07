package modelo.dao;

import modelo.Detalle;
import org.hibernate.Session;
import org.hibernate.Transaction;

import conexion.HibernateUtil;

import java.util.List;

public class DetalleDao {

    // Método para agregar un nuevo detalle
    public static boolean addDetalle(Detalle detalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(detalle); // Guarda el detalle
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
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

    // Método para actualizar un detalle
    public static boolean updateDetalle(Detalle detalle) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(detalle); // Actualiza el detalle
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un detalle por ID
    public static boolean deleteDetalle(int id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Detalle detalle = session.get(Detalle.class, id); // Obtiene el detalle por ID
            if (detalle != null) {
                session.delete(detalle); // Elimina el detalle
                transaction.commit();
                return true;
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
        return false;
    }
}


