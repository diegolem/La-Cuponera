<%-- 
    Document   : register
    Created on : 08-16-2018, 08:45:20 PM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <jsp:include page="cabecera.jsp"/>
        <title>Registro de Clientes</title>
    </head>
    <body>
        <div class="">
            <nav class="teal z-depth-0" id="nav">
                <div class="nav-wrapper">
                    <a href="#" class="brand-logo">
                        &nbsp;La Cuponera
                    </a>
                    <ul class="right hide-on-med-and-down" id="opc">
                        <li><a href="${pageContext.request.contextPath}/sales.do?op=public">Cupones</a></li>
                        <li><a href="login.jsp">Iniciar Sesión</a></li>
                        <li><a href="register.jsp">Registrarme</a></li>
                    </ul>
                </div>
            </nav>
        </div>
        <br>
        <div class="container">
            <div class="row">
                <form class="col s12" id="frmRegistClient" action="${pageContext.request.contextPath}/user.do" method="POST">
                    <input type="hidden" name="op" value="insert_client_public"/>
                    <div class="row">
                        <div class="input-field col l6 s12">
                            <input id="name" type="text" name="name" value="${client.name}" autofocus>
                            <label for="name">Nombre</label>
                        </div>

                        <div class="input-field col l6 s12">
                            <input id="last_name" type="text" name="last_name" value="${client.lastName}">
                            <label for="last_name">Apellido</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col l6 s12">
                            <input id="dui" type="text" name="dui" value="${client.dui}">
                            <label for="dui">DUI</label>
                        </div>
                        <div class="input-field col l6 s12">
                            <input id="nit" type="text" name="nit" value="${client.nit}">
                            <label for="nit">NIT</label>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col l12 s12">
                            <input id="email" type="text" name="email" value="${client.email}">
                            <label for="email">E-Mail</label>
                        </div>
                    </div>
                    <div class="row center">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Registrar
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function () {
                let loader = new Loader();
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
                        var formData = $(form).serializeArray();
                        loader.in();
                        $.ajax({
                            url: "${pageContext.request.contextPath}/user.do",
                            data: formData,
                            type: 'POST',
                            success: function (response) {
                                console.log(response);
                                if (parseInt(response) === 0) {
                                    M.toast({html: 'Algunos datos ingresados pueden ser incorrectos'});
                                } else if (parseInt(response) === 1) {
                                    M.toast({html: 'Se ha registrado al usuario. Revisar correo', completeCallback: function () {
                                            $(form)[0].reset();
                                        }});
                                } else if (parseInt(response) === -1) {
                                    M.toast({html: 'Correo electrónico no existe'});
                                } else if (parseInt(response) === -2) {
                                    M.toast({html: 'Ha ocurrido un error'});
                                }
                            },
                            error: function (err) {
                                M.toast({html: "En este momento no se puede establecer la conexión con el servidor. Inténtelo más tarde... <i class='material-icons right'>error</i>", classes: "red darken-5"});
                            }
                        }).done(function(){
                            loader.out();
                        });
                        return false;
                    }
                });
            });
        </script>
    </body>
</html>
