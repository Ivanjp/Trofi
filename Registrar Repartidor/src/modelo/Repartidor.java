package modelo;

public class Repartidor {
   
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
      
  
  
}
