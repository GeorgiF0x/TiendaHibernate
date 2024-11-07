package control.servicios;

import modelo.Usuario;
import modelo.dao.UsuarioDao;

public class ServiciosUsuario {
	
    public static Usuario validarUsuario(String email, String clave) {
        return UsuarioDao.validarUsuario(email, clave);
    }
    
    public static boolean registrarUsuario(Usuario usuario) {
        return UsuarioDao.insertUsuario(usuario);
    }

}
