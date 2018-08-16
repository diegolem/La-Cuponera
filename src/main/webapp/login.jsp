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

                            <a class="btn btn-link" href="">
                                Recuperar contraseña
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
