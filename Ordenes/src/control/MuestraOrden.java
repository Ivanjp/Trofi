package controlador;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.OrdenDAO;
import modelo.Alimento;
import modelo.Categoria;
import modelo.Orden;
import modelo.Repartidor;


@WebServlet("/muestra_orden")
public class MuestraOrden extends HttpServlet {
	private static final long serialVersionUID = 1L;
	OrdenDAO ordenDAO;

	
	public void init() {
		String jdbcURL = getServletContext().getInitParameter("jdbcURL");
		String jdbcUsername = getServletContext().getInitParameter("jdbcUsername");
		String jdbcPassword = getServletContext().getInitParameter("jdbcPassword");
		try { 
			ordenDAO = new OrdenDAO(jdbcURL, jdbcUsername, jdbcPassword);
		} catch (Exception e) { 
			
		}
	}
	
	public MuestraOrden() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola ...");
		String action = request.getParameter("action");
		try {
			switch (action) {
			case "index":
				index(request, response);
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Hola ....");
		doGet(request, response);
	}
	
	private void index(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher= request.getRequestDispatcher("index.jsp");
		dispatcher.forward(request, response);
	}
	
	private void verOrdenes(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/ordenes.jsp");
		System.out.println("dispatcher ready..");
		List<Orden> listaordenes= ordenDAO.listarOrdenes();
		request.setAttribute("lista", listaordenes);
		System.out.println("regreso..");
		dispatcher.forward(request, response);
	}
	
	private void asignaOrden(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		Repartidor repartidor = ordenDAO.getRepartidor();
		ordenDAO.asignarOrden(repartidor, id_ord); 
		String mensaje = "Orden asignada";
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/asigna.jsp");
		request.setAttribute("mensaje", mensaje);
		dispatcher.forward(request, response);
	}
	
	private void verOrdenesAceptadas(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		Repartidor repartidor = ordenDAO.getRepartidor();
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/ordenesAceptadas.jsp");
		List<Orden> listaordenesacept= ordenDAO.listarOrdenesAceptadas(repartidor, id_ord);
		request.setAttribute("lista", listaordenesacept);
		dispatcher.forward(request, response);
	}
	
	private void verDetalles(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/detalles.jsp");
		List<Alimento> alimentos= ordenDAO.listaAlimentos(id_ord);
		request.setAttribute("lista", alimentos);
		dispatcher.forward(request, response);
	}
	
	private void cambiarEstado(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id_ord = Integer.parseInt(request.getParameter("id_orden"));
		String estado = ordenDAO.verEstado(id_ord);
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/estados.jsp");
		System.out.println("dispatcher ready..");
		List<String> estados = new ArrayList<String>();
		if(estado.equals("PREPARANDO")) {
			estados.add("LISTA");
			estados.add("EN CAMINO");
			estados.add("ENTREGADA");
			estados.add("NO ENTREGADA");
		}else if(estado.equals("LISTA")) {
			estados.add("EN CAMINO");
			estados.add("ENTREGADA");
			estados.add("NO ENTREGADA");
		}else if(estado.equals("EN CAMINO")) {
			estados.add("ENTREGADA");
			estados.add("NO ENTREGADA");
		}
		request.setAttribute("lista", estados);
		System.out.println("regreso..");
		dispatcher.forward(request, response);
	}
	
	private void aviso(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String aviso = "Estado cambiado";
		RequestDispatcher dispatcher= request.getRequestDispatcher("/jsp/Ordenes/aviso.jsp");
		request.setAttribute("mensaje", aviso);
		dispatcher.forward(request, response);
	}
	
}

