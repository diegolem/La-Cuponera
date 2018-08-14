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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Promotion;
import sv.edu.udb.www.beans.PromotionState;
import sv.edu.udb.www.model.PromotionModel;
import sv.edu.udb.www.model.PromotionStateModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "promotionController", urlPatterns = {"/company/promotion.do", "/admin/promotion.do"})
public class promotionController extends HttpServlet {

    PromotionModel promotionModel = new PromotionModel();
    PromotionStateModel promotionStateModel = new PromotionStateModel();
    HashMap<String, String> errorsList = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
                add(request, response);
                break;
            case "details":
                details(request, response);
                break;
            case "edit":
                edit(request, response);
                break;
            case "delete":
                delete(request, response);
                break;
            case "rejected":
                rejected(request, response);
                break;
            case "accept":
                accept(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String pathImage = getServletContext().getRealPath("/img");
        MultipartRequest multimedia = new MultipartRequest(request, pathImage, 1 * 1024 * 1024, new DefaultFileRenamePolicy());
        String operation = multimedia.getParameter("op");

        switch (operation) {
            case "insert":
                insert(multimedia, request, response);
                break;
            case "update":
                update(multimedia, request, response);
                break;
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void list(HttpServletRequest request, HttpServletResponse response) {
        try {
            String id = "";
            HttpSession _s = request.getSession(true);

            switch (_s.getAttribute("type").toString()) {
                case "company":
                    Company company = (Company) _s.getAttribute("user");
                    id = company.getIdCompany();

                    request.setAttribute("title", "Lista de ofertas");
                    request.setAttribute("promotionsList", promotionModel.getPromotions(id, false));
                    request.getRequestDispatcher("/company/promotion/listPromotions.jsp").forward(request, response);
                    break;
                case "admin":
                    request.setAttribute("title", "Lista de ofertas");
                    request.setAttribute("promotionsList", promotionModel.getPromotions(false));
                    request.getRequestDispatcher("/admin/promotion/listPromotions.jsp").forward(request, response);
                    break;
            }

        } catch (ServletException | IOException | SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin list()

    private void insert(MultipartRequest multimedia, HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            errorsList.clear();
            boolean verifyDates = true;
            Promotion promotion = new Promotion();

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

            if (Validacion.isEmpty(multimedia.getParameter("initDate"))) {
                errorsList.put("initDate", "La fecha de inicio es requerida");
                verifyDates = false;
            } else {
                try {
                    promotion.setInitDate(initDate.parse(multimedia.getParameter("initDate")));
                } catch (ParseException ex) {
                    verifyDates = false;
                    errorsList.put("initDate", "La fecha de inicio no es válida");
                }
            }

            if (Validacion.isEmpty(multimedia.getParameter("initDate"))) {
                errorsList.put("endDate", "La fecha final es requerida");
                verifyDates = false;
            } else {
                try {
                    promotion.setEndDate(endDate.parse(multimedia.getParameter("endDate")));
                } catch (ParseException ex) {
                    verifyDates = false;
                    errorsList.put("endDate", "La fecha final no es válida");
                }
            }

            if (Validacion.isEmpty(multimedia.getParameter("limitDate"))) {
                errorsList.put("limitDate", "La fecha limite es requerida");
                verifyDates = false;
            } else {
                try {
                    promotion.setLimitDate(limitDate.parse(multimedia.getParameter("limitDate")));
                } catch (ParseException ex) {
                    verifyDates = false;
                    errorsList.put("limitDate", "La limite no es válida");
                }
            }

            if (verifyDates) {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                if (!Validacion.verificarFechas(promotion.getInitDate(), now)) {
                    errorsList.put("initDate", "Fecha inicial debe ser mayor a la actual");
                }
                if (!Validacion.verificarFechas(promotion.getInitDate(), promotion.getEndDate())) {
                    errorsList.put("initDate", "Fecha inicial debe ser menor a la final");
                }
                if (!Validacion.verificarFechas(promotion.getEndDate(), promotion.getLimitDate())) {
                    errorsList.put("endDate", "Fecha final debe ser menor a la fecha limite");
                }
            }

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

            if (Validacion.isEmpty(multimedia.getParameter("limitCant"))) {
                errorsList.put("limitCant", "La cantidad limite minima debe ser 0");
            } else {
                if (Validacion.esEnteroPositivo(multimedia.getParameter("limitCant"))) {
                    errorsList.put("limitCant", "La cantidad limite no es válida");
                } else {
                    promotion.setLimitCant(Integer.parseInt(multimedia.getParameter("limitCant")));
                }
            }

            if (multimedia.getFile("img") == null) {
                errorsList.put("img", "La imagen es obligatoria");
            } else {
                File fileTemp = multimedia.getFile("img");
                promotion.setImage(fileTemp.getName());
            }

            if (errorsList.size() > 0) {
                if (multimedia.getFile("img") != null) {
                    multimedia.getFile("img").delete();
                }

                request.setAttribute("errorsList", errorsList);
                request.setAttribute("promotion", promotion);
                request.getRequestDispatcher("/company/promotion/newPromotion.jsp").forward(request, response);
                //add(request, response);
            } else {
                HttpSession _s = request.getSession(true);
                Company company = (Company) _s.getAttribute("user");
                promotion.setCompany(company);
                if (promotionModel.insertPromotion(promotion)) {
                    request.getSession().setAttribute("success", "Oferta registrada");
                    response.sendRedirect(request.getContextPath() + "/company/promotion/newPromotion.jsp");
                    System.out.println("Oferta registrada");
                } else {
                    if (multimedia.getFile("img") != null) {
                        multimedia.getFile("img").delete();
                    }
                    request.getSession().setAttribute("error", "Oferta no registrada");
                    response.sendRedirect(request.getContextPath() + "/company/promotion/newPromotion.jsp");
                    System.out.println("Oferta no regitrada");
                }
            }
        } catch (SQLException | ServletException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin insert()

    private void add(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("title", "Agregar oferta");
            request.getRequestDispatcher("/company/promotion/newPromotion.jsp").forward(request, response);
        } catch (IOException | ServletException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin add()

    private void details(HttpServletRequest request, HttpServletResponse response) {
        try {
            Promotion promotion = null;
            String id = "";
            boolean flagAdmin = true;
            if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                if (Integer.parseInt(request.getParameter("idPromotion")) > 0) {
                    HttpSession _s = request.getSession(true);

                    switch (_s.getAttribute("type").toString()) {
                        case "company":
                            flagAdmin = false;
                            break;
                    }
                    promotion = promotionModel.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), flagAdmin);
                }
            }

            if (promotion != null) {
                request.setAttribute("title", "Detalles de oferta");
                request.setAttribute("promotion", promotion);
                if (flagAdmin) {
                    request.getRequestDispatcher("/admin/promotion/detailsPromotion.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/company/promotion/detailsPromotion.jsp").forward(request, response);
                }
            } else {
                if (flagAdmin) {
                    request.getRequestDispatcher("/admin/promotion/detailsPromotion.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/company/promotion/detailsPromotion.jsp").forward(request, response);
                }
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession _s = request.getSession(true);

            switch (_s.getAttribute("type").toString()) {
                case "company": //Para que una empresa modifique las ofertas estan debe estar en estado 3 (Rechazada)
                    Promotion promotion = null;

                    if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                        if (Integer.parseInt(request.getParameter("idPromotion")) > 0) {
                            promotion = promotionModel.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), false);
                        }
                    }

                    if (promotion != null) {
                        if (promotion.getPromotionState().getState().toLowerCase().equals("rechazada")) {
                            request.setAttribute("title", "Editar oferta");
                            request.setAttribute("promotion", promotion);
                            request.getRequestDispatcher("/company/promotion/editPromotion.jsp").forward(request, response);
                        } else {
                            request.getRequestDispatcher("/company/promotion.do?op=list").forward(request, response);
                        }
                    } else {
                        request.getRequestDispatcher("/company/promotion.do?op=list").forward(request, response);
                    }
                    break;
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    } // Fin edit

    private void update(MultipartRequest multimedia, HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            HttpSession _s = request.getSession(true);

            switch (_s.getAttribute("type").toString()) {
                case "company": //Para que una empresa modifique las ofertas estan debe estar en estado 3 (Rechazada)
                    errorsList.clear();
                    boolean verifyDates = true;
                    Promotion promotion = new Promotion();

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

                    if (Validacion.isEmpty(multimedia.getParameter("initDate"))) {
                        errorsList.put("initDate", "La fecha de inicio es requerida");
                        verifyDates = false;
                    } else {
                        try {
                            promotion.setInitDate(initDate.parse(multimedia.getParameter("initDate")));
                        } catch (ParseException ex) {
                            verifyDates = false;
                            errorsList.put("initDate", "La fecha de inicio no es válida");
                        }
                    }

                    if (Validacion.isEmpty(multimedia.getParameter("initDate"))) {
                        errorsList.put("endDate", "La fecha final es requerida");
                        verifyDates = false;
                    } else {
                        try {
                            promotion.setEndDate(endDate.parse(multimedia.getParameter("endDate")));
                        } catch (ParseException ex) {
                            verifyDates = false;
                            errorsList.put("endDate", "La fecha final no es válida");
                        }
                    }

                    if (Validacion.isEmpty(multimedia.getParameter("limitDate"))) {
                        errorsList.put("limitDate", "La fecha limite es requerida");
                        verifyDates = false;
                    } else {
                        try {
                            promotion.setLimitDate(limitDate.parse(multimedia.getParameter("limitDate")));
                        } catch (ParseException ex) {
                            verifyDates = false;
                            errorsList.put("limitDate", "La limite no es válida");
                        }
                    }

                    if (verifyDates) {
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                        Date now = new Date();
                        if (!Validacion.verificarFechas(promotion.getInitDate(), now)) {
                            errorsList.put("initDate", "Fecha inicial debe ser mayor a la actual");
                        }
                        if (!Validacion.verificarFechas(promotion.getInitDate(), promotion.getEndDate())) {
                            errorsList.put("initDate", "Fecha inicial debe ser menor a la final");
                        }
                        if (!Validacion.verificarFechas(promotion.getEndDate(), promotion.getLimitDate())) {
                            errorsList.put("endDate", "Fecha final debe ser menor a la fecha limite");
                        }
                    }

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

                    if (Validacion.isEmpty(multimedia.getParameter("limitCant"))) {
                        errorsList.put("limitCant", "La cantidad limite minima debe ser 0");
                    } else {
                        if (Validacion.esEnteroPositivo(multimedia.getParameter("limitCant"))) {
                            errorsList.put("limitCant", "La cantidad limite no es válida");
                        } else {
                            promotion.setLimitCant(Integer.parseInt(multimedia.getParameter("limitCant")));
                        }
                    }

                    if (multimedia.getFile("img") == null) {
                        errorsList.put("img", "La imagen es obligatoria");
                    } else {
                        File fileTemp = multimedia.getFile("img");
                        promotion.setImage(fileTemp.getName());
                    }

                    if (Validacion.esEnteroPositivo(multimedia.getParameter("idPromotion"))) {
                        Promotion _p = promotionModel.getPromotion(Integer.parseInt(multimedia.getParameter("idPromotion")), false);
                        if ((_p == null) || (_p.getPromotionState().getIdPromotionState() != 3)) {
                            errorsList.put("idPromotion", "Promoción no válida para modificar");
                        } else {
                            promotion.setIdPromotion(Integer.parseInt(multimedia.getParameter("idPromotion")));
                        }
                    } else {
                        errorsList.put("idPromotion", "ID es obligatorio");
                    }

                    if (errorsList.size() > 0) {
                        if (multimedia.getFile("img") != null) {
                            multimedia.getFile("img").delete();
                        }
                        request.setAttribute("errorsList", errorsList);
                        request.setAttribute("promotion", promotion);
                        request.getRequestDispatcher("/company/promotion/editPromotion.jsp").forward(request, response);
                    } else {
                        Company company = (Company) _s.getAttribute("user");
                        promotion.setCompany(company);
                        if (promotionModel.updatePromotion(promotion)) {
                            request.getSession().setAttribute("success", "Oferta modificada");
                            response.sendRedirect(request.getContextPath() + "/company/promotion.do?op=list");
                            System.out.println("Oferta modificada");
                        } else {
                            if (multimedia.getFile("img") != null) {
                                multimedia.getFile("img").delete();
                            }
                            request.getSession().setAttribute("success", "Oferta no modificada");
                            response.sendRedirect(request.getContextPath() + "/company/promotion.do?op=list");
                            System.out.println("Oferta no modificada");
                        }
                    }
                    break;
                default:
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    break;
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin update()

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            HttpSession _s = request.getSession(true);

            switch (_s.getAttribute("type").toString()) {
                case "company": //Para que una empresa elimine las ofertas estan debe estar en estado 3 (Rechazada)
                    Promotion promotion = null;

                    if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                        if (Integer.parseInt(request.getParameter("idPromotion")) > 0) {
                            promotion = promotionModel.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), false);
                        }
                    }

                    if (promotion != null) {
                        if (promotion.getPromotionState().getState().toLowerCase().equals("rechazada")) {
                            request.setAttribute("promotion", promotion);
                            if (promotionModel.deletePromotion(Integer.parseInt(request.getParameter("idPromotion")))) {
                                out.print("1");
                            } else {
                                out.print("0");
                            }
                        } else {
                            out.print("0");
                        }
                    } else {
                        out.print("0");
                    }
                    break;
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin delete()

    private void rejected(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrintWriter out = response.getWriter();
            Promotion promotion = null;
            
            if (!Validacion.isEmpty(request.getParameter("idPromotion"))) {
                if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                    promotion = promotionModel.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), false);
                }
            }

            if(promotion != null){
                if(promotion.getPromotionState().getIdPromotionState() == 1){ //Estado (En espera de aprobación)
                    promotion.setRejectedDescription(request.getParameter("rejectedDescription"));
                    if(promotionModel.rejectedPromotion(promotion)){
                        out.print("1");
                    }else{
                        out.print("0");
                    }
                }else{
                    out.print("0");
                }
            }else{
                out.print("0");
            }
        } catch (SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin rejected()

    private void accept(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            PrintWriter out = response.getWriter();
            Promotion promotion = null;
            
            if (!Validacion.isEmpty(request.getParameter("idPromotion"))) {
                if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                    promotion = promotionModel.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), false);
                }
            }

            if(promotion != null){
                if(promotion.getPromotionState().getIdPromotionState() == 1){ //Estado (En espera de aprobación)
                    if(promotionModel.acceptPromotion(promotion)){
                        out.print("1");
                    }else{
                        out.print("0");
                    }
                }else{
                    out.print("0");
                }
            }else{
                out.print("0");
            }
        } catch (SQLException ex) {
            Logger.getLogger(promotionController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin accept()
}
