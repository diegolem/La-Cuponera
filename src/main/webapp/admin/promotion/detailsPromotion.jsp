<%-- 
    Document   : detailsPromotion
    Created on : 08-13-2018, 07:51:58 PM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Detalles de Oferta</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/promotion.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de ofertas</a>
                <br>
                <br>
                <div style="display: flex; justify-content: space-evenly; width: 70%; margin: auto">
                    <a href="#mdlSales"  title="Compradores" class="modal-trigger btnSales waves-effect waves-light btn btn-large"><i class="large material-icons">contacts</i></a>
                    <c:choose>
                        <c:when test="${promotion.promotionState.state eq 'Rechazada'}">
                            <a title="Aceptar Oferta" disabled  href="#" class="waves-effect waves-light btn-large"><i class="material-icons centered">check</i></a>
                            <a title="Rechazar Oferta" disabled href="#"  class="waves-effect waves-light btn-large"><i class="material-icons centered">clear</i></a>
                        </c:when>
                        <c:when test="${promotion.promotionState.state eq 'En espera de aprobación'}">
                            <a title="Aceptar Oferta" onclick="acceptPromotion('${promotion.idPromotion}')" class="waves-effect waves-light btn-large"><i class="material-icons centered">check</i></a>
                            <a title="Rechazar Oferta" onclick="setIdRejected('${promotion.idPromotion}')" href="#mdlRejected" class="modal-trigger waves-effect waves-light btn-large"><i class="material-icons centered">clear</i></a>
                        </c:when>
                        <c:otherwise>
                            <a title="Aceptar Oferta" disabled href="#" class="waves-effect waves-light btn-large"><i class="material-icons centered">check</i></a>
                            <a title="Rechazar Oferta" disabled href="#" class="waves-effect waves-light btn-large"><i class="material-icons centered">clear</i></a>
                        </c:otherwise>
                    </c:choose>
                </div>
                <div style="display: flex; justify-content: center; font-size: 18px;">
                    <ul>
                        <li><b>Título: </b> ${promotion.title}</li>
                        <li><b>Precio regular: </b> $${promotion.regularPrice}</li>
                        <li><b>Precio oferta: </b> $${promotion.ofertPrice}</li>
                        <li><b>Fecha de inicio: </b> ${promotion.initDate}</li>
                        <li><b>Fecha final: </b> ${promotion.endDate}</li>
                        <li><b>Fecha limite: </b> ${promotion.limitDate}</li>
                        <li><b>Cantidad limite: </b>
                            <c:choose>
                                <c:when test = "${promotion.limitCant eq 0}">
                                    No aplica
                                </c:when>
                                <c:otherwise>
                                    ${promotion.limitCant}
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li><b>Cupones vendidos: </b>${promotion.couponsSold}</li>
                        <li><b>Cantidad disponibles: </b>
                            <c:choose>
                                <c:when test = "${promotion.limitCant eq 0}">
                                    No aplica
                                </c:when>
                                <c:otherwise>
                                    ${promotion.couponsAvailable}
                                </c:otherwise>
                            </c:choose>
                        </li>
                        <li><b>Ganancias: </b>$${promotion.earnings}</li>
                        <li><b>Total de cargos de servicio: </b>$${promotion.chargeService}</li>
                        <li><b>Estado: </b>${promotion.promotionState.state}</li>
                        <li><b>Empresa: </b>${promotion.company.name}</li>
                    </ul>
                </div>
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
            <div id="mdlSales" class="modal bottom-sheet">
                <div class="modal-content">
                    <h3 class="center deep-purple-text text-darken-4">Compradores</h3>
                    <c:choose>
                        <c:when test="${fn:length(promotion.sales) gt 0}">
                            <table class="centered responsive-table" id="tblSales">
                                <thead>
                                    <tr> 
                                        <th>Código</th>
                                        <th>Cliente</th>
                                        <th>Email</th>
                                        <th>DUI</th>
                                        <th>NIT</th>
                                        <th>Estado</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${promotion.sales}" var="sale">
                                        <tr>
                                            <td>${sale.couponCode}</td>
                                            <td>${sale.client.name} ${sale.client.lastName}</td>
                                            <td>${sale.client.email}</td>
                                            <td>${sale.client.dui}</td>
                                            <td>${sale.client.nit}</td>
                                            <td>${sale.state.state}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </c:when>
                        <c:otherwise>
                            No hay compradores
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
                $('.modal').modal();

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
