/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.beans;

import java.util.List;

/**
 *
 * @author Diego Lemus
 */
public class UserType {
    private int idUserType;
    private String type;
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
    
    public int getIdUserType() {
        return idUserType;
    }

    public void setIdUserType(int idUserType) {
        this.idUserType = idUserType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    // Constructores
    public UserType(){
        this.idUserType = 0;
        this.type = "";
        this.users = null;
    }
    
    public UserType(int idUserType, String type){
        this.idUserType = idUserType;
        this.type = type;
        this.users = null;
    }
    
    public UserType(int idUserType, String type, List<User> users){
        this.idUserType = idUserType;
        this.type = type;
        this.users = users;
    }
}
