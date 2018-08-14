<%-- 
    Document   : menuCompany
    Created on : 08-11-2018, 11:57:21 AM
    Author     : leonardo
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <form id="logout-form" action="#" method="POST" style="display: none;"></form>

    <nav class="purple darken-4">
        <div class="container">
            <a href="#" data-target="user_nav" class="sidenav-trigger "><i class="material-icons">menu</i></a>
            <div class="nav-wrapper"><a class="brand-logo center"><c:out value="${title}" default="Empresa" /></a></div>
        </div>
    </nav>
    <c:set var = "user" scope = "session" value = "${user}"/>
    <ul id="user_nav" class="sidenav sidenav-fixed">
        <li>
            <div class="user-view">
                <div class="background purple darken-4">
                </div>
                <a>
                    <span style="font-weight: bold;" class="white-text email"><b>Empresa: </b>${user.name}</span>
                </a>
                <a>
                    <span class="white-text email">${user.email}</span>
                </a>
            </div>
        </li>
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/company/index.jsp">
                <i class="material-icons">home</i>Inicio</a>
        </li>
        <li class="no-padding">
            <ul class="collapsible collapsible-accordion">
                <li>
                    <a class="collapsible-header"><i class="material-icons">person</i> Empleados</a>
                    <div class="collapsible-body">
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/company/employee.do?op=list">Mostrar <i class="material-icons left">remove_red_eye</i></a></li>
                            <li><a href="${pageContext.request.contextPath}/company/employee.do?op=new">Registrar <i class="material-icons left">add</i></a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </li>
        <li class="no-padding">
            <ul class="collapsible collapsible-accordion">
                <li>
                    <a class="collapsible-header"><i class="material-icons">add_shopping_cart</i> Ofertas</a>
                    <div class="collapsible-body">
                        <ul>
                            <li><a href="${pageContext.request.contextPath}/company/promotion.do?op=list">Mostrar <i class="material-icons left">remove_red_eye</i></a></li>
                            <li><a href="${pageContext.request.contextPath}/company/promotion.do?op=new">Registrar <i class="material-icons left">add</i></a></li>
                        </ul>
                    </div>
                </li>
            </ul>
        </li>
        
        <li>
            <div class="divider"></div>
        </li>
        <li class="nav-item"><a href="#"><i class="material-icons">settings</i>Mi cuenta</a></li> 
        <li  class="nav-item">
            <a onclick="javascript:void(0)" href="login.do?op=logout" title="Cerrar Sesión"><i class="material-icons">exit_to_app</i>Cerrar Sesión</a>
        </li>
    </ul>
</header>
