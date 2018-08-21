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
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Sales;
import sv.edu.udb.www.beans.SalesState;
import sv.edu.udb.www.beans.User;
import sv.edu.udb.www.model.SalesModel;
import sv.edu.udb.www.model.SalesStateModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.beans.Promotion;
import sv.edu.udb.www.model.PromotionModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "salesController", urlPatterns = {"/client/sales.do"})
public class salesController extends HttpServlet {

    SalesModel sales = new SalesModel();
    UserModel users = new UserModel();
    SalesStateModel salesStates = new SalesStateModel();
    PromotionModel promotions = new PromotionModel();
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
            String op = request.getParameter("op");
            HttpSession _s = request.getSession(true);

            if (_s.getAttribute("logged") != null) {
                switch (op) {
                    case "obener_por_usuario":
                        obtenerPorUsuerio(request, response);
                        break;
                    case "listC":
                        tusCupones(request, response);
                        break;
                    case "newC":
                        newCupon(request, response);
                        break;
                    case "buy":
                        buyCupon(request,response);
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
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

        for (Sales sale : cupones) {

            json += (primero) ? "" : ",";

            String code = sale.getCouponCode();
            String descripcion = sale.getPromotion().getDescription();
            String image = sale.getPromotion().getImage();
            String titulo = sale.getPromotion().getTitle();
            String fechaLimite = sale.getPromotion().getLimitDate().toString();

            json += "\"" + code + "\":{";

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

    private void tusCupones(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        try {
            HttpSession session = request.getSession(true);
            User user = (User) session.getAttribute("user");
            request.setAttribute("salesList", sales.getSales(user.getIdUser(), true));
            request.setAttribute("title", "Lista de tus cupones");
            request.getRequestDispatcher("/client/Sales/listSales.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newCupon(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        try {
            request.setAttribute("title", "Comprar Cupon");
            request.setAttribute("promotions", promotions.getPromotionsA("Aprobados", true));
            request.getRequestDispatcher("/client/Sales/newSales.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void buyCupon(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            errorsList.clear();
            PrintWriter out = response.getWriter();
            int cant = 0;
            String idC = "";
            int idP = 0;
            if (Validacion.esEnteroPositivo(request.getParameter("Cantidad"))) {
                cant = Integer.parseInt(request.getParameter("Cantidad"));
            } else {
                errorsList.put("cantidad", "Porfavor ingrese un número válido");
            }
            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                idC = request.getParameter("idCompany");
            } else {
                errorsList.put("codigo", "Porfavor revise los datos");
            }
            if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                idP = Integer.parseInt(request.getParameter("idPromotion"));
            } else {
                errorsList.put("promotion", "Porfavor revise los datos");
            }
            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("cantidad", cant);
                request.getRequestDispatcher("/client/sales.do?op=newC").forward(request, response);
            } else {
                ArrayList<String> ids = new ArrayList<>();
                //inicio del for
                for (int i = 0;i <= cant;i++) {
                    Company company = new Company();
                    Sales salesN = new Sales();
                    Promotion promot = new Promotion();
                    SalesState stat = new SalesState();
                    
                    company.setIdCompany(idC);
                    //Asignando el codigo del cupon
                    salesN.setCouponCode(sales.genCodeSalesnew(company));
                    //Asignando el codigo de la promotion
                    promot.setIdPromotion(idP);
                    salesN.setPromotion(promot);
                    //Asignando el Cliente
                    HttpSession session = request.getSession(true);
                    User user = (User) session.getAttribute("user");
                    user.setIdUser(user.getIdUser());
                    salesN.setClient(user);
                    //Falta asignar el estado
                    stat.setIdSalesState(2);
                    salesN.setState(stat);
                    //Realizando el proceso - Falta el for para la cantidad
                    if (sales.insertSales(salesN)) {
                        //Añadiendo el codigo del cupon a un array 
                        ids.add(salesN.getCouponCode());
                    } else {
                        out.print("0");
                    }
                }//fin for
                //Verificando si el tamaño de transacciones es igual a la cantidad
                    if(ids.size() == cant){
                        out.print("1");
                        //Aqui vamos a generar el JasperReport
                        request.getSession().setAttribute("success", "Cupones comprados");
                        response.sendRedirect(request.getContextPath() + "/client/sales.do?op=listC");
                    }else{
                        out.print("0");
                        request.getSession().setAttribute("error", "Los cupones no se pudieron comprar");
                        response.sendRedirect(request.getContextPath() + "/client/sales.do?op=listC");
                    }
            }
        } catch (ServletException | IOException | SQLException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
