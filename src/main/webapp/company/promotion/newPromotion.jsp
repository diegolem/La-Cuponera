<%-- 
    Document   : newPromotion
    Created on : 08-11-2018, 11:44:10 AM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Oferta</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/company/promotion.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de ofertas</a>
                <br>
                <br>
                <form enctype="multipart/form-data" class="col s12" id="frmRegisterPromotion" action="${pageContext.request.contextPath}/company/promotion.do" method="POST">
                    <input type="hidden" name="op" value="insert"/>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="title" type="text" name="title" value="${promotion.title}">
                            <label for="title">Título</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['title']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['title']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="regularPrice" type="number" min="0" step="0.01" name="regularPrice" value="${promotion.regularPrice}">
                            <label for="regularPrice">Precio regular</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['regularPrice']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['regularPrice']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="ofertPrice" type="number" min="0" step="0.01" name="ofertPrice" value="${promotion.ofertPrice}">
                            <label for="ofertPrice">Precio oferta</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['ofertPrice']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['ofertPrice']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input type="date" name="initDate" id="initDate" value="${promotion.initDate}">
                            <label for="initDate">Fecha de Inicio</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['initDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['initDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input type="date" name="endDate" id="endDate" value="${promotion.endDate}">
                            <label for="initDate">Fecha Final</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['endDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['endDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input type="date" name="limitDate" id="limitDate" value="${promotion.limitDate}">
                            <label for="limitDate">Fecha Límite</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['limitDate']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['limitDate']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="limitCant" type="number" min="0" name="limitCant" value="${promotion.limitCant}">
                            <label for="limitCant">Cantidad límite</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['limitCant']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['limitCant']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6" style="display: flex;">
                            <p>
                                <label>
                                    <input name="groupLimitCant" type="radio" checked value="false" />
                                    <span>Ingresar Limite</span>
                                </label>
                            </p>
                            <p>
                                <label>
                                    <input name="groupLimitCant" type="radio" value="true"/>
                                    <span>Cupones ilimitados</span>
                                </label>
                            </p>
                        </div>
                    </div>
                    <div class="input-field col s12">
                        <p>Descripción</p>
                        <div id="editor">
                            ${promotion.description}
                        </div>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['description']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['description']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="input-field col s12" style="display: none;">
                        <textarea id="description" name="description" class="materialize-textarea">${promotion.description}</textarea>
                    </div>
                    <div class="input-field col s12">
                        <textarea id="otherDetails" name="otherDetails" class="materialize-textarea">${promotion.otherDetails}</textarea>
                        <label for="otherDetails">Detalles</label>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['otherDetails']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['otherDetails']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="input-field col s12">
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Imagen</span>
                                <input type="file" name="img">
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path" type="text">
                            </div>
                        </div>
                        <c:if test="${not empty requestScope.errorsList}">
                            <c:if test = "${not empty requestScope.errorsList['img']}">
                                <span class="error-block red-text">
                                    <strong>${requestScope.errorsList['img']}</strong>
                                </span>
                            </c:if>
                        </c:if>
                    </div>
                    <div class="row center">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Registrar
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </form>
            </div>
        </main>
        <script>
            $.validator.setDefaults({
                errorClass: 'invalid',
                validClass: 'none',
                errorPlacement: function (error, element) {
                    $(element).parent().find('span.error-block.red-text').remove();
                    $(element).parent().find('span.helper-text').remove();
                    $(element).parent().append("<span class='helper-text' data-error='" + error.text() + "'></span>");
                }
            });

            $.validator.addMethod('validInitDate', function (value, element) {
                //console.log(value);
                let initDate = new Date(value), now = new Date();
                now.setHours(0, 0, 0, 0);
                initDate.setHours(0, 0, 0, 0);
                //console.log(initDate.getDay());
                return this.optional(element) || (initDate.getTime() >= now.getTime());
            }, 'Fecha Inicial debe ser mayor a la actual');

            $.validator.addMethod('validEndDate', function (value, element) {
                let endDate = new Date(value), initDate = new Date($('#initDate').val());
                endDate.setHours(0, 0, 0, 0);
                initDate.setHours(0, 0, 0, 0);
                return this.optional(element) || (endDate.getTime() >= initDate.getTime());
            }, 'Fecha final debe ser mayor a la inicial');

            $.validator.addMethod('validLimitDate', function (value, element) {
                let limitDate = new Date(value), endDate = new Date($('#endDate').val());
                limitDate.setHours(0, 0, 0, 0);
                endDate.setHours(0, 0, 0, 0);
                return this.optional(element) || (limitDate.getTime() >= endDate.getTime());
            }, 'Fecha limite debe ser mayor a la final');

            $.validator.setDefaults({ignore: ".ql-editor"});

            $('#frmRegisterPromotion').validate({
                rules: {
                    title: {
                        required: true
                    },
                    regularPrice: {
                        required: true
                    },
                    ofertPrice: {
                        required: true
                    },
                    initDate: {
                        required: true,
                        validInitDate: true
                    },
                    endDate: {
                        required: true,
                        validEndDate: true
                    },
                    limitDate: {
                        required: true,
                        validLimitDate: true
                    },
                    limitCant: {
                        required: true
                    },
                    description: {
                        required: true
                    },
                    otherDetails: {
                        required: true
                    },
                    img: {
                        required: true
                    }
                },
                messages: {
                    title: {
                        required: 'El campo título es requerido'
                    },
                    regularPrice: {
                        required: 'El campo precio regular es requerido'
                    },
                    ofertPrice: {
                        required: 'El campo precio oferta es requerido'
                    },
                    initDate: {
                        required: 'El campo fecha inicial es requerido'
                    },
                    endDate: {
                        required: 'El campo fecha de finalización es requerido'
                    },
                    limitDate: {
                        required: 'El campo fecha limite es requerido'
                    },
                    limitCant: {
                        required: 'El campo cantidad limite es requerido (minímo 0)'
                    },
                    description: {
                        required: 'El campo descripción es requerido'
                    },
                    otherDetails: {
                        required: 'El campo otros detalles es requerido'
                    },
                    img: {
                        required: 'El campo img es requerido',
                        extension: "jpg|png|jpeg|gif"
                    }
                },
                submitHandler: function (form) {
                    if ($('input[name=groupLimitCant]:checked').val() === 'true') {
                        $('#limitCant').val(0);
                    }
                    form.submit();
                }
            });
            $(document).ready(function () {
                var toolbarOptions = [
                    ['italic', 'underline', 'strike'], // toggled buttons
                    ['blockquote'],
                    [{'header': 1}, {'header': 2}], // custom button values
                    [{'list': 'ordered'}, {'list': 'bullet'}],
                    [{'header': [1, 2, 3, 4, 5, 6, false]}],
                    [{'font': []}],
                    [{'align': []}],
                    ['clean']
                ];
                var quill = new Quill('#editor', {
                    theme: 'snow',
                    modules: {
                        toolbar: toolbarOptions
                    },
                });

                quill.on('text-change', function (delta, oldDelta, source) {
                    $('#description').val(quill.container.firstChild.innerHTML);
                });

                $('input[name=groupLimitCant]').change(function () {
                    let value = (($(this).val() === 'true') ? true : false);
                    $('#limitCant').attr('readonly', value);

                    if (value) {
                        $('#limitCant').val(0);
                    }
                });
            });
        </script>
    </body>
</html>
