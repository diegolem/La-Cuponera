<%-- 
    Document   : listPromotions
    Created on : 08-11-2018, 11:56:54 AM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de Ofertas</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <c:set var = "title" scope = "page" value = "Lista de Ofertas"/>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/company/promotion.do?op=new" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Agregar Oferta</a>
                <br>
                <br>
                <div id="selectFiltered" class="center col s6 offset-s3">

                </div>
                <table class="centered responsive-table" id="tblPromotions">
                    <thead>
                        <tr>
                            <th>Título</th>
                            <th>Precio Regular</th>
                            <th>Precio Oferta</th>
                            <th>Ganancias</th>
                            <th>Estado</th>
                            <th>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.promotionsList}" var="promotion">
                            <tr>
                                <td>${promotion.title}</td>
                                <td>$${promotion.regularPrice}</td>
                                <td>$${promotion.ofertPrice}</td>
                                <td>$${promotion.earnings}</td>
                                <td>${promotion.promotionState.state}</td>
                                <td>
                                    <a title="Detalles" href="${pageContext.request.contextPath}/company/promotion.do?op=details&idPromotion=${promotion.idPromotion}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                    <c:if test = "${promotion.promotionState.state eq 'Rechazada'}">
                                        
                                    </c:if>
                                    <c:choose>
                                        <c:when test="${promotion.promotionState.state eq 'Rechazada'}">
                                            <a title="Editar" href="${pageContext.request.contextPath}/company/promotion.do?op=edit&idPromotion=${promotion.idPromotion}" class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                            <a title="Eliminar" href="#" onclick="deletePromotion('${promotion.idPromotion}')" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="Editar" disabled class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                            <a title="Eliminar" disabled href="#" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                $("#tblPromotions").DataTable({
                    initComplete: function () {
                        let i = 0;
                        this.api().columns().every(function () {
                            var column = this;
                            if (i === 4) {

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
            });
        </script>
    </body>
</html>
