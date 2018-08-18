<%-- 
    Document   : newEmployee
    Created on : 11/08/2018, 03:48:34 PM
    Author     : pc
--%>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles cliente</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/user.do?op=list_client" class="purple lighten-2 waves-effect waves-purple btn-large"><i class="material-icons left centered">line_weight</i>Lista de clientes</a>
                <br>
                <br>
                <center><h2>Cliente: ${client.name} ${client.lastName}</h2></center>
                <center><h3>E-Mail: ${client.email}</h3></center>
                
                <center><h4>Dui: ${client.dui} Nit: ${client.nit}</h4></center>
                
                 <ul class="collapsible">
                    <li>
                      <div class="collapsible-header"><i class="material-icons">new_releases</i>Cupones disponibles</div>
                      <div class="collapsible-body">
                          <table class="centered responsive-table" id="tblEmployees">
                            <thead>
                                <tr>
                                    <th>Codigo</th>
                                    <th>Titulo</th>
                                    <th>Precio regular</th>
                                    <th>Precio de oferta</th>
                                    <th>Fecha limite</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cuponesDisponibles}" var="cupon">
                                    <tr>
                                        <td>${cupon.couponCode}</td>
                                        <td>${cupon.promotion.title}</td>
                                        <td>${cupon.promotion.regularPrice}$</td>
                                        <td>${cupon.promotion.ofertPrice}$</td>
                                        <td>${cupon.promotion.limitDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                      </div>
                    </li>
                    <li>
                      <div class="collapsible-header"><i class="material-icons">insert_emoticon</i>Cupones canjeados</div>
                      <div class="collapsible-body">
                          <table class="centered responsive-table" id="tblEmployees">
                            <thead>
                                <tr>
                                    <th>Codigo</th>
                                    <th>Titulo</th>
                                    <th>Precio regular</th>
                                    <th>Precio de oferta</th>
                                    <th>Fecha limite</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cuponesCanjeados}" var="cupon">
                                    <tr>
                                        <td>${cupon.couponCode}</td>
                                        <td>${cupon.promotion.title}</td>
                                        <td>${cupon.promotion.regularPrice}$</td>
                                        <td>${cupon.promotion.ofertPrice}$</td>
                                        <td>${cupon.promotion.limitDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                      </div>
                    </li>
                    <li>
                      <div class="collapsible-header"><i class="material-icons">block</i>Cupones vencidos</div>
                      <div class="collapsible-body">
                          <table class="centered responsive-table" id="tblEmployees">
                            <thead>
                                <tr>
                                    <th>Codigo</th>
                                    <th>Titulo</th>
                                    <th>Precio regular</th>
                                    <th>Precio de oferta</th>
                                    <th>Fecha limite</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${cuponesVencidos}" var="cupon">
                                    <tr>
                                        <td>${cupon.couponCode}</td>
                                        <td>${cupon.promotion.title}</td>
                                        <td>${cupon.promotion.regularPrice}$</td>
                                        <td>${cupon.promotion.ofertPrice}$</td>
                                        <td>${cupon.promotion.limitDate}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                      </div>
                    </li>
                </ul>
                
            </div>
        </main>
        
        <script>
            $('.collapsible').collapsible();
        </script>
    </body>
</html>
