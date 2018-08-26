<%-- 
    Document   : recoverForm
    Created on : Aug 16, 2018, 7:57:54 PM
    Author     : Frank Esquivel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
    <head>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Cuponera - Cambiar Contraseña</title>
            <jsp:include page="/cabecera.jsp"/>
            <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
            <!--<script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript"></script>-->
        </head>
    </head>
    <body>
        <nav class="teal z-depth-0" id="nav">
            <div class="nav-wrapper">
                <a href="#" class="brand-logo">
                    Cuponera
                </a>
                <ul class="right hide-on-med-and-down" id="opc">    
                    <li><a href="login.jsp">Iniciar Sesión</a></li>
                    <li><a href="register.jsp">Registrarme</a></li>
                </ul>
            </div>
        </nav>

        <div class="container">
            <h2 class="teal-text text-darken-2 center">Cambiar contraseña</h2>

            <form action="${pageContext.request.contextPath}/password.do" name="frmReset" class="row" method="POST">
                <div class="input-field col s10 m6 l6 offset-s1 offset-m3 offset-l3">
                    <input type="email" name="email" id="email" value="${requestScope.email}">
                    <label for="email">Correo electronico</label>
                    <c:if test="${not empty requestScope.errors}">
                        <c:if test = "${not empty requestScope.errors['email']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errors['email']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>

                <div class="input-field col s10 m6 l6 offset-s1 offset-m3 offset-l3">
                    <input type="password" name="password" id="password">
                    <label for="password">Nueva contraseña</label>
                    <c:if test="${not empty requestScope.errors}">
                        <c:if test = "${not empty requestScope.errors['password']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errors['password']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>

                <div class="input-field col s10 m6 l6 offset-s1 offset-m3 offset-l3">
                    <input type="password" name="conf_password" id="conf_password">
                    <label for="conf_password">Confirmar contraseña</label>
                    <c:if test="${not empty requestScope.errors}">
                        <c:if test = "${not empty requestScope.errors['conf_password']}">
                            <span class="error-block red-text">
                                <strong>${requestScope.errors['conf_password']}</strong>
                            </span>
                        </c:if>
                    </c:if>
                </div>

                <input type="hidden" name="token" value="${param['token']}">

                <div class="col s12 btn-cont">
                    <button class="btn waves-effect">Guardar contraseña<i class="material-icons right">send</i></button>
                </div>
            </form>
        </div>

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
    
            $.validator.addMethod('email', function (value, element) {
                return this.optional(element) || /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(value);
            }, 'Ingrese un email válido.');

            $.validator.addMethod('passRule', function (value, element) {
                return this.optional(element) || /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/.test(value);
            }, 'Ingrese un email válido.');
    
            $(frmReset).validate({
                rules: {
                    email: {
                        required: true,
                        email: true
                    },
                    password: {
                        required: true,
                        passRule: true
                    },
                    conf_password: {
                        equalTo: "#password"
                    }
                },
                messages: {
                    email: {
                        required: "Debes ingresar un correo!"
                    },
                    password: {
                        required: "Debes ingresar una nueva contraseña!",
                        passRule: "La contraseña debe contener una longitud de minimo 8 caracteres, al menos una letra, un numero y un caracter especial!"
                    },
                    conf_password: {
                        equalTo: "Las contraseñas no coinciden"
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            })
        </script>
    </body>
</html>
