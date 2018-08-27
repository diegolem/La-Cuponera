/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.edu.udb.www.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import org.primefaces.json.JSONObject;
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
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.jfree.util.Log;
import org.primefaces.json.JSONObject;
/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "salesController", urlPatterns = {"/sales.do","/client/sales.do", "/employee/sales.do"})

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
                    case "details":
                        detailsCupon(request, response);
                        break;
                    case "detailP":
                        getInfo(request, response);
                        break;
                    case "buy":
                        buyCupon(request, response);
                        break;
                    case "pagination":
                        pagination(request, response);
                        break;
                    case "exchange":
                        exchange(request,response);
                        break;
                    case "cuponPdf":
                        generarPdf(request, response);
                        break;
                    case "invoice":
                        invoice(request, response);
                        break;
                    default:
                        tusCupones(request, response);
                        break;
                }
            } else {
                if(null == op){
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                }else switch (op) {
                    case "public":
                        publicP(request,response);
                        break;
                    case "pagination":
                        pagination(request, response);
                        break;
                    case "detailPublic":
                        getInfoPublic(request, response);
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/login.jsp");
                        break;
                }
            }
        } catch(IOException | SQLException | ServletException error) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, error);
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
            request.setAttribute("salesDisponible", sales.getSales(user.getIdUser(), true,"Disponible"));
            request.setAttribute("salesVencidos", sales.getSales(user.getIdUser(), true,"Vencido"));
            request.setAttribute("salesCanjeados", sales.getSales(user.getIdUser(), true,"Canjeado"));
            request.setAttribute("title", "Lista de tus cupones");
            request.getRequestDispatcher("/client/Sales/listSales.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void newCupon(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        try {
            request.setAttribute("title", "Compra de Cupones");
            request.setAttribute("promotions", promotions.getPromotionsA(true));
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
            //Aqui para abajo
            if (errorsList.size() > 0) {
                out.print("-2");
            } else {
                ArrayList<String> ids = new ArrayList<>();
                //inicio del for
                Company company = new Company();
                company.setIdCompany(idC);
                HttpSession session = request.getSession(true);
                User user = (User) session.getAttribute("user");
                user.setIdUser(user.getIdUser());
                
                Promotion promot = new Promotion();
                SalesState stat = new SalesState();
                stat.setIdSalesState(2);
                promot.setIdPromotion(idP);
                
                for (int i = 0; i < cant; i++) {
                    Sales salesN = new Sales();

                    //Asignando el codigo del cupon
                    salesN.setCouponCode(sales.generateCode(company));
                    //Asignando el codigo de la promotion
                    salesN.setPromotion(promot);
                    //Asignando el Cliente

                    salesN.setClient(user);
                    //Falta asignar el estado

                    salesN.setState(stat);
                    //Realizando el proceso 
                    if (sales.insertSales(salesN)) {
                    //Añadiendo el codigo del cupon a un array 
                        ids.add(salesN.getCouponCode());
                    }
                }//fin for
                //Verificando si el tamaño de transacciones es igual a la cantidad
                if (ids.size() == cant) {
                    JSONObject json = new JSONObject();
                    json.put("cupones", ids);
                    out.print(json.toString());
                } else {
                    out.print("0");
                }
            }
        } catch (IOException | SQLException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void detailsCupon(HttpServletRequest request, HttpServletResponse response) throws SQLException {
            try {
            if (Validacion.esEnteroPositivo(request.getParameter("idSales"))) {
                Sales sale = sales.getSale(Integer.parseInt(request.getParameter("idSales")), true);
                if (sale != null) {
                    request.setAttribute("coupon", sale);
                    request.setAttribute("title", "Detalle del cupon");
                    request.getRequestDispatcher("/client/Sales/detailsSales.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha encontrado ningun cupon");
                    response.sendRedirect(request.getContextPath() + "/client/sales.do?op=listC");
                }
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                response.sendRedirect(request.getContextPath() + "/client/sales.do?op=listC");
            }
        } catch (ServletException | IOException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void exchange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/employee/sale/redeemCoupons.jsp").forward(request, response);
    }
    private void getInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                Promotion promo = promotions.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), true);
                if (promo != null) {
                    request.setAttribute("title", "Detalle de la promocion");
                    request.setAttribute("promotion", promo);
                    request.setAttribute("fechali", promo.getStringLimitDate());
                    request.setAttribute("fechaexp", promo.getStringEndDate());
                    request.getRequestDispatcher("/client/Sales/detailsPromotion.jsp").forward(request, response);
                } else {
                    request.setAttribute("title", "Compra de Cupones");
                    request.setAttribute("promotions", promotions.getPromotionsA(true));
                    request.getRequestDispatcher("/client/Sales/newSales.jsp").forward(request, response);
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void publicP(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        try {
            request.setAttribute("promotions", promotions.getPromotionsA(true));
            request.getRequestDispatcher("/promociones.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void getInfoPublic(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if (Validacion.esEnteroPositivo(request.getParameter("idPromotion"))) {
                Promotion promo = promotions.getPromotion(Integer.parseInt(request.getParameter("idPromotion")), true);
                if (promo != null) {
                    request.setAttribute("promotion", promo);
                    request.setAttribute("fechali", promo.getStringLimitDate());
                    request.setAttribute("fechaexp", promo.getStringEndDate());
                    request.getRequestDispatcher("/detailsPromotion.jsp").forward(request, response);
                } else {
                    request.setAttribute("promotions", promotions.getPromotionsA(true));
                    request.getRequestDispatcher("/promociones.jsp").forward(request, response);
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pagination(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if(Validacion.esEnteroPositivo(request.getParameter("page"))){
                int page = Integer.parseInt(request.getParameter("page"));
                List<Promotion> cupones = promotions.getPagination(page, true);
                if(cupones != null){
                    JSONObject json = new JSONObject();
                    json.put("promotions", cupones);
                    json.put("btn",promotions.getBtn(page));
                    out.print(json);
                }else{
                    out.print("0");
                }                    
            }else{
                out.print("0");
            }
            
        } catch (IOException | SQLException ex) {
            Logger.getLogger(salesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void generarPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, IOException{
        ServletOutputStream out = response.getOutputStream();
        String tipo = request.getParameter("type");
        
        try {
            Context init = new InitialContext();
        
            Context context = (Context) init.lookup("java:comp/env");
        
            DataSource dataSource =(DataSource)context.lookup("jdbc/mysql");
            Connection conexion = dataSource.getConnection();
            
            String codes = "";
            boolean first = true;
            
            for (Entry<String, String[]> paramEntry : request.getParameterMap().entrySet())
                if(paramEntry.getKey().toLowerCase().startsWith("code"))
                    for (String val : paramEntry.getValue()){
                        codes += (first)? "'"+val+"'": ",'"+val+"'";
                        first = false;
                    }
            
            JasperReport reporte = (JasperReport)JRLoader.loadObjectFromFile(getServletContext().getRealPath("/jasper/FacturaCupones.jasper"));
        
            Map parameters = new HashMap();
            parameters.put("codes", codes);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(reporte,parameters, conexion);
            JRExporter exporter = null;

            if (tipo.equals("pdf")) {
                response.setContentType("application/pdf");
                exporter = new JRPdfExporter();
            } else {
                response.setContentType("text/html;charset=UTF-8");
                exporter = new JRHtmlExporter();
            }
            
            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
            getServletContext().getContextPath() + "/jasperImage?rnd=" + Math.random() + "&image=");

            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,Boolean.FALSE);

            request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            
            conexion.close();
            exporter.exportReport();
        }
        catch (Exception e)
        {
        e.printStackTrace();
        Log.debug("Error", e);
        }
        finally {
        out.close();
        }
    }

    private void invoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         request.setAttribute("code", request.getParameter("couponCode"));
        request.getRequestDispatcher("/client/Sales/invoice.jsp").forward(request, response);
    }
}
