/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Employee;
import sv.edu.udb.www.beans.Entity;
import sv.edu.udb.www.beans.User;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.EmployeeModel;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.utilities.Mail;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Frank Esquivel
 */
@WebServlet(name = "ConfigController", urlPatterns = {"/admin/config.do", "/client/config.do", "/employee/config.do", "/company/config.do"})
public class ConfigController extends HttpServlet {

    UserModel uMdl = new UserModel();
    EmployeeModel eMdl = new EmployeeModel();
    CompanyModel cmdl = new CompanyModel();
    PasswordResetModel pRMdl = new PasswordResetModel();

    Entity _ey = new Entity();
    User _u = new User();
    Employee _e = new Employee();
    Company _c = new Company();

    HashMap<String, String> errorsList = new HashMap<>();
    Boolean emailChangedFlag = false;
    String usrConfig = "0";

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
        String type = (String) request.getSession().getAttribute("type"), view = "";

        if (type != null) {
            switch (type) {
                case "admin":
                case "client":
                case "employee":
                    view = "/config/userConfig.jsp";
                    usrConfig = "user";
                    break;
                case "company":
                    view = "/config/companyConfig.jsp";
                    usrConfig = "company";
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login");
                    break;
            }

            request.setAttribute("title", "Mi cuenta");
            request.getRequestDispatcher(view).forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
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
        errorsList.clear();
        emailChangedFlag = false;
        request.setAttribute("title", "Mi cuenta");
        if ((Boolean) request.getSession().getAttribute("logged")) {
            String type = (String) request.getSession().getAttribute("type"), view = "";
            boolean sameUserFlag = false;

            switch (type) {
                case "admin":
                case "client":
                    _u = (User) request.getSession().getAttribute("user");
                    sameUserFlag = _u.getIdUser() == Integer.parseInt(request.getParameter("id"));
                    break;
                case "company":
                    _c = (Company) request.getSession().getAttribute("user");
                    sameUserFlag = _c.getIdCompany().equals(request.getParameter("id"));
                    break;
                case "employee":
                    _e = (Employee) request.getSession().getAttribute("user");
                    sameUserFlag = _e.getIdEmployee() == Integer.parseInt(request.getParameter("id"));
                    break;
            }

            if (request.getParameter("id") != null && sameUserFlag) {

                switch (type) {
                    case "admin":
                    case "client":
                        userProcess(request, response);
                        break;
                    case "employee":
                        employeeProcess(request, response);
                        break;
                    case "company":
                        companyProcess(request, response);
                        break;
                }

                if (errorsList.size() > 0) {
                    request.setAttribute("errorsList", errorsList);
                    request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                    return;
                } else {

                    try {
                        switch (type) {
                            case "admin":
                            case "client":
                                uMdl.updateUser(_u);
                                break;
                            case "employee":
                                eMdl.updateEmployee(_e);
                                break;
                            case "company":
                                cmdl.updateCompany(_c);
                                break;
                        }

                        //Change password
                        if (!Validacion.isEmpty(request.getParameter("password"))) {
                            if (!Validacion.isEmpty(request.getParameter("current_password"))) {
                                try {
                                    if (uMdl.login(request.getParameter("email"), PasswordResetModel.parsingPassword(request.getParameter("current_password"))) != null) {
                                        if (request.getParameter("password").equals(request.getParameter("conf_password"))) {
                                            pRMdl.resetUserPassword(request.getParameter("email"), request.getParameter("password"));
                                        } else {
                                            errorsList.put("password", "Las contraseñas no coinciden...");
                                            errorsList.put("conf_password", "Las contraseñas no coinciden...");
                                            request.setAttribute("errorsList", errorsList);
                                            request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                                            return;
                                        }
                                    } else {
                                        errorsList.put("current_password", "El valor ingresado no coincide con su contraseña actual!");
                                        request.setAttribute("errorsList", errorsList);
                                        request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                                        return;
                                    }
                                } catch (SQLException ex) {
                                    Logger.getLogger(ConfigController.class.getName()).log(Level.SEVERE, null, ex);

                                    errorsList.put("msg", "Ha ocurrido un error al modificar la contraseña!");
                                    request.setAttribute("errorsList", errorsList);
                                    request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                                    return;
                                }
                            } else {
                                errorsList.put("current_password", "Si quiere cambiar su contraseña debe de ingresar su contraseña actual!");
                                request.setAttribute("errorsList", errorsList);
                                request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                                return;
                            }
                        }
                        //End change password
                    } catch (SQLException ex) {
                        Logger.getLogger(ConfigController.class.getName()).log(Level.SEVERE, null, ex);

                        errorsList.put("msg", "Ha ocurrido un error al modificar los datos!");
                        request.setAttribute("errorsList", errorsList);
                        request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
                    }
                }

                if (emailChangedFlag) {
                    String name = type.equals("company") ? request.getParameter("name") : (request.getParameter("name") + " " + request.getParameter("lastName"));
                    Mail mail = new Mail();

                    mail.setAddressee(request.getParameter("email"));
                    mail.setAffair("Cuponera SV - Cambio de correo");
                    mail.setMessage("Mediante este medio te comunicamos que a la cuenta a nombre de <b>" + name + "</b> de <i>La Cuponera</i> se le ha asignado"
                            + " este correo como destinatario!");

                    mail.sendEmail();

                }

                request.getSession().setAttribute("msg", "Tu cuenta ha sido moficada exitosamente!");
                request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
            } else {
                errorsList.put("msg", "No se puede modificar un usuario no existente o que sea distinto al suyo...");
                request.setAttribute("errorsList", errorsList);
                request.getRequestDispatcher("/config/" + usrConfig + "Config.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect(request.getContextPath() + "/login");
        }
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

    public void userProcess(HttpServletRequest request, HttpServletResponse response) {
        if (Validacion.esCorreo(request.getParameter("email"))) {
            emailChangedFlag = !_u.getEmail().equals(request.getParameter("email"));
            _u.setEmail(request.getParameter("email"));
        } else {
            errorsList.put("email", "Debe ingresar un email valido...");
        }

        if (!Validacion.isEmpty(request.getParameter("name"))) {
            _u.setName(request.getParameter("name"));
        } else {
            errorsList.put("name", "Debe ingresar un nombre...");
        }

        if (!Validacion.isEmpty(request.getParameter("lastName"))) {
            _u.setLastName(request.getParameter("lastName"));
        } else {
            errorsList.put("lastName", "Debe ingresar un apellido...");
        }
    }

    public void employeeProcess(HttpServletRequest request, HttpServletResponse response) {
        if (Validacion.esCorreo(request.getParameter("email"))) {
            emailChangedFlag = !_e.getEmail().equals(request.getParameter("email"));
            _e.setEmail(request.getParameter("email"));
        } else {
            errorsList.put("email", "Debe ingresar un email valido...");
        }

        if (!Validacion.isEmpty(request.getParameter("name"))) {
            _e.setName(request.getParameter("name"));
        } else {
            errorsList.put("name", "Debe ingresar un nombre...");
        }

        if (!Validacion.isEmpty(request.getParameter("lastName"))) {
            _e.setLastName(request.getParameter("lastName"));
        } else {
            errorsList.put("lastName", "Debe ingresar un apellido...");
        }
    }

    public void companyProcess(HttpServletRequest request, HttpServletResponse response) {
        if (Validacion.esCorreo(request.getParameter("email"))) {
            emailChangedFlag = !_c.getEmail().equals(request.getParameter("email"));
            _c.setEmail(request.getParameter("email"));
        } else {
            errorsList.put("email", "Debe ingresar un email valido...");
        }

        if (!Validacion.isEmpty(request.getParameter("name"))) {
            _c.setName(request.getParameter("name"));
        } else {
            errorsList.put("name", "Debe ingresar un nombre...");
        }

        if (!Validacion.isEmpty(request.getParameter("address"))) {
            _c.setAddress(request.getParameter("address"));
        } else {
            errorsList.put("address", "Debe ingresar una direccion...");
        }

        if (!Validacion.isEmpty(request.getParameter("contactName"))) {
            _c.setContactName(request.getParameter("contactName"));
        } else {
            errorsList.put("contact_name", "Debe ingresar un nombre de contacto...");
        }

        if (!Validacion.isEmpty(request.getParameter("telephone"))) {
            _c.setTelephone(request.getParameter("telephone"));
        } else {
            errorsList.put("telephone", "Debe ingresar un numero de telefono...");
        }
    }

}
