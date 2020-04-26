package control;

import java.sql.ResultSet;
import java.util.ArrayList;

import modelo.ConexionBD;
import modelo.Orden;

public class CrearOrden {
	
	ConexionBD con = new ConexionBD("jdbc:mysql://localhost:3306/trofi?serverTimezone=UTC","root","Pumasunam540.");
	ArrayList<Orden> ord_acep = new ArrayList<>();
	
	public ArrayList<Orden> getOrdenesAceptadas() {
		return ord_acep;
	}
    
    public void crearOrden(int id_alimento,String correo_cl,int id_orden,String estado_orden,String dir_cliente,String correo_rep,int cat){
        Orden ord = new Orden(id_alimento,correo_cl,id_orden,estado_orden,dir_cliente,correo_rep);
        ord.insertarBD(ord,cat);
    }
    
    public ResultSet mostrarDetalles(Orden o){
        return con.getQuery("SELECT c.nombre, c.a_paterno,c.materno,c.telefono,o.direccion_cliente FROM cliente c inner join orden o on c.correo_e = "+o.getCorreo_cl()+";");
    }
    
    public void actualizaEstado(Orden o,String est_ord){
        con.setQuery("UPDATE orden SET estado_orden ='"+est_ord+"' WHERE id_orden = "+o.getId_orden()+";");
    }
    
    public void agregaOrdAceptada(Orden o) {
    	ord_acep.add(o);
    }
}
