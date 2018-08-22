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
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.CompanyType;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.CompanyTypeModel;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.utilities.Mail;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "companyController", urlPatterns = {"/admin/company.do"})
public class companyController extends HttpServlet {

    CompanyModel companyModel = new CompanyModel();
    CompanyTypeModel companyTypeModel = new CompanyTypeModel();
    HashMap<String, String> errorsList = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
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
                    case "insert":
                        insert(request, response);
                        break;
                    case "get":
                        get(request, response);
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
            request.setAttribute("companiesList", companyModel.getCompanies(true));
            request.setAttribute("title", "Lista de Empresas");
            request.getRequestDispatcher("/admin/company/listCompanies.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin  list()

    private void add(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setAttribute("typesCompany", companyTypeModel.getCompanyTypes(false));
            request.setAttribute("title", "Nueva Empresa");
            request.getRequestDispatcher("/admin/company/newCompany.jsp").forward(request, response);
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin add()

    private void insert(HttpServletRequest request, HttpServletResponse response) {
        try {
            errorsList.clear();
            Company company = new Company();
            CompanyType companyType;

            if (Validacion.esEnteroPositivo(request.getParameter("type"))) {
                companyType = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("type")), false);
                if (companyType != null) {
                    company.setCompanyType(companyType);
                } else {
                    errorsList.put("companyType", "El tipo de empresa no es válido");
                }
            } else {
                errorsList.put("companyType", "El tipo de empresa no es válido");
            }

            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                company.setIdCompany(request.getParameter("idCompany"));
            } else {
                errorsList.put("idCompany", "El código de empresa no es válido [AAA000]");
            }

            if (Validacion.isEmpty(request.getParameter("name"))) {
                errorsList.put("name", "El nombre de la empresa no es válido");
            } else {
                company.setName(request.getParameter("name"));
            }

            if (Validacion.isEmpty(request.getParameter("address"))) {
                errorsList.put("address", "La dirección no debe ser vacía");
            } else {
                company.setAddress(request.getParameter("address"));
            }

            if (Validacion.esNombreEmpresa(request.getParameter("contact"))) {
                company.setContactName(request.getParameter("contact"));
            } else {
                errorsList.put("contact", "El nombre de contacto no es válido");
            }

            if (Validacion.esTelefono(request.getParameter("telephone"))) {
                company.setTelephone(request.getParameter("telephone"));
            } else {
                errorsList.put("telephone", "El número de teléfono no es válido [0000-0000]");
            }

            if (Validacion.esCorreo(request.getParameter("email"))) {
                if (!companyModel.checkEmail(request.getParameter("email"))) {
                    company.setEmail(request.getParameter("email"));
                } else {
                    errorsList.put("email", "Favor agregar otra dirección de correo electrónico");
                }
            } else {
                errorsList.put("email", "El email no es válido [algo@server.com]");
            }

            if (Validacion.esEnteroPositivo(request.getParameter("comission"))) {
                company.setPctComission(Integer.parseInt(request.getParameter("comission")));
                if (company.getPctComission() > 99) {
                    errorsList.put("comission", "El porcentaje de comisión no es válido [0-99]");
                }
            } else {
                errorsList.put("comission", "El porcentaje de comisión no es válido [0-99]");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("company", company);
                request.getRequestDispatcher("/company.do?op=new").forward(request, response);
            } else {
                String password = PasswordResetModel.generatePasswordWithoutEncrypt();
                company.setPassword(PasswordResetModel.parsingPassword(password));

                /* Creando el correo */
                Mail mail = new Mail();
                mail.setAddressee(company.getEmail());
                mail.setAffair("Bienvenido a La Cuponera");
                mail.setMessage("Bienvenido empresa <h3>" + company.getName() + "</h3>"
                        + "<br><br>Se le informa que su clave es <h1>" + password + "</h1>");

                if (companyModel.insertCompany(company)) { // Se insertó correctamente
                    if (mail.sendEmail()) {
                        request.getSession().setAttribute("success", "Empresa registrada");
                        response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                    } else {
                        Company LastCompany = companyModel.getCompany(company.getIdCompany(), false);
                        companyModel.deleteCompany(LastCompany.getIdCompany());
                        request.getSession().setAttribute("error", "Empresa no registrada. Ingrese otro email");
                        response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                    }
                } else {
                    request.getSession().setAttribute("error", "Empresa no registrada. Ingrese otro código");
                    response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin insert()

    private void details(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                Company company = companyModel.getCompany(request.getParameter("idCompany"), true);
                if (company != null) {
                    request.setAttribute("company", company);
                    request.setAttribute("title", "Detalles de la Empresa");
                    request.getRequestDispatcher("/admin/company/detailsCompany.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha encontrado ninguna empresa");
                    response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                }
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ninguna empresa");
                response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin get()

    private void edit(HttpServletRequest request, HttpServletResponse response) {
        try {
            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                String idCompany = request.getParameter("idCompany");
                Company company = companyModel.getCompany(idCompany, true);

                if (company != null) {
                    request.setAttribute("company", company);
                    request.setAttribute("title", "Modificar Empresa");
                    request.setAttribute("typesCompany", companyTypeModel.getCompanyTypes(false));
                    request.getRequestDispatcher("/admin/company/editCompany.jsp").forward(request, response);
                } else {
                    request.getSession().setAttribute("error", "No se ha encontrado ninguna empresa");
                    response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                }
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ninguna empresa");
                response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
            }
        } catch (SQLException | ServletException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// fin edit()

    private void update(HttpServletRequest request, HttpServletResponse response) {
        try {
            errorsList.clear();
            Company company = new Company();
            CompanyType companyType;

            if (Validacion.esEnteroPositivo(request.getParameter("type"))) {
                companyType = companyTypeModel.getCompanyType(Integer.parseInt(request.getParameter("type")), false);
                if (companyType != null) {
                    company.setCompanyType(companyType);
                } else {
                    errorsList.put("companyType", "El tipo de empresa no es válido");
                }
            } else {
                errorsList.put("companyType", "El tipo de empresa no es válido");
            }

            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                company = companyModel.getCompany(request.getParameter("idCompany"), true);
                company.setIdCompany(request.getParameter("idCompany"));
            } else {
                errorsList.put("idCompany", "El código de empresa no es válido [AAA000]");
            }

            if (Validacion.isEmpty(request.getParameter("name"))) {
                errorsList.put("name", "El nombre de la empresa no es válido");
            } else {
                company.setName(request.getParameter("name"));
            }

            if (Validacion.isEmpty(request.getParameter("address"))) {
                errorsList.put("address", "La dirección no debe ser vacía");
            } else {
                company.setAddress(request.getParameter("address"));
            }

            if (Validacion.esNombreEmpresa(request.getParameter("contact"))) {
                company.setContactName(request.getParameter("contact"));
            } else {
                errorsList.put("contact", "El nombre de contacto no es válido");
            }

            if (Validacion.esTelefono(request.getParameter("telephone"))) {
                company.setTelephone(request.getParameter("telephone"));
            } else {
                errorsList.put("telephone", "El número de teléfono no es válido [0000-0000]");
            }

            if (Validacion.esCorreo(request.getParameter("email"))) {
                if (!companyModel.checkEmail(request.getParameter("email"), request.getParameter("idCompany"))) {
                    company.setEmail(request.getParameter("email"));
                } else {
                    errorsList.put("email", "Favor agregar otra dirección de correo electrónico");
                }
            } else {
                errorsList.put("email", "El email no es válido [algo@server.com]");
            }
            System.out.println(company.getCompanyType().getType());
            if (Validacion.esEnteroPositivo(request.getParameter("comission"))) {
                company.setPctComission(Integer.parseInt(request.getParameter("comission")));
                if (company.getPctComission() > 99) {
                    errorsList.put("comission", "El porcentaje de comisión no es válido [0-99]");
                }
            } else {
                errorsList.put("comission", "El porcentaje de comisión no es válido [0-99]");
            }

            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("company", company);
                request.setAttribute("typesCompany", companyTypeModel.getCompanyTypes(false));
                request.getRequestDispatcher("/admin/company/editCompany.jsp").forward(request, response);
            } else {
                if (companyModel.updateCompany(company)) { // Se insertó correctamente
                    request.getSession().setAttribute("success", "Empresa editada");
                    response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                } else {
                    request.getSession().setAttribute("error", "Empresa no editada");
                    response.sendRedirect(request.getContextPath() + "/admin/company.do?op=list");
                }
            }
        } catch (SQLException | IOException | ServletException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }// Fin update()

    private void delete(HttpServletRequest request, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                Company company = companyModel.getCompany(request.getParameter("idCompany"), false);
                if (company != null) {
                    if (companyModel.deleteCompany(request.getParameter("idCompany"))) {
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
            if (Validacion.esCodigoEmpresa(request.getParameter("idCompany"))) {
                String idCompany = request.getParameter("idCompany");
                Company company = companyModel.getCompany(idCompany, true);

                if (company != null) {
                    JSONObject json = new JSONObject();
                    json.put("id", company.getIdCompany());
                    out.print(json);
                } else {
                    out.print("0");
                }
            } else {
                out.print("0");
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//Fin get
}
