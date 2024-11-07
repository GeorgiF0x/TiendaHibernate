package control.servicios;

import java.util.List;

import modelo.Detalle;
import modelo.dao.DetalleDao;

public class ServiciosDetalle {
	 // Método para agregar un nuevo detalle
    public boolean agregarDetalle(Detalle detalle) {
        return DetalleDao.addDetalle(detalle);
    }

    // Método para obtener un detalle por ID
    public Detalle obtenerDetallePorId(int id) {
        return DetalleDao.getDetalleById(id);
    }

    // Método para obtener todos los detalles
    public List<Detalle> obtenerTodosLosDetalles() {
        return DetalleDao.getAllDetalles();
    }

    // Método para actualizar un detalle
    public boolean actualizarDetalle(Detalle detalle) {
        return DetalleDao.updateDetalle(detalle);
    }

    // Método para eliminar un detalle por ID
    public boolean eliminarDetalle(int id) {
        return DetalleDao.deleteDetalle(id);
    }
}
