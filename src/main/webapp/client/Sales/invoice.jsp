<%-- 
    Document   : index
    Created on : 08-10-2018, 10:29:46 PM
    Author     : leonardo
--%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cliente</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuClient.jsp"/>
        
        <main>
            <a href="${pageContext.request.contextPath}/client/sales.do?op=listC" class="waves-effect waves-light btn-large"><i class="material-icons left centered">local_offer</i>Ver lista de cupones</a>
            <br>
            <br>
            <div id="invoice"></div>
        </main>
    </body>
    <script>
        $(document).ready(function(){
            $.get("${pageContext.request.contextPath}/client/sales.do?op=cuponPdf&type=html&code=${code}", function(data){
                $('#invoice').html(data);
            });
        });
    </script>
</html>
