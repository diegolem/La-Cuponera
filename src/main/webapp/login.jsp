<%-- 
    Document   : login
    Created on : 08-10-2018, 09:15:59 PM
    Author     : leonardo
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login [Cuponera]</title>
        <jsp:include page="cabecera.jsp"/>
        <link href="${pageContext.request.contextPath}/assets/css/login.css" rel="stylesheet">
    </head>
    <body>
        <div class="">
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
        </div>
        <br>
        <div class="container">
            <c:if test="${not empty requestScope.errorConfirmation}">
                <div class="alert lighten-2 white-text red darken-4 center">
                    ${requestScope.errorConfirmation}
                </div>
            </c:if>
            <c:if test="${not empty sessionScope.error}">
                <div class="alert lighten-2 white-text red darken-4 center">
                    ${sessionScope.error}
                </div>
                <c:set var = "error" scope = "session" value = ""/>
            </c:if>
            <c:if test="${not empty requestScope.invalid}">
                <div class="alert lighten-2 white-text red darken-4 center">
                    ${requestScope.invalid}
                </div>
            </c:if>

            <div class="row">
                <form class="col s12" method="POST" action="login.do">
                    <input type="hidden" class="form-control" name="op" value="login">
                    <div class="row">
                        <div class="input-field col s12">
                            <label for="email" class="col-md-4 control-label">Correo Electrónico</label>
                            <input id="email" type="email" class="form-control" name="email" value="${email}" required autofocus>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['email']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['email']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <label for="password" class="col-md-4 control-label">Password</label>
                            <input id="password" type="password" class="form-control" name="password" required value="${password}">
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['password']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['password']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 col-md-offset-4">
                            <button type="submit" class="btn btn-primary">
                                Iniciar sesión
                            </button>

                            <a class="btn btn-link" href="">
                                Recuperar contraseña
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                $.validator.setDefaults({
                    errorClass: 'invalid',
                    validClass: 'none',
                    errorPlacement: function (error, element) {
                        $(element).parent().find('span.error-block.red-text').remove();
                        $(element).parent().find('span.helper-text').remove();
                        $(element).parent().append("<span class='helper-text' data-error='" + error.text() + "'></span>");
                    }
                });

                $.validator.addMethod('validEmail', function (value, element) {
                    return this.optional(element) || /^(([^<>()\[\]\.,;:\s@\"]+(\.[^<>()\[\]\.,;:\s@\"]+)*)|(\".+\"))@(([^<>()[\]\.,;:\s@\"]+\.)+[^<>()[\]\.,;:\s@\"]{2,})$/i.test(value);
                }, 'Ingrese un email válido.');

                $.validator.addMethod('validPassword', function (value, element) {
                    return this.optional(element) || /^[0-9]{1}[0-9]{7}[-]{1}[0-9]{1}$/i.test(value);
                }, 'Ingrese un dui válido.');

                $('#frmRegistClient').validate({
                    rules: {
                        email: {
                            required: true,
                            validEmail: true
                        },
                        password: {
                            required: true
                        }
                    },
                    messages: {
                        email: {
                            required: 'El campo email es requerido'
                        },
                        password: {
                            required: 'El campo contraseña es requerido'
                        }
                    },
                    submitHandler: function (form) {
                        form.submit();
                    }
                });
            });
        </script>
    </body>
</html>
