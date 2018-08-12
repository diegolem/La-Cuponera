<%-- 
    Document   : listCompany
    Created on : 08-07-2018, 06:56:15 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lista de empresas</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <c:set var = "title" scope = "page" value = "Lista de empresas"/>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/company.do?op=new" class="waves-effect waves-light btn-large"><i class="material-icons left centered">add</i>Agregar Empresa</a>
                <table class="centered responsive-table" id="tblCompanies">
                    <thead>
                        <tr>
                            <th>Código</th>
                            <th>Nombre</th>
                            <th>Contacto</th>
                            <th>Telefono</th>
                            <th>Rubro</th>
                            <th>% de comisión</th>
                            <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.companiesList}" var="company">
                            <tr>
                                <td>${company.idCompany}</td>
                                <td>${company.name}</td>
                                <td>${company.contactName}</td>
                                <td>${company.telephone}</td>
                                <td>${company.companyType.type}</td>
                                <td>${company.pctComission}</td>
                                <td>
                                    <a title="Detalles" href="${pageContext.request.contextPath}/admin/company.do?op=details&idCompany=${company.idCompany}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                    <a title="Editar" href="${pageContext.request.contextPath}/admin/company.do?op=edit&idCompany=${company.idCompany}" class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                    <c:choose>
                                        <c:when test="${(fn:length(company.employees) gt 0) || (fn:length(company.promotion) gt 0)}">
                                            <a title="Eliminar" disabled class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="Eliminar" href="#" onclick="deleteCompany('${company.idCompany}')" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                $("#tblCompanies").DataTable();
                <c:if test="${not empty success}">
                    M.toast({html: '${success}'})
                    <c:set var="success" value="" scope="session"></c:set>
                </c:if>
                <c:if test="${not empty error}">
                    M.toast({html: '${error}'})
                    <c:set var="error" value="" scope="session"></c:set>
                </c:if>
                
            });
            function deleteCompany(id){
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/company.do?op=delete",
                    type: "POST",
                    data: {
                        idCompany: id
                    },
                    success: function(response){
                        if(response === "0"){
                            M.toast({html: 'Ha ocurrido un error en el proceso de eliminación'})
                        }else if(response === "1"){
                            M.toast({html: 'Eliminación exitosa', completeCallback: function(){ location.href = 'company.do' }})
                        }
                    }
                })
            }
        </script>
    </body>
</html>
