package modelo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import conexion.Conexion;

public class FactoryBD {


    public static PreparedStatement realizaConsulta(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = Conexion.getConexion(); 
            pstmt = conn.prepareStatement(sql);
            
            // Establecer parámetros
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pstmt; // Devuelve el PreparedStatement para su uso posterior
    }

    public static void cerrarRecursos(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            // No cerramos la conexión aquí porque la estamos manejando en otro lugar
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
