package controlador;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import java.sql.SQLException;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RepartidorDAO;
import modelo.Repartidor;

/**
* Clase que se encarga de hacer las llamadas a los metodos de RepartidorDAO y manipular las vistas.
* @version 1.3 5/5/2020
* @author Perez Perez Jorge Ivan
*/
@WebServlet("/alta_repartidor")
public class Alta_Repartidor extends HttpServlet {
	private static final long serialVersionUID = 1L;
	RepartidorDAO repartidorDAO;//Se crea un objeto RepartidorDAO

	/**
	* Inicializa la conexion a la base de datos para el Repartidor
	*/
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		try { 
			repartidorDAO = new RepartidorDAO(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) { 
			
		}
	}
	
	public Alta_Repartidor() {
		super();
	}
	
	/**
	* Metodo que se encarga de recibir las peticiones del servidor y mostrar la vista que se pide
	* @param HttpServletRequest request
	* @param HttpServletResponse response
	*/
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola ...");
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "index":
				index(request, response);
				break;
			case "nuevoRepartidor":
				nuevaCuenta(request, response);
				break;
			case "dardeAlta":
				darDeAlta(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.getStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola ....");
		doGet(request, response);
	}
	
	/**
	* Muestra la vista de la pagina inicial
	*/
	private void index(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher= request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que muestra la vista para registrar un repartidor
	*/
	private void nuevaCuenta(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Administrador/crearRepartidor.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que registra un repartidor en la base de datos y regresa la vista del menu del administrador.
	*/
	private void darDeAlta(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NoSuchAlgorithmException {
		String password = repartidorDAO.mandaContrasena(request.getParameter("correo_e"), request.getParameter("nombre"), request.getParameter("a_paterno"), request.getParameter("a_materno"));
		System.out.println(password);        
		Repartidor repartidor = new Repartidor(request.getParameter("correo_e"), request.getParameter("nombre"), request.getParameter("a_paterno"), request.getParameter("a_materno"), password);
		
		repartidorDAO.agregarCuenta(repartidor);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Administrador/menu.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
}