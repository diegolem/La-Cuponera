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
        <title>Lista de empreados</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuCompany.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/company/employee.do?op=new" class="waves-effect waves-light btn-large"><i class="material-icons left centered">add</i>Agregar Empleado</a>
                <table class="centered responsive-table" id="tblEmployees">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Nombre completo</th>
                            <th>E-Mail</th>
                            <th>Compañia</th>
                            <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.employeeList}" var="employee">
                            <tr id="tr_${employee.idEmployee}">
                                <td>${employee.idEmployee}</td>
                                <td>${employee.name} ${employee.lastName}</td>
                                <td>${employee.email}</td>
                                <td>${employee.company.name}</td>
                                <td>
                                    <a title="Detalles" href="${pageContext.request.contextPath}/company/employee.do?op=details&idEmployee=${employee.idEmployee}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                    <a title="Editar" href="${pageContext.request.contextPath}/company/employee.do?op=edit&idEmployee=${employee.idEmployee}" class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                    <a id="a_${employee.idEmployee}" title="Eliminar" href="#" onclick="deleteEmploye(${employee.idEmployee}, 'tr_${employee.idEmployee}', 'a_${employee.idEmployee}');" class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </main>
        <script>
            $(document).ready(function () {
                $("#tblEmployees").DataTable();
                <c:if test="${not empty success}">
                    M.toast({html: '${success}'})
                    <c:set var="success" value="" scope="session"></c:set>
                </c:if>
                <c:if test="${not empty error}">
                    M.toast({html: '${error}'})
                    <c:set var="error" value="" scope="session"></c:set>
                </c:if>
                
            });
            function deleteEmploye(id, tr, a){
                alertify.confirm('Desea eliminar el empleado?', function(e){
                    if (e) {
                        $.ajax({
                            url: "${pageContext.request.contextPath}/company/employee.do?op=delete&idEmployee=" + id,
                            type: "POST",
                            success: function(response){
                                if(response === "0"){
                                    M.toast({html: 'Ha ocurrido un error en el proceso de eliminación'});
                                }else if(response === "1"){
                                    $( "#"+a ).remove();
                                    M.toast({html: 'Eliminación exitosa', completeCallback: function(){ location.href = 'employee.do?op=list' }});
                                }
                            }
                        });
                    }
                });
            }
        </script>
    </body>
</html>
