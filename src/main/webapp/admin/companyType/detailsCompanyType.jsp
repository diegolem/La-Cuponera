<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de rubros</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/companiesType.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de rubros</a>
                <div class="card">
                    <div class="card-content center">
                        <p>${companyType.type}</p>
                    </div>
                    <div class="card-tabs">
                        <ul class="tabs tabs-fixed-width">
                            <li class="tab"><a class="active" href="#test4">Datos Generales</a></li>
                            <li class="tab"><a  href="#test5">Compañias</a></li>
                        </ul>
                    </div>
                    <div class="card-content grey lighten-4">
                        <div id="test4"> <!-- Datos generales -->
                            <ul class="collection">
                                <li class="collection-item">Código: ${companyType.idCompanyType}</li>
                                <li class="collection-item">Nombre del rubro ${companyType.type}</li>
                            </ul>
                        </div> <!-- Fin datos generales -->
                        <div id="test5"> <!-- Compañias -->
                            <table class="centered responsive-table" id="tblCompanies">
                                <thead>
                                    <tr>
                                        <th>Nombre</th>
                                        <th>Direccion</th>
                                        <th>Nombre del Contacto</th>
                                        <th>Telefono</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${companyType.companies}" var="companies">
                                        <tr>
                                            <td>${companies.name}</td>
                                            <td>${companies.address}</td>
                                            <td>${companies.contactName}</td>
                                            <td>${companies.telephone}</td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div><!-- Fin compañias -->
                    </div>
                </div>
            </div>
        </main>
        <script>
            $("#tblCompanies").DataTable();
        </script>               
    </body>
</html>
