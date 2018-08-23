<%-- 
    Document   : listPromotion
    Created on : 14-ago-2018, 22:14:26
    Author     : Diego Lemus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Tus Cupones</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuClient.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/client/sales.do?op=newC" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Comprar cupon</a>
                <br>
                <br>
                <div class="col s12">
                    <ul class="tabs">
                        <li class="tab col s3"><a class="active" href="#tab_disponibles">Cupones Disponibles</a></li>
                        <li class="tab col s3"><a href="#tab_canjeados">Cupones Canjeados</a></li>
                        <li class="tab col s3"><a href="#tab_vencidos">Cupones Vencidos</a></li>
                    </ul>
                </div>
                <div id="tab_disponibles" class="col s12">
                        <table class="centered responsive-table" id="tblSalesD">
                            <thead>
                                <tr>
                                    <th>Codigo del cupon</th>
                                    <th>Nombre de la promocion</th>
                                    <th>Verificación</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.salesDisponible}" var="sales">
                                    <tr>
                                        <td>${sales.couponCode}</td>
                                        <td>${sales.promotion.title}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sales.verified eq 1}">
                                                    Verificado
                                                </c:when>
                                                <c:when test="${sales.verified eq 0}">
                                                    Sin verificacion
                                                </c:when>
                                                <c:otherwise>
                                                    Sin verificacion
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a title="Detalles" href="${pageContext.request.contextPath}/client/sales.do?op=details&idSales=${sales.idSales}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                </div>
                <div id="tab_canjeados" class="col s12">
                        <table class="centered responsive-table" id="tblSalesC">
                            <thead>
                                <tr>
                                    <th>Codigo del cupon</th>
                                    <th>Nombre de la promocion</th>
                                    <th>Verificación</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.salesCanjeados}" var="sales">
                                    <tr>
                                        <td>${sales.couponCode}</td>
                                        <td>${sales.promotion.title}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sales.verified eq 1}">
                                                    Verificado
                                                </c:when>
                                                <c:when test="${sales.verified eq 0}">
                                                    Sin verificacion
                                                </c:when>
                                                <c:otherwise>
                                                    Sin verificacion
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a title="Detalles" href="${pageContext.request.contextPath}/client/sales.do?op=details&idSales=${sales.idSales}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                </div>
                <div id="tab_vencidos" class="col s12">
                        <table class="centered responsive-table" id="tblSalesV">
                            <thead>
                                <tr>
                                    <th>Codigo del cupon</th>
                                    <th>Nombre de la promocion</th>
                                    <th>Verificación</th>
                                    <th>Acciones</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.salesVencidos}" var="sales">
                                    <tr>
                                        <td>${sales.couponCode}</td>
                                        <td>${sales.promotion.title}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sales.verified eq 1}">
                                                    Verificado
                                                </c:when>
                                                <c:when test="${sales.verified eq 0}">
                                                    Sin verificacion
                                                </c:when>
                                                <c:otherwise>
                                                    Sin verificacion
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <a title="Detalles" href="${pageContext.request.contextPath}/client/sales.do?op=details&idSales=${sales.idSales}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
            </div>
        </main>
        <script>
                $(document).ready(function(){
                    $('.tabs').tabs();
                    $('#tblSalesD').DataTable();
                    $('#tblSalesC').DataTable();
                    $('#tblSalesV').DataTable();
                });
        </script>
    </body>
</html>
