package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.Promotion;
import sv.edu.udb.www.beans.PromotionState;

/**
 *
 * @author Diego Lemus
 */
public class PromotionModel extends Connection {
    
    public List<Promotion> getPromotions(boolean relationship) throws SQLException{
        try {
            ArrayList<Promotion> promotions = new ArrayList<>();
            ArrayList<Integer> id = new ArrayList<>();
            String sql = "SELECT id FROM promotion";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                promotions.add(this.getPromotion(id.get(i), relationship)); 
            }
            return promotions;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getPromotions()
    public List<Promotion> getPromotionsA(boolean relationship) throws SQLException{
        try {
                ArrayList<Promotion> promotions = new ArrayList<>();
                ArrayList<Integer> id = new ArrayList<>();
                String sql = "SELECT id FROM promotion WHERE id_state = 2";

                this.conectar();
                st = conexion.prepareStatement(sql);
                rs = st.executeQuery();
                while (rs.next()) {
                    id.add(rs.getInt("id"));
                }
                this.desconectar();

                for (int i = 0; i < id.size(); i++) {
                    promotions.add(this.getPromotion(id.get(i), relationship));
                }
                return promotions;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getPromotions()
    public ArrayList<Promotion> getPagination(int page,boolean relationship) throws SQLException{
        try {
            ArrayList<Promotion> promotion = new ArrayList<Promotion>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "CALL pagination(?)";
            
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, page);
            rs = cs.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();
            
            for (int i = 0; i < id.size(); i++) {
                promotion.add(this.getPromotion(id.get(i), relationship));
            }
            return promotion;
        } catch (SQLException ex) {
            this.desconectar();
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public int getBtn(int page) throws SQLException{
        try {
            int cuenta = 0;
            String sql = "CALL pagination(?)";
            
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, page);
            rs = cs.executeQuery();
            while (rs.next()) {
                cuenta = rs.getInt("Items");
            }
            this.desconectar();
                        
            return cuenta;
        } catch (SQLException ex) {
            this.desconectar();
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
    public List<Promotion> getPromotions(PromotionState promotionState, boolean relationship) throws SQLException{
        try {
            ArrayList<Promotion> promotions = new ArrayList<>();
            ArrayList<Integer> id = new ArrayList<>();
            String sql = "SELECT id FROM promotion WHERE id_state = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, promotionState.getIdPromotionState());
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                promotions.add(this.getPromotion(id.get(i), relationship)); 
            }
            return promotions;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getPromotions()
    
    public List<Promotion> getPromotions(String idCompany, boolean relationship) throws SQLException{
        try {
            ArrayList<Promotion> promotions = new ArrayList<>();
            ArrayList<Integer> id = new ArrayList<>();
            String sql = "SELECT id FROM promotion WHERE id_company = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, idCompany);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                promotions.add(this.getPromotion(id.get(i), relationship)); 
            }
            return promotions;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getPromotions()
      
    public Promotion getPromotion(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM promotion WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                CompanyModel companyModel = new CompanyModel();
                PromotionStateModel promotionStateModel = new PromotionStateModel();
                SalesModel salesModel = new SalesModel();
                
                Promotion promotion = new Promotion(rs.getInt("id"), rs.getString("title"), rs.getDouble("regular_price"), rs.getDouble("ofert_price"), rs.getDate("init_date"), rs.getDate("end_date"), rs.getDate("limit_date"), rs.getInt("limit_cant"), rs.getString("description"), rs.getString("other_details"), rs.getString("image"), rs.getInt("coupons_sold"), rs.getInt("coupons_available"), rs.getDouble("earnings"), rs.getDouble("charge_service"), rs.getString("rejected_description"));
                promotion.setCompany(companyModel.getCompany(rs.getString("id_company"), relationship));
                promotion.setPromotionState(promotionStateModel.getPromotionState(rs.getInt("id_state"), false));
                
                this.desconectar();               
                if(relationship){
                    promotion.setSales(salesModel.getSales(promotion, relationship));
                }
                return promotion;
            }
            this.desconectar();
            return null;
        } 
        catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getPromotion()
    
    public boolean insertPromotion(Promotion promotion) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "CALL insert_promotion(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setString(1, promotion.getTitle());
            cs.setDouble(2, promotion.getRegularPrice());
            cs.setDouble(3, promotion.getOfertPrice());
            cs.setTimestamp(4, new java.sql.Timestamp(promotion.getInitDate().getTime()));
            cs.setTimestamp(5, new java.sql.Timestamp(promotion.getEndDate().getTime()));
            cs.setTimestamp(6, new java.sql.Timestamp(promotion.getLimitDate().getTime()));
            cs.setInt(7, promotion.getLimitCant());
            cs.setString(8, promotion.getDescription());
            cs.setString(9, promotion.getOtherDetails());
            cs.setString(10, promotion.getImage());
            cs.setString(11, promotion.getCompany().getIdCompany());
            affectedRows = cs.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin insertPromotion()
    
    public boolean updatePromotion(Promotion promotion) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE promotion SET title = ?, regular_price = ?, ofert_price = ?, init_date = ?, end_date = ?, limit_date = ?, limit_cant = ?, description = ?, other_details = ?, image = ?, id_state = ?, rejected_description = ?  WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, promotion.getTitle());
            st.setDouble(2, promotion.getRegularPrice());
            st.setDouble(3, promotion.getOfertPrice());
            st.setTimestamp(4, new java.sql.Timestamp(promotion.getInitDate().getTime()));
            st.setTimestamp(5, new java.sql.Timestamp(promotion.getEndDate().getTime()));
            st.setTimestamp(6, new java.sql.Timestamp(promotion.getLimitDate().getTime()));
            st.setInt(7, promotion.getLimitCant());
            st.setString(8, promotion.getDescription());
            st.setString(9, promotion.getOtherDetails());
            st.setString(10, promotion.getImage());
            //st.setString(11, promotion.getCompany().getIdCompany());
            st.setInt(11, 1); //Estado 1 - En espera de aprobacion
            st.setString(12, "");//Descripción vacía
            st.setInt(13, promotion.getIdPromotion());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }
    
    public boolean changeStatePromotion(int idPromotion, int idState) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE promotion SET id_state = ? WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, idState);
            st.setInt(2, idPromotion);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// changeStatePromotion()
    
    public boolean rejectedPromotion(Promotion promotion) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE promotion SET id_state = ?, rejected_description = ? WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, 3); //Rechazada
            st.setString(2, promotion.getRejectedDescription());
            st.setInt(3, promotion.getIdPromotion());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin rejectedPromotion()
    
    public boolean acceptPromotion(Promotion promotion) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE promotion SET id_state = ? WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, 2); //Aprobada
            st.setInt(2, promotion.getIdPromotion());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin rejectedPromotion()
    
    public boolean deletePromotion(int id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM promotion WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(PromotionModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin deletePromotion()
}
