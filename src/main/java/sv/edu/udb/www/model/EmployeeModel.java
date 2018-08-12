package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Employee;

/**
 *
 * @author Diego Lemus
 */
public class EmployeeModel extends Connection {
    public ArrayList<Employee> getEmployees(boolean relationship) throws SQLException{
        try {
            ArrayList<Employee> employees = new ArrayList<Employee>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM employee";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                employees.add(this.getEmployee(id.get(i), relationship)); 
            }
           
            return employees;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getEmployees();
    
    public Employee getEmployee(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM employee WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                CompanyModel companyModel = new CompanyModel();
                Employee employee = new Employee(rs.getInt("id"), rs.getString("name"), rs.getString("last_name"), rs.getString("email"), rs.getString("password"));
                String idCompany = rs.getString("id_company");
                
                this.desconectar();
                employee.setCompany(companyModel.getCompany(idCompany, false));
                return employee;
            }
            
            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getEmployee()
    
    public List<Employee> getEmployees(Company company, boolean relationship) throws SQLException{
        try {
            ArrayList<Employee> employees = new ArrayList<>();
            ArrayList<Integer> id = new ArrayList<>();
            String sql = "SELECT id FROM employee WHERE id_company = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, company.getIdCompany());
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                employees.add(this.getEmployee(id.get(i), relationship)); 
            }
            return employees;
        } 
        catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getEmployees();
            
    public boolean insertEmployee(Employee employee) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO employee(name, last_name, email, password, id_company) VALUES(?, ?, ?, ?, ?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1,employee.getName());
            st.setString(2,employee.getLastName());
            st.setString(3, employee.getEmail());
            st.setString(4, employee.getPassword());
            st.setString(5, employee.getCompany().getIdCompany());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }
    
    public boolean updateEmployee(Employee employee) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "CALL update_employee(?, ?, ?, ?)";
            
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, employee.getIdEmployee());
            cs.setString(2, employee.getEmail());
            cs.setString(3, employee.getName());
            cs.setString(4, employee.getLastName());
            affectedRows = cs.executeUpdate(); 
                    
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateEmployee()
    
    public boolean updateEmployeePassword(Employee employee) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "CALL update_employee_password(?, ?)";
            
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, employee.getIdEmployee());
            cs.setString(2, employee.getPassword());
            affectedRows = cs.executeUpdate(); 
                    
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateEmployeePassword
    
    public boolean deleteEmployee(int id) throws SQLException{
        try {
            int affectedRows = 0;
            //String sql = "SELECT * FROM employee WHERE id = ?";
            String sql = "DELETE FROM employee WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(EmployeeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// FIn deleteEmployee();
}


