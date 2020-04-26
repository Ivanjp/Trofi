package control;

import modelo.Repartidor;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


public class Alta_Repartidor {
    
    public Repartidor darDeAlta(String correo, String nombre, String app, String apm, String psw) {
        Repartidor rep = new Repartidor(correo,nombre,app,apm,psw); 
        return rep;
    }

    public String generaContrasenia() throws NoSuchAlgorithmException {
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
        return password;
    }
}
