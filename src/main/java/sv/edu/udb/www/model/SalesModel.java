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
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Promotion;
import sv.edu.udb.www.beans.Sales;
import sv.edu.udb.www.beans.SalesState;
import sv.edu.udb.www.beans.User;

/**
 *
 * @author Diego Lemus
 */
public class SalesModel extends Connection {

    public ArrayList<Sales> getSales(User user, SalesState saleState, boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales WHERE client_id = ? AND sales_state = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, user.getIdUser());
            st.setInt(2, saleState.getIdSalesState());
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSales()

    public ArrayList<Sales> getSales(int ids, boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT * FROM sales WHERE client_id = ? AND sales_state = 2";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, ids);
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();
            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }

    public ArrayList<Sales> getSales(boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales";

            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();

            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSales()

    public ArrayList<Sales> getSales(SalesState state, boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales WHERE sales_state = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, state.getIdSalesState());
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSales()

    public ArrayList<Sales> getSales(Promotion promotion, boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales WHERE promotion_id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, promotion.getIdPromotion());
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSales()

    public ArrayList<Sales> getSales(User user, boolean relationship) throws SQLException {
        try {
            ArrayList<Sales> sales = new ArrayList<Sales>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM sales WHERE client_id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, user.getIdUser());
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                sales.add(this.getSale(id.get(i), relationship));
            }
            return sales;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSales()

    public Sales getSale(int id, boolean relationship) throws SQLException {
        try {
            String sql = "SELECT * FROM sales WHERE id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                UserModel userModel = new UserModel();
                PromotionModel promotionModel = new PromotionModel();
                SalesStateModel salesStateModel = new SalesStateModel();

                Sales sale = new Sales();
                sale.setIdSales(rs.getInt("id"));
                sale.setCouponCode(rs.getString("coupon_code"));
                sale.setVerified(rs.getByte("verified"));

                int idPromotion = rs.getInt("promotion_id");
                int idClient = rs.getInt("client_id");
                int idState = rs.getInt("sales_state");

                this.desconectar();
                sale.setPromotion(promotionModel.getPromotion(idPromotion, false));
                sale.setClient(userModel.getUser(idClient, false));
                sale.setState(salesStateModel.getSalesState(idState, false));
                return sale;
            }

            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }//Fin getSale

    public String generateCode(Company company) throws SQLException {
        try {
            int cuenta = 0;
            String codigo = "";
            String sql = "CALL count_sales(?)";
            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setString(1, company.getIdCompany());
            rs = cs.executeQuery();
            if (rs.next()) {
                cuenta = Integer.parseInt(rs.getString("cuenta"));
                this.desconectar();
                if (cuenta > 0) {
                    cuenta += 1;
                    if (cuenta < 10) {
                        codigo = company.getIdCompany() + "000000" + cuenta;
                    } else if (cuenta >= 10 && cuenta < 100) {
                        codigo = company.getIdCompany() + "00000" + cuenta;
                    } else if (cuenta >= 100 && cuenta < 1000) {
                        codigo = company.getIdCompany() + "0000" + cuenta;
                    } else if (cuenta >= 1000 && cuenta < 10000) {
                        codigo = company.getIdCompany() + "000" + cuenta;
                    } else if (cuenta >= 10000 && cuenta < 100000) {
                        codigo = company.getIdCompany() + "00" + cuenta;
                    } else if (cuenta >= 100000 && cuenta < 1000000) {
                        codigo = company.getIdCompany() + "0" + cuenta;
                    } else {
                        codigo = company.getIdCompany() + cuenta;
                    }
                } else {
                    codigo = company.getIdCompany() + "0000001";
                }
            }
            return codigo;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }

    public boolean insertSales(Sales sale) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "CALL insert_sales(?, ?, ?, ?, ?)";

            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setString(1, sale.getCouponCode());
            cs.setInt(2, sale.getPromotion().getIdPromotion());
            cs.setInt(3, sale.getClient().getIdUser());
            cs.setInt(4, sale.getState().getIdSalesState());
            cs.registerOutParameter(5, java.sql.Types.INTEGER);
            cs.executeUpdate();
            affectedRows = cs.getInt(5);
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin insertSales

    public boolean checkSale(String couponCode, String userDui) throws SQLException {
        try {
            String sql = "CALL check_sale(?, ?)";

            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setString(1, couponCode);
            cs.setString(2, userDui);
            rs = cs.executeQuery();

            if (rs.next()) {
                this.desconectar();
                return true;
            }

            this.desconectar();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// fin checkSale()

    public boolean changeState(int id) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "UPDATE sale SET verified = 1 WHERE id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// changeState

    /*
    public boolean updateSales(Sales sales) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE sales SET coupon_code = ?, promotion_id = ?, client_id = ?, verified = ?, sales_state = ? WHERE id = ?";
            
            this.conectar();
            st= conexion.prepareStatement(sql);
            st.setString(1, sales.getCouponCode());
            st.setInt(2, sales.getPromotion().getIdPromotion());
            st.setInt(3, sales.getClient().getIdUser());
            st.setByte(4, sales.getVerified());
            st.setInt(5, sales.getState().getIdSalesState());
            st.setInt(6, sales.getIdSales());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;          
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateSales()
     */
    public boolean deleteSales(int id) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM sales WHERE id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// deleteSales()

    public boolean deleteSales(User user) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM sales WHERE client_id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, user.getIdUser());
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(SalesModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// deleteSales()
}
