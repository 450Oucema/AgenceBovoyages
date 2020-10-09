<%@ page language="java" import="org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ include file="haut_page.jsp" %>
<div class="container text-center">
	<c:if test="${user != null}">
		<div style="background-color: #3586ff;" class="alert alert-primary">
			<h6 style="color: white;">${user.nom} ${user.prenom}</h6>
		</div>
	</c:if>
	<div class="card">
		<div class="card-header">
			<h4>Mon compte</h4>
		</div>
		<div class="card-body">
			<form action="controleur?cde=editaccount" method="POST">
					<div class="form-row">
						<div class="form-group col-md-6">
							<label for="nom">Nom</label>
							<input type="text" class="form-control" value="${user.nom}" id="nom" name="nom">
						</div>
						<div class="form-group col-md-6">
							<label for="prenom">Prénom</label>
							<input type="text" class="form-control" value="${user.prenom}" id="prenom" name="prenom">
						</div>
					</div>
					<div class="form-group">
						<label for="inputAddress">Adresse</label>
						<input type="text" class="form-control" value="${user.adresse}" id="inputAddress" name="adresse">
					</div>
					<div class="form-row">
						<div class="form-group">
							<label for="email">Email</label>
							<input type="email" class="form-control" id="email" name="email" value="${user.email}">
						</div>
					</div>
					<button type="submit" class="btn btn-primary">Modifier mes informations</button>
			</form>
		</div>
	</div>
	<div class="card">
		<div class="card-header">
			<h5>Mes commandes</h5>
		</div>
		<div class="card-body">
			<table class="table">
				<thead>
				<tr>
					<th>N° Commande</th>
					<th>Pers.</th>
					<th>Destination</th>
					<th>Départ</th>
					<th>Retour</th>
					<th>Prix TTC</th>
				</tr>
				</thead>
				<tbody>
				<c:forEach var="voyage" items="${commandes}">
					<tr>
						<td>${voyage.id}</td>
						<td>${voyage.nbPersonnes}</td>
						<td>${voyage.pays}</td>
						<td><fmt:formatDate value="${voyage.sejour.depart }"  dateStyle="full" /></td>
						<td><fmt:formatDate value="${voyage.sejour.retour }"  dateStyle="full" /></td>
						<td><fmt:formatNumber value="${voyage.prixTotal }" type="currency" currencySymbol="&euro;" /></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>
