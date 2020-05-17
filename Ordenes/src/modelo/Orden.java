package modelo;

/**
* Clase que se encarga de crear un objeto Orden
* @version 1.3 5/5/2020
* @author Perez Perez Jorge Ivan
*/
public class Orden {

	   String correo_cl;//Correo del cliente que hace la orden
	   int id_orden;//id de la orden
	   String estado_orden;//estado de entrega de la orden
	   String dir_cliente;//direccion de entrega del cliente
	   String correo_rep;//correo del repartidor que se encargara de llevar la orden

		/**
		* Constructor del objeto Orden
		*/
	    public Orden(String correo_cl,int id_orden,String estado_orden,String dir_cliente,String correo_rep) {
	    	this.correo_cl = correo_cl;
	    	this.id_orden = id_orden;
	    	this.estado_orden = estado_orden;
	    	this.dir_cliente = dir_cliente;
	    	this.correo_rep = correo_rep;
	    }

		/**
		* Metodo que regresa el correo del cliente
		*/
		public String getCorreo_cl() {
			return correo_cl;
		}

		 /**
		* Metodo que regresa el id de la orden
		*/
		public int getId_orden() {
			return id_orden;
		}

		 /**
		* Metodo que regresa el estado de la orden
		*/
		public String getEstado_orden() {
			return estado_orden;
		}

		 /**
		* Metodo que regresa la direccion de entrega del cliente
		*/
		public String getDireccion_cliente() {
			return dir_cliente;
		}

		 /**
		* Metodo que regresa el correo del repartidor
		*/
		public String getCorreo_rep() {
			return correo_rep;
		}
		
		 /**
		* Metodo que modifica el correo del cliente
		*/
		public void setCorreo_cl(String correo_cl) {
			this.correo_cl = correo_cl;
		}

		/**
		* Metodo que modifica el id de la orden
		*/
		public void setId_orden(int id_orden) {
			this.id_orden = id_orden;
		}

		/**
		* Metodo que modifica el estado de la orden
		*/
		public void setEstado_orden(String estado_orden) {
			this.estado_orden = estado_orden;
		}


		/**
		* Metodo que modifica la direccion de entrega del cliente
		*/
		public void setDir_cliente(String dir_cliente) {
			this.dir_cliente = dir_cliente;
		}


		/**
		* Metodo que modifica el correo del repartidor
		*/
		public void setCorreo_rep(String correo_rep) {
			this.correo_rep = correo_rep;
		}

	}