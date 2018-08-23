/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Diego Lemus
 */
public class Promotion {
    private int idPromotion;
    private String title;
    private double regularPrice;
    private double ofertPrice;
    private Date initDate;
    private Date endDate;
    private Date limitDate;
    private int limitCant;
    private String description;
    private String otherDetails;
    private String image;
    private int couponsSold;
    private int couponsAvailable;
    private double earnings;
    private double chargeService;
    private String rejectedDescription;
    private Company company;
    private List<Sales> sales;
    private PromotionState promotionState;

    public String getRejectedDescription() {
        return rejectedDescription;
    }

    public void setRejectedDescription(String rejectedDescription) {
        this.rejectedDescription = rejectedDescription;
    }

    public int getCouponsSold() {
        return couponsSold;
    }

    public void setCouponsSold(int couponsSold) {
        this.couponsSold = couponsSold;
    }

    public int getCouponsAvailable() {
        return couponsAvailable;
    }

    public void setCoupons_available(int couponsAvailable) {
        this.couponsAvailable = couponsAvailable;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public double getChargeService() {
        return chargeService;
    }

    public void setChargeService(double chargeService) {
        this.chargeService = chargeService;
    }
    
    public int getIdPromotion() {
        return idPromotion;
    }

    public void setIdPromotion(int idPromotion) {
        this.idPromotion = idPromotion;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(double regularPrice) {
        this.regularPrice = regularPrice;
    }

    public double getOfertPrice() {
        return ofertPrice;
    }

    public void setOfertPrice(double ofertPrice) {
        this.ofertPrice = ofertPrice;
    }

    public Date getInitDate() {
        return initDate;
    }

    public void setInitDate(Date initDate) {
        this.initDate = initDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getLimitDate() {
        return limitDate;
    }

    public void setLimitDate(Date limitDate) {
        this.limitDate = limitDate;
    }

    public int getLimitCant() {
        return limitCant;
    }

    public void setLimitCant(int limitCant) {
        this.limitCant = limitCant;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public List<Sales> getSales() {
        return sales;
    }

    public void setSales(List<Sales> sales) {
        this.sales = sales;
    }

    public PromotionState getPromotionState() {
        return promotionState;
    }

    public void setPromotionState(PromotionState promotionState) {
        this.promotionState = promotionState;
    }
    
    public String getStringInitDate() {
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(initDate);
    }
    
    public String getStringEndDate() {
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(endDate);
    }
    
    public String getStringLimitDate() {
        DateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
        return dt.format(limitDate);
    }
    
    // Constructores
    public Promotion(){
        this.idPromotion = 0;
        this.title = "";
        this.regularPrice = 0;
        this.ofertPrice = 0;
        this.initDate = null;
        this.endDate = null;
        this.limitDate = null;
        this.limitCant = 0;
        this.description = "";
        this.otherDetails = "";
        this.image = "";
        this.sales = null;
        this.company = null;
        this.promotionState = null;
        this.couponsSold = 0;
        this.couponsAvailable = 0;
        this.earnings = 0;
        this.chargeService = 0;
        this.rejectedDescription = "";
    }
    
    /*
        idPromotion,
        title,
        regularPrice,
        ofertPrice,
        initDate,
        endDate,
        limitDate,
        limitCant,
        description,
        otherDetails,
        image,
        couponsSold,
        couponsAvailable,
        earnings,
        chargeService,
        rejectedDescription,
        sales (List Sales),
        company (Object Company),
        promotionState (object PromotionState)
    */
    public Promotion(String title, double regularPrice, double ofertPrice, Date initDate, Date endDate, Date limitDate, int limitCant, String description, String otherDetails, String image, Company company, PromotionState promotionState, String rejectedDescription){
        this.idPromotion = 0;
        this.title = title;
        this.regularPrice = regularPrice;
        this.ofertPrice = ofertPrice;
        this.initDate = initDate;
        this.endDate = endDate;
        this.limitDate = limitDate;
        this.limitCant = limitCant;
        this.description = description;
        this.otherDetails = otherDetails;
        this.image = image;
        this.sales = null;
        this.company = company;
        this.promotionState = promotionState;
        this.couponsSold = 0;
        this.couponsAvailable = 0;
        this.earnings = 0;
        this.chargeService = 0;
        this.rejectedDescription = rejectedDescription;
    }
    
    /*
        idPromotion,
        title,
        regularPrice,
        ofertPrice,
        initDate,
        endDate,
        limitDate,
        limitCant,
        description,
        otherDetails,
        image, 
        couponsSold,
        couponsAvailable,
        earnings,
        chargeService,
        rejectedDescription
    */
    public Promotion(int idPromotion, String title, double regularPrice, double ofertPrice, Date initDate, Date endDate, Date limitDate, int limitCant, String description, String otherDetails, String image, int couponsSold, int couponsAvailable, double earnings, double chargeService, String rejectedDescription){
        this.idPromotion = idPromotion;
        this.title = title;
        this.regularPrice = regularPrice;
        this.ofertPrice = ofertPrice;
        this.initDate = initDate;
        this.endDate = endDate;
        this.limitDate = limitDate;
        this.limitCant = limitCant;
        this.description = description;
        this.otherDetails = otherDetails;
        this.image = image;
        this.sales = null;
        this.company = null;
        this.promotionState = null;
        this.couponsSold = couponsSold;
        this.couponsAvailable = couponsAvailable;
        this.earnings = earnings;
        this.chargeService = chargeService;
        this.rejectedDescription = rejectedDescription;
    }
    
    /*
        idPromotion,
        title,
        regularPrice,
        ofertPrice,
        initDate,
        endDate,
        limitDate,
        limitCant,
        description,
        otherDetails,
        image,
        couponsSold,
        couponsAvailable,
        earnings,
        chargeService,
        rejectedDescription,
        sales (List Sales),
        company (Object Company),
        promotionState (object PromotionState)
    */
    public Promotion(int idPromotion, String title, double regularPrice, double ofertPrice, Date initDate, Date endDate, Date limitDate, int limitCant, String description, String otherDetails, String image, int couponsSold, int couponsAvailable, double earnings, double chargeService, String rejectedDescription, List<Sales> sales, Company company, PromotionState promotionState){
        this.idPromotion = idPromotion;
        this.title = title;
        this.regularPrice = regularPrice;
        this.ofertPrice = ofertPrice;
        this.initDate = initDate;
        this.endDate = endDate;
        this.limitDate = limitDate;
        this.limitCant = limitCant;
        this.description = description;
        this.otherDetails = otherDetails;
        this.image = image;
        this.sales = sales;
        this.company = company;
        this.promotionState = promotionState;
        this.couponsSold = couponsSold;
        this.couponsAvailable = couponsAvailable;
        this.earnings = earnings;
        this.chargeService = chargeService;
        this.rejectedDescription = rejectedDescription;
    }
}
