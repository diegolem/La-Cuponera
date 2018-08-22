<%-- 
    Document   : detailSales
    Created on : 21/08/2018, 10:10:34 PM
    Author     : Diego
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles del Cupon</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuClient.jsp"/>
        
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/client/sales.do?op=listC" class="waves-effect waves-purple btn-large"><i class="material-icons left centered">line_weight</i>Lista de tus cupones</a>
                <br>                
                 <div class="card">
                    <div class="card-content center">
                        <i class="material-icons">description</i>
                    </div>
                    <div class="card-tabs">
                        <ul class="tabs tabs-fixed-width">
                            <li class="tab"><a class="active" href="#test4">Datos Generales de tu Cupon</a></li>
                        </ul>
                    </div>
                    <div class="card-content grey lighten-4">
                        <div id="test4"> <!-- Datos generales -->
                            <ul class="collection">
                                <li class="collection-item">Código del cupon: ${coupon.couponCode}</li>
                                <li class="collection-item">Nombre de la oferta: ${coupon.promotion.title}</li>
                                <li class="collection-item">Estado: ${coupon.state.state}</li>
                                <li class="collection-item">Nombre de la empresa: ${coupon.promotion.company.name}</li>
                                <li class="collection-item">Precio de la oferta: $ ${coupon.promotion.ofertPrice}</li>
                                <li class="collection-item">Fecha limite ${coupon.promotion.limitDate}</li>
                                <li class="collection-item">
                                    <div class="row">
                                        <div class="col s2"></div>
                                        <div class="col s4">
                                            <img width="500" src="${pageContext.request.contextPath}/img/${coupon.promotion.image}">
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div> <!-- Fin datos generales -->
                    </div>
                </div>                
            </div>
        </main>
    </body>
</html>
