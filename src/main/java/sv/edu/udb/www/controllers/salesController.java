/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sv.edu.udb.www.beans.Sales;
import sv.edu.udb.www.beans.SalesState;
import sv.edu.udb.www.beans.User;
import sv.edu.udb.www.model.SalesModel;
import sv.edu.udb.www.model.SalesStateModel;
import sv.edu.udb.www.model.UserModel;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "salesController", urlPatterns = {"/sales.do"})
public class salesController extends HttpServlet {

    SalesModel sales = new SalesModel();
    UserModel users = new UserModel();
    SalesStateModel salesStates = new SalesStateModel();
    
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
            
            String op = request.getParameter("op");
            
            switch(op){
                case "obener_por_usuario":
                    obtenerPorUsuerio(request, response);
                    break;
            }
            
        } catch(Exception error){
        
        } finally {
            
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

    private void obtenerPorUsuerio(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        int idUsuario = Integer.parseInt(request.getParameter("usuario"));
        int idEstado = Integer.parseInt(request.getParameter("estado"));
        
        User usuario = users.getUser(idUsuario, false);
        SalesState salesState = salesStates.getSalesState(idEstado, false);
        
        List<Sales> cupones = sales.getSales(usuario, salesState, true);
        
        PrintWriter out = response.getWriter();
        
        String json = "{";
        boolean primero = true;
        
        for(Sales sale : cupones){
            
            json += (primero)? "" : ",";
            
            String code = sale.getCouponCode();
            String descripcion = sale.getPromotion().getDescription();
            String image = sale.getPromotion().getImage();
            String titulo = sale.getPromotion().getTitle();
            String fechaLimite = sale.getPromotion().getLimitDate().toString();
            
            json += "\""+code+"\":{";
            
            json += "\"titulo\":\"" + titulo + "\",";
            json += "\"image\":\"" + image + "\",";
            json += "\"descripcion\":\"" + descripcion + "\",";
            json += "\"fechaLimite\":\"" + fechaLimite + "\"";
            
            json += "}";
            
            primero = false;
        }
        
        json += "}";
        
        out.print(json);
    }

}
