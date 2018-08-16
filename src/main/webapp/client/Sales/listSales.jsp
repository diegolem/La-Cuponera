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
                <div id="selectFiltered" class="center col s6 offset-s3">

                </div>
                <table class="centered responsive-table" id="tblSales">
                    <thead>
                        <tr>
                            <th>Codigo del cupon</th>
                            <th>Nombre de la promocion</th>
                            <th>Verificacion</th>
                            <th>Estado del cupon</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.salesList}" var="sales">
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
                                <td>${sales.state.state}</td>
                                <td>
                                    <a title="Detalles" href="${pageContext.request.contextPath}/client/sales.do?op=details&idSales=${sales.couponCode}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        <script>
                $(document).ready(function(){
                    $('select').formSelect();
                    $('#tblSales').DataTable();
                });
        </script>
    </body>
</html>
