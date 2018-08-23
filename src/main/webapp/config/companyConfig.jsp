<%-- 
    Document   : config
    Created on : Aug 22, 2018, 6:50:45 PM
    Author     : Frank Esquivel
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administrador - Configuracion</title>
        <jsp:include page="/cabecera.jsp"/>
        <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
    </head>
    <body>
        <jsp:include page="../menus/menuCompany.jsp"/>

        <main class="container">
            <br>

            <c:if test="${not empty requestScope.errorsList}">
                <c:if test = "${not empty requestScope.errorsList['msg']}">
                    <div class="alert red lighten-4 red-text text-darken-2 center">
                        ${requestScope.errorsList['msg']}
                    </div>
                </c:if>
            </c:if>

            <c:if test="${sessionScope.msg != null}">
                    <div class="alert green lighten-4 green-text text-darken-2 center">
                        ${sessionScope.msg}
                    </div>
                <c:remove var="msg" scope="session" />
            </c:if>

            <form class="row" method="POST" action="${pageContext.request.contextPath}/company/config.do" name="frmConfig">
                <div class="col s12">
                    <h5 class="grey-text text-darken-2 center">Datos generales</h5>
                    <br>
                </div>
                <input type="hidden" name="id" value="${user.getIdCompany()}">
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="text" id="name" name="name" value="${user.getName()}">
                    <label for="name">Nombre de la empresa</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['name']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['name']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="text" id="contactName" name="contactName" value="${user.getContactName()}">
                    <label for="contactName">Nombre de contacto</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['contactName']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['contactName']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s12">
                    <textarea class="materialize-textarea" name="address" id="address" cols="30" rows="10">${user.getAddress()}</textarea>
                    <label for="address">Direccion</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['address']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['address']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="email" id="email" name="email" value="${user.getEmail()}">
                    <label for="email">Email</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['email']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['email']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="text" id="telephone" name="telephone" value="${user.getTelephone()}">
                    <label for="telephone">Telefono</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['telephone']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['telephone']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <hr>
                <div class="col s12">
                    <br><br>
                    <h5 class="grey-text text-darken-2 center">Cambiar contraseña</h5>
                    <br>
                </div>
                <div class="input-field col s12">
                    <input type="password" id="current_password" name="current_password">
                    <label for="current_password">Contraseña actual</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['current_password']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['current_password']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="password" id="password" name="password">
                    <label for="password">Nueva contraseña</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['password']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['password']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="password" id="conf_password" name="conf_password">
                    <label for="conf_password">Confirmar contraseña</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['conf_password']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['conf_password']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                    <br><br>
                </div>
                <div class="col s12 btn-cont">
                    <button class="btn deep-purple darken-1 waves-effect">Guardar cambios<i class="material-icons right">save</i></button>
                </div>
            </form>

        </main>
    </body>
</html>
