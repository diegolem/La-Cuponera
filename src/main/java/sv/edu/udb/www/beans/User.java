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
public class User {
    private int idUser;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String dui;
    private String nit;
    private List<Sales> sales;
    private UserType type;
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
    
    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
    
    // Constructores
    public User(){
        this.idUser = 0;
        this.lastName = "";
        this.email = "";
        this.password = "";
        this.type = null;
        this.dui = "";
        this.nit = "";
        this.sales = null;
    }
    
    /*
        idUser,
        name,
        lastName,
        email,
        password,
        dui, 
        nit,
        type (Object UserType)
    */
    public User(int idUser, String name, String lastName, String email, String password, String dui, String nit, UserType type){
        this.idUser = idUser;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.dui = dui;
        this.nit = nit;
        this.sales = null;
    }
    
    /*
        idUser,
        name,
        lastName,
        email,
        password,
        dui, 
        nit,
        type (Object UserType),
        sales (List sales)
    */
    public User(int idUser, String name, String lastName, String email, String password, String dui, String nit, UserType type, List<Sales> sales){
        this.idUser = idUser;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.type = type;
        this.dui = dui;
        this.nit = nit;
        this.sales = sales;
    }
}
