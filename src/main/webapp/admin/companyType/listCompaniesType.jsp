<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
                <a href="${pageContext.request.contextPath}/admin/companiesType.do?op=new" class="waves-effect waves-light btn-large"><i class="material-icons left centered">add</i>Agregar Rubro</a>
                <table class="centered responsive-table" id="tblCompaniesType">
                    <thead>
                        <tr>
                            <th>Codigo</th>
                            <th>Rubro</th>
                            <th>Opciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.typesCompany}" var="companytype">
                            <tr>
                                <td>${companytype.idCompanyType}</td>
                                <td>${companytype.type}</td>
                                <td>
                                    <a title="Detalles" href="${pageContext.request.contextPath}/admin/companiesType.do?op=details&idCompanyType=${companytype.idCompanyType}" class="waves-effect waves-light btn-small"><i class="material-icons centered">line_weight</i></a>
                                    <a title="Editar" href="${pageContext.request.contextPath}/admin/companiesType.do?op=edit&idCompanyType=${companytype.idCompanyType}" class="waves-effect waves-light btn-small"><i class="material-icons centered">edit</i></a>
                                    <c:choose>
                                        <c:when test="${(fn:length(companytype.companies) gt 0)}">
                                            <a title="Eliminar" disabled class="waves-effect waves-light btn-small"><i class="material-icons centered">delete</i></a>
                                        </c:when>
                                        <c:otherwise>
                                            <a title="Eliminar" href="#modal" onclick="setId('${companytype.idCompanyType}')" class="waves-effect waves-light btn btn-small modal-trigger"><i class="material-icons centered">delete</i></a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div id="modal" class="modal">
                <div class="modal-content">
                    <h4 class="center purple-text text-darken-4">¿Realmente deseas eliminar este rubro?</h4>
                    <input type="hidden" readonly="true" id="idCt"/>
                </div>
                <div class="col s12  btn-cont">
                    <a href="javascript:void(0)" onclick="deleteCompanyType($('#idCt').val())" class="waves-effect waves-light red darken-3 btn"><i class="material-icons left">delete</i>Eliminar</a>
                    <a href="#!" class="modal-close waves-effect waves-light teal darken-1 btn"><i class="material-icons left">close</i>Cancelar</a>
                </div>
                <br>
            </div>
        </main>
        <script>
            let loader = new Loader();
            $(document).ready(function () {
                $("#tblCompaniesType").DataTable();
            <c:if test="${not empty success}">
                M.toast({html: '${success}'})
                <c:set var="success" value="" scope="session"></c:set>
            </c:if>
            <c:if test="${not empty error}">
                M.toast({html: '${error}'})
                <c:set var="error" value="" scope="session"></c:set>
            </c:if>

            });
            function setId(id) {
                //$('#header-modal').text("¿Estas seguro que deseas eliminar esto?");
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/companiesType.do?op=get",
                    type: "POST",
                    data: {
                        idCompanyType: id
                    },
                    success: function (response) {
                        if (response === "0") {
                            M.toast({html: 'No existe un rubro con este codigo'})
                        } else {
                            let json = JSON.parse(response);
                            //$('#test4 #lvl1').text("Codigo: " + json.id);
                            //$('#test4 #lvl2').text("Nombre del rubro: " + json.type);
                            $('#idCt').val(json.id);
                        }
                    }
                });
            }
            function deleteCompanyType(id) {
                loader.in();
                $.ajax({
                    url: "${pageContext.request.contextPath}/admin/companiesType.do?op=delete",
                    type: "POST",
                    data: { idCompanyType: id },
                    success: function (response) {
                        let text = '', classes = '', callback;
                        if (response === "0") {
                            text = 'Ha ocurrido un error en el proceso de eliminación';
                            classes = 'red lighten-1';
                            callback = function(){};
                        } else if (response === "1") {
                            classes = 'green darken-2';
                            text = 'Eliminación exitosa';
                            callback = function(){ location.href = '${pageContext.request.contextPath}/admin/companiesType.do?op=list'; };
                        }
                        M.toast({html: text, classes, displayLength: 1500, completeCallback: callback});
                    }
                }).done(function(){
                    loader.out();
                });
            }
        </script>
    </body>
</html>
