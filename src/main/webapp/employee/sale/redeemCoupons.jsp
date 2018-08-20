<%-- 
    Document   : redeemCoupons
    Created on : 19/08/2018, 10:55:17 AM
    Author     : pc
--%>

<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Canjear cupones</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuEmployee.jsp"/>
        
        <main class="">
            <div class="row">
                <form class="col s12" id="frmRecoverClient" action="${pageContext.request.contextPath}/employee.do" method="POST">
                    <input type="hidden" name="op" value="client_sales" />
                    <div class="row">
                        <div class="input-field col s12 m8 offset-m1">
                            <input id="dui" name="dui" type="text" class="validate">
                            <label for="dui">Dui</label>
                        </div>
                        <div class="input-field col s12 m2">
                            <button type="submit" id="btnRecover" class="btn-floating"><i class="material-icons">search</i></button>
                        </div>
                    </div>
                    
                </form>
            </div>
                    
            <div class="row hide" id="card-client">
                <div class="col s12 ">
                    <div class="card-panel purple darken-1 white-text">
                        
                        <center>
                            <h4>Cliente: <span id="client_name"></span></h4>
                            <h5>Dui: <span id="client_dui"></span> Nit: <span id="client_nit"></span></h5>
                            <h5>E-Mail: <span id="client_email"></span></h5>
                        </center>
                    </div>
                </div>
            </div>
            
            <div class="row hide" id="card-sales">
                <div class="col s12 ">
                    
                </div>
            </div>

        </main>
                    
        <script>

            function reset(){
                $("#client_name").html("");
                $("#client_email").html("");
                $("#client_dui").html("");
                $("#client_nit").html("");
                $('#card-sales .col').html("");
                $("#card-client").addClass("hide");
                $('#card-sales').addClass("hide");
            }

            $(document).ready(function(){
                $(document).ready(function(){
                    $('.collapsible').collapsible();
                });
                
                $( "#frmRecoverClient" ).submit(function( event ) {
                    event.preventDefault();
                    
                    $.get( "${pageContext.request.contextPath}/employee.do?" + $(this).serialize(), function( data ) {
                        var client = jQuery.parseJSON( data );
                        
                        if (client.hasOwnProperty("error")){
                            M.toast({html: client.error});
                            reset();
                        } else {
                            $("#client_name").html(client.name + " " + client.last_name);
                            $("#client_email").html(client.email);
                            $("#client_dui").html(client.dui);
                            $("#client_nit").html(client.nit);
                            
                            var list = "<ul class='collapsible'>";
                            
                            $.each(client.cupones_disponibles, function( key, value ) {
                                list += "<li id='id_" + value.id + "'>";
                                    list += "<div class='collapsible-header'><i class='material-icons'>new_releases</i>" + value.codigo + " " + value.titulo + "</div>";
                                    list += "<div class='collapsible-body'>";
                                        list += "<div class='row'>";
                                            list += "<div class='col s12'>";
                                                list += "<center><img src='${pageContext.request.contextPath}/assets/img/" + value.imagen +"' width='250px' alt=''></center>";
                                            list += "</div>";
                                        list += "</div>";

                                        list += "<div class='row'>";
                                            list += "<div class='col s12'>";
                                                list += "<table class='centered'>";
                                                    list += "<thead>";
                                                        list += "<tr>";
                                                            list += "<th>Descripcion</th>";
                                                            list += "<th>Registro</th>";
                                                            list += "<th>Vencimiento</th>";
                                                        list += "</tr>";
                                                    list += "</thead>";

                                                    list += "<tbody>";
                                                        list += "<tr>";
                                                            list += "<td>"+ value.descripcion +"</td>";
                                                            list += "<td>"+ value.fecha_inicio +"</td>";
                                                            list += "<td>"+ value.fecha_vencimiento +"</td>";
                                                        list += "</tr>";
                                                    list += "</tbody>";
                                                list += "</table>";
                                                
                                                list += "<br>";
                                                
                                                list += "<form class='frmCanjear' action='${pageContext.request.contextPath}/employee.do' method='POST'>";
                                                
                                                    list += "<input type='hidden' name='idDiv' value='id_" + value.id + "' />";
                                                    list += "<input type='hidden' name='op' value='redeem' />";
                                                    list += "<input type='hidden' name='id' value='" + value.id + "' />";
                                                    list += "<button type='submit' id='btnRecover' class='btn waves-effect waves-light'>Canjear</button>";
    
                                                list += "</form>";
                                            list += "</div>";
                                        list += "</div>";
                                    list += "</div>";
                                list += "</li>";
                            });
                            
                            list += "</ul>"
                            $('#card-sales .col').html(list);
                            
                            $('#card-sales').removeClass("hide");
                            $("#card-client").removeClass("hide");
                            
                            $('.collapsible').collapsible();
                            
                             $( ".frmCanjear" ).submit(function( event ) {
                                 event.preventDefault();
                                 
                                 var id = $(this).find('input[name="idDiv"]').val();
                                 
                                 $.post( "${pageContext.request.contextPath}/employee.do?" + $(this).serialize(), function( data ) {
                                    var result = jQuery.parseJSON(data);

                                    if (result.hasOwnProperty("error"))
                                        M.toast({html: result.error});
                                    else {
                                        $('#'+id).remove();
                                        M.toast({html: result.exito});
                                    }
                                });
                            });
                        }
                    });
                });
            });

            

        </script>
    </body>
</html>