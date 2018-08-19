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
public class Company extends Entity{
    private String idCompany;
    private String address;
    private String contactName;
    private String telephone;
    private int pctComission;
    
    private List<Employee> employees;
    private CompanyType companyType;
    private List<Promotion> promotions;

    public CompanyType getCompanyType() {
        return companyType;
    }

    public void setCompanyType(CompanyType companyType) {
        this.companyType = companyType;
    }

    public List<Promotion> getPromotion() {
        return promotions;
    }

    public void setPromotion(List<Promotion> promotions) {
        this.promotions = promotions;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
     
    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public int getPctComission() {
        return pctComission;
    }

    public void setPctComission(int pctComission) {
        this.pctComission = pctComission;
    }
    
    public String getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(String idCompany) {
        this.idCompany = idCompany;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    // Constructores
    public Company(){
        super();
        this.idCompany = "";
        this.address = "";
        this.contactName = "";
        this.telephone = "";
        this.pctComission = 0;
        this.employees = null;
        this.promotions = null;
        this.companyType = null;
    }
    
    /*
        idCompany,
        name,
        address,
        contactName,
        telephone,
        email,
        pctComission,
        password,
        companyType (object)
    */
    
    public Company(String idCompany, String name, String address, String contactName, String telephone, String email, int pctComission, String password, CompanyType companyType){
        super(name, email, password);
        this.idCompany = idCompany;
        this.address = address;
        this.contactName = contactName;
        this.telephone = telephone;
        this.pctComission = pctComission;
        this.companyType = companyType;
    }
    
    /*
        idCompany,
        name,
        address,
        contactName,
        telephone,
        email,
        pctComission,
        password,
        employees (List objects),
        promotions (List objects),
        companyType (object)
    */
    
    public Company(String idCompany, String name, String address, String contactName, String telephone, String email, int typeCompany, int pctComission, String password, List<Employee> employees, List<Promotion> promotions, CompanyType companyType){
        super(name, email, password);
        this.idCompany = idCompany;
        this.address = address;
        this.contactName = contactName;
        this.telephone = telephone;
        this.pctComission = pctComission;
        this.employees = employees;
        this.promotions = promotions;
        this.companyType = companyType;
    }
}
