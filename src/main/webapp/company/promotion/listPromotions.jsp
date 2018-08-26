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
                                    <c:choose>
                                        <c:when test="${promotion.promotionState.idPromotionState eq 3}">
                                            <a title="Editar" href="${pageContext.request.contextPath}/company/promotion.do?op=edit&idPromotion=${promotion.idPromotion}" class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                            <a title="Eliminar" href="#mdlDelete" onclick="setIdDelete('${promotion.idPromotion}')" class="modal-trigger  waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
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
            <div id="mdlDelete" class="modal">
                <div class="modal-content">
                    <h4 class="center purple-text text-darken-4">¿Realmente deseas eliminar esta oferta?</h4>
                    <input type="hidden" readonly="true" id="idPromotionDelete"/>
                </div>
                <div class="col s12  btn-cont">
                    <button type="button" onclick="deletePromotion()" class="waves-effect waves-light teal darken-1 btn"><i class="material-icons left">send</i>Aceptar</button>
                    <a href="#!" class="modal-close waves-effect waves-light red darken-3 btn"><i class="material-icons left">close</i>Cancelar</a>
                </div>
                <br>
            </div>
        </main>
        <script>
            let loader = new Loader();
            $(document).ready(function () {
            <c:if test="${not empty success}">
                M.toast({html: '${success}'})
                <c:set var="success" value="" scope="session"></c:set>
            </c:if>
            <c:if test="${not empty error}">
                M.toast({html: '${error}'})
                <c:set var="error" value="" scope="session"></c:set>
            </c:if>
                $("#tblPromotions").DataTable({
                    "searching": false,
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

            function setIdDelete(id) {
                $('#mdlDelete #idPromotionDelete').val(id);
            }

            function deletePromotion() {
                if ($('#mdlDelete #idPromotionDelete').val() !== null) {
                    loader.in();
                    $.ajax({
                        url: "${pageContext.request.contextPath}/company/promotion.do?op=delete",
                        type: "GET",
                        data: {
                            idPromotion: $('#mdlDelete #idPromotionDelete').val()
                        },
                        success: function (response) {
                            let text = '', classes = '', callback;
                            if (response === "0") {
                                text = 'Ha ocurrido un error en el proceso de eliminación';
                                classes = 'red lighten-1';
                                callback = function(){};
                            } else if (response === "1") {
                                text = 'Oferta aceptada';
                                classes = 'green darken-2';
                                callback = function(){ location.href = '${pageContext.request.contextPath}/company/promotion.do?op=list'; };
                            }
                            M.toast({html: text, classes, displayLength: 1500, completeCallback: callback});
                        }, error: function (err) {
                            M.toast({html: "En este momento no se puede establecer la conexión con el servidor. Inténtelo más tarde... <i class='material-icons right'>error</i>", classes: "red darken-5"});
                        }
                    }).done(function(){
                        loader.out();
                    });
                }
            }
        </script>
    </body>
</html>
