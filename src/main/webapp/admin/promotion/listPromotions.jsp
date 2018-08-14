<%-- 
    Document   : listPromotion
    Created on : 08-13-2018, 07:51:40 PM
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
        <jsp:include page="../../menus/menuAdmin.jsp"/>
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
                                    <a title="Detalles" href="${pageContext.request.contextPath}/admin/promotion.do?op=details&idPromotion=${promotion.idPromotion}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                    <c:choose>
                                        <c:when test="${promotion.promotionState.state eq 'Rechazada'}">
                                            <a title="Aceptar Oferta" disabled href="#" class="waves-effect waves-light btn-small"><i class="material-icons centered">check</i></a>
                                            <a title="Rechazar Oferta" disabled href="#"  class="waves-effect waves-light btn-small"><i class="material-icons centered">clear</i></a>
                                        </c:when>
                                        <c:when test="${promotion.promotionState.state eq 'En espera de aprobación'}">
                                            <a title="Aceptar Oferta" onclick="acceptPromotion('${promotion.idPromotion}')" class="waves-effect waves-light btn-large"><i class="material-icons centered">check</i></a>
                                            <a title="Rechazar Oferta" onclick="setIdRejected('${promotion.idPromotion}')" href="#mdlRejected" class="modal-trigger waves-effect waves-light btn-large"><i class="material-icons centered">clear</i></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="Aceptar Oferta" disabled href="#" class="waves-effect waves-light btn-small"><i class="material-icons centered">check</i></a>
                                            <a title="Rechazar Oferta" disabled href="#" class="waves-effect waves-light btn-small"><i class="material-icons centered">clear</i></a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
            <div id="mdlRejected" class="modal modal-fixed-footer">
                <div class="modal-content center">
                    <h4 id="header-modal center">Rechazar Oferta</h4>
                    <div class="divider"></div> 
                    <br>
                    <br>
                    <form enctype="multipart/form-data" class="col s12" id="frmRejectedPromotion" method="POST">
                        <input type="hidden" readonly="true" id="idPromotionRejected" name="idPromotionRejected"/>
                        <div class="input-field col s12">
                            <textarea id="rejectedDescription" name="rejectedDescription" class="materialize-textarea"></textarea>
                            <label for="rejectedDescription">Motivo de rechazo</label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="submit" form="frmRejectedPromotion" class="waves-effect waves-light teal darken-1 btn"><i class="material-icons left">send</i>Enviar</button>
                    <a href="#!" class="modal-close waves-effect waves-light red darken-3 btn"><i class="material-icons left">close</i>Cancelar</a>
                </div>
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
                $('#frmRejectedPromotion').validate({
                    rules: {
                        rejectedDescription: {
                            required: true
                        }
                    },
                    messages: {
                        rejectedDescription: {
                            required: 'Motivo de rechazo es requerido'
                        }
                    },
                    submitHandler: function (form) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/admin/promotion.do?op=rejected",
                            type: "GET",
                            data: {
                                idPromotion: $('#frmRejectedPromotion #idPromotionRejected').val(),
                                rejectedDescription: $('#frmRejectedPromotion #rejectedDescription').val()
                            },
                            success: function (response) {
                                if (response === "0") {
                                    M.toast({html: 'Ha ocurrido un error en el proceso de rechazo'})
                                } else if (response === "1") {
                                    M.toast({html: 'Rechazo ingresado exitosamente', completeCallback: function () {
                                            location.href = '${pageContext.request.contextPath}/admin/promotion.do?op=list'
                                        }});
                                }
                            }, error: function (err) {
                                M.toast({html: "En este momento no se puede establecer la conexión con el servidor. Inténtelo más tarde... <i class='material-icons right'>error</i>", classes: "red darken-5"});
                            }
                        });
                        return false;  //This doesn't prevent the form from submitting.
                    }
                });
            });
            function setIdRejected(id) {
                $('#frmRejectedPromotion #idPromotionRejected').val(id);
            }

            function acceptPromotion(id) {
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/promotion.do?op=accept",
                    type: "GET",
                    data: {
                        idPromotion: id
                    },
                    success: function (response) {
                        if (response === "0") {
                            M.toast({html: 'Ha ocurrido un error en el proceso de aceptación'})
                        } else if (response === "1") {
                            M.toast({html: 'Se ha aceptado la oferta exitosamente', completeCallback: function () {
                                    location.href = '${pageContext.request.contextPath}/admin/promotion.do?op=list'
                                }});
                        }
                    }, error: function (err) {
                        M.toast({html: "En este momento no se puede establecer la conexión con el servidor. Inténtelo más tarde... <i class='material-icons right'>error</i>", classes: "red darken-5"});
                    }
                });
            }
        </script>
    </body>
</html>
