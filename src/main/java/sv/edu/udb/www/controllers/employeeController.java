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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sv.edu.udb.www.beans.Company;
import sv.edu.udb.www.beans.Employee;
import sv.edu.udb.www.beans.UserApp;
import sv.edu.udb.www.model.CompanyModel;
import sv.edu.udb.www.model.EmployeeModel;
import sv.edu.udb.www.model.PasswordResetModel;
import sv.edu.udb.www.model.UserModel;
import sv.edu.udb.www.utilities.Mail;
import sv.edu.udb.www.utilities.Validacion;

/**
 *
 * @author Diego Lemus
 */
@WebServlet(name = "employeeController", urlPatterns = {"/company/employee.do"})
public class employeeController extends HttpServlet {

    CompanyModel companyModel = new CompanyModel();
    EmployeeModel employeeModel = new EmployeeModel();
    HashMap<String, String> errorsList = new HashMap<String, String>();
    UserModel users = new UserModel();

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

        String op = request.getParameter("op");

        try {
            switch (op) {
                case "new":
                    add(request, response);
                    break;

                case "insert":
                    insert(request, response);
                    break;

                case "list":
                    list(request, response);
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

                case "details":
                    details(request, response);
                    break;
            }
        } catch (Exception error) {
            Logger.getLogger(companyController.class.getName()).log(Level.SEVERE, null, error);
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

    private void add(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.setAttribute("title", "Agregar empleado");
        request.setAttribute("companies", companyModel.getCompanies(false));
        request.getRequestDispatcher("/company/employee/newEmployee.jsp").forward(request, response);
    }

    private void insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        HttpSession _s = request.getSession(true);

        Company company = (Company) _s.getAttribute("user");

        Employee employee = new Employee();

        employee.setName(request.getParameter("name"));
        employee.setLastName(request.getParameter("last_name"));
        employee.setCompany(company);
        employee.setEmail(request.getParameter("email"));

        String password = PasswordResetModel.generatePasswordWithoutEncrypt();
        employee.setPassword(PasswordResetModel.parsingPassword(password));

        errorsList.clear();

        if (Validacion.isEmpty(employee.getName())) {
            errorsList.put("name", "El campo Nombre es un campo obligatorio");
        }

        if (Validacion.isEmpty(employee.getLastName())) {
            errorsList.put("last_name", "El campo Apellido es un campo obligatorio");
        }

        if (Validacion.esCorreo(employee.getEmail())) {
            if (employeeModel.checkEmail(employee.getEmail())) {
                errorsList.put("email", "Favor agregar otra dirección de correo electrónico");
            }
        } else {
            errorsList.put("email", "El email no es válido [algo@server.com]");
        }
        /*if (Validacion.isEmpty(employee.getEmail())) {
            errorsList.put("email", "El campo E-mail es un campo obligatorio");
        } else if (!Validacion.esCorreo(employee.getEmail())) {
            errorsList.put("email", "El correo electronico debe de ser valido");
        }*/

        // Si hay errores
        if (errorsList.size() > 0) {
            request.setAttribute("errorsList", errorsList);
            request.setAttribute("employee", employee);
            request.getRequestDispatcher("/company/employee.do?op=new").forward(request, response);
        } else {
            Mail mail = new Mail();
            mail.setAddressee(employee.getEmail());
            mail.setAffair("Reguistro en la cuponera");
            mail.setMessage("Clave de usuario: <h1>" + password + "</h1>");

            if (!users.mailExists(employee.getEmail())) {
                if (employeeModel.insertEmployee(employee)) {
                    mail.sendEmail();
                    request.getSession().setAttribute("success", "Empleado registrado");
                    response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
                } else {
                    request.getSession().setAttribute("error", "El empleado no ha podido registrarse");
                    response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
                }
            } else {
                errorsList.put("email", "El correo ya ha sido registrado");
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("/company/employee.do?op=new").forward(request, response);
            }
        }
    }

    private void list(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        request.setAttribute("title", "Lista de empleados");
        request.setAttribute("employeeList", employeeModel.getEmployees(true));
        request.getRequestDispatcher("/company/employee/listEmployee.jsp").forward(request, response);
    }

    private void edit(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        if (Validacion.esEnteroPositivo(request.getParameter("idEmployee"))) {
            int id = Integer.parseInt(request.getParameter("idEmployee"));
            Employee employee = employeeModel.getEmployee(id, true);

            if (employee != null) {
                request.setAttribute("employee", employee);
                request.setAttribute("companies", companyModel.getCompanies(false));
                request.setAttribute("title", "Editar de empleados");
                request.getRequestDispatcher("/company/employee/editEmployee.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ningun empleado");
                response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
            }
        } else {
            request.getSession().setAttribute("error", "No se ha encontrado ningun empleado");
            response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
        }
    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("idEmployee"));

        Employee employee = employeeModel.getEmployee(id, false);

        if (employee != null) {
            HttpSession _s = request.getSession(true);
            Company company = (Company) _s.getAttribute("user");

            employee.setIdEmployee(id);
            employee.setName(request.getParameter("name"));
            employee.setLastName(request.getParameter("last_name"));
            employee.setCompany(company);
            employee.setEmail(request.getParameter("email"));
            employee.setPassword(employee.getPassword());

            errorsList.clear();

            if (Validacion.isEmpty(employee.getName())) {
                errorsList.put("name", "El campo Nombre es un campo obligatorio");
            }

            if (Validacion.isEmpty(employee.getLastName())) {
                errorsList.put("last_name", "El campo Apellido es un campo obligatorio");
            }

            if (Validacion.isEmpty(employee.getEmail())) {
                errorsList.put("email", "El campo E-mail es un campo obligatorio");
            } else if (!Validacion.esCorreo(employee.getEmail())) {
                errorsList.put("email", "El correo electronico debe de ser valido");
            }

            // Si hay errores
            if (errorsList.size() > 0) {
                request.setAttribute("errorsList", errorsList);
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("/company/employee.do?op=edit").forward(request, response);
            } else {
                if (!users.mailExists(employee.getEmail(), "" + employee.getIdEmployee())) {
                    Mail mail = new Mail();
                    mail.setAddressee(employee.getEmail());
                    mail.setAffair("Reguistro en la cuponera");
                    mail.setMessage("Se han actualizado los datos de su cuenta");

                    if (employeeModel.updateEmployee(employee)) {
                        mail.sendEmail();
                        request.getSession().setAttribute("success", "Empleado modificado");
                        response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
                    } else {
                        request.getSession().setAttribute("error", "No se ha podido modificar el usuario");
                        response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
                    }
                } else {
                    errorsList.put("email", "El correo electronico ya ha sido registrado");
                    request.setAttribute("errorsList", errorsList);
                    request.setAttribute("employee", employee);
                    request.getRequestDispatcher("/company/employee.do?op=edit").forward(request, response);
                }
            }
        } else {
            request.getSession().setAttribute("error", "Empleado no registrado");
            response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
        }

    }

    private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        PrintWriter out = response.getWriter();

        String idEmployee = request.getParameter("idEmployee");

        if (Validacion.esEnteroPositivo(idEmployee)) {
            int id = Integer.parseInt(idEmployee);
            Employee employee = employeeModel.getEmployee(id, false);
            if (employee != null) {
                if (employeeModel.deleteEmployee(employee.getIdEmployee())) {
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
    }

    private void details(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        if (Validacion.esEnteroPositivo(request.getParameter("idEmployee"))) {
            int id = Integer.parseInt(request.getParameter("idEmployee"));
            Employee employee = employeeModel.getEmployee(id, true);

            if (employee != null) {
                request.setAttribute("employeeList", employeeModel.getEmployees(true));
                request.setAttribute("employee", employee);
                request.getRequestDispatcher("/company/employee/detailsEmployee.jsp").forward(request, response);
            } else {
                request.getSession().setAttribute("error", "No se ha encontrado ningun empleado");
                response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
            }
        } else {
            request.getSession().setAttribute("error", "No se ha encontrado ningun empleado");
            response.sendRedirect(request.getContextPath() + "/company/employee.do?op=list");
        }
    }
}
