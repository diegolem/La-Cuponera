<%-- 
    Document   : newEmployee
    Created on : 11/08/2018, 03:48:34 PM
    Author     : pc
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.logged != true}">
    <%
        if (!request.getRequestURI().equals(request.getContextPath() + "/login.jsp")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    %>
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Editar empleado</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        
        <c:set var = "title" scope = "page" value = "Editar empleado"/>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/employee.do?op=list" class="purple lighten-2 waves-effect waves-purple btn-large"><i class="material-icons left centered">line_weight</i>Lista de empleados</a>
                <br>
                <br>
                <center><h2>Empleado: ${employee.name} ${employee.lastName}</h2></center>
                <center><h3>E-Mail: ${employee.email}</h3></center>
                
                 <ul class="collapsible">
                    <li>
                      <div class="collapsible-header"><i class="material-icons">business</i>Empresa: ${employee.company.name}</div>
                      <div class="collapsible-body">
                          <table class="centered responsive-table" id="tblEmployees">
                            <thead>
                                <tr>
                                    <th>ID</th>
                                    <th>Nombre completo</th>
                                    <th>E-Mail</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.employeeList}" var="employee">
                                    <tr id="tr_${employee.idEmployee}">
                                        <td>${employee.idEmployee}</td>
                                        <td>${employee.name} ${employee.lastName}</td>
                                        <td>${employee.email}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                      </div>
                    </li>
                </ul>
                
            </div>
        </main>
        
        <script>
            $('.collapsible').collapsible();
        </script>
    </body>
</html>
