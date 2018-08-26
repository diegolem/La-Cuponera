<%-- 
    Document   : detailsPromotion
    Created on : 08-12-2018, 03:02:47 PM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:choose>
    <c:when test = "${requestScope.promotion == null}">
        <c:redirect url = "/login.jsp"/>
    </c:when>
    <c:otherwise>
        <c:if test="${requestScope.promotion.company.idCompany != sessionScope.user.idCompany}">
            <c:redirect url = "/login.jsp"/>
        </c:if>
    </c:otherwise>
</c:choose>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles de Oferta</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/company/promotion.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de ofertas</a>
                <br>
                <br>
                <div style="display: flex; justify-content: center; font-size: 18px;">
                    <ul>
                        <li><b>Título: </b> ${promotion.title}</li>
                        <li><b>Precio regular: </b> $${promotion.regularPrice}</li>
                        <li><b>Precio oferta: </b> $${promotion.ofertPrice}</li>
                        <li><b>Fecha de inicio: </b> ${promotion.initDate}</li>
                        <li><b>Fecha final: </b> ${promotion.endDate}</li>
                        <li><b>Fecha limite: </b> ${promotion.limitDate}</li>
                        <li><b>Cantidad limite: </b>
                            <c:choose>
                                <c:when test = "${promotion.limitCant eq 0}">
                                    No aplica
                                </c:when>
                                <c:otherwise>
                                    ${promotion.limitCant}
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li><b>Cupones vendidos: </b>${promotion.couponsSold}</li>
                        <li><b>Cantidad disponibles: </b>
                            <c:choose>
                                <c:when test = "${promotion.limitCant eq 0}">
                                    No aplica
                                </c:when>
                                <c:otherwise>
                                    ${promotion.couponsAvailable}
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li><b>Ganancias: </b>$${promotion.earnings}</li>
                        <li><b>Total de cargos de servicio: </b>$${promotion.chargeService}</li>
                        <li><b>Estado: </b>${promotion.promotionState.state}</li>
                    </ul>
                </div>
                <center>
                    <div class="">
                        <img class="materialboxed" width="650" src="${pageContext.request.contextPath}/img/${promotion.image}">
                    </div>   
                </center>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                $('.materialboxed').materialbox();
            });
        </script>
    </body>
</html>
