package sv.edu.udb.www.model;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.codec.digest.DigestUtils;
import sv.edu.udb.www.beans.PasswordReset;
import static sv.edu.udb.www.model.Connection.conexion;

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
    
    public boolean insertResetRequest(String email) throws SQLException{
        PasswordReset _pr = new PasswordReset(email, UUID.randomUUID().toString());
        boolean res;
        
        try {
            this.conectar();
            st = conexion.prepareStatement("INSERT INTO password_reset(email, token) VALUES(?, ?);");
            st.setString(1, _pr.getEmail());
            st.setString(2, _pr.getToken());
            res = st.execute();
            this.desconectar();

            return res;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }
    
    public PasswordReset getResetRequest(String token) throws SQLException{
        PasswordReset _pr = null;
        
        try {
            this.conectar();
            st = conexion.prepareStatement("SELECT * FROM password_reset WHERE token = ?;");
            st.setString(1, token);
            rs = st.executeQuery();

            if (rs.next()) {                
                _pr = new PasswordReset(rs.getInt("id"), rs.getString("email"), rs.getString("token"), LocalDateTime.parse(rs.getString("date")), rs.getBoolean("expired"));
            }
            this.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
        }
        
        return _pr;
    }
}
