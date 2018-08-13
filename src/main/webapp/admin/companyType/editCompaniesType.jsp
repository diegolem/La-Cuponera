<%-- 
    Document   : editCompany
    Created on : 08-07-2018, 06:56:03 PM
    Author     : leonardo
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Modificar Rubro</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/companiesType.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de rubros</a>
                <br>
                <br>
                <form class="col s12" id="frmUpdateCompaniesType" action="${pageContext.request.contextPath}/admin/companiesType.do" method="POST">
                    <input type="hidden" name="op" value="update"/>
                    <div class="row">
                        <div class="input-field col s6">
                            <input readonly="true" id="idCompanyType" type="text" name="idCompanyType" value="${companiesType.idCompanyType}">
                            <label for="idCompanyType">Código del rubro</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['idCompanyType']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['idCompanyType']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                        <div class="input-field col s6">
                            <input id="name" type="text" name="name" value="${companiesType.type}">
                            <label for="name">Nombre del rubro</label>
                            <c:if test="${not empty requestScope.errorsList}">
                                <c:if test = "${not empty requestScope.errorsList['name']}">
                                    <span class="error-block red-text">
                                        <strong>${requestScope.errorsList['name']}</strong>
                                    </span>
                                </c:if>
                            </c:if>
                        </div>
                    </div>
                    <div class="row center">
                        <button class="btn waves-effect waves-light" type="submit" name="action">Modificar
                            <i class="material-icons right">send</i>
                        </button>
                    </div>
                </form>
            </div>
        </main>
        <script>
            $.validator.setDefaults({
                errorClass: 'invalid',
                validClass: 'none',
                errorPlacement: function (error, element) {
                    $(element).parent().find('span.error-block.red-text').remove();
                    $(element).parent().find('span.helper-text').remove();
                    $(element).parent().append("<span class='helper-text' data-error='" + error.text() + "'></span>");
                }
            });
            
            $.validator.addMethod('validName',function(value,element){
                return this.optional(element) || /^([a-z]|[A-Z]|[ñÑ]){1}[a-zA-Z ñÑáéíóú]*$/.test(value);
            }, 'Ingrese un nombre de rubro valido.');
            
            $.validator.addMethod('validRubro',function(value,element){
                return this.optional(element) || /^[0-9]*$/.test(value);
            }, 'Ingrese un codigo de rubro valido.');
            
            $('#frmUpdateCompaniesType').validate({
                rules: {
                    name: {
                        required: true,
                        validName: true
                    },
                    idCompanyType:{
                        required: true,
                        validRubro: true
                    }
                },
                messages: {
                     name: {
                        required: 'El campo nombre del rubro es requerido'
                    },
                    idCompanyType:{
                        required: 'El campo de codigo no puede estar vacio'
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
        </script>
    </body>
</html>
