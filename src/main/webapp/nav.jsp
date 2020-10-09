<%@ page language="java" import="org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<nav class="navbar navbar-expand-lg navbar-light fixed-top">
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
        <a class="navbar-brand logo" href="controleur?cde=acc">BoVoyages</a>
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="controleur?cde=acc">Nos voyages</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="controleur?cde=a-propos">A propos</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="controleur?cde=a-propos">Contact</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="controleur?cde=caddie">Panier (${caddie.items} article(s))</a>
            </li>
            <c:if test="${empty user}">
                <li class="nav-item">
                    <a class="nav-link" href="controleur?cde=register">Créer un compte</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="controleur?cde=login">Connexion</a>
                </li>
            </c:if>
            <c:if test="${user != null}">
                <li class="nav-item">
                    <a class="nav-link" href="controleur?cde=account">${user.email}</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="controleur?cde=logout">Déconnexion</a>
                </li>
            </c:if>
        </ul>
    </div>
</nav>
