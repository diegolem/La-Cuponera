package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.CompanyType;

/**
 *
 * @author Diego Lemus
 */
public class CompanyModel extends Connection {
    public List<Company> getCompanies(boolean relationship) throws SQLException {
        try {
            ArrayList<Company> companies = new ArrayList<>();
            ArrayList<String> id = new ArrayList<>();
            String sql = "SELECT id FROM company";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getString("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                companies.add(this.getCompany(id.get(i), relationship)); 
            }
            return companies;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getCompanies()
    
    public List<Company> getCompanies(CompanyType companyType, boolean relationship) throws SQLException {
        try {
            ArrayList<Company> companies = new ArrayList<>();
            ArrayList<String> id = new ArrayList<>();
            String sql = "SELECT * FROM company WHERE type_company = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, companyType.getIdCompanyType());
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getString("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){ 
                companies.add(this.getCompany(id.get(i), relationship)); 
            }
            return companies;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getCompanies()
    
    public Company getCompany(String id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM company WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                EmployeeModel employees = new EmployeeModel();
                PromotionModel promotions = new PromotionModel();
                CompanyTypeModel companyTypeModel = new CompanyTypeModel();
                String idCompany = rs.getString("id");
                int idCompanyType = rs.getInt("type_company");
                
                Company company = new Company();  
                company.setIdCompany(rs.getString("id"));
                company.setName(rs.getString("name"));
                company.setAddress(rs.getString("address"));
                company.setContactName(rs.getString("contact_name"));
                company.setTelephone(rs.getString("telephone"));
                company.setEmail(rs.getString("email"));
                company.setPctComission(rs.getInt("pct_comission"));
                company.setPassword(rs.getString("password"));
                
                this.desconectar();
                company.setCompanyType(companyTypeModel.getCompanyType(idCompanyType, false));
                if(relationship){
                    company.setEmployees(employees.getEmployees(company, false));
                    company.setPromotion(promotions.getPromotions(idCompany, false));
                }
                return company;
            }
            this.desconectar();
            return null;
        } 
        catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getCompany
    
    public boolean insertCompany(Company company) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO company VALUES(?,?,?,?,?,?,?,?,?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, company.getIdCompany());
            st.setString(2, company.getName());
            st.setString(3, company.getAddress());
            st.setString(4, company.getContactName());
            st.setString(5, company.getTelephone());
            st.setString(6, company.getEmail());
            st.setInt(7, company.getCompanyType().getIdCompanyType());
            st.setInt(8,company.getPctComission());
            st.setString(9,company.getPassword());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin insertCompany
    
    public boolean updateCompany(Company company) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "CALL update_company(?, ?, ?, ?, ?, ?)";
            
            this.conectar();
            cs= conexion.prepareCall(sql);
            cs.setString(1, company.getIdCompany());
            cs.setString(2, company.getAddress());
            cs.setString(3, company.getContactName());
            cs.setString(4, company.getTelephone());
            cs.setString(5, company.getEmail());
            cs.setInt(6, company.getPctComission());
            affectedRows = cs.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin updateCompany
    
    public boolean updateCompanyPassword(Company company) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "CALL update_company_password(?, ?)";
            
            this.conectar();
            cs= conexion.prepareCall(sql);
            cs.setString(1, company.getIdCompany());
            cs.setString(2, company.getPassword());
            affectedRows = cs.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin updateCompanyUpdate
    
    public boolean deleteCompany(String id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM company WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, id);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    } //Fin deleteCompany
}
