package modelo;

/**
 * Una clase que nos ayuda a modelar un repartidor
 * @author Quintero Villeda Erik
 */
public class Repartidor {
    private String nombre; //Nombre del repartidor
    private String aPaterno; //Apellido Paterno del repartidor
    private String aMaterno; //Apellido Materno del repartidor
    private String correo_e; //Correo del repartidor
    private String contrasenia; //Contrasenia del repartidor
    
    /**
     * Metodo contructor que inicializa un repartidor
     */
    public Repartidor() {}
    
    /**
     * Metodo contructor que inicializa un repartidor
     * @param email correo del repartidor
     * @param nombre nombre del repartidor
     * @param apellido1 apellido paterno del repartidor
     * @param apellido2 apellido materno del repartidor
     * @param password contrasenia del repartidor
     */
    public Repartidor(String email,String nombre, String apellido1,String apellido2,String password) {
        this.nombre=nombre;
        this.aPaterno = apellido1;
        this.aMaterno = apellido2;
        this.correo_e = email;
        this.contrasenia = password;
    }
    
    /**
     * Metodo que devuelve el nombre del repartidor
     * @return String el nombre del repartidor
     */
    public String getNombre() {
        return nombre;
    }
    
    /**
     * Metodo que asigna un nombre al repartidor
     * @param String el nombre del repartidor
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Metodo que devuelve el apellido paterno del repartidor
     * @return String el apellido paterno del repartidor
     */
    public String getApaterno() {
        return aPaterno;
    }
    
    /**
     * Metodo que asigna un apellido paterno al repartidor
     * @param String el apellido paterno del repartidor
     */
    public void setApaterno(String apellido1) {
        this.aPaterno = apellido1;
    }
    
    /**
     * Metodo que devuelve el apellido materno del repartidor
     * @return String el apellido materno del repartidor
     */
    public String getAmaterno() {
        return aMaterno;
    }
    
    /**
     * Metodo que asigna un apellido materno al repartidor
     * @param String el apellido materno del repartidor
     */
    public void setAmaterno(String apellido2) {
        this.aMaterno = apellido2;
    }
    
    /**
     * Metodo que devuelve el correo del repartidor
     * @return String el correo del repartidor
     */
    public String getCorreo() {
        return correo_e;
    }
    
    /**
     * Metodo que asigna un correo al repartidor
     * @param String el correo del repartidor
     */
    public void setCorreo(String email) {
        this.correo_e = email;
    }
    
    /**
     * Metodo que devuelve la contrasenia del repartidor
     * @return String la contrasenia del repartidor
     */
    public String getContrasenia() {
        return contrasenia;
    }
    
    /**
     * Metodo que asigna una contrasenia al repartidor
     * @param String la contrasenia del repartidor
     */
    public void setContrasenia(String password) {
        this.contrasenia = password;
    }
}
      
  
  
}
