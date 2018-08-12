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
public class SalesState {
    private int idSalesState;
    private String state;
    private List<Sales> sales;
    
    public int getIdSalesState() {
        return idSalesState;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }
    
    public void setIdSalesState(int idSalesState) {
        this.idSalesState = idSalesState;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    
    // Constructores
    public SalesState(){
        this.idSalesState = 0;
        this.state = "";
        this.sales = null;
    }
    
    public SalesState(int idSalesState, String state){
        this.idSalesState = idSalesState;
        this.state = state;
        this.sales = null;
    }
    
    public SalesState(int idSalesState, String state, List<Sales> sales){
        this.idSalesState = idSalesState;
        this.state = state;
        this.sales = sales;
    }
}
