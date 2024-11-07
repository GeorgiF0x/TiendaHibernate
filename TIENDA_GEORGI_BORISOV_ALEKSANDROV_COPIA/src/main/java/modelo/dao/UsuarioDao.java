package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import conexion.Conexion;
import modelo.Usuario;

public class UsuarioDao {

	// Método para agregar un nuevo usuario
	public static boolean insertUsuario(Usuario usuario) {
	    String sql = "INSERT INTO usuario (rol_id, email, clave, nombre, apellidos, baja) VALUES (?, ?, ?, ?, ?, ?)";
	    PreparedStatement pstmt = null;

	    try {
	        
	        Connection conn = Conexion.getConexion();
	        pstmt = FactoryBD.realizaConsulta(sql, 
	                usuario.getRolId(), 
	                usuario.getEmail(), 
	                usuario.getClave(), 
	                usuario.getNombre(), 
	                usuario.getApellidos(), 
	                usuario.isBaja());
	        
	      
	        pstmt.executeUpdate();
	        
	   
	        conn.commit();
	        return true; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	        try {
	           
	            Connection conn = Conexion.getConexion();
	            if (conn != null) {
	                conn.rollback();
	            }
	        } catch (SQLException rollbackEx) {
	            rollbackEx.printStackTrace();
	        }
	        return false; 
	    } finally {
	     
	        FactoryBD.cerrarRecursos(null, pstmt);
	    }
	}


    // Método para obtener un usuario por ID
    public static Usuario getUsuarioById(int id) {
        String sql = "SELECT * FROM usuario WHERE id = ?";
        Usuario usuario = null;
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id);
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            if (rs != null && rs.next()) {
                usuario = new Usuario(
                    rs.getInt("id"),
                    rs.getInt("rol_id"),
                    rs.getString("email"),
                    rs.getString("clave"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getBoolean("baja")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return usuario;
    }

    // Método para obtener todos los usuarios
    public static List<Usuario> getAllUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        ResultSet rs = null;

        try {
            PreparedStatement pstmt = FactoryBD.realizaConsulta(sql); // Obtiene el PreparedStatement
            rs = pstmt.executeQuery(); // Ejecuta la consulta
            while (rs != null && rs.next()) {
                usuarios.add(new Usuario(
                    rs.getInt("id"),
                    rs.getInt("rol_id"),
                    rs.getString("email"),
                    rs.getString("clave"),
                    rs.getString("nombre"),
                    rs.getString("apellidos"),
                    rs.getBoolean("baja")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FactoryBD.cerrarRecursos(rs, null); // Cierra el ResultSet
        }
        return usuarios;
    }

    // Método para actualizar un usuario
    public static boolean updateUsuario(Usuario usuario) {
        String sql = "UPDATE usuario SET rol_id = ?, email = ?, clave = ?, nombre = ?, apellidos = ?, baja = ? WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, usuario.getRolId(), usuario.getEmail(),
                usuario.getClave(), usuario.getNombre(), usuario.getApellidos(), usuario.isBaja(), usuario.getId())) {
            pstmt.executeUpdate(); // Ejecuta la actualización
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un usuario por ID
    public static boolean deleteUsuario(int id) {
        String sql = "DELETE FROM usuario WHERE id = ?";
        try (PreparedStatement pstmt = FactoryBD.realizaConsulta(sql, id)) {
            pstmt.executeUpdate(); // Ejecuta la eliminación
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static Usuario validarUsuario(String email, String password) {
        Usuario usuario = null; 
        ResultSet rs = null; 
        PreparedStatement pstmt = null; 

    
        String sql = "SELECT * FROM usuario WHERE email = ? AND clave = ?";

        try {
     
            pstmt = FactoryBD.realizaConsulta(sql, email, password);
            
           
            rs = pstmt.executeQuery();

      
            if (rs.next()) { 
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                boolean baja = rs.getBoolean("baja");

            
                usuario = new Usuario(id, 1, email, password, nombre, apellidos, baja); 
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Captura y muestra excepciones
        } finally {
            // Cerrar recursos
            FactoryBD.cerrarRecursos(rs, pstmt);
        }

        return usuario; // Devolverá null si no se encontró un usuario
    }

    
    
    
}