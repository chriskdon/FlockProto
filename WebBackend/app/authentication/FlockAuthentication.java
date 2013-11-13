package authentication;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Created by chriskellendonk on 11/13/2013.
 */
public class FlockAuthentication {
    private MessageDigest md;

    private static FlockAuthentication ourInstance = new FlockAuthentication();

    public static FlockAuthentication getInstance() {
        return ourInstance;
    }

    private FlockAuthentication() {
        try {
          md = MessageDigest.getInstance("MD5");
        }   catch(NoSuchAlgorithmException ex) {
          System.err.println("MD5 Algorithm Not Available");
        }
    }

    public String generateSalt() {
       return UUID.randomUUID().toString();
    }

    public String generateSaltedPassowrd(String password, String salt) {
        return new String(Hex.encodeHex(md.digest((password + salt).getBytes())));
    }

    public boolean checkPasswod(String saltedPassword, String password, String salt) {
        return (generateSaltedPassowrd(password, salt) == saltedPassword);
    }
}
