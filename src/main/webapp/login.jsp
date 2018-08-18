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
        <!--<script src="${pageContext.request.contextPath}/js/login.js" type="text/javascript"></script>-->
    </head>
    <body>
        <div class="container">
            <li>${errorConfirmation}</li>
            <c:if test="${not empty error}">
                
                <div class="row">
                    <div class="col s12">
                        <div class="card-panel red darken-1">
                            <ul class="white-text">
                                <li>${error}</li>
                            </ul>
                        </div>
                    </div>
                </div>
                
            </c:if>

            <div class="row">

                <form class="col s12" method="POST" action="login.do">
                    <input type="hidden" class="form-control" name="op" value="login">
                    <div class="row">
                        <div class="input-field col s12">
                            <label for="email" class="col-md-4 control-label">Correo Electrónico</label>
                            <input id="email" type="email" class="form-control" name="email" value="" required autofocus>
                        </div>
                    </div>
                    <div class="row">
                        <div class="input-field col s12">
                            <label for="password" class="col-md-4 control-label">Password</label>

                            <input id="password" type="password" class="form-control" name="password" required>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-8 col-md-offset-4">
                            <button type="submit" class="btn btn-primary">
                                Iniciar sesión
                            </button>

                            <a class="btn modal-trigger" href="#mdlRecover">
                                Recuperar contraseña
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>

        <div id="mdlRecover" class="modal">
            <div class="modal-content">
                <h4>Modal Header</h4>
                <p>A bunch of text</p>
                <form name="frmRecover" class="frmAjax row">
                    <div class="input-field col s12">
                        <label for="recover_email" class="col-md-4 control-label">Correo Electrónico</label>
                        <input id="recover_email" type="email" class="form-control" name="recover_email">
                    </div>
                    <div class="col s12 btn-cont">
                        <button id="btnRecover" class="btn waves-effect">Enviar peticion<i class="material-icons right">send</i></button>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="#!" class="modal-close waves-effect waves-green btn-flat">Agree</a>
            </div>
        </div>
                                
    </body>
    
    <script>
        $(frmRecover).submit(function () {
            console.log('hola');
            $.ajax({
                url: `${pageContext.request.contextPath}/password.do`,
                type: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: {
                    email: frmRecover.recover_email.value.trim()
                },
                success: function (r) {
                    console.log(r);
                }
            });
        });
        
    </script>
</html>
