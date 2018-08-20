<%-- 
    Document   : detailsCompany
    Created on : 08-09-2018, 02:27:28 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<c:if test="${empty company}">
    <c:redirect url = "/admin/company.do?op=list"/>
</c:if>
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
                <br>
                <br>
                <div style="display: flex; justify-content: space-evenly; width: 70%; margin: auto">
                    <a href="#mdlEmployees"  title="Empleados" class="modal-trigger btnSales waves-effect waves-light btn btn-large"><i class="large material-icons">contacts</i></a>
                    <a href="#mdlPromotions"  title="Promociones" class="modal-trigger btnSales waves-effect waves-light btn btn-large"><i class="large material-icons">add_shopping_cart</i></a>
                </div>

                <div style="display: flex; justify-content: center; font-size: 18px;">
                    <ul>
                        <li class=""><b>Nombre:</b> ${company.name}</li>
                        <li class=""><b>Código:</b> ${company.idCompany}</li>
                        <li class=""><b>Dirección:</b> ${company.address}</li>
                        <li class=""><b>Nombre de contacto: </b>${company.contactName}</li>
                        <li class=""><b>Teléfono: </b>${company.telephone}</li>
                        <li class=""><b>Email:</b> ${company.email}</li>
                        <li class=""><b>Rubro:</b> ${company.companyType.type}</li>
                        <li class=""><b>Porcentaje de comisión: </b>${company.pctComission}%</li>
                    </ul>
                </div>
            </div>
            <div id="mdlEmployees" class="modal bottom-sheet">
                <div class="modal-content">
                    <h3 class="center deep-purple-text text-darken-4">Empleados</h3>
                    <c:choose>
                        <c:when test="${fn:length(company.employees) gt 0}">
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
                        </c:when>
                        <c:otherwise>
                            No hay empleados
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-close waves-effect waves-red btn-flat">Cerrar</a>
                </div>
            </div>
            <div id="mdlPromotions" class="modal bottom-sheet">
                <div class="modal-content">
                    <h3 class="center deep-purple-text text-darken-4">Promociones</h3>
                    <c:choose>
                        <c:when test="${fn:length(company.promotion) gt 0}">
                            <div id="selectFiltered" class="center col s6 offset-s3">

                            </div>
                            <br>
                            <br>
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
                        </c:when>
                        <c:otherwise>
                            No hay Promociones
                        </c:otherwise>
                    </c:choose>
                </div>
                <div class="modal-footer">
                    <a href="#!" class="modal-close waves-effect waves-red btn-flat">Cerrar</a>
                </div>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                if ($('#tblEmployees').length > 0) {
                    $("#tblEmployees").DataTable();
                }

                if ($('#tblEmployees').length > 0) {
                    $("#tblPromotions").DataTable({
                        "searching": false,
                        initComplete: function () {
                            let i = 0;
                            this.api().columns().every(function () {
                                var column = this;
                                if (i === 5) {

                                    var select = $('<select><option value="" disabled selected>Filtrar por estado</option></select>')
                                            .appendTo($('#selectFiltered').empty())
                                            .on('change', function () {
                                                var val = $.fn.dataTable.util.escapeRegex($(this).val());
                                                column.search(val ? '^' + val + '$' : '', true, false).draw();
                                            })
                                            ;

                                    column.data().unique().sort().each(function (d, j) {
                                        select.append('<option value="' + d + '">' + d + '</option>')
                                    });
                                }
                                i++;
                            });
                        }
                    });
                    $('select').formSelect();
                }
            });
        </script>               
    </body>
</html>
