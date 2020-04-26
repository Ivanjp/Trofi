import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import control.Alta_Repartidor;
import modelo.ConexionBD;
import modelo.Repartidor;

public class Main {
	public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
		 
	     
	    ConexionBD conexion = new ConexionBD("jdbc:mysql://localhost:3306/trofi?serverTimezone=UTC","root","Pumasunam540.");
	    conexion.conectar();
	    Alta_Repartidor a = new Alta_Repartidor();
	    
	    Repartidor r =a.darDeAlta("Luis123@gmail.com", "Luis3","Suarez3", "Lopez3", a.generaContrasenia());
	    
	    r.insertarBD(r);
	    
	    
	     
	 }
}
