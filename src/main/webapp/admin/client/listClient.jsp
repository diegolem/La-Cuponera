<%-- 
    Document   : listClient
    Created on : 8/08/2018, 06:08:46 PM
    Author     : pc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de clientes</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <c:set var = "title" scope = "page" value = "Lista de clientes"/>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        
        <main class="">
            <a href="${pageContext.request.contextPath}/admin/user.do?op=new_client" class="waves-effect waves-light btn-large"><i class="material-icons left centered">add</i>Añadir usuario</a>
            <br>
            <br>
            
            <div class="row">
                <table class="centered striped">
                    <thead>
                      <tr>
                          <th>ID</th>
                          <th>Nombre completo</th>
                          <th>E-mail</th>
                          <th>DUI</th>
                          <th>NIT</th>
                          <th>Cupones</th>
                      </tr>
                    </thead>

                    <tbody>
                        <c:forEach items="${requestScope.users}" var="user" >
                            <tr>
                                <td>${user.idUser}</td>
                                <td>${user.name} ${user.lastName}</td>
                                <td>${user.email}</td>
                                <td>${user.dui}</td>
                                <td>${user.nit}</td>
                                <td>
                                    <a title="Disponibles" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',1)"><i class="material-icons centered">new_releases</i></a>
                                    <a title="Canjeados" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',2)"><i class="material-icons centered">insert_emoticon</i></a>
                                    <a title="Vencidos" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',3)"><i class="material-icons centered">block</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        
        <div id="modal" class="modal bottom-sheet">
            <div class="modal-content">
                <h4 id="cuponerTitulos">Modal Header</h4>
                <p id='usuario'>A bunch of text</p>
            </div>
            
            <div>
                <ul id="listSales" class="collection">
                    
                </ul>
            </div>
        </div>
        
        <script>
            
            <c:if test="${not empty success}">
                M.toast({html: '${success}'})
                <c:set var="success" value="" scope="session"></c:set>
            </c:if>
            <c:if test="${not empty error}">
                M.toast({html: '${error}'})
                <c:set var="error" value="" scope="session"></c:set>
            </c:if>
            
            function obtenerCupones(usuarioCod, usuario, tipoCupon){
                var instance = M.Modal.getInstance($('#modal'));
                instance.open();
                
                var titulos = ['disponibles', 'canjeados', 'vencidos'];
                
                $('#cuponerTitulos').html("Cupones " + titulos[tipoCupon - 1]);
                $('#usuario').html('Cliente: ' + usuario);
                
                $.ajax({
                    url: '${pageContext.request.contextPath}/sales.do?op=obener_por_usuario&usuario='+usuarioCod+'&estado='+tipoCupon,
                    type: 'GET',
                    dataType: 'JSON',
                    success: function(data){
                        var list = "";
                        
                        $.each( data, function( key, value ) {
                            list += "<li class='collection-item avatar row'><img src='${pageContext.request.contextPath}/assets/img/" + value.image + "' alt=''  class='col s6 m4 l1'><div class='col s6 m8 l11'><span class='title'>Code: " + key + "</span><p>Titulo: " + value.titulo + " <br>Descripcion: " + value.descripcion + "<br>Fecha limite: " + value.fechaLimite + "</p><div></li>";
                        });
                        
                        $("#listSales").html(list);
                    }
                });
            }
            
            $(document).ready(function(){
                $('.modal').modal();
            });
        </script>
    </body>
</html>
