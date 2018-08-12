/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import java.io.File;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Promotion;
import sv.edu.udb.www.model.PromotionModel;
import sv.edu.udb.www.model.PromotionStateModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "promotionController", urlPatterns = {"/promotion.do", "/company/promotion.do"})
public class promotionController extends HttpServlet {

    PromotionModel promotionModel = new PromotionModel();
    PromotionStateModel promotionStateModel = new PromotionStateModel();
    HashMap<String, String> errorsList = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String operation = request.getParameter("op");

            if (operation == null) {
                list(request, response);
                return;
            }

            switch (operation) {
                case "list":
                    list(request, response);
                    break;
                case "new":
                    response.sendRedirect(request.getContextPath() + "/company/promotion/newPromotion.jsp");
                    break;
                case "insert":
                    insert(request, response);
                    break;
            }
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

    private void list(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = "";
            HttpSession _s = request.getSession(true);
            
            switch(_s.getAttribute("type").toString()){
                case "company":
                    Company company = (Company) _s.getAttribute("user");
                    id = company.getIdCompany();
                    break;
            }
            
            request.setAttribute("promotionsList", promotionModel.getPromotions(id, false));
            request.getRequestDispatcher("/company/promotion/listPromotions.jsp").forward(request, response);
        } 
        catch (ServletException | IOException | SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin list()

    private void insert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {

            errorsList.clear();
            boolean verifyDates = true;
            Promotion promotion = new Promotion();
            String pathImage = getServletContext().getRealPath("/img");

            MultipartRequest multimedia = new MultipartRequest(request, pathImage, 1 * 1024 * 1024, new DefaultFileRenamePolicy());
            DateFormat initDate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat endDate = new SimpleDateFormat("yyyy-MM-dd");
            DateFormat limitDate = new SimpleDateFormat("yyyy-MM-dd");

            if (Validacion.isEmpty(multimedia.getParameter("title"))) {
                errorsList.put("title", "El nombre de la oferta es requerido");
            } else {
                promotion.setTitle(multimedia.getParameter("title"));
            }

            if (Validacion.esDecimalPositivo(multimedia.getParameter("regularPrice"))) {
                promotion.setRegularPrice(Double.parseDouble(multimedia.getParameter("regularPrice")));
                if (!(promotion.getRegularPrice() > 0)) {
                    errorsList.put("regularPrice", "El precio regular debe ser mayor a 0");
                }
            } else {
                errorsList.put("regularPrice", "El precio regular debe ser un número decimal");
            }

            if (Validacion.esDecimalPositivo(multimedia.getParameter("ofertPrice"))) {
                promotion.setOfertPrice(Double.parseDouble(multimedia.getParameter("ofertPrice")));
                if (!(promotion.getRegularPrice() > 0)) {
                    errorsList.put("ofertPrice", "El precio de oferta debe ser mayor a 0");
                }
            } else {
                errorsList.put("ofertPrice", "El precio de oferta debe ser un número decimal");
            }

            if ((promotion.getOfertPrice() > 0) && (promotion.getRegularPrice() > 0)) {
                if (!(promotion.getRegularPrice() > promotion.getOfertPrice())) {
                    errorsList.put("ofertPrice", "El precio de oferta debe ser mayor al precio regular");
                }
            }

            try {
                promotion.setInitDate(initDate.parse(multimedia.getParameter("initDate")));
            } catch (ParseException ex) {
                verifyDates = false;
                errorsList.put("iniDate", "La fecha de inicio no es válida");
            }

            try {
                promotion.setEndDate(endDate.parse(multimedia.getParameter("endDate")));
            } catch (ParseException ex) {
                verifyDates = false;
                errorsList.put("endDate", "La fecha final no es válida");
            }

            try {
                promotion.setLimitDate(limitDate.parse(multimedia.getParameter("limitDate")));
            } catch (ParseException ex) {
                verifyDates = false;
                errorsList.put("iniDate", "La limite no es válida");
            }

            /*if(verifyDates){
                if(!Validacion.verificarFechas(promotion.getInitDate(), promotion.getEndDate())){
                    errorsList.put("initDate", "Fecha inicial debe ser menor a la final");
                }
                if(!Validacion.verificarFechas(promotion.getEndDate(), promotion.getLimitDate())){
                    errorsList.put("endDate", "Fecha final debe ser menor a la fecha limite");
                }
            }*/
            if (Validacion.isEmpty(multimedia.getParameter("description"))) {
                errorsList.put("description", "La descripción es requerida");
            } else {
                promotion.setDescription(multimedia.getParameter("description"));
            }

            if (Validacion.isEmpty(multimedia.getParameter("otherDetails"))) {
                errorsList.put("otherDetails", "Los detalles son requeridos");
            } else {
                promotion.setOtherDetails(multimedia.getParameter("otherDetails"));
            }

            if (Validacion.esEnteroPositivo(multimedia.getParameter("limitCant"))) {
                errorsList.put("limitCant", "La cantidad limite no es válida");
            } else {
                promotion.setLimitCant(Integer.parseInt(multimedia.getParameter("limitCant")));
            }

            if (multimedia.getFile("img") == null) {
                errorsList.put("img", "La imagen es obligatoria");
            } else {
                File fileTemp = multimedia.getFile("img");
                promotion.setImage(fileTemp.getName());
            }

            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("promotion", promotion);
                request.getRequestDispatcher("/company/promotion/newPromotion.jsp").forward(request, response);
            } else {
                HttpSession _s = request.getSession(true);
                Company company = (Company) _s.getAttribute("user");
                promotion.setCompany(company);
                if (promotionModel.insertPromotion(promotion)) {
                    request.getSession().setAttribute("success", "Oferta registrada");
                    response.sendRedirect(request.getContextPath() + "/company/promotion/newPromotion.jsp");
                    System.out.println("Oferta registrada");
                } else {
                    request.getSession().setAttribute("error", "Oferta no registrada");
                    response.sendRedirect(request.getContextPath() + "/company/promotion/newPromotion.jsp");
                    System.out.println("Oferta no regitrada");
                }
            }
        } catch (SQLException | ServletException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin insert()
}
