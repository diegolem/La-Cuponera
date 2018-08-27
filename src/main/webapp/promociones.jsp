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
            <h4 class="center teal-text text-darken-4">Promociones disponibles</h4>
            <div id="sinpromos"></div>
            </div>
            <div class="grid">
                <div class="grid-sizer"></div>
                <!--Generate grid-item-->
            </div>
        
        <ul class="pagination center" id="pagination"></ul>
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
        /* ---- .grid-item ---- */
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
            url: "${pageContext.request.contextPath}/sales.do?op=pagination",
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
                                                `+ _p["title"] +`<a href="${pageContext.request.contextPath}/sales.do?op=detailPublic&idPromotion=`+ _p["idPromotion"] +`"><i class="material-icons right">more_vert</i></a>
                                            </span>
                                        </div>
                                    </div>
                                </div>`;
                    });
                    for(var i = 1;i <= botn;i++){
                        btns += `<li class="waves-effect btn pag"><a href="javascript:void(0)" class="w waves-light" onclick="pagination(`+ i +`)">`+ i +`</a></li>`;
                    }
                    let $b = $(btns);
                    $page.append($b);
                    let $c = $(cards);
                    $grid.append($c).masonry('appended',$c);
                    $grid.masonry('reloadItems');
                    }//fin de else
                }//fin else response
            }//fin de success
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
        </script>
    </body>
</html>
