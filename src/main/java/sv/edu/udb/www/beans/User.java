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
public class User extends Entity{
    private int idUser;
    private String lastName;
    private String dui;
    private String nit;
    private boolean confirmed;
    private String idConfirmation;	
    private List<Sales> sales;
    private UserType type;
    
    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public String getIdConfirmation() {
        return idConfirmation;
    }

    public void setIdConfirmation(String idConfirmation) {
        this.idConfirmation = idConfirmation;
    }
    
    // Constructores
    public User(){
        super();
        this.idUser = 0;
        this.lastName = "";
        this.type = null;
        this.dui = "";
        this.nit = "";
        this.sales = null;
        this.confirmed = false;
        this.idConfirmation = "";
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
        super(name, email, password);
        this.idUser = idUser;
        this.lastName = lastName;
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
        super(name, email, password);
        this.idUser = idUser;
        this.lastName = lastName;
        this.type = type;
        this.dui = dui;
        this.nit = nit;
        this.sales = sales;
    }
}
