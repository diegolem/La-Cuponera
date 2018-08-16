<%-- 
    Document   : newCompany
    Created on : 08-07-2018, 06:55:50 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Nuevo cliente</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/user.do?op=list_client" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Listar usurios</a>
                <br>
                <br>
                <form class="col s12" id="frmRegistClient" action="${pageContext.request.contextPath}/admin/user.do" method="POST">
                    <input type="hidden" name="op" value="insert_client"/>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="name" type="text" name="name" value="${client.name}">
                            <label for="name">Nombre</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                            
                        <div class="input-field col s6">
                            <input id="last_name" type="text" name="last_name" value="${client.lastName}">
                            <label for="last_name">Apellido</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['last_name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['last_name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s6">
                            <input id="dui" type="text" name="dui" value="${client.dui}">
                            <label for="dui">DUI</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['dui']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['dui']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="nit" type="text" name="nit" value="${client.nit}">
                            <label for="nit">NIT</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['nit']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['nit']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <input id="email" type="text" name="email" value="${client.email}">
                            <label for="email">E-Mail</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['email']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['email']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
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
                    $(element).parent().append("<span class='helper-text' data-error='"+ error.text() +"'></span>");
                }
            });
            
            $.validator.addMethod('validEmail', function (value, element) {
                return this.optional(element) || /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(value);
            }, 'Ingrese un email válido.');
            
            $.validator.addMethod('validDui', function (value, element) {
                return this.optional(element) || /^[0-9]{1}[0-9]{7}[-]{1}[0-9]{1}$/i.test(value);
            }, 'Ingrese un dui válido.');
            
            $.validator.addMethod('validNit', function (value, element) {
                return this.optional(element) || /^[0-9]{1}[0-9]{3}[-]{1}[0-9]{6}[-]{1}[0-9]{3}[-]{1}[0-9]{1}$/i.test(value);
            }, 'Ingrese un nit válido.');
            
            $('#frmRegistClient').validate({
                rules: {
                    name: {
                        required: true
                    },
                    last_name: {
                        required: true
                    },
                    dui: {
                        required: true,
                        validDui: true
                    },
                    nit: {
                        required: true,
                        validNit: true
                    },
                    email: {
                        required: true,
                        validEmail: true
                    }
                },
                messages: {
                    name: {
                        required: 'El campo nombre es requerido'
                    },
                    last_name: {
                        required: 'El campo apellido es requerido'
                    },
                    dui: {
                        required: 'El campo dui de contacto es requerido'
                    },
                    nit: {
                        required: 'El campo nit es requerido'
                    },
                    email: {
                        required: 'El campo email es requerido'
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
            $(document).ready(function () {
                $('select').formSelect();
            });
        </script>
    </body>
</html>
