/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.beans;

/**
 *
 * @author Diego Lemus
 */
public class Sales {
    private int idSales;
    private String couponCode;
    private byte verified;
    private Promotion promotion;
    private User client;
    private SalesState state;
    
    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public User getClient() {
        return client;
    }

    public void setClient(User client) {
        this.client = client;
    }

    public SalesState getState() {
        return state;
    }

    public void setState(SalesState state) {
        this.state = state;
    }
    
    public int getIdSales() {
        return idSales;
    }

    public void setIdSales(int idSales) {
        this.idSales = idSales;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public byte getVerified() {
        return verified;
    }

    public void setVerified(byte verified) {
        this.verified = verified;
    }

    // Constructores
    public Sales(){
        this.idSales = 0;
        this.couponCode = "";
        this.verified = 0;
        this.client = null;
        this.promotion = null;
        this.state = null;
    }
    
    public Sales(int idSales, String couponCode, byte verified, Promotion promotion, User client, SalesState state){
        this.idSales = idSales;
        this.couponCode = couponCode;
        this.verified = verified;
        this.promotion = promotion;
        this.client = client;
        this.state = state;
    }
    
    public Sales(String couponCode, byte verified, Promotion promotion, User client, SalesState state){
        this.idSales = 0;
        this.couponCode = couponCode;
        this.verified = verified;
        this.promotion = promotion;
        this.client = client;
        this.state = state;
    }
}
