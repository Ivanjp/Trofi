package modelo;

public class Repartidor {
   
  ConexionBD con = new ConexionBD();
  String nombre;
  String Ap_paterno;
  String Ap_materno;
  String correo;
  String contrasenia;

    public Repartidor(String correo,String nombre, String Ap_paterno, String Ap_materno, String contrasenia) {
        this.nombre = nombre;
        this.Ap_paterno = Ap_paterno;
        this.Ap_materno = Ap_materno;
        this.correo = correo;
        this.contrasenia = contrasenia;
    }

    public String getNombre() {
        return nombre;
    }

    public String getAp_paterno() {
        return Ap_paterno;
    }

    public String getAp_materno() {
        return Ap_materno;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContrasenia() {
        return contrasenia;
    }
    
    public void setContrasenia(String psw){
        contrasenia = psw;
    }
    
    public void insertarBD(Repartidor r){
        con.setQuery("INSERT INTO repartidor(correo_e,nombre,a_paterno,a_materno,contrasenia) VALUES('"+ r.getCorreo() +"','"+r.getNombre()+"','"+r.getAp_paterno()+"','"+r.getAp_materno()+"','"+r.getContrasenia()+"');");
    }
    
    
  
  
}
