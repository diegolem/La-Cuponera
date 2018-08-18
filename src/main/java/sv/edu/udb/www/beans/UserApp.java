package sv.edu.udb.www.beans;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.EmployeeModel;
import sv.edu.udb.www.model.UserModel;

/**
 *
 * @author leonardo
 */
public class UserApp {
    private String id;
    private String email;
    private String password;
    private byte confirmed;
    private String idConfirmation;

    public String getIdConfirmation() {
        return idConfirmation;
    }

    public void setIdConfirmation(String idConfirmation) {
        this.idConfirmation = idConfirmation;
    }
    
    public byte getConfirmed() {
        return confirmed;
    }

    public void setConfirmed(byte confirmed) {
        this.confirmed = confirmed;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
    private String userType;
    
    public UserApp(){
        this.id = "";
        this.email = "";
        this.password = "";
        this.userType = "";
        this.confirmed = 0;
        this.idConfirmation = "";
    }
    
    public UserApp(String id, String email, String password, String userType, byte confirmed, String idConfirmartion){
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.confirmed = confirmed;
        this.idConfirmation = idConfirmartion;
    }
    
    public Company getCompany(){
        try {
            CompanyModel model = new CompanyModel();
            return model.getCompany(this.id, false);
        } catch (SQLException ex) {
            Logger.getLogger(UserApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public User getUser(){
        try {
            UserModel model = new UserModel();
            return model.getUser(Integer.parseInt(this.id), false);
        } catch (SQLException ex) {
            Logger.getLogger(UserApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public Employee getEmployee(){
        try {
            EmployeeModel model = new EmployeeModel();
            return model.getEmployee(Integer.parseInt(this.id), false);
        } catch (SQLException ex) {
            Logger.getLogger(UserApp.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
