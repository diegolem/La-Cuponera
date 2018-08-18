package sv.edu.udb.www.beans;

import java.time.LocalDateTime;

/**
 *
 * @author leonardo
 */
public class PasswordReset {
   private int id;
   private String email;
   private String token;
   private LocalDateTime date;
   private boolean expired;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isExpired() {
        return expired;
    }

    public void setExpired(boolean expired) {
        this.expired = expired;
    }

    public PasswordReset() {
    }

    public PasswordReset(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public PasswordReset(int id, String email, String token, LocalDateTime date, boolean expired) {
        this.id = id;
        this.email = email;
        this.token = token;
        this.date = date;
        this.expired = expired;
    }
    
    
}
