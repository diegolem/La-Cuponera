/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.primefaces.json.JSONArray;
import org.primefaces.json.JSONObject;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.CompanyType;
import sv.edu.udb.www.beans.Employee;
import sv.edu.udb.www.beans.Sales;
import sv.edu.udb.www.beans.SalesState;
import sv.edu.udb.www.beans.User;
import sv.edu.udb.www.beans.UserType;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.SalesModel;
import sv.edu.udb.www.model.SalesStateModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.model.UserTypeModel;
import sv.edu.udb.www.utilities.Mail;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "userController", urlPatterns = {"/user.do", "/admin/user.do"})
public class userController extends HttpServlet {

    SalesModel sales = new SalesModel();
    UserModel users = new UserModel();
    SalesStateModel salesStates = new SalesStateModel();
    UserTypeModel typeUsers = new UserTypeModel();
    HashMap<String, String> errorsList = new HashMap<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try {
            String opcion = request.getParameter("op");
            HttpSession _s = request.getSession(true);
            if (_s.getAttribute("logged") != null) {
                switch (opcion) {
                    case "list_client":
                        listClient(request, response);
                        break;

                    case "new_client":
                        newClient(request, response);
                        break;

                    case "insert_client":
                        insertClient(request, response);
                        break;

                    case "details_client":
                        detailsClient(request, response);
                        break;

                    case "delete_client":
                        deleteClient(request, response);
                        break;
                }
            } else {
                switch (opcion) {
                    case "insert_client_public":
                        insertClientPublic(request, response);
                        break;
                    case "confirmation":
                        confirmation(request, response);
                        break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(userController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void listClient(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        UserType typeClient = typeUsers.getUserType(1, false);
        ArrayList<User> users = this.users.getUsers(typeClient, false);

        request.setAttribute("users", users);
        request.getRequestDispatcher("/admin/user/listClient.jsp").forward(request, response);
    }

    private void newClient(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.setAttribute("title", "Agregar Cliente");
        request.getRequestDispatcher("/admin/user/newClient.jsp").forward(request, response);
    }

    private void insertClient(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        errorsList.clear();
        User user = new User();
        user.setType(this.typeUsers.getUserType(1, false));

        if (!Validacion.isEmpty(request.getParameter("name"))) {
            user.setName(request.getParameter("name"));
        } else {
            errorsList.put("name", "El nombre es un campo obligatorio");
        }

        if (!Validacion.isEmpty(request.getParameter("last_name"))) {
            user.setLastName(request.getParameter("last_name"));
        } else {
            errorsList.put("name", "El apellido es un campo obligatorio");
        }

        if (Validacion.esCorreo(request.getParameter("email"))) {
            user.setEmail(request.getParameter("email"));
            if (!users.checkEmail(request.getParameter("email"))) {
                user.setEmail(request.getParameter("email"));
            } else {
                errorsList.put("email", "Favor agregar otra dirección de correo electrónico");
            }
        } else {
            errorsList.put("email", "El email no es válido [algo@server.com]");
        }

        if (Validacion.esDui(request.getParameter("dui"))) {
            user.setDui(request.getParameter("dui"));
        } else {
            errorsList.put("dui", "El dui no es válido");
        }

        if (Validacion.esNit(request.getParameter("nit"))) {
            user.setNit(request.getParameter("nit"));
        } else {
            errorsList.put("nit", "El nit no es válido");
        }

        if (errorsList.size() > 0) {
            request.setAttribute("errorsList", errorsList);
            request.setAttribute("client", user);
            request.getRequestDispatcher("/admin/user.do?op=new_client").forward(request, response);
        } else {
            String password = PasswordResetModel.generatePasswordWithoutEncrypt();
            user.setPassword(PasswordResetModel.parsingPassword(password));

            user.setIdConfirmation(UserModel.getIdConfirmation());

            Mail gmail = new Mail();
            String url = request.getRequestURL().toString() + "?op=confirmation&id=" + user.getIdConfirmation();
            gmail.setAddressee(user.getEmail());
            gmail.setAffair("Bienvenido a la cuponera");
            gmail.setMessage("Bienvenido usuario <h3>" + user.getName() + " " + user.getLastName() + "</h3>"
                    + "<br><br>Se le informa que su clave es <h1>" + password + "</h1>"
                    + "<br><br>Sin embargo debe primero confirmar que es su cuenta confirme <a target='a_blank' href= '" + url + "'>aqui</a>");

            if (users.insertUserWithConfirmation(user)) { // Se insertó correctamente

                if (gmail.sendEmail()) {
                    request.getSession().setAttribute("success", "Usuario registrado");
                    response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
                } else {
                    User last = this.users.getLastUser(false);
                    this.users.deleteUser(last.getIdUser());
                    request.getSession().setAttribute("error", "El correo electronico no existe");
                    response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
                }
            } else {
                request.getSession().setAttribute("error", "El usuario no se ha podido registrar");
                response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
            }
        }
    }

    private void confirmation(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        String idConfirmation = request.getParameter("id");

        User user;

        if ((user = this.users.getUser(idConfirmation, true)) != null) {

            if (users.confirmUser(user)) {
                HttpSession _s = request.getSession(true);
                _s.setAttribute("logged", true);
                _s.setAttribute("user", user);
                _s.setAttribute("redirect", request.getContextPath() + "/client/index.jsp");
                _s.setAttribute("type", "client");
                response.sendRedirect(_s.getAttribute("redirect").toString());
            } else {
                request.getSession().setAttribute("error", "No se ha pódido confirmar su cuenta");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }

        } else {
            request.getSession().setAttribute("error", "El Id de confirmacion no existe");
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    }

    private void insertClientPublic(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        errorsList.clear();
        User user = new User();
        user.setType(this.typeUsers.getUserType(1, false));

        if (!Validacion.isEmpty(request.getParameter("name"))) {
            user.setName(request.getParameter("name"));
        } else {
            errorsList.put("name", "El nombre es un campo obligatorio");
        }

        if (!Validacion.isEmpty(request.getParameter("last_name"))) {
            user.setLastName(request.getParameter("last_name"));
        } else {
            errorsList.put("name", "El apellido es un campo obligatorio");
        }

        if (Validacion.esCorreo(request.getParameter("email"))) {
            user.setEmail(request.getParameter("email"));
            if (!users.checkEmail(request.getParameter("email"))) {
                user.setEmail(request.getParameter("email"));
            } else {
                errorsList.put("email", "Favor agregar otra dirección de correo electrónico");
            }
        } else {
            errorsList.put("email", "El email no es válido [algo@server.com]");
        }

        if (Validacion.esDui(request.getParameter("dui"))) {
            user.setDui(request.getParameter("dui"));
        } else {
            errorsList.put("dui", "El dui no es válido");
        }

        if (Validacion.esNit(request.getParameter("nit"))) {
            user.setNit(request.getParameter("nit"));
        } else {
            errorsList.put("nit", "El nit no es válido");
        }

        if (errorsList.size() > 0) {
            out.println("0");
        } else {
            String password = PasswordResetModel.generatePasswordWithoutEncrypt();
            user.setPassword(PasswordResetModel.parsingPassword(password));

            user.setIdConfirmation(UserModel.getIdConfirmation());
            Mail gmail = new Mail();
            String url = request.getRequestURL().toString() + "?op=confirmation&id=" + user.getIdConfirmation();

            gmail.setAddressee(user.getEmail());
            gmail.setAffair("Bienvenido a la cuponera");
            gmail.setMessage("Bienvenido usuario <h3>" + user.getName() + " " + user.getLastName() + "</h3>"
                    + "<br><br>Se le informa que su clave es <h1>" + password + "</h1>"
                    + "<br><br>Sin embargo debe primero confirmar que es su cuenta confirme <a target='a_blank' href= '" + url + "'>aqui</a>");

            if (users.insertUserWithConfirmation(user)) { // Se insertó correctamente
                if (gmail.sendEmail()) {
                    out.println("1");
                } else {
                    User last = this.users.getLastUser(false);
                    this.users.deleteUser(last.getIdUser());
                    out.println("-1");
                }
            } else {
                out.println("-2");
            }
        }
    } // Fin insertClientPublic()

    private void detailsClient(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, ServletException {
        String id = request.getParameter("id");

        if (!Validacion.isEmpty(id)) {
            if (Validacion.esEnteroPositivo(id)) {

                int idClient = Integer.parseInt(id);

                User user = users.getUser(idClient, false);

                if (user != null) {
                    List<Sales> cuponesCanjeados = sales.getSales(user, salesStates.getSalesState(1, false), true);
                    List<Sales> cuponesDisponibles = sales.getSales(user, salesStates.getSalesState(2, false), true);
                    List<Sales> cuponesVencidos = sales.getSales(user, salesStates.getSalesState(3, false), true);

                    request.setAttribute("client", user);

                    request.setAttribute("cuponesCanjeados", cuponesCanjeados);
                    request.setAttribute("cuponesDisponibles", cuponesDisponibles);
                    request.setAttribute("cuponesVencidos", cuponesVencidos);
                    request.getRequestDispatcher("/admin/user/detailsClient.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha podido encontrar");
                    response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
                }

            } else {
                request.getSession().setAttribute("error", "El id del usuario es equivocado");
                response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
            }
        } else {
            request.getSession().setAttribute("error", "Debe de usar un ID para cliente");
            response.sendRedirect(request.getContextPath() + "/admin/user.do?op=list_client");
        }
    }

    private void deleteClient(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        PrintWriter out = response.getWriter();
        String id = request.getParameter("id");

        if (!Validacion.isEmpty(id)) {
            if (Validacion.esEnteroPositivo(id)) {
                int idClient = Integer.parseInt(id);

                User client;

                if ((client = users.getUser(idClient, true)) != null) {
                    boolean next = true;

                    if (!client.getSales().isEmpty() && !sales.deleteSales(client)) {
                        next = false;
                        out.print("0");
                    }

                    if (next && users.deleteUser(idClient)) {
                        out.print("1");
                    }

                } else {
                    out.print("0");
                }
            } else {
                out.print("0");
            }
        } else {
            out.print("-1");
        }
    }
}
