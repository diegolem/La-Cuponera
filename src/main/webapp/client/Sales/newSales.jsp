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
        <main class="col s12"> 
            <div class="grid">
                <div class="grid-sizer"></div>
                <!--Generate grid-item-->
            </div>
        <div id="sinpromos"></div>
        <ul class="pagination center" id="pagination"></ul>
            
    </main>
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
                <a href="#!" onclick="buyPromotion($('#idCt').val(), $('#numCu').val(), $('#idCom').val())" class="waves-effect waves-light green darken-3 btn"><i class="material-icons left">attach_money</i>Comprar</a>
            </div>
            <br>
        </div>
</body>
<style>
    * { box-sizing: border-box; }
    /* force scrollbar */
    html { overflow-y: scroll; }
    body { font-family: sans-serif; }
    /* ---- grid ---- */
    .grid {
        background: white;
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
    .w{
            color: white !important;
        }
</style>
<script>
    let loader = new Loader();
    var botn = 0;
    var $page = $('.pagination');
    var $grid;
    $(document).ready(function () {
        $grid = $('.grid').masonry({
          itemSelector: '.grid-item',
          percentPosition: true,
          columnWidth: '.grid-sizer'
        });
        pagination(1);
    });
    function pagination(page){
        let promotion = [];
        loader.in();
        $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/client/sales.do?op=pagination",
            data: {page: page},
            success: function(response){
                if(response === "0"){
                    console.log("Revise los datos");
                }else {
                let cards = '';
                let btns = '';
                let json = JSON.parse(response);
                promotion = json.promotions;
                botn = json.btn;
                $('.grid-item').remove();
                $('.pag').remove();
                if(promotion.length === 0){
                    $('#sinpromos').html('<div class="alert lighten-2 white-text red darken-4 center">No hay Promociones aprobadas!!</div>');
                }else{
                    promotion.forEach(function(_p,i){
                    cards += `<div class="grid-item">
                                    <div class="card">
                                        <div class="card-image waves-effect waves-block waves-light">
                                            <img class="activator" src="${pageContext.request.contextPath}/img/`+ _p["image"] +`">
                                        </div>
                                        <div class="card-content">
                                            <span class="card-title grey-text text-darken-4">
                                                `+ _p["title"] +`<a href="${pageContext.request.contextPath}/client/sales.do?op=detailP&idPromotion=`+ _p["idPromotion"] +`"><i class="material-icons right">more_vert</i></a>
                                            </span>
                                            <p class='center-align'>
                                                <a href="${pageContext.request.contextPath}/client/sales.do?op=detailP&idPromotion=`+ _p["idPromotion"] +`" class="blue darken-2 waves-effect waves-light btn btnReserve">Comprar</a>
                                            </p>
                                        </div>
                                    </div>
                                </div>`;
                    });
                    //<a href="#mdlBuy1" class="blue darken-2 waves-effect waves-light btn btnReserve modal-trigger" onclick="setId(`+ _p["idPromotion"] +`, '`+ _p["company"]["idCompany"] +`')">Comprar</a>
                    //modal trigger
                    //onclick="setId(`+ _p["idPromotion"] +`, '`+ _p["company"]["idCompany"] +`')"
                    for(var i = 1;i <= botn;i++){
                        btns += `<li class="waves-effect btn pag light-blue darken-2"><a href="javascript:void(0)" class="w waves-light" onclick="pagination(`+ i +`)">`+ i +`</a></li>`;
                    }
                    let $b = $(btns);
                    $page.append($b);
                    let $c = $(cards);
                    $grid.append($c).masonry('appended',$c);
                    $grid.masonry('reloadItems');
                }//fin else
                    
                }//fin else response
            }//fin del success
        }).done(function(){
            $(".grid").imagesLoaded(function(){
            $('.grid').masonry({
                // options
                itemSelector: '.grid-item',
                columnWidth: '.grid-sizer',
                percentPosition: true
                });
            });
            loader.out();
        });
    }
    
    function setId(id, idC) {
        $('#idCt').val(id);
        $('#idCom').val(idC);
    }
    function buyPromotion(id, cant, idC) {
        loader.in();
        $.ajax({
            url: "${pageContext.request.contextPath}/client/sales.do?op=buy",
            type: "POST",
            data: {
                idPromotion: id,
                Cantidad: cant,
                idCompany: idC
            },
            success: function (response) {
                let text = '', classes = '', callback;
                if (response === "0") {
                    text = 'Ha ocurrido un error en el proceso de compra';
                    classes = 'red lighten-1';
                    callback = function () {};
                } else if (response === "-2") {
                    text = 'Por favor revise los datos';
                    classes = 'red lighten-1';
                    callback = function () {};
                } else {
                    var sales = jQuery.parseJSON( response );
                    
                    var url = "${pageContext.request.contextPath}/client/sales.do?op=cuponPdf&type=pdf";
                    
                    $.each(sales.cupones, function( key, value ) {
                        url += "&code="+value;
                    });
                    
                    var a = document.createElement("a");
                        a.classList.add("hide");
                        a.target = "_blank";
                        a.href = url;
                        a.click();
                    
                    classes = 'green darken-2';
                    text = 'Compra exitosa';
                    callback = function () { location.href = "${pageContext.request.contextPath}/client/sales.do?op=listC" };
                }
                
                loader.out();
                M.toast({html: text, classes, displayLength: 1500, completeCallback: callback});
            }
        });
    }
</script>
</html>

