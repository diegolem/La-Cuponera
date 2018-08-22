<%-- 
    Document   : buypromotion
    Created on : 14-ago-2018, 18:54:19
    Author     : Diego Lemus
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Comprar Cupones</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuClient.jsp"/>
        <main class="">
            <div class="row">
                <div class="grid">
                    <div class="grid-sizer">
                        <c:choose>
                            <c:when test="${empty requestScope.promotions}">
                                <div class="alert lighten-2 white-text red darken-4 center">
                                    No hay Promociones aprobadas!!
                                </div>
                            </c:when>
                            <c:when test="${not empty requestScope.promotions}">
                                <c:forEach var="promotion" items="${requestScope.promotions}">
                                    <div class="grid-item">
                                        <div class="card">
                                            <div class="card-image waves-effect waves-block waves-light">
                                                <img class="activator" src="${pageContext.request.contextPath}/img/${promotion.image}">
                                            </div>
                                            <div class="card-content">
                                                <span class="card-title activator grey-text text-darken-4">
                                                    ${promotion.title}<i class="material-icons right">more_vert</i>
                                                </span>

                                                <p class='center-align'>
                                                    <a href="#mdlBuy1" class="blue darken-2 waves-effect waves-light btn btnReserve modal-trigger" onclick="setId(${promotion.idPromotion}, '${promotion.company.idCompany}')">Comprar</a>
                                                </p>
                                            </div>
                                            <div class="card-reveal">
                                                <span class="card-title grey-text text-darken-4">${promotion.title}<i class="material-icons right">close</i></span>
                                                <p>
                                                <ul>
                                                    <li>Titulo: ${promotion.title}</li>
                                                    <li>Precio regular: $ ${promotion.regularPrice}</li>
                                                    <li>Precio oferta: $ ${promotion.ofertPrice}</li>
                                                    <li>Empresa: ${promotion.company.name}</li>
                                                </ul>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        <!-- Modal Structure -->
                        <div id="mdlBuy1" class="modal modal-fixed-footer">
                            <div class="modal-content">
                                <h4 id="header-modal">¿Cuantos cupones desea comprar?</h4>
                                <div class="divider"></div>
                                <div class="row"></div>
                                <div class="row">
                                    <div class="input-field col s6">
                                        <input type="hidden" readonly="true" id="idCt"/>
                                        <input type="hidden" readonly="true" id="idCom"/>
                                        <label for="numCu">Por favor especifique el número de cupones:</label>
                                        <input type="number" max="10" min="1" id="numCu" autocomplete="false"/>
                                    </div>
                                </div>
                            </div>
                            <div class="modal-footer">
                                <a href="javascript:void(0)" class="modal-close waves-effect waves-light red darken-1 btn"><i class="material-icons left">close</i>Cancelar</a>
                                <a href="#!" onclick="buyPromotion($('#idCt').val(),$('#numCu').val(),$('#idCom').val())" class="waves-effect waves-light green darken-3 btn"><i class="material-icons left">attach_money</i>Comprar</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </body>
    <script text="">
        $(document).ready(function () {
            $('.grid').masonry({
                // options
                itemSelector: '.grid-item',
                columnWidth: 200
            });
        });
        
        function setId(id){
            $('#idCt').val(id);
        }
        function buyPromotion(id,cant){
            $.ajax({
                url: "${pageContext.request.contextPath}/client/sales.do?op=buy",
                type: "POST",
                data: {
                    idPromotion: id,
                    Cantidad: cant
                },
                success: function(response){
                    if(response === "0"){
                        M.toast({html: 'Ha ocurrido un error en el proceso de compra'})
                    }else if(response === "1"){
                        M.toast({html: 'Compra Exitosa', completeCallback: function(){ location.href = '${pageContext.request.contextPath}/client/sales.do?op=listC' }})
                    }
                }
            });
        }
    </script>
</html>

