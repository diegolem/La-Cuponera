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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.udb.www.beans.RequestMessage;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Frank Esquivel
 */
@WebServlet(name = "passwordController", urlPatterns = {"/password.do"})
public class passwordController extends HttpServlet {


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
        //request.getRequestDispatcher("/password/recoverForm.jsp").forward(request, response);
        
        try (PrintWriter out = response.getWriter()) {
            response.setHeader("Content-type", "application/json");
            Gson gson = new Gson();
            RequestMessage reqMsg;
            
            
            if(Validacion.isEmpty(request.getParameter("email"))){
                reqMsg = new RequestMessage(-1, "La peticion debe de contener un correo", "error");
            }else{
                UserModel userMdl = new UserModel();
                PasswordResetModel passMdl = new PasswordResetModel();
                if (userMdl.checkUserByEmail(request.getParameter("email"))) {
                    if(passMdl.insertResetRequest(request.getParameter("email"))){
                        reqMsg = new RequestMessage(1, "La peticion para reestablecer la contraseña ha sido registrada exitosamente", "success");
                    }else{
                        reqMsg = new RequestMessage(2, "Ha ocurrido un error al guardar la peticion, intentelo mas tarde...", "error");
                    }
                }else{
                   reqMsg = new RequestMessage(3, "Ese correo no se encuentra registrado en la plataforma", "error"); 
                }
            }
            
            out.print(gson.toJson(reqMsg));
        }catch (SQLException ex) {
            Logger.getLogger(passwordController.class.getName()).log(Level.SEVERE, null, ex);
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
