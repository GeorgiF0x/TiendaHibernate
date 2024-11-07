package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    
    static String bd = "tiendaserbatic"; // Nombre correcto de la base de datos
    static String login = "root"; // Usuario de la base de datos
    static String password = ""; // Contraseña del usuario
    static String host = "127.0.0.1"; // Dirección del host (localhost)
     static int port = 3307; // Puerto de MySQL CASA
    //static int port = 3306; // Puerto de MySQL CLASE

    static String url = "jdbc:mysql://" + host + ":" + port + "/" + bd; // URL de conexión
    static Connection conexion; // Atributo para guardar el objeto Connection
        
    public static Connection getConexion() {
        // SINGLETON
        if (conexion == null) {
            if (!crearConexion()) {
                throw new IllegalStateException("No se pudo establecer la conexión a la base de datos");
            }
        }
        return conexion;
    }

    // Devuelve true si se ha creado correctamente
    public static boolean crearConexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, login, password);
            conexion.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace(); // Muestra el error de SQL
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace(); // Muestra el error si el driver no se encuentra
            return false;
        }
        return true;
    }

    public static void desconectar() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                conexion = null;
                System.out.println("La conexión a la base de datos " + bd + " ha terminado.");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }

    public static void pruebaConexion() {
        try (Connection testConnection = getConexion()) {
            if (testConnection != null) {
                System.out.println("Conexión a la base de datos exitosa.");
            } else {
                System.out.println("No se pudo establecer la conexión.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
