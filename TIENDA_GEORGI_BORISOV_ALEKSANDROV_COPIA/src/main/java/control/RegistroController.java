package control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import control.servicios.ServiciosUsuario;
import modelo.Usuario;

/**
 * Servlet implementation class RegistroController
 */
@WebServlet("/RegistroController")
public class RegistroController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistroController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener los parámetros del formulario
        String email = request.getParameter("email");
        String clave = request.getParameter("password");
        String nombre = request.getParameter("nombre");
        String apellidos = request.getParameter("apellidos");
        
        // Crear el objeto Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setEmail(email);
        nuevoUsuario.setClave(clave);
        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setApellidos(apellidos);
        
        // Registrar el usuario usando el servicio
        boolean registroExitoso = ServiciosUsuario.registrarUsuario(nuevoUsuario);
        
        if (registroExitoso) {
        	System.out.println("Registro Completado");
            response.sendRedirect("login.jsp"); // Cambia a la ruta que desees
        } else {
            // Redirigir al registro con un mensaje de error
            request.setAttribute("errorMessage", "Error al registrar el usuario. Inténtalo de nuevo.");
            request.getRequestDispatcher("registro.jsp").forward(request, response);
        }
    }

}
