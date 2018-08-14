<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <form id="logout-form" action="#" method="POST" style="display: none;"></form>

    <nav class="purple darken-4">
        <div class="container">
            <a href="#" data-target="user_nav" class="sidenav-trigger "><i class="material-icons">menu</i></a>
            <div class="nav-wrapper"><a class="brand-logo center"><c:out value="${title}" default="Administrador" /></a></div>
        </div>
    </nav>
    <c:set var = "user" scope = "session" value = "${user}"/>
    <ul id="user_nav" class="sidenav sidenav-fixed">
        <li>
            <div class="user-view">
                <div class="background purple darken-4">
                </div>
                <a>
                    <img class="circle" src="#">
                </a>
                <a>
                    <span class="white-text name">Administrador</span>
                </a>
                <a>
                    <span style="font-weight: bold;" class="white-text email">${user.name} ${user.lastName}</span>
                </a>
                <a>
                    <span class="white-text email">${user.email}</span>
                </a>
            </div>
        </li>
        <li class="active nav-item">
            <a href="${pageContext.request.contextPath}/admin/index.jsp">
                <i class="material-icons">home</i>Inicio</a>
        </li>
        <li class="active nav-item">
            <a href="${pageContext.request.contextPath}/admin/company.do?op=list">
                <i class="material-icons">business</i>Empresas</a>
        </li>
        <li class="active nav-item">
            <a href="${pageContext.request.contextPath}/admin/companiesType.do?op=list">
                <i class="material-icons">local_offer</i>Rubros</a>
        </li>
        <li class="nav-item"><a href="#"><i class="material-icons">info</i>Mi cuenta</a></li> 
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <a class="subheader"></a>
        </li>
        <li  class="nav-item">
            <a onclick="javascript:void(0)" href="login.do?op=logout" title=""><i class="material-icons">exit_to_app</i>Cerrar sesión</a>
        </li>
    </ul>
</header>
