package sv.edu.udb.www.model;

import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author leonardo
 */
public class PasswordResetModel extends Connection{
    private static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePasswordWithoutEncrypt(){
        String password = "";
        for(int i = 0; i < 10; i++){
            password += ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
        }
        return password;
    }//fin generatePasswordWithoutEncrypt()
    
    public static String generatePassword(){
        String password = "";
        for(int i = 0; i < 10; i++){
            password += ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
        }
        String encryptPassword =  DigestUtils.sha256Hex(password);
        return encryptPassword;
    }//fin generatePassword()
    
    public static String parsingPassword(String password){
        return DigestUtils.sha256Hex(password);
    }// Fin parinfPassword()
}
