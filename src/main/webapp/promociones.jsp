<%-- 
    Document   : login
    Created on : 08-10-2018, 09:15:59 PM
    Author     : leonardo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promociones [Cuponera]</title>
        <jsp:include page="/cabecera.jsp"/>
        <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
    </head>
    <body>
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
        <div class="col s12">
            <h4 class="center purple-text text-darken-4">Promociones disponibles</h4>
            <c:if test="${empty requestScope.promotions}">
                <div class="alert lighten-2 white-text red darken-4 center">
                    No hay Promociones aprobadas!!
                </div>
            </c:if>
            </div>
            <div class="grid">
                <div class="grid-sizer"></div>
                    <c:choose>
                        <c:when test="${not empty requestScope.promotions}">
                            <c:forEach var="promotion" items="${requestScope.promotions}">
                                <div class="grid-item">
                                    <div class="card">
                                        <div class="card-image waves-effect waves-block waves-light">
                                            <img class="activator" src="${pageContext.request.contextPath}/img/${promotion.image}">
                                        </div>
                                        <div class="card-content">
                                            <span class="card-title grey-text text-darken-4">
                                                ${promotion.title}<a href="${pageContext.request.contextPath}/sales.do?op=detailPublic&idPromotion=${promotion.idPromotion}"><i class="material-icons right">more_vert</i></a>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:when>
                    </c:choose>
            </div>
    <style>
        * { box-sizing: border-box; }

        /* force scrollbar */
        html { overflow-y: scroll; }

        body { font-family: sans-serif; }

        /* ---- grid ---- */

        .grid {
            background: #DDD;
        }

        /* clear fix */
        .grid:after {
            content: '';
            display: block;
            clear: both;
        }

        /* ---- .grid-item ---- */

        .grid-sizer,
        .grid-item {
            width: 33.333%;
        }

        .grid-item {
            float: left;
        }

        .grid-item .card {
            display: block;
            max-width: 100%;
        }

    </style>
        <script>
            $(document).ready(function () {
              var $grid = $('.grid').masonry({
              itemSelector: '.grid-item',
              percentPosition: true,
              columnWidth: '.grid-sizer'
            });
        // layout Masonry after each image loads
        $grid.imagesLoaded().progress( function() {
          $grid.masonry('layout');
        });  

        });
        </script>
    </body>
</html>
