<%-- 
    Document   : index
    Created on : 08-10-2018, 10:29:54 PM
    Author     : leonardo
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:out value = "${sessionScope.logged}"/>
        <a href="login.do?op=logout">Cerrar sesión</a>
        <h1>Administrador</h1>
    </body>
</html>
