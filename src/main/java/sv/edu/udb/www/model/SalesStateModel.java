/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.SalesState;

/**
 *
 * @author Diego Lemus
 */
public class SalesStateModel extends Connection{
    
    public ArrayList<SalesState> getSalesState(boolean relationship) throws SQLException{
        try {
            ArrayList<SalesState> list = new ArrayList<SalesState>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales_state";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                list.add(this.getSalesState(id.get(i), relationship)); 
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getSalesState()
    
    public SalesState getSalesState(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM sales_state WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs =  st.executeQuery();
            
            if(rs.next()){
                SalesModel salesModel = new SalesModel();
                
                SalesState salesState = new SalesState(rs.getInt("id"), rs.getString("state"));
                
                this.desconectar();
                if(relationship){
                    salesState.setSales(salesModel.getSales(salesState, relationship));
                }
                return salesState;
            }
            
            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(SalesStateModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getSalesState
    
    public boolean insertSalesState(SalesState salesState) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO sales_state(state) VALUES(?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, salesState.getState());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin insertSalesState()
    
    public boolean updateSalesState(SalesState salesState) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPATE sales_state SET state = ? WHERE id = ?";
            this.conectar();
            
            st = conexion.prepareStatement(sql);
            st.setString(1, salesState.getState());
            st.setInt(2, salesState.getIdSalesState());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateSalesState()
    
    public boolean deleteSalesState(int id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM sales_state WHERE id = ?";
            
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
    }// Fin deleteSalesState()
}
