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
        <c:choose>
            <c:when test = "${sessionScope.type == 'admin'}">
                <jsp:include page="../menus/menuAdmin.jsp"/>
            </c:when>
            <c:when test = "${sessionScope.type == 'client'}">
                <jsp:include page="../menus/menuClient.jsp"/>
            </c:when>
            <c:when test = "${sessionScope.type == 'employee'}">
                <jsp:include page="../menus/menuEmployee.jsp"/>
            </c:when>
        </c:choose>

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

            <form class="row" method="POST" action="${pageContext.request.contextPath}/${sessionScope.type}/config.do" name="frmConfig">
                <div class="col s12">
                    <h5 class="grey-text text-darken-2 center">Datos generales</h5>
                    <br>
                </div>

                <c:choose>
                    <c:when test = "${sessionScope.type == 'admin' || sessionScope.type == 'client'}">
                        <input type="hidden" name="id" value="${user.getIdUser()}">
                    </c:when>
                    <c:when test = "${sessionScope.type == 'employee'}">
                            <input type="hidden" name="id" value="${user.getIdEmployee()}">
                    </c:when>
                </c:choose>

                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="text" id="name" name="name" value="${user.getName()}">
                    <label for="name">Nombre</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['name']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['name']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s10 m6 l6 offset-s1">
                    <input type="text" id="lastName" name="lastName" value="${user.getLastName()}">
                    <label for="lastName">Apellido</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['lastName']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['lastName']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>
                <div class="input-field col s12">
                    <input type="text" id="email" name="email" value="${user.getEmail()}">
                    <label for="email">Email</label>
                    <c:if test="${not empty requestScope.errorsList}">
                        <c:if test = "${not empty requestScope.errorsList['email']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errorsList['email']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>

                <c:if test="${requestScope.type == 'admin' || requestScope.type == 'client'}">
                    <div class="input-field col s10 m6 l6 offset-s1">
                        <input readonly disabled type="text" id="dui" name="dui" value="${user.getDui()}">
                        <label for="dui">DUI</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['dui']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['dui']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="input-field col s10 m6 l6 offset-s1">
                        <input readonly disabled type="text" id="nit" name="nit" value="${user.getNit()}">
                        <label for="nit">NIT</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['nit']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['nit']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                </c:if>
                
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
                    <button class="btn ${sessionScope.userColor} darken-1 waves-effect">Guardar cambios<i class="material-icons right">save</i></button>
                </div>
            </form>

        </main>
    </body>
</html>
