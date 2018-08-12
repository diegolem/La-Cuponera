/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import sv.edu.udb.www.beans.User;
import sv.edu.udb.www.beans.UserApp;
import sv.edu.udb.www.beans.UserType;

/**
 *
 * @author Diego Lemus
 */
public class UserModel extends Connection {

    public ArrayList<User> getUsers(boolean relationship) throws SQLException {
        try {
            ArrayList<User> users = new ArrayList<User>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM user";

            this.conectar();
            st = conexion.prepareStatement(sql);
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                users.add(this.getUser(id.get(i), relationship));
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getUsers()

    public ArrayList<User> getUsers(UserType type, boolean relationship) throws SQLException {
        try {
            ArrayList<User> users = new ArrayList<User>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM user WHERE user_type = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, type.getIdUserType());
            rs = st.executeQuery();
            while (rs.next()) {
                id.add(rs.getInt("id"));
            }
            this.desconectar();

            for (int i = 0; i < id.size(); i++) {
                users.add(this.getUser(id.get(i), relationship));
            }
            return users;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getUsers()

    public User getUser(int id, boolean relationship) throws SQLException {
        try {
            String sql = "SELECT * FROM user WHERE id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                int userType = rs.getInt("user_type");
                SalesModel salesModel = new SalesModel();
                UserTypeModel typeModel = new UserTypeModel();

                User user = new User();
                user.setIdUser(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setDui(rs.getString("dui"));
                user.setNit(rs.getString("nit"));

                this.desconectar();
                user.setType(typeModel.getUserType(userType, false));
                if (relationship) {
                    user.setSales(salesModel.getSales(user, relationship));
                }
                return user;
            }
            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin getUser()

    public boolean insertUser(User user) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO user(name, last_name, email, password, user_type, dui, nit) VALUES(?, ?, ?, ?, ?, ?, ?)";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setInt(5, user.getType().getIdUserType());
            st.setString(6, user.getDui());
            st.setString(7, user.getNit());
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin insertUser

    public boolean updateUser(User user) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "CALL update_user(?, ?, ?, ?)";

            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, user.getIdUser());
            cs.setString(2, user.getName());
            cs.setString(3, user.getLastName());
            cs.setString(4, user.getEmail());
            cs.setInt(5, user.getType().getIdUserType());
            affectedRows = cs.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin updateUser()

    public boolean updateUserPassword(User user) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "CALL update_user_password(?, ?)";

            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setInt(1, user.getIdUser());
            cs.setString(2, user.getPassword());
            affectedRows = cs.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }//Fin updateUserPassword()

    public boolean checkUserDui(String dui) throws SQLException {
        try {
            String sql = "SELECT * FROM user WHERE dui = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, dui);
            rs = st.executeQuery();

            if (rs.next()) {
                this.desconectar();
                return true;
            }
            this.desconectar();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// checkUser

    public UserApp login(String email, String password) throws SQLException {
        try {
            String sql = "SELECT * FROM all_users WHERE email = ? AND password = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, email);
            st.setString(2, password);
            rs = st.executeQuery();

            if (rs.next()) {
                UserApp user = new UserApp(rs.getString("id"), rs.getString("email"), rs.getString("password"), rs.getString("user_type"));
                this.desconectar();
                return user;
            }
            this.desconectar();
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return null;
        }
    }// Fin login()

    public boolean deleteUser(int id) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "DELETE FROM user WHERE id = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setInt(1, id);
            affectedRows = st.executeUpdate();

            this.desconectar();
            return affectedRows > 0;
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            return false;
        }
    }// Fin deleteUser()
}