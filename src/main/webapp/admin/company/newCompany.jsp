<%-- 
    Document   : newCompany
    Created on : 08-07-2018, 06:55:50 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Arrays"%>
<%@page import="sv.edu.udb.www.beans.Employee"%>
<%@page import="sv.edu.udb.www.beans.User"%>
<%@page import="sv.edu.udb.www.beans.User"%>
<%@page import="sv.edu.udb.www.beans.Company"%>
<c:if test="${sessionScope.logged != true}">
    <%
        if (!request.getRequestURI().equals(request.getContextPath() + "/login.jsp")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        }
    %>
</c:if>

<c:if test="${sessionScope.logged == true}">
    <c:set var="user" scope="session" value="${sessionScope.user}"></c:set>
    <%
        boolean flag = false;
        String[] actualView = request.getRequestURI().split("/");
        System.out.println(session.getAttribute("logged"));
        switch (session.getAttribute("type").toString()) {
            case "company":
                Company user = (Company) session.getAttribute("user");
                flag = (user != null);
                break;
            case "client":
                User client = (User) session.getAttribute("user");
                flag = (client != null);
                break;
            case "administrator":
                User admin = (User) session.getAttribute("user");
                flag = (admin != null);
                break;
            case "employee":
                Employee employee = (Employee) session.getAttribute("user");
                flag = (employee != null);
                break;
        }
        
        if(flag){
            if (!Arrays.asList(actualView).contains(session.getAttribute("type").toString())) {
                response.sendRedirect("/Cuponera/" + session.getAttribute("type").toString() + "/index.jsp");
            } 
        } else {
            session.setAttribute("logged", false);
            session.setAttribute("user", false);
            session.setAttribute("type", false);
            response.sendRedirect("/Cuponera/login.jsp");
        }
    %>  
</c:if>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Empresa</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/company.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de empresas</a>
                <br>
                <br>
                <form class="col s12" id="frmRegisterCompany" action="${pageContext.request.contextPath}/admin/company.do" method="POST">
                    <input type="hidden" name="op" value="insert"/>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="idCompany" type="text" name="idCompany" value="${company.idCompany}">
                            <label for="idCompany">Código</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['idCompany']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['idCompany']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="name" type="text" name="name" value="${company.name}">
                            <label for="name">Nombre</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="address" type="text" name="address" value="${company.address}">
                            <label for="address">Dirección</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['address']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['address']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="contact" type="text" name="contact" value="${company.contactName}">
                            <label for="contact">Nombre de Contacto</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['contact']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['contact']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="telephone" type="text" name="telephone" value="${company.telephone}">
                            <label for="telephone">Teléfono</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['telephone']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['telephone']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="email" type="text" name="email" value="${company.email}">
                            <label for="email">Email</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['email']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['email']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="comission" type="number" min="0" name="comission" value="${company.pctComission}">
                            <label for="comission">Porcentaje de comisión</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['comission']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['comission']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="input-field col s12">
                        <select name="type">
                            <option value="null" disabled selected>Elegir un rubro</option>
                            <c:forEach items="${requestScope.typesCompany}" var="t">
                                <c:choose>
                                    <c:when test="${company.companyType.idCompanyType eq t.idCompanyType}">
                                        <option selected value="${company.companyType.idCompanyType}">${company.companyType.type}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${t.idCompanyType}">${t.type}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>
                        <label>Rubros</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['companyType']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['companyType']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="row center">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Registrar
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </form>
            </div>
        </main>
        <script>
            $.validator.setDefaults({
                errorClass: 'invalid',
                validClass: 'none',
                errorPlacement: function (error, element) {
                    $(element).parent().find('span.error-block.red-text').remove();
                    $(element).parent().find('span.helper-text').remove();
                    $(element).parent().append("<span class='helper-text' data-error='"+ error.text() +"'></span>");
                }
            });
            
            $.validator.addMethod('validIdCompany', function (value, element) {
                return this.optional(element) || /^([a-z]|[A-Z]){3}[0-9]{3}$/.test(value);
            }, 'Ingrese un id válido [AAA000].');

            $.validator.addMethod('validNum', function (value, element) {
                return this.optional(element) || /^[1-9]\d*$/.test(value);
            }, 'Ingrese un valor válido.');
            
            $.validator.addMethod('validEmail', function (value, element) {
                return this.optional(element) || /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(value);
            }, 'Ingrese un email válido.');
            
            $.validator.addMethod('validTelephone', function (value, element) {
                return this.optional(element) || /^[267]{1}[0-9]{3}([- ])[0-9]{4}$/.test(value);
            }, 'Ingrese un número de teléfono válido [0000-0000].');
            
            $('#frmRegisterCompany').validate({
                rules: {
                    idCompany: {
                        required: true,
                        validIdCompany: true
                    },
                    name: {
                        required: true
                    },
                    address: {
                        required: true
                    },
                    contact: {
                        required: true
                    },
                    telephone: {
                        required: true,
                        validTelephone: true
                    },
                    email: {
                        required: true,
                        validEmail: true
                    },
                    comission: {
                        required: true,
                        validNum: true
                    },
                    type: {
                        required: true
                    }
                },
                messages: {
                    idCompany: {
                        required: 'El campo id es requerido'
                    },
                    name: {
                        required: 'El campo nombre es requerido'
                    },
                    address: {
                        required: 'El campo dirección es requerido'
                    },
                    contact: {
                        required: 'El campo nombre de contacto es requerido'
                    },
                    telephone: {
                        required: 'El campo teléfono es requerido'
                    },
                    email: {
                        required: 'El campo email es requerido'
                    },
                    comission: {
                        required: 'El campo porcentaje de comisión es requerido'
                    },
                    type: {
                        required: 'El campo tipo de empresa es requerido'
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
            $(document).ready(function () {
                $('select').formSelect();
            });
        </script>
    </body>
</html>
