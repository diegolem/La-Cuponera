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
            <div class="nav-wrapper"><a class="brand-logo center"><c:out value = "${title}"/></a></div>
        </div>
    </nav>

    <ul id="user_nav" class="sidenav sidenav-fixed">
        <li>
            <div class="user-view">
                <div class="background purple darken-4">
                </div>
                <a>
                    <img class="circle" src="#">
                </a>
                <a>
                    <span class="white-text name">Session</span>
                </a>
                <a>
                    <span style="font-weight: bold;" class="white-text email">Nombre</span>
                </a>
                <a>
                    <span class="white-text email">Correo</span>
                </a>
            </div>
        </li>
        <li class="active nav-item">
            <a href="menu.jsp">
                <i class="material-icons">home</i>Inicio</a>
        </li>
        <li class="nav-item"><a href="#"><i class="material-icons">info</i>Mi cuenta</a></li> 
        <li>
            <div class="divider"></div>
        </li>
        <li>
            <a class="subheader"></a>
        </li>
        <li  class="nav-item">
            <a onclick="javascript:void(0)"><i class="material-icons">exit_to_app</i>Cerrar Sesión</a>
        </li>
    </ul>
</header>
