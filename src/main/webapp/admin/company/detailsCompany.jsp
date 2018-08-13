<%-- 
    Document   : detailsCompany
    Created on : 08-09-2018, 02:27:28 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de empresas</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/company.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de empresas</a>
                <div class="card">
                    <div class="card-content center">
                        <p>${company.name}</p>
                    </div>
                    <div class="card-tabs">
                        <ul class="tabs tabs-fixed-width">
                            <li class="tab"><a class="active" href="#test4">Datos Generales</a></li>
                            <li class="tab"><a  href="#test5">Empleados</a></li>
                            <li class="tab"><a href="#test6">Promociones</a></li>
                        </ul>
                    </div>
                    <div class="card-content grey lighten-4">
                        <div id="test4"> <!-- Datos generales -->
                            <ul class="collection">
                                <li class="collection-item">Código: ${company.idCompany}</li>
                                <li class="collection-item">Dirección: ${company.address}</li>
                                <li class="collection-item">Nombre de contacto: ${company.contactName}</li>
                                <li class="collection-item">Teléfono: ${company.telephone}</li>
                                <li class="collection-item">Email: ${company.email}</li>
                                <li class="collection-item">Rubro: ${company.companyType.type}</li>
                                <li class="collection-item">Porcentaje de comisión: ${company.pctComission}%</li>
                            </ul>
                        </div> <!-- Fin datos generales -->
                        <div id="test5"> <!-- Empleados -->
                            <table class="centered responsive-table" id="tblEmployees">
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Apellido</th>
                                        <th>Email</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${company.employees}" var="employee">
                                        <tr>
                                            <td>${employee.name}</td>
                                            <td>${employee.lastName}</td>
                                            <td>${employee.email}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div><!-- Fin empleados -->
                        <div id="test6"> <!-- Promociones -->
                            <table class="centered responsive-table" id="tblPromotions">
                                <thead>
                                    <tr>
                                        <th>Título</th>
                                        <th>Precio regular</th>
                                        <th>Precio oferta</th>
                                        <th>Fecha de inicio</th>
                                        <th>Fecha final</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${company.promotion}" var="promotion">
                                        <tr>
                                            <td>${promotion.title}</td>
                                            <td>$${promotion.regularPrice}</td>
                                            <td>$${promotion.ofertPrice}</td>
                                            <td>${promotion.initDate}</td>
                                            <td>${promotion.endDate}</td>
                                            <td>${promotion.promotionState.state}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div> <!-- Fin promociones -->
                    </div>
                </div>
            </div>
        </main>
        <script>
            $("#tblEmployees").DataTable();
            $("#tblPromotions").DataTable();
        </script>               
    </body>
</html>
