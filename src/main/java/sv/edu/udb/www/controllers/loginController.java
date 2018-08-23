package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.udb.www.beans.UserApp;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.EmployeeModel;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author leonardo
 */
@WebServlet(name = "loginController", urlPatterns = {"/login", "/login.do", "/company/login.do", "/employee/login.do", "/client/login.do", "/admin/login.do"})
public class loginController extends HttpServlet {

    UserModel userModel = new UserModel();
    CompanyModel companyModel = new CompanyModel();
    EmployeeModel employeeModel = new EmployeeModel();
    HashMap<String, String> errorsList = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String operation = request.getParameter("op");

        if (operation != null) {
            switch (operation) {
                case "login":
                    login(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
            }
        } else {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
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

    private void login(HttpServletRequest request, HttpServletResponse response) {
        try {
            errorsList.clear();
            String email = request.getParameter("email"), password = request.getParameter("password");

            if (Validacion.isEmpty(email)) {
                errorsList.put("email", "El campo email es requerido");
            } else {
                if (!Validacion.esCorreo(email)) {
                    errorsList.put("email", "El campo email es válido");
                }
            }

            if (Validacion.isEmpty(password)) {
                errorsList.put("password", "El campo email password es requerido");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                request.setAttribute("errorsList", errorsList);
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            } else {
                UserApp user = userModel.login(email, PasswordResetModel.parsingPassword(password));

                if (user != null) {
                    boolean flag = true;

                    if (user.getUserType().toLowerCase().equals("client")) {
                        flag = (user.getConfirmed() == 1);
                    }

                    if (flag) {
                        HttpSession _s = request.getSession(true);
                        _s.setAttribute("logged", true);

                        switch (user.getUserType().toLowerCase()) {
                            case "company":
                                _s.setAttribute("user", user.getCompany());
                                _s.setAttribute("redirect", request.getContextPath() + "/company/user.do");
                                _s.setAttribute("type", "company");
                                break;
                            case "client":
                                _s.setAttribute("user", user.getUser());
                                _s.setAttribute("redirect", request.getContextPath() + "/client/user.do");
                                _s.setAttribute("type", "client");
                                break;
                            case "administrator":
                                _s.setAttribute("user", user.getUser());
                                _s.setAttribute("redirect", request.getContextPath() + "/admin/user.do");
                                _s.setAttribute("type", "admin");
                                break;
                            case "employee":
                                _s.setAttribute("user", user.getEmployee());
                                _s.setAttribute("redirect", request.getContextPath() + "/employee/user.do");
                                _s.setAttribute("type", "employee");
                                break;
                        }
                        response.sendRedirect(_s.getAttribute("redirect").toString());
                    } else {
                        request.setAttribute("invalid", "Favor confirma tú cuenta");
                        request.getRequestDispatcher("/login.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("invalid", "Cuenta no registrada");
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                }
            }
        } catch (IOException | SQLException | ServletException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin login()

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession _s = request.getSession(true);
            if (_s.getAttribute("logged") != null) {
                _s.invalidate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } catch (IOException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin logout()
}
