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
                                                <span class="card-title grey-text text-darken-4">
                                                    ${promotion.title}<a href="${pageContext.request.contextPath}/client/sales.do?op=detailP&idPromotion=${promotion.idPromotion}" class="modal-trigger" ><i class="material-icons right">more_vert</i></a>
                                                </span>
                                                <p class='center-align'>
                                                    <a href="#mdlBuy1" class="blue darken-2 waves-effect waves-light btn btnReserve modal-trigger" onclick="setId(${promotion.idPromotion}, '${promotion.company.idCompany}')">Comprar</a>
                                                </p>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:when>
                        </c:choose>
                        <!-- Modal Structure -->
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
                        </div>
                        <!--
                        <div id="mdlInfo" class="modal">
                            <div class="modal-content">
                                <h4 class="center light-blue-text text-darken-4">Información General</h4>
                                <div class="col s12">
                                    <h5>
                                        <br>
                                    <span id="title" class="center black-text text-darken-4"></span><br>
                                    <span id="ofertPrice" class="center black-text text-darken-4"></span><br>
                                    <span id="limitDate" class="center black-text text-darken-4"></span><br>
                                    <span id="couponrest" class="center red-text text-darken-4"></span><br>
                                    <span id="description" class="center black-text text-darken-4"></span><br>
                                    </h5>
                                </div>
                            </div>
                            <div class="col s12  btn-cont">
                                <a href="javascript:void(0)" class="modal-close waves-effect waves-light light-blue darken-2 btn"><i class="material-icons left">close</i> Cerrar</a>
                            </div>
                            <br>-->
                        </div>
                    </div>
                </div>
        </main>
    </body>
    <script>
        let loader = new Loader();
        $(document).ready(function () {
            $('.grid').masonry({
                // options
                itemSelector: '.grid-item',
                columnWidth: 10
            });
        });
        /*
        function setInfo(idP){
            $('#mdlInfo #title').text("");
            $('#mdlInfo #ofertPrice').text("");
            $('#mdlInfo #limitDate').text("");
            $('#mdlInfo #couponrest').text("");
            $('#mdlInfo #description').text("");
            loader.in();
           $.ajax({
              url: "${pageContext.request.contextPath}/client/sales.do?op=get",
              type: "POST",
              data: {
                  idPromo: idP
              },
              success: function(response){
                  let json = JSON.parse(response);
                  $('#mdlInfo #title').text('Nombre de la oferta: ' + json.title);
                  let fechaLimite = moment(json.limitDate), now = moment();
                  fechaLimite.subtract(parseInt(now.format('DD')), 'days').calendar();
                  $('#mdlInfo #ofertPrice').text("Precio de la oferta: $" +json.ofertPrice);
                  $('#mdlInfo #limitDate').text('Ya solo quedan '+ fechaLimite.format('DD') +' dias para que la oferta termine!!');
                  if(json.couponrest === 0){
                      $('#mdlInfo #couponrest').text('Los cupones se han agotado :(');
                  }else if(json.couponrest.toString() === "0"){
                      $('#mdlInfo #couponrest').text('Los cupones se han agotado :(');
                  }else{
                      $('#mdlInfo #couponrest').text('Solo quedan ' + json.couponrest + ' cupones restantes!!, consigue el tuyo YA');
                  }
                  $('#mdlInfo #description').html('Descripción de la oferta: '+ json.description);
                  loader.out();
              }
           });
        }*/
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

