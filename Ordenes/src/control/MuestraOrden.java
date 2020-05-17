package controlador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrdenDAO;
import dao.RepartidorDAO;
import modelo.Alimento;
import modelo.Orden;
import modelo.Repartidor;

/**
* Clase que se encarga de hacer las llamadas a los metodos de OrdenrDAO y manipular las vistas.
* @version 1.3 5/5/2020
* @author Perez Perez Jorge Ivan
*/
@WebServlet("/muestra_orden")
public class MuestraOrden extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrdenDAO ordenDAO; //Se crea un objeto OrdenDAO
	RepartidorDAO repartidorDAO; //Se crea un objeto RepartidorDAO
	Repartidor repartidor;//Se crea un objeto Repartidor
	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		try { 
			ordenDAO = new OrdenDAO(jdbcURL, jdbcUsername, jdbcPassword);
			repartidorDAO = new RepartidorDAO(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) { 
			
		}
	}
	
	public MuestraOrden() {
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
		System.out.println(action);
		try {
			switch (action) {
			case "index":
				index(request, response);
				break;
			case "cerrarSesion":
				cerrarSesion(request, response);
				break;
			case "verOrdenes":
				verOrdenes(request, response);
				break;
			case "verOrdenesAceptadas":
				verOrdenesAceptadas(request, response);
				break;
			case "verDetalles":
				verDetalles(request, response);
				break;
			case "cambiarEstado":
				cambiarEstado(request, response);
				break;
			case "aviso":
				aviso(request, response);
				break;
			case "asignaOrden":
				asignaOrden(request, response);
				break;
			default:
				break;
			}
		} catch (SQLException e) {
			e.getStackTrace();
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
	* Metodo que obtiene todas las ordenes del sistema que aun no estan aceptadas y se muestran en la vista correspondiente y se asigna 
	* el repartidor que esta utilizando el sistema y lo guarda en el objeto repartidor que se creo al principio.
	*/
	private void verOrdenes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String sesionRepartidor = request.getParameter("sesionRepartidor");
		if(sesionRepartidor != null) {
			repartidor = repartidorDAO.obtenerPorCorreo(sesionRepartidor);
		}
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/ordenes.jsp");
		System.out.println("dispatcher ready..");
		List<Orden> listaordenes= ordenDAO.listarOrdenesListas();
		request.setAttribute("lista", listaordenes);
		System.out.println("regreso..");
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que asigna una orden a un repartidor
	*/
	private void asignaOrden(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		String correoCliente = request.getParameter("correoCliente");
		ordenDAO.asignarOrden(repartidor, id_ord, correoCliente); 
		ordenDAO.cambiarEstadoOrden(id_ord, "EN CAMINO", correoCliente);
		String mensaje = "Orden asignada";
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/asigna.jsp");
		request.setAttribute("mensaje", mensaje);
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que muestra las ordenes aceptadas por el repartidor en la vista correspondiente
	*/
	private void verOrdenesAceptadas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		//Repartidor repartidor = ordenDAO.getRepartidor();
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/ordenesAceptadas.jsp");
		List<Orden> listaordenesacept= ordenDAO.listarOrdenesAceptadas(repartidor);
		request.setAttribute("lista", listaordenesacept);
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que muestra los detalles de las ordenes
	*/
	private void verDetalles(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		String correoCliente = request.getParameter("correoCliente");
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/detalles.jsp");
		List<Alimento> alimentos= ordenDAO.listaAlimentos(id_ord, correoCliente);
		request.setAttribute("lista", alimentos);
		request.setAttribute("cliente", correoCliente);
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que cambia el estado de entrega de la orden aaceptada por el repartidor
	*/
	private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		String correoCliente = request.getParameter("correoCliente");
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/estados.jsp");
		System.out.println("dispatcher ready..");
		List<String> estados = new ArrayList<String>();
		estados.add("ENTREGADA");
		estados.add("NO ENTREGADA");
		request.setAttribute("lista", estados);
		request.setAttribute("id_orden", id_ord);
		request.setAttribute("correoCliente", correoCliente);
		System.out.println("regreso..");
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que muestra un mensaje de que se ah cambiado el estado de la orden y cambia el estado en la BD.
	*/
	private void aviso(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String aviso = "Estado cambiado";
		String nuevoEstado = request.getParameter("estado");
		String correoCliente = request.getParameter("correoCliente");
		int id_orden = Integer.parseInt(request.getParameter("id_orden"));
		ordenDAO.cambiarEstadoOrden(id_orden, nuevoEstado, correoCliente);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/aviso.jsp");
		request.setAttribute("mensaje", aviso);
		dispatcher.forward(request, response);
	}
	
	/**
	* Metodo que cierra sesion del repartidor y se vuelve a la pagina principal.
	*/
	private void cerrarSesion (HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{
		repartidor = null;
		RequestDispatcher dispatcher= request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}
	//az5459kHFr
}