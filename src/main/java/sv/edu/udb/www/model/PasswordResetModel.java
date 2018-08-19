package sv.edu.udb.www.model;

import java.security.SecureRandom;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public class PasswordResetModel extends Connection {

    private static String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generatePasswordWithoutEncrypt() {
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
        }
        return password;
    }//fin generatePasswordWithoutEncrypt()

    public static String generatePassword() {
        String password = "";
        for (int i = 0; i < 10; i++) {
            password += ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length()));
        }
        String encryptPassword = DigestUtils.sha256Hex(password);
        return encryptPassword;
    }//fin generatePassword()

    public static String parsingPassword(String password) {
        return DigestUtils.sha256Hex(password);
    }// Fin parinfPassword()

    public PasswordReset insertResetRequest(String email) throws SQLException {
        PasswordReset _pr = new PasswordReset(email, UUID.randomUUID().toString());
        boolean res;

        try {
            this.conectar();
            st = conexion.prepareStatement("INSERT INTO password_resets(email, token) VALUES(?, ?);");
            st.setString(1, _pr.getEmail());
            st.setString(2, _pr.getToken());
            res = st.executeUpdate() > 0;
            this.desconectar();

            return res ? _pr : null;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }

    public PasswordReset getResetRequest(String token) throws SQLException {
        PasswordReset _pr = null;

        try {
            this.conectar();
            st = conexion.prepareStatement("SELECT * FROM password_resets WHERE token = ?;");
            st.setString(1, token);
            rs = st.executeQuery();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            if (rs.next()) {
                _pr = new PasswordReset(rs.getInt("id"), rs.getString("email"), rs.getString("token"), LocalDateTime.parse(rs.getString("date"), formatter), rs.getBoolean("expired"));
            }
            this.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
        }

        return _pr;
    }

    public boolean confirmRequest(String token) throws SQLException {
        boolean res;
        
        try {
            this.conectar();
            st = conexion.prepareStatement("UPDATE password_resets SET expired = 1 WHERE token = ?;");
            st.setString(1, token);
            res = st.executeUpdate() > 0;
            
            this.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            res = false;
            this.desconectar();
        }

        return res;
    }

    public boolean resetUserPassword(String email, String password) {
        boolean res;
        try {
            UserModel usrMdl = new UserModel();

            String userType = usrMdl.getUserEntity(email);
            String table = "";

            switch (userType) {
                case "client":
                    table = "user";
                    break;

                case "company":
                    table = "company";
                    break;

                case "employee":
                    table = "employee";
                    break;
            }

            this.conectar();
            st = conexion.prepareStatement("UPDATE " + table + " SET password = ?;");
            st.setString(1, PasswordResetModel.parsingPassword(password));
            res = st.executeUpdate() > 0;

            this.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(PasswordResetModel.class.getName()).log(Level.SEVERE, null, ex);
            res = false;
        }

        return res;
    }
}
