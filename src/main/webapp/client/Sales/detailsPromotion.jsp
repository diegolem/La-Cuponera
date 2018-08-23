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
        <title>Detalles de la promocion</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body onload="cargarFecha()">
        <jsp:include page="../../menus/menuClient.jsp"/>

        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/client/sales.do?op=newC" class="waves-effect waves-blue btn-large"><i class="material-icons left centered">line_weight</i>Listado de cupones</a>
                <br>                
                <div class="card">
                    <div class="card-content center">
                        <p class='center-align'>
                            <a href="#mdlBuy1" class="blue darken-2 waves-effect waves-light btn btnReserve modal-trigger" onclick="setId(${promotion.idPromotion}, '${promotion.company.idCompany}')">Comprar</a>
                        </p>
                    </div>
                    <div class="card-tabs">
                        <ul class="tabs tabs-fixed-width">
                            <li class="tab"><a class="active blue-text darken-2" href="#test4">Información de la oferta</a></li>
                        </ul>
                    </div>
                    <div class="card-content grey lighten-4 center">
                        <div id="test4"> <!-- Datos generales -->
                            <ul class="collection">
                                <li class="collection-item"><b>Nombre de la promoción: </b>${promotion.title}</li>
                                <li class="collection-item"><b>Precio de la oferta: </b>$ ${promotion.ofertPrice}</li>
                                <li id="fecha" class="collection-item red-text darken-4"></li>
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
                                    <div class="row">
                                        <div class="col s2"></div>
                                        <div class="col s4">
                                            <img width="500" src="${pageContext.request.contextPath}/img/${promotion.image}">
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div> <!-- Fin datos generales -->
                    </div>
                    <!--Modal Structure-->
                    <div id="mdlBuy1" class="modal">
                        <div class="modal-content">
                            <h4 class="center purple-text text-darken-4">¿Cuantos cupones desea comprar?</h4>
                            <div class="row">
                                <div class="input-field col s12">
                                    <input type="hidden" readonly="true" id="idCt"/>
                                    <input type="hidden" readonly="true" id="idCom"/>
                                    <label for="numCu">Por favor especifique el número de cupones:</label>
                                    <input type="number" max="10" min="1" id="numCu" autocomplete="false"/>
                                </div>
                            </div>
                        </div>
                        <div class="col s12  btn-cont">
                            <a href="javascript:void(0)" class="modal-close waves-effect waves-light red darken-1 btn"><i class="material-icons left">close</i>Cancelar</a>
                            <a href="#!" onclick="buyPromotion($('#idCt').val(),$('#numCu').val(),$('#idCom').val())" class="waves-effect waves-light green darken-3 btn"><i class="material-icons left">attach_money</i>Comprar</a>
                        </div>
                        <br>
                        <br>
                    </div>
                </div>                
            </div>
        </main>
    </body>
    <script>
        let loader = new Loader();
        function cargarFecha(){
            let fechaLimite = moment(${promotion.limitDate}), now = moment();
            fechaLimite.subtract(parseInt(now.format('DD')), 'days').calendar(); 
            $('#fecha').html('Ya solo quedan <b>'+ fechaLimite.format('DD') + ' dias</b> para que la oferta termine!!');
        }
    function setId(id,idC){
                $('#idCt').val(id);
                $('#idCom').val(idC);
            }
        function buyPromotion(id,cant,idC){
            loader.in();
            $.ajax({
                url: "${pageContext.request.contextPath}/client/sales.do?op=buy",
                type: "POST",
                data: {
                    idPromotion: id,
                    Cantidad: cant,
                    idCompany: idC
                },
                success: function(response){
                    let text = '', classes = '', callback;
                    if(response === "0"){
                            text = 'Ha ocurrido un error en el proceso de compra';
                            classes = 'red lighten-1';
                            callback = function(){};
                        }else if(response === "1"){
                            classes = 'green darken-2';
                            text = 'Compra exitosa';
                            callback = function(){location.href='${pageContext.request.contextPath}/client/sales.do?op=listC';};
                        }else if(response === "-2"){
                            text = 'Por favor revise los datos';
                            classes = 'red lighten-1';
                            callback = function(){};
                        }
                        loader.out();
                        M.toast({html: text, classes, displayLength: 1500, completeCallback: callback});
                }
            });
        }
    </script>
</html>
