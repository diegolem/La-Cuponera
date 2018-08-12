<%-- 
    Document   : newPromotion
    Created on : 08-11-2018, 11:44:10 AM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Oferta</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <c:set var = "title" scope = "page" value = "Agregar Oferta"/>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/company/promotion.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de ofertas</a>
                <br>
                <br>
                <form enctype="multipart/form-data" class="col s12" id="frmRegisterCompany" action="${pageContext.request.contextPath}/company/promotion.do?op=insert" method="POST">
                    <input type="hidden" name="op" value="insert"/>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="title" type="text" name="title" value="${promotion.title}">
                            <label for="title">Título</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['title']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['title']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="regularPrice" type="number" min="0" step="0.01" name="regularPrice" value="${promotion.regularPrice}">
                            <label for="regularPrice">Precio regular</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['regularPrice']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['regularPrice']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="ofertPrice" type="number" min="0" step="0.01" name="ofertPrice" value="${promotion.ofertPrice}">
                            <label for="ofertPrice">Precio oferta</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['ofertPrice']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['ofertPrice']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input type="date" name="initDate" id="initDate">
                            <label for="initDate">Fecha de Inicio</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['initDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['initDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input type="date" name="endDate" id="endDate">
                            <label for="initDate">Fecha Final</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['endDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['endDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input type="date" name="limitDate" id="limitDate">
                            <label for="limitDate">Fecha Límite</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['limitDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['limitDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="limitCant" type="number" min="0" name="limitCant" value="${company.pctComission}">
                            <label for="limitCant">Cantidad límite [Ingrese 0 por defecto]</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['limitCant']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['limitCant']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="input-field col s12">
                        <textarea id="description" name="description" class="materialize-textarea"></textarea>
                        <label for="description">Descripción</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['description']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['description']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="input-field col s12">
                        <textarea id="otherDetails" name="otherDetails" class="materialize-textarea"></textarea>
                        <label for="otherDetails">Detalles</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['otherDetails']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['otherDetails']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="input-field col s12">
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Imagen</span>
                                <input type="file" name="img">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path" type="text">
                            </div>
                        </div>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['img']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['img']}</strong>
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
    </body>
</html>
