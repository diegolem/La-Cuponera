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
import sv.edu.udb.www.beans.UserType;

/**
 *
 * @author Diego Lemus
 */
public class UserTypeModel extends Connection{
    
    public ArrayList<UserType> getUserType(boolean relationship) throws SQLException{
        try {
            ArrayList<UserType> list = new ArrayList<UserType>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM user_type";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) { 
                id.add(rs.getInt("id")); 
            }
            this.desconectar();
            
            for(int i = 0; i < id.size(); i++){
                list.add(this.getUserType(id.get(i), relationship)); 
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(UserTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }
    
    public UserType getUserType(int id, boolean relationship) throws SQLException{
        try {
            String sql = "SELECT * FROM user_type WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if(rs.next()){
                UserModel userModel = new UserModel();
                UserType type = new UserType(rs.getInt("id"), rs.getString("type"));
                
                this.desconectar();
                if(relationship){
                    type.setUsers(userModel.getUsers(type, relationship));
                }
                return type;
            }
            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UserTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin geUserType
    
    public boolean insertUserType(UserType userType) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO user_type VALUES(?, ?)";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, userType.getIdUserType());
            st.setString(2, userType.getType());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin insertUserType
    
    public boolean updateUserType(UserType type) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "UPDATE user_type SET type = ? WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, type.getType());
            st.setInt(2, type.getIdUserType());
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateUserType()
    
    public boolean deleteUserType(String id) throws SQLException{
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM user_type WHERE id = ?";
            
            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, id);
            affectedRows = st.executeUpdate();
            
            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserTypeModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// deleteUserType
}
