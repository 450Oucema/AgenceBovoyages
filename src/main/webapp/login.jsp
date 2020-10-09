<%@ page language="java" import="java.util.*,org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="haut_page.jsp" %>
<div class="container">
    <form class="form-signin" action="controleur?cde=connexion" method="POST">
        <h1 class="h3 mb-3 font-weight-normal">Connexion</h1>
        <label for="inputEmail" class="sr-only">Email</label>
        <input type="email" name="email" id="inputEmail" class="form-control" placeholder="Email address" required autofocus>
        <label for="inputPassword" class="sr-only">Mot de passe</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
        <button class="btn btn-lg btn-primary btn-block" type="submit">Connexion</button>
    </form>
</div>
<%@ include file="footer.jsp" %>
