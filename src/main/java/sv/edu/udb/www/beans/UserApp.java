package sv.edu.udb.www.beans;

import java.io.Serializable;
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
public class UserApp implements Serializable {
    private String id;
    private String email;
    private String password;

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
    }
    
    public UserApp(String id, String email, String password, String userType){
        this.id = id;
        this.email = email;
        this.password = password;
        this.userType = userType;
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
