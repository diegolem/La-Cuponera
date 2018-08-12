package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.CompanyType;

/**
 *
 * @author Diego Lemus
 */
public class CompanyTypeModel extends Connection {
    
    public ArrayList<CompanyType> getCompanyTypes(boolean relationship) throws SQLException{
        try {
            ArrayList<CompanyType> list = new ArrayList<CompanyType>();
            ArrayList<String> id = new ArrayList<String>();
            String sql = "SELECT id FROM company_type";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { id.add(rs.getString("id")); }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){ list.add(this.getCompanyType(Integer.parseInt(id.get(i)), relationship)); }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }
    
    public CompanyType getCompanyType(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM company_type WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                CompanyType companyType = new CompanyType(rs.getInt("id"), rs.getString("type"));
                
                this.desconectar();
                if(relationship){
                    CompanyModel companyModel = new CompanyModel();
                    companyType.setCompanies(companyModel.getCompanies(companyType, relationship));   
                }
                return companyType;
            }
            
            this.desconectar();
            return null;
        } 
        catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getCompanyType
    
    public  boolean insertCompanyType(CompanyType companyType) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO company_type(type) VALUES(?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, companyType.getType());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }
    
    public boolean updateCompanyType(int id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "SELECT * FROM company_type WHERE id = ?";
            this.conectar();
            
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateCompanyType
    
    public boolean deleteCompanyType(int id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM company_type WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin deleteCompanyType
}
