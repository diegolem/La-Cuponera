package sv.edu.udb.www.beans;

import java.util.List;

/**
 *
 * @author Diego Lemus
 */
public class CompanyType {
    private int idCompanyType;
    private String type;
    private List<Company> companies;

    public int getIdCompanyType() {
        return idCompanyType;
    }

    public List<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(List<Company> companies) {
        this.companies = companies;
    }

    public void setIdCompanyType(int idCompanyType) {
        this.idCompanyType = idCompanyType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    //Constructores
    public CompanyType(){
        this.idCompanyType = 0;
        this.type = "";
        this.companies = null;
    }
    
    public CompanyType(int idCompanyType, String type){
        this.idCompanyType = idCompanyType;
        this.type = type;
        this.companies = null;
    }
    
    public CompanyType(int idCompanyType, String type, List<Company> companies){
        this.idCompanyType = idCompanyType;
        this.type = type;
        this.companies = companies;
    }
}
