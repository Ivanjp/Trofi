package dao;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import modelo.Repartidor;
import modelo.Conexion;

/**
 * Una clase que nos ayuda a obtener informacion de los repartidores en la base de datos
 * @author Perez Perez Jorge Ivan
 */
public class RepartidorDAO {
	private Conexion con; //Objeto conexion para conectar la base de datos
	private Connection connection; //Objeto connection para conectar 

	/**
	 * Metodo constructor que inicializa un objeto RepartidorDAO
	 * @param jdbcURL
	 * @param jdbcUsername
	 * @param jdbcPassword
	 */
	public RepartidorDAO(String jdbcURL, String jdbcUsername, String jdbcPassword) throws SQLException {
		System.out.println(jdbcURL);
		con = new Conexion(jdbcURL, jdbcUsername, jdbcPassword);
	}
	
	/**
	 * Metodo que obtiene la lista de repartidores que estan en la base de datos
	 * @return la lista de repartidores en la base de datos
	 */
	public List<Repartidor> listarRepartidores() throws SQLException {
		List<Repartidor> listaRepartidores = new ArrayList<Repartidor>();
		String sql = "SELECT * FROM repartidor";
		con.conectar();
		connection = con.getJdbcConnection();
		Statement statement = connection.createStatement();
		ResultSet resulSet = statement.executeQuery(sql);
		while (resulSet.next()) {
			String correo_e = resulSet.getString("correo_e");
			String nombre = resulSet.getString("nombre");
			String a_paterno = resulSet.getString("a_paterno");
			String a_materno = resulSet.getString("a_materno");
			String contrasenia = resulSet.getString("contrasenia");
			Repartidor repartidor = new Repartidor(correo_e, nombre, a_paterno, a_materno, contrasenia);
			listaRepartidores.add(repartidor);
		}
		con.desconectar();
		return listaRepartidores;
	}

	/**
	 * Metodo que regresa un repartidor que esta en la base de datos
	 * @param correo correo a buscar en la base de datos
	 * @return el repartidor que tenga el correo proporcionado
	 */
	public Repartidor obtenerPorCorreo(String correo) throws SQLException {
		Repartidor admin = null;
		String sql = "SELECT * FROM repartidor WHERE correo_e = ? ";
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, correo);
		ResultSet res = statement.executeQuery();
		if (res.next()){
			admin = new Repartidor(res.getString("correo_e"), res.getString("nombre"), res.getString("a_paterno"),
					res.getString("a_materno"), res.getString("contrasenia"));
		}
		res.close();
		con.desconectar();
		return admin;
	}
	
	/**
	 * Metodo que regresa un repartidor que esta en la base de datos
	 * @param repartidor repartidor para los datos de la nueva Cuenta.
	 * @return true si se logro agreagr correctamente la cuenta del repartidor, false en otro caso.
	 */
	public boolean agregarCuenta(Repartidor repartidor) throws SQLException {
		String sql = "INSERT INTO repartidor (correo_e, nombre, a_paterno, a_materno, contrasenia) VALUES (?, ?, ?, ?, SHA(?))";
		System.out.println("conectando");
		con.conectar();
		connection = con.getJdbcConnection();
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, repartidor.getCorreo());
		statement.setString(2, repartidor.getNombre());
		statement.setString(3, repartidor.getApaterno());
		statement.setString(4, repartidor.getAmaterno());
		statement.setString(5, repartidor.getContrasenia());
		System.out.println("agregado");
		boolean agregar = statement.executeUpdate() > 0;
		statement.close();
		con.desconectar();
		return agregar;
	}
	
	/**
	 * Envia una contrasenia autogenerada al correo del repartidor.
	 * @param repartidor repartidor para los datos de la nueva Cuenta.
	 * @return La contrasenia autogenerada.
	 */
	public String mandaContrasena(String correo_us, String nombre, String app, String apm) throws NoSuchAlgorithmException {
		System.out.println(correo_us);
		
		String[] symbols = {"0", "1", "2", "3","4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f","g","h","i","j","k","l","m","n","o","p","q"
		        ,"r","s","t","u","v","w","x","y","z","A", "B", "C", "D", "E", "F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
		        int length = 10;
		        Random random = SecureRandom.getInstanceStrong();
		        StringBuilder sb = new StringBuilder(length);
		        for (int i = 0; i < length; i++) {
		            int indexRandom = random.nextInt(symbols.length);
		            sb.append(symbols[indexRandom]);
		        }
		        String password = sb.toString();
		 
		        Properties propiedad = new Properties();
		        propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		        propiedad.setProperty("mail.smtp.starttls.enable", "true");
		        propiedad.setProperty("mail.smtp.port", "587");
		        propiedad.setProperty("mail.smtp.auth", "true");
		        
		        Session sesion = Session.getDefaultInstance(propiedad);
		        String correoEnvia = "trofiinfo.noreply@gmail.com";
		        String contrasena = "TrofiappIS.";
		        String receptor = correo_us;
		        String asunto = "Bienvenido a la Comunidad Trofi";
		        String mensaje= "Hola "+nombre+" "+app+ " "+apm+".\n"+
		        "\nBienvenido a nuestro servicio de entrega de alimentos a domicilio Trofi. Has sido registrado como repartidor en el sistema con exito.\n"+
		        		"\nTu contraseña para ingresar es: "+password+"\n"+"\nIngresa a la app para poder empezar a entregar los pedidos que TU selecciones.\n"+
		        "\nGracias por formar parte de esta gran comunidad.\n"+"\nEl equipo de cuentas de Trofi.";
		        
		        MimeMessage mail = new MimeMessage(sesion);
		        try {
		            mail.setFrom(new InternetAddress (correoEnvia));
		            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (receptor));
		            mail.setSubject(asunto);
		            mail.setText(mensaje);
		            
		            Transport transportar = sesion.getTransport("smtp");
		            transportar.connect(correoEnvia,contrasena);
		            transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));          
		            transportar.close(); 
		            
		        } catch (AddressException ex) {
		          System.out.println("no se pudo enviar 1");
		        } catch (MessagingException ex) {
		        	 System.out.println("no se pudo enviar 2");
		        }
		return password;
		
	}

}