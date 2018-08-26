<%-- 
    Document   : menuClient
    Created on : 14-ago-2018, 21:12:00
    Author     : Diego Lemus
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <c:if test="${sessionScope.userColor == null}">
        <c:set var="userColor" value="blue" scope="session"/>
    </c:if>
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
                    <span style="font-weight: bold;" class="white-text email"><b>Cliente: </b>${user.name}</span>
                </a>
                <a>
                    <span class="white-text email">${user.email}</span>
                </a>
            </div>
        </li>
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/client/index.jsp">
                <i class="material-icons">home</i>Inicio
            </a>
        </li>
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/client/sales.do?op=listC">
                <i class="material-icons">local_offer</i> Mis cupones
            </a>
        </li>
        <li class="nav-item">
            <a href="${pageContext.request.contextPath}/client/sales.do?op=newC">
                <i class="material-icons">attach_money</i> Comprar cupones
            </a>
        </li>
        <li>
            <div class="divider"></div>
        </li>
        <li class="nav-item"><a href="${pageContext.request.contextPath}/client/config.do"><i class="material-icons">settings</i>Mi cuenta</a></li> 
        <li  class="nav-item">
            <a onclick="javascript:void(0)" href="login.do?op=logout" title="Cerrar Sesion"><i class="material-icons">exit_to_app</i>Cerrar Sesion</a>
        </li>
    </ul>
</header>
