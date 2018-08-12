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
import sv.edu.udb.www.beans.PromotionState;

/**
 *
 * @author Diego Lemus
 */
public class PromotionStateModel extends Connection {
    
    public ArrayList<PromotionState> getPromotionState(boolean relationship) throws SQLException{
        try {
            ArrayList<PromotionState> list = new ArrayList<PromotionState>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM promotion_state";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                list.add(this.getPromotionState(id.get(i), relationship)); 
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }
    
    public PromotionState getPromotionState(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM promotion_state WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                PromotionModel promotionModel = new PromotionModel();
                int idState = rs.getInt("id");
                
                PromotionState promotionState = new PromotionState(rs.getInt("id"), rs.getString("state"));
                
                this.desconectar();
                if(relationship){
                    promotionState.setPromotions(promotionModel.getPromotions(promotionState, relationship));
                }
                return promotionState;
            }
            this.desconectar();
            return null;
        } 
        catch (SQLException ex) {
            Logger.getLogger(PromotionStateModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getPromotionState
    
    public boolean insertPromotionState(PromotionState promotionState) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO promotion_state(state) VALUES(?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, promotionState.getState());
            
            affectedRows = st.executeUpdate();
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin insertPromotionState()
    
    public boolean updatePromotionState(PromotionState promotionState) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE promotion_state SET state = ? WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, promotionState.getState());
            st.setInt(2, promotionState.getIdPromotionState());
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(CompanyTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin updatePromotionState()
    
    public boolean deletePromotionState(int id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM promotion_state WHERE id = ?";
            
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
    }// Fin deletePromotionState()
}
