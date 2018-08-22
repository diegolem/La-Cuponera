/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.primefaces.json.JSONObject;
import sv.edu.udb.www.beans.CompanyType;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.CompanyTypeModel;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "companyTypeController", urlPatterns = {"/admin/companiesType.do"})
public class companyTypeController extends HttpServlet {

    CompanyModel companyModel = new CompanyModel();
    CompanyTypeModel companyTypeModel = new CompanyTypeModel();
    HashMap<String, String> errorsList = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String operation = request.getParameter("op");
            HttpSession _s = request.getSession(true);

            if (operation == null) {
                list(request, response);
                return;
            }

            if (_s.getAttribute("logged") != null) {
                switch (operation) {
                    case "list":
                        list(request, response);
                        break;
                    case "new":
                        add(request, response);
                        break;
                    case "get":
                        get(request, response);
                        break;
                    case "insert":
                        insert(request, response);
                        break;
                    case "details":
                        details(request, response);
                        break;
                    case "edit":
                        edit(request, response);
                        break;
                    case "update":
                        update(request, response);
                        break;
                    case "delete":
                        delete(request, response);
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
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

    private void list(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        try {
            request.setAttribute("typesCompany", companyTypeModel.getCompanyTypes(true));
            request.setAttribute("title", "Lista de Rubros");
            request.getRequestDispatcher("/admin/companyType/listCompaniesType.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin  list()

    private void add(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("title", "Nuevo Rubro");
            request.getRequestDispatcher("/admin/companyType/newCompaniesType.jsp").forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin add()

    private void insert(HttpServletRequest request, HttpServletResponse response) {
        try {
            errorsList.clear();
            CompanyType companytype = new CompanyType();
            if (Validacion.esRubro(request.getParameter("name"))) {
                if (!Validacion.isEmpty(request.getParameter("name"))) {
                    //asignamos valor
                    companytype.setType(request.getParameter("name"));
                } else {
                    errorsList.put("name", "El nombre del rubro no debe estar vacio");
                }
            } else {
                errorsList.put("name", "El nombre del rubro no es valido");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("companiesType", companytype);
                request.getRequestDispatcher("/admin/companiesType.do?op=new").forward(request, response);
            } else {
                if (companyTypeModel.insertCompanyType(companytype)) {
                    request.getSession().setAttribute("success", "Rubro registrado");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                } else {
                    request.getSession().setAttribute("error", "Rubro no registrado. Revise los datos");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin insert()

    private void details(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Validacion.esEnteroPositivo(request.getParameter("idCompanyType"))) {
                CompanyType companytype = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("idCompanyType")), true);
                if (companytype != null) {
                    request.setAttribute("companyType", companytype);
                    request.setAttribute("title", "Detalle de Rubro");
                    request.getRequestDispatcher("/admin/companyType/detailsCompanyType.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                }
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin get()

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Validacion.esEnteroPositivo(request.getParameter("idCompanyType"))) {
                CompanyType companytype = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("idCompanyType")), true);
                if (companytype != null) {
                    request.setAttribute("companiesType", companytype);
                    request.setAttribute("title", "Modificar Rubro");
                    request.getRequestDispatcher("/admin/companyType/editCompaniesType.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                }
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin edit()

    private void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            errorsList.clear();
            CompanyType companytype = new CompanyType();
            if (Validacion.esEnteroPositivo(request.getParameter("idCompanyType"))) {
                companytype = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("idCompanyType")), true);
                companytype.setIdCompanyType(Integer.parseInt(request.getParameter("idCompanyType")));
            } else {
                errorsList.put("idCompanyType", "El código del rubro es invalido");
            }
            if (Validacion.esRubro(request.getParameter("name"))) {
                if (!Validacion.isEmpty(request.getParameter("name"))) {
                    //asignamos valor
                    companytype.setType(request.getParameter("name"));
                } else {
                    errorsList.put("name", "El nombre del rubro no debe estar vacio");
                }
            } else {
                errorsList.put("name", "El nombre del rubro no es válido");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("companiesType", companytype);
                request.getRequestDispatcher("/admin/companyType/editCompaniesType.jsp").forward(request, response);
            } else {
                if (companyTypeModel.updateCompanyType(companytype)) {
                    request.getSession().setAttribute("success", "Rubro modificado");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                } else {
                    request.getSession().setAttribute("error", "El rubro no pudo ser modificado");
                    response.sendRedirect(request.getContextPath() + "/admin/companiesType.do?op=list");
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin update()

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if (Validacion.esEnteroPositivo(request.getParameter("idCompanyType"))) {
                CompanyType companytype = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("idCompanyType")), false);
                if (companytype != null) {
                    if (companyTypeModel.deleteCompanyType(companytype)) {
                        out.print("1");
                    } else {
                        out.print("0");
                    }
                } else {
                    out.print("0");
                }
            } else {
                out.print("-1");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin delete()

    private void get(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if (Validacion.esEnteroPositivo(request.getParameter("idCompanyType"))) {
                CompanyType companytype = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("idCompanyType")), true);
                if (companytype != null) {
                    JSONObject json = new JSONObject();
                    json.put("id", companytype.getIdCompanyType());
                    json.put("type", companytype.getType());
                    out.print(json);
                } else {
                    out.print("0");
                    request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
                }
            } else {
                out.print("0");
                request.getSession().setAttribute("error", "No se ha encontrado ningun rubro");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(companyTypeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin get
}
