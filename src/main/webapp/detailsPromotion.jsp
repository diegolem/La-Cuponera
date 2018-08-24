<%-- 
    Document   : detailsPromotion
    Created on : 23-ago-2018, 11:13:50
    Author     : Diego Lemus
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles de la promocion [Cuponera]</title>
        <jsp:include page="cabecera.jsp"/>
        <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
    </head>
    <body onload="cargarFecha()">
        <style>
            .tabs .indicator{
                background-color: #00796b !important;
            }
        </style>
        <div>
            <nav class="teal z-depth-0" id="nav">
                <div class="nav-wrapper">
                    <a href="#" class="brand-logo">
                        &nbsp;La Cuponera
                    </a>
                    <ul class="right hide-on-med-and-down" id="opc">   
                        <li><a href="${pageContext.request.contextPath}/sales.do?op=public">Cupones</a></li>
                        <li><a href="login.jsp">Iniciar Sesión</a></li>
                        <li><a href="register.jsp">Registrarme</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <br>
        <div class="row">
            <div class="col s1"></div>
            <div class="col s10">
                <a href="${pageContext.request.contextPath}/sales.do?op=public" class="waves-effect waves-blue btn-large"><i class="material-icons left centered">line_weight</i>Listado de cupones</a>
                <br>                
                <div class="card">
                    <div class="card-content center">
                    </div>
                    <div class="card-tabs">
                        <ul class="tabs tabs-fixed-width">
                            <li class="tab"><a class="active teal-text darken-2" href="#test4">Información de la oferta</a></li>
                        </ul>
                    </div>
                    <div class="card-content grey lighten-4 center">
                        <div id="test4"> <!-- Datos generales -->
                            <ul class="collection">
                                <li class="collection-item"><b>Nombre de la promoción: </b>${promotion.title}</li>
                                <li class="collection-item"><b>Precio de la oferta: </b>$ ${promotion.ofertPrice}</li>
                                <li id="fecha" class="collection-item"></li>
                                <li class="collection-item">
                                    <c:choose>
                                        <c:when test="${promotion.couponsAvailable eq 0}">
                                            Los cupones se han agotado :(
                                        </c:when>
                                        <c:otherwise>
                                            <span>Solo quedan <b>${promotion.couponsAvailable} cupones restantes!!</b>, consigue el tuyo YA</span>
                                        </c:otherwise>
                                    </c:choose>
                                </li>
                                <li class="collection-item"><b>Descripción de la promoción:</b>
                                    <br>${promotion.description}
                                </li>
                                <li class="collection-item">
                                    <div class="center">
                                        <img width="550" src="${pageContext.request.contextPath}/img/${promotion.image}">
                                    </div>
                                </li>
                            </ul>
                        </div> <!-- Fin datos generales -->
                    </div>
                </div>                
            </div>
        </div>
    </body>
    <script>
        let loader = new Loader();
        function cargarFecha() {
            loader.in();
            let fechaLimite = moment('${fechali}'), now = moment();
            fechaLimite.subtract(parseInt(now.format('DD')), 'days').calendar();
            
            if(${fechali != fechaexp}){
                if(fechaLimite.format('DD') > 5){
                    loader.out();
                    $('#fecha').html('Quedan <b>' + fechaLimite.format('DD') + ' dias</b> para que la oferta termine!!');
                    $('#fecha').addClass('green-text darken-4');
                }else if(fechaLimite.format('DD') <= 5 && fechaLimite.format('DD') >= 2){
                    loader.out();
                    $('#fecha').html('Ya solo quedan <b>' + fechaLimite.format('DD') + ' dias</b> para que la oferta termine!!');
                    $('#fecha').addClass('orange-text darken-4');
                }else if(fechaLimite.format('DD') = 1){
                    loader.out();
                    $('#fecha').html('Ya solo queda <b>' + fechaLimite.format('DD') + ' dia</b> para que la oferta termine!!');
                    $('#fecha').addClass('red-text darken-4');
                }else{
                    loader.out();
                    $('#fecha').html('La oferta ya ha expirado');
                    $('#fecha').addClass('black-text darken-4');
                }
            }else{
                loader.out();
                $('#fecha').html('<b>La oferta ya ha expirado</b>');
                $('#fecha').addClass('red-text darken-4');
            }
        }
    </script>
</html>
