/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
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

    public User getLastUser(boolean relationship) throws SQLException {
        try {
            String sql = "SELECT MAX(user.id) FROM user ORDER BY user.id DESC";

            this.conectar();
            st = conexion.prepareStatement(sql);
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
    } // Fin getLastUser()

    public ArrayList<User> getUsers(boolean relationship) throws SQLException {
        try {
            ArrayList<User> users = new ArrayList<>();
            ArrayList<Integer> id = new ArrayList<>();
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

    public ArrayList<User> getUsersWithoutEspecificUser(UserType type, User user, boolean relationship) throws SQLException {
        try {
            ArrayList<User> users = new ArrayList<User>();
            ArrayList<Integer> id = new ArrayList<Integer>();
            String sql = "SELECT id FROM user WHERE user_type = ? AND id != ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            
            st.setInt(1, type.getIdUserType());
            st.setInt(2, user.getIdUser());
            
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
    
    public User getUserClientDui(String dui, boolean relationship) throws SQLException {
        try {
            String sql = "SELECT * FROM user WHERE dui = ? AND user_type = 1";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, dui);
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
                user.setIdConfirmation(rs.getString("id_confirmation"));
                user.setConfirmed(rs.getBoolean("confirmed"));

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
    
    public User getUser(String id_confirmation, boolean relationship) throws SQLException {
        try {
            String sql = "SELECT * FROM user WHERE id_confirmation = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, id_confirmation);
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
                user.setIdConfirmation(rs.getString("id_confirmation"));
                user.setConfirmed(rs.getBoolean("confirmed"));

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
                user.setIdConfirmation(rs.getString("id_confirmation"));
                user.setConfirmed(rs.getBoolean("confirmed"));

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
            String sql = "INSERT INTO user(name, last_name, email, password, user_type, dui, nit, confirmed) VALUES(?, ?, ?, ?, ?, ?, ?, 1)";

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

    public boolean insertUserWithConfirmation(User user) throws SQLException {
        try {
            int affectedRows = 0;
            String sql = "INSERT INTO user(name, last_name, email, password, user_type, dui, nit, confirmed, id_confirmation) VALUES(?, ?, ?, ?, ?, ?, ?, 0, ?)";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, user.getName());
            st.setString(2, user.getLastName());
            st.setString(3, user.getEmail());
            st.setString(4, user.getPassword());
            st.setInt(5, user.getType().getIdUserType());
            st.setString(6, user.getDui());
            st.setString(7, user.getNit());
            st.setString(8, user.getIdConfirmation());
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

    public boolean confirmUser(User user) throws SQLException {
        try {
            int idUsers = user.getIdUser();

            int affectedRows = 0;
            String sql = "UPDATE user SET confirmed = 1, id_confirmation = '' where id_confirmation = ?";

            this.conectar();
            cs = conexion.prepareCall(sql);
            cs.setString(1, user.getIdConfirmation());
            affectedRows = cs.executeUpdate();

            this.desconectar();
            boolean result = affectedRows > 0;

            if (result) {
                user = this.getUser(idUsers, true);
            }

            return result;
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
                UserApp user = new UserApp(rs.getString("id"), rs.getString("email"), rs.getString("password"), rs.getString("user_type"), rs.getByte("confirmed"), rs.getString("id_confirmation"));
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

    public static String getIdConfirmation() {
        return UUID.randomUUID().toString();
    }// Fin getIdConfirmation()

    public boolean mailExists(String email) {
        try {

            String sql = "SELECT * FROM `all_users` WHERE email = ?";

            this.conectar();

            this.st = conexion.prepareStatement(sql);
            this.st.setString(1, email);
            this.rs = this.st.executeQuery();

            boolean result = this.rs.next();

            this.desconectar();

            return result;

        } catch (Exception error) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, error);
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }

    public boolean mailExists(String email, String id) {
        try {

            String sql = "SELECT * FROM `all_users` WHERE email = ? AND id != ?";

            this.conectar();

            this.st = conexion.prepareStatement(sql);

            this.st.setString(1, email);
            this.st.setString(2, id);

            this.rs = this.st.executeQuery();

            boolean result = this.rs.next();

            this.desconectar();

            return result;

        } catch (Exception error) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, error);
            try {
                this.desconectar();
            } catch (SQLException ex) {
                Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
    }

    public boolean checkEmail(String email) throws SQLException {
        try {
            String sql = "SELECT * FROM all_users WHERE email = ?";

            this.conectar();
            st = conexion.prepareStatement(sql);
            st.setString(1, email);
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
    }// Fin checkEmail()

    public String getUserEntity(String email) throws SQLException {
        String res = null;
        try {

            this.conectar();
            st = conexion.prepareStatement("SELECT * FROM all_users WHERE email = ?");
            st.setString(1, email);
            rs = st.executeQuery();

            if (rs.next()) {
                res = rs.getString("user_type");
            }else{
                res = null;
            }

            this.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(UserModel.class.getName()).log(Level.SEVERE, null, ex);
            this.desconectar();
            res = null;
        }

        return res;
    }
}
