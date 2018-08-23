<%-- 
    Document   : listClient
    Created on : 8/08/2018, 06:08:46 PM
    Author     : pc
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
            
            <div class="row">
                <div class="col s12">
                    <ul class="tabs">
                        <li class="tab col s3"><a href="#tab_client">Clientes</a></li>
                        <li class="tab col s3"><a class="active" href="#tab_admin">Administradores</a></li>
                    </ul>
                </div>
                <div id="tab_client" class="col s12">
                
                    <div class="row">
                        <table id="tblClients" class="centered striped">
                            <thead>
                              <tr>
                                  <th>ID</th>
                                  <th>Nombre completo</th>
                                  <th>E-mail</th>
                                  <th>Estado</th>
                                  <c:if test="${client.confirmed}">
                                    <th>Cupones</th>
                                  </c:if>
                                  <th>Opciones</th>
                              </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.clientType.users}" var="user" >
                                    <tr id="tr_${user.idUser}">
                                        <td>${user.idUser}</td>
                                        <td>${user.name} ${user.lastName}</td>
                                        <td>${user.email}</td>
                                        <td>${(user.confirmed)? "Habilitado" : "Esperando comprobacion"}</td>
                                        <c:if test="${client.confirmed}">
                                            <td>
                                            
                                                <a title="Disponibles" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',2)"><i class="material-icons centered">new_releases</i></a>
                                                <a title="Canjeados" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',1)"><i class="material-icons centered">insert_emoticon</i></a>
                                                <a title="Vencidos" class="waves-effect waves-light btn-small" href="javascript:obtenerCupones(${user.idUser},'${user.name} ${user.lastName}',3)"><i class="material-icons centered">block</i></a>

                                            </td>
                                        </c:if>
                                        <td>
                                            <a title="Detalle" href="${pageContext.request.contextPath}/admin/user.do?op=details_client&id=${user.idUser}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>

                                            <c:choose>
                                                <c:when test="${fn:length(user.sales) gt 0}">
                                                    <a disabled title="Eliminar" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a id="a_${user.idUser}" title="Eliminar" href="#" onclick="deleteClient(${user.idUser}, 'tr_${user.idUser}', 'a_${user.idUser}');" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                                </c:otherwise>
                                            </c:choose>

                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    
                </div>
                <div id="tab_admin" class="col s12">
                    <br>
                    <a href="${pageContext.request.contextPath}/admin/user.do?op=new_client" class="waves-effect waves-light btn-large"><i class="material-icons left centered">add</i>Añadir administrador</a>
                    <br>
                    <br>
                    <div class="row">
                        <table id="tblAdmins" class="centered striped">
                            <thead>
                              <tr>
                                  <th>ID</th>
                                  <th>Nombre completo</th>
                                  <th>E-mail</th>
                                  <th>Estado</th>
                                  <th>Opciones</th>
                              </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.adminType.users}" var="user" >
                                    <tr id="tr_${user.idUser}">
                                        <td>${user.idUser}</td>
                                        <td>${user.name} ${user.lastName}</td>
                                        <td>${user.email}</td>
                                        <td>${(user.confirmed)? "Habilitado" : "Esperando comprobacion"}</td>
                                        <td>
                                            <a title="Detalle" href="${pageContext.request.contextPath}/admin/user.do?op=details_client&id=${user.idUser}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
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
            
            function deleteClient(id, tr, a){
                alertify.confirm('Desea eliminar el cliente?', function(e){
                    if (e) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/admin/user.do?op=delete_client&id=" + id,
                            type: "POST",
                            success: function(response){
                                if(response === "0"){
                                    M.toast({html: 'Ha ocurrido un error en el proceso de eliminación'});
                                }else if(response === "1"){
                                    $( "#"+a ).remove();
                                    M.toast({html: 'Eliminación exitosa', completeCallback: function(){ location.href = 'user.do?op=list_client' }});
                                }
                            }
                        });
                    }
                });
            }
            
            function obtenerCupones(usuarioCod, usuario, tipoCupon){
                var instance = M.Modal.getInstance($('#modal'));
                instance.open();
                
                var titulos = ['canjeados', 'disponibles', 'vencidos'];
                
                $('#cuponerTitulos').html("Cupones " + titulos[tipoCupon - 1]);
                $('#usuario').html('Cliente: ' + usuario);
                
                $.ajax({
                    url: '${pageContext.request.contextPath}/sales.do?op=obener_por_usuario&usuario='+usuarioCod+'&estado='+tipoCupon,
                    type: 'GET',
                    dataType: 'JSON',
                    success: function(data){
                        var list = "";
                        
                        $.each( data, function( key, value ) {
                            list += "<li class='collection-item avatar row'><img src='${pageContext.request.contextPath}/img/" + value.image + "' alt=''  class='col s6 m4 l1'><div class='col s6 m8 l11'><span class='title'>Code: " + key + "</span><p>Titulo: " + value.titulo + "<br>Fecha limite: " + value.fechaLimite + "</p><div></li>";
                        });
                        
                        $("#listSales").html(list);
                    }
                });
            }
            
            $(document).ready(function(){
                $('.modal').modal();
                $("#tblClients").DataTable();
                $("#tblAdmins").DataTable();
                $('.tabs').tabs();
            });
        </script>
    </body>
</html>
