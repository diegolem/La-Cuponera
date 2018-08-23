<%-- 
    Document   : menuClient
    Created on : 14-ago-2018, 21:12:00
    Author     : Diego Lemus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${sessionScope.userColor == null}">
    <c:set var="userColor" value="blue" scope="session"/>
</c:if>
<header>
    <form id="logout-form" action="#" method="POST" style="display: none;"></form>

    <nav class="light-blue darken-2">
        <div class="container">
            <a href="#" data-target="user_nav" class="sidenav-trigger "><i class="material-icons">menu</i></a>
            <div class="nav-wrapper"><a class="brand-logo center"><c:out value="${title}" default="Cliente" /></a></div>
        </div>
    </nav>
    <c:set var = "user" scope = "session" value = "${user}"/>
    <ul id="user_nav" class="sidenav sidenav-fixed">
        <li>
            <div class="user-view">
                <div class="background light-blue darken-2">
                </div>
                <a>
                    <span style="font-weight: bold;" class="white-text email"><b>Empleado: </b>${user.name} ${user.lastName}</span>
                </a>
                <a>
                    <span class="white-text">Compa�ia: ${user.company.name}</span>
                    <span class="white-text email">${user.email}</span>
                </a>
            </div>
        </li>
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/employee/index.jsp">
                <i class="material-icons">home</i>Inicio</a>
        </li>
        <li class="no-padding">
            <ul class="collapsible collapsible-accordion">
                <li>
                    <a href="${pageContext.request.contextPath}/employee/sales.do?op=exchange" class="collapsible-header"><i class="material-icons">local_offer</i>Canjear Cupones</a>
                </li>
            </ul>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/employee/config.do"><i class="material-icons">settings</i>Mi cuenta</a></li> 
        <li  class="nav-item">
            <a onclick="javascript:void(0)" href="login.do?op=logout" title="Cerrar Sesi�n"><i class="material-icons">exit_to_app</i>Cerrar Sesi�n</a>
        </li>
    </ul>
</header>
