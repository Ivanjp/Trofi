package modelo;

import java.sql.ResultSet;

public class Orden {
	
   ConexionBD con = new ConexionBD("jdbc:mysql://localhost:3306/trofi?serverTimezone=UTC","root","Pumasunam540.");
    
   int id_alimento;
   String correo_cl;
   int id_orden;
   String estado_orden;
   String dir_cliente;
   String correo_rep;
   
    public Orden(int id_alimento,String correo_cl,int id_orden,String estado_orden,String dir_cliente,String correo_rep) {
    	this.id_alimento = id_alimento;
    	this.correo_cl = correo_cl;
    	this.id_orden = id_orden;
    	this.estado_orden = estado_orden;
    	this.dir_cliente = dir_cliente;
    	this.correo_rep = correo_rep;
    }
    
	public int getId_alimento() {
		return id_alimento;
	}

	public String getCorreo_cl() {
		return correo_cl;
	}

	public int getId_orden() {
		return id_orden;
	}

	public String getEstado_orden() {
		return estado_orden;
	}

	public String getDir_cliente() {
		return dir_cliente;
	}

	public String getCorreo_rep() {
		return correo_rep;
	}
	
	public ResultSet consultarBD(Orden ord){
      return con.getQuery("SELECT "+ord.id_orden+" FROM orden");
   }
   
//   public void actualizarBD(Orden o){
//      con.setQuery("UPDATE orden SET estado_orden ='"+est_ord+"' WHERE id_orden = "+o.getId_orden()+";");
//   }
   
   public void insertarBD(Orden o,int id_cat){
       con.setQuery("INSERT INTO orden(correo_e,id_orden,estado_orden,direccion_cliente,repartidor) values('" + o.correo_cl + "','"+ o.id_orden+"','"+ o.estado_orden+"','"+ o.dir_cliente +"','"+o.correo_rep+";");
       con.setQuery("INSERT INTO tener(correo_e,id_orden,id_categoria,id_alimento) values('" + o.correo_cl + "','"+ o.id_orden+"','"+id_cat+"','"+ o.id_alimento+";");
   }
    
}
