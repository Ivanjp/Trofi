package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import modelo.Orden;
import modelo.Repartidor;
import modelo.Alimento;
import modelo.Cliente;
import modelo.Conexion;

public class OrdenDAO {
	private Conexion con;
	private Connection connection;
	
	Cliente cliente;
	

	public OrdenDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) throws SQLException {
		System.out.println(jdbcURL);
		con = new Conexion(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	// listar todos los productos
	public List<Orden> listarOrdenes() throws SQLException {
		List<Orden> listaOrdenes = new ArrayList<Orden>();
		String sql = "SELECT * FROM orden WHERE repartidor = ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, null);
		ResultSet resulSet = statement.executeQuery(sql);
		while (resulSet.next()) {
			String correo_cliente = resulSet.getString("correo_e");
			int id_orden = resulSet.getInt("id_orden");
			String estado_orden = resulSet.getString("estado_orden");
			String direccion_cliente = resulSet.getString("direccion_cliente");
			String repartidor = resulSet.getString("repartidor");
			Orden orden = new Orden(correo_cliente,id_orden,estado_orden,direccion_cliente,repartidor);
			listaOrdenes.add(orden);
		}
		con.desconectar();
		return listaOrdenes;
	}
	
	public boolean crearOrden(ArrayList<Alimento> alim, String direccion) throws SQLException {
		String sql = "INSERT INTO orden (correo_e, id_orden, estado_orden, direccion_cliente, repartidor) VALUES (?, ?, ?, ?, ?)";
		String sql2 = "INSERT INTO tener (correo_e, id_orden, id_categoria, id_alimento) VALUES (?, ?, ?, ?)";
		System.out.println("conectando");
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, cliente.getCorreo());
		statement.setString(2, null);
		statement.setString(3, "PREPARANDO");
		statement.setString(4, direccion);
		statement.setString(5, null);
		
		statement.executeUpdate();
	
		statement = connection.prepareStatement(sql2);
		String sql3 = "SELECT id_orden FROM orden WHERE correo_e = "+cliente.getCorreo();
		Statement statement3 = connection.createStatement();
		ResultSet resulSet = statement3.executeQuery(sql3);
		int id_ord = resulSet.getInt("id_orden");
		
		String sql4 = "SELECT * FROM alimento ";
		Statement statement4 = connection.createStatement();
		ResultSet resulSet2 = statement4.executeQuery(sql4);
		
		int id_cat;
		while (resulSet2.next()) {
			id_cat = resulSet2.getInt("id_categoria");
		for (int i = 0; i < alim.size(); i++) {
			if(resulSet2.getInt("id_alimento") == alim.get(i).getId()) {
			statement.setString(1, cliente.getCorreo());
			statement.setInt(2, id_ord);
			statement.setInt(3, id_cat);
			statement.setInt(4, alim.get(i).getId());
			}
        }
		}
		
		System.out.println("agregado");
		boolean agregar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return agregar;
	}

	
	public void obtenCliente(Cliente c) {
		cliente = c;
	}
	
	public boolean asignarOrden(Repartidor repartidor,int id_orden) throws SQLException {
		boolean actualizar = false;
		String sql = "UPDATE orden SET repartidor= ? WHERE id_orden = ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, repartidor.getCorreo());
		statement.setInt(2, id_orden);
		actualizar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return actualizar;
	}
	
	public boolean cambiarEstadoOrden(int id_orden, String opcion ) throws SQLException {
		boolean actualizar = false;
		String sql = "UPDATE orden SET estado_orden= ? WHERE id_orden = ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, opcion);
		statement.setInt(2, id_orden);
		actualizar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return actualizar;
	}
	
	public List<Orden> listarOrdenesAceptadas(Repartidor repartidor, int id_orden) throws SQLException {
		List<Orden> listaOrdenesAceptadas = new ArrayList<Orden>();
		String sql = "SELECT * FROM orden WHERE repartidor = ? AND id_orden = ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, repartidor.getCorreo());
		statement.setInt(2, id_orden);
		ResultSet resulSet = statement.executeQuery(sql);
		while (resulSet.next()) {
			String correo_cliente = resulSet.getString("correo_e");
			int id_ord = resulSet.getInt("id_orden");
			String estado_orden = resulSet.getString("estado_orden");
			String direccion_cliente = resulSet.getString("direccion_cliente");
			String rep = resulSet.getString("repartidor");
			Orden orden = new Orden(correo_cliente,id_ord,estado_orden,direccion_cliente,rep);
			listaOrdenesAceptadas.add(orden);
		}
		con.desconectar();
		return listaOrdenesAceptadas;
	}
	
	public List<Alimento> listaAlimentos(int id_orden) throws SQLException {
		List<Alimento> listaAlimento = new ArrayList<Alimento>();
		String sql = "SELECT id_alimento, nombre, descripcion, costo FROM alimento LEFT JOIN tener ON alimento.id_alimento = tener.id_alimento AND tener.id_orden = ?";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id_orden);
		ResultSet resulSet = statement.executeQuery(sql);
		while (resulSet.next()) {
			int id_alimento = resulSet.getInt("id_alimento");
			String nombre = resulSet.getString("nombre");
			String descripcion = resulSet.getString("descripcion");
			BigDecimal costo  = resulSet.getBigDecimal("costo");
			double d = costo.doubleValue();
			Alimento alimento = new Alimento(nombre,descripcion,d,id_alimento);
			listaAlimento.add(alimento);
		}
		con.desconectar();
		return listaAlimento;
	}
}
	