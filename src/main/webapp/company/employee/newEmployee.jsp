<%-- 
    Document   : newEmployee
    Created on : 11/08/2018, 03:48:34 PM
    Author     : pc
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo empleado</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        
        <c:set var = "title" scope = "page" value = "Nuevo empleado"/>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/employee.do?op=list" class="purple lighten-2 waves-effect waves-purple btn-large"><i class="material-icons left centered">line_weight</i>Lista de empleados</a>
                <br>
                <br>
                <form class="col s12" id="frmRegisterCompany" action="${pageContext.request.contextPath}/employee.do" method="POST">
                    <input type="hidden" name="op" value="insert"/>
                    <div class="row">
                        <div class="input-field col s12 m6">
                            <input id="name" type="text" name="name" value="${employee.name}">
                            <label for="name">Nombre</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    
                        <div class="input-field col s12 m6">
                            <input id="last_name" type="text" name="last_name" value="${employee.lastName}">
                            <label for="last_name">Apellido</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['last_name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['last_name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="email" type="text" name="email" value="${employee.email}">
                            <label for="email">Email</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['email']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['email']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    
                    <div class="input-field col s12">
                        <select name="company">
                            <option value="null" disabled selected>Seleccione una compañia</option>
                            <c:forEach items="${requestScope.companies}" var="comp">
                                <c:choose>
                                    <c:when test="${comp.idCompany eq employee.company.idCompany}">
                                        <option selected value="${comp.idCompany}">${comp.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${comp.idCompany}">${comp.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <label>Compañia</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['company']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['company']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                                    
                    <div class="row center">
                        <button class="btn purple lighten-2 waves-effect waves-purple" type="submit" name="action">Registrar
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </form>
            </div>
        </main>
        
    </body>
</html>
