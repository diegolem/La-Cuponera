/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import com.google.gson.Gson;
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
import sv.edu.udb.www.beans.PasswordReset;
import sv.edu.udb.www.beans.RequestMessage;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.utilities.Mail;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Frank Esquivel
 */
@WebServlet(name = "passwordController", urlPatterns = {"/password.do"})
public class passwordController extends HttpServlet {

    HashMap<String, String> errorsList = new HashMap<>();
    UserModel userMdl = new UserModel();
    PasswordResetModel passMdl = new PasswordResetModel();
    Gson gson = new Gson();
    RequestMessage reqMsg;

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
        //request.getRequestDispatcher("/password/recoverForm.jsp").forward(request, response);

        switch (request.getParameter("op")) {
            case "request":
                requestReset(request, response);
                break;
            case "render":
                renderReset(request, response);
                break;
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

        try {
            if (Validacion.isEmpty(request.getParameter("email"))) {
                errorsList.put("email", "Debes ingresar un correo electronico!");
            }
            
            if (Validacion.isEmpty(request.getParameter("password"))) {
                errorsList.put("password", "Debes ingresar una contraseña");
            }
            
            if (Validacion.isEmpty(request.getParameter("conf_password"))) {
                errorsList.put("conf_password", "Debes ingresar una confirmacion");
            }
            
            if (!Validacion.isEmpty(request.getParameter("password")) && !request.getParameter("password").equals(request.getParameter("conf_password"))) {
                errorsList.put("conf_password", "Las contraseñas no coinciden...");
            }
            
            if (Validacion.isEmpty(request.getParameter("token"))) {
                errorsList.put("token", "...");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("email", request.getParameter("email"));
                request.setAttribute("token", request.getParameter("token"));

                request.setAttribute("errors", errorsList);
                request.getRequestDispatcher("/password/recoverForm.jsp").forward(request, response);
            } else {
                PasswordReset _pr = passMdl.getResetRequest(request.getParameter("token"));

                if (_pr.getEmail().equals(request.getParameter("email"))) {
                    if (passMdl.resetUserPassword(request.getParameter("email"), request.getParameter("password"))) {
                        passMdl.confirmRequest(_pr.getToken());
                        
                        request.getSession().setAttribute("msg", "Tu contraseña ha sido modificada exitosamente!");
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                    }
                } else {
                    errorsList.put("email", "El correo ingresado no coincide con el que se encuentra registrado en la peticion!");
                    request.setAttribute("errors", errorsList);
                    request.getRequestDispatcher("/password/recoverForm.jsp").forward(request, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(passwordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void requestReset(HttpServletRequest request, HttpServletResponse response) {
        try (PrintWriter out = response.getWriter()) {
            response.setHeader("Content-type", "application/json");

            if (Validacion.isEmpty(request.getParameter("email"))) {
                reqMsg = new RequestMessage(-1, "La peticion debe de contener un correo", "error");
            } else {

                if (userMdl.checkEmail(request.getParameter("email"))) {
                    PasswordReset _pr = passMdl.insertResetRequest(request.getParameter("email"));
                    if (_pr != null) {
                        String resetLink = request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/password.do?op=render&token=" + _pr.getToken();
                        Mail mail = new Mail();

                        mail.setAddressee(_pr.getEmail());
                        mail.setAffair("Cuponera SV - Recuperacion de Contraseña");
                        mail.setMessage("Saludos usuario! Hemos recibido una peticion para reestablecer la contraseña de tu cuenta. Para proceder a cambiar tu contraseña haz click sobre el siguiente boton:"
                                + "<br><a href='" + resetLink + "'>Cambiar contraseña</a>"
                                + "<br><br><em>Si el boton no te redirecciona haz click en el siguiente link <a href='" + resetLink + "'>" + resetLink + "</a></em>"
                        );

                        if (mail.sendEmail()) {
                            reqMsg = new RequestMessage(1, "La peticion para reestablecer la contraseña ha sido registrada exitosamente", "success");
                        } else {
                            reqMsg = new RequestMessage(2, "Ha ocurrido un error al enviar el correo electronico", "error");
                        }

                    } else {
                        reqMsg = new RequestMessage(3, "Ha ocurrido un error al guardar la peticion, intentelo mas tarde...", "error");
                    }
                } else {
                    reqMsg = new RequestMessage(4, "Ese correo no se encuentra registrado en la plataforma", "error");
                }
            }

            out.print(gson.toJson(reqMsg));
        } catch (SQLException | IOException ex) {
            Logger.getLogger(passwordController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void renderReset(HttpServletRequest request, HttpServletResponse response) {

        try {
            if (Validacion.isEmpty(request.getParameter("token"))) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            } else {

                PasswordReset _pr = passMdl.getResetRequest(request.getParameter("token"));

                if (_pr != null) {
                    if (_pr.isExpired()) {
                        request.getSession().setAttribute("error", "La peticion de recuperacion de contraseña a la que deseas acceder no existe!");
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                    } else {
                        request.getRequestDispatcher("/password/recoverForm.jsp").forward(request, response);
                    }
                } else {
                    request.getSession().setAttribute("error", "La peticion de recuperacion de contraseña a la que deseas acceder no existe!");
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }
            }
        } catch (ServletException | IOException | SQLException ex) {
            Logger.getLogger(passwordController.class.getName()).log(Level.SEVERE, null, ex);
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

}
