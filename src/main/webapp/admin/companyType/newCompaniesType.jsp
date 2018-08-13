
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Agregar Rubro</title>
        <jsp:include page="../../cabecera.jsp"/>
    </head>
    <body>
        <jsp:include page="../../menus/menuAdmin.jsp"/>
        <main class="">
            <div class="row">
                <a href="${pageContext.request.contextPath}/admin/companiesType.do?op=list" class="waves-effect waves-light btn-large"><i class="material-icons left centered">line_weight</i>Lista de rubros</a>
                <br>
                <br>
                <form class="col s12" id="frmRegisterCompaniesType" action="${pageContext.request.contextPath}/admin/companiesType.do" method="POST">
                    <input type="hidden" name="op" value="insert"/>
                    <div class="row">
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
                        <button class="btn waves-effect waves-light" type="submit" name="action">Registrar
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
                    $(element).parent().append("<span class='helper-text' data-error='"+ error.text() +"'></span>");
                }
            }); 
            $.validator.addMethod('validName',function(value,element){
                return this.optional(element) || /^([a-z]|[A-Z]|[ñÑ]){1}[a-zA-Z ñÑáéíóú]*$/.test(value);
            }, 'Ingrese un nombre de rubro valido.');
            
            $('#frmRegisterCompaniesType').validate({
                rules: {
                    name: {
                        required: true,
                        validName: true
                    }
                },
                messages: {
                    name: {
                        required: 'El campo nombre del rubro es requerido'
                    }
                },
                submitHandler: function (form) {
                    form.submit();
                }
            });
        </script>
    </body>
</html>
