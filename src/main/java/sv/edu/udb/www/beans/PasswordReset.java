package sv.edu.udb.www.beans;

/**
 *
 * @author leonardo
 */
public class PasswordReset {
   private int id;
   private String email;
   private String token;
   private boolean auth;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
   
    public PasswordReset(){
        this.id = 0;
        this.email = "";
        this.token = "";
        this.auth = false;
    }
    
    public PasswordReset(int id, String email, String token, boolean auth){
        this.id = id;
        this.email = email;
        this.token = token;
        this.auth = auth;
    }
}
