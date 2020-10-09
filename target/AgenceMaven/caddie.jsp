<%@ page language="java" import="java.util.*,org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="haut_page.jsp" %>
<div class="container p-5">
		<c:forEach var="voyage" items="${caddie.voyages}" varStatus="loop">
			<form action="controleur?cde=chgt" method="post">
				<input type="hidden" value="${voyage.sejour.id}" name="sejour">
				<table class="table">
					<thead><th>Destination</th><th>Départ</th><th>Retour</th><th>Nb personnes</th><th>Prix Total TTC</th></thead>
					<tr>
						<td>${voyage.pays }</td>
						<td><fmt:formatDate value="${voyage.sejour.depart }" dateStyle="full" /></td>
						<td><fmt:formatDate value="${voyage.sejour.retour }" dateStyle="full"  /></td>
						<td align="center">
							<select name="nb_personnes" onChange="document.forms[${loop.index}].submit();">
								<c:forEach var="i" begin="1" end="10">
									<c:if test="${i == voyage.nbPersonnes }" >
										<c:set var="selected" value="selected='selected'"/>
									</c:if>
									<c:if test="${i != voyage.nbPersonnes }" >
										<c:set var="selected" value=""/>
									</c:if>
									<option value="${i }" ${selected }>${i }</option>
								</c:forEach>
							</select>
						</td>
						<td><fmt:formatNumber value="${voyage.prixTotal }" type="currency" currencySymbol="&euro;" /></td></tr>
				</table>
			</form>
	  </c:forEach>
	<div style="border: 5px black;">
		<h6>Vous avez ${caddie.items} articles dans votre panier.</h6>
		<c:if test="${caddie.items > 0}">
			<c:if test="${user != null}">
				<a href="controleur?cde=commander" class="btn btn-primary">Cliquez ici pour commander</a>
			</c:if>
			<c:if test="${empty user}">
				<a href="controleur?cde=register" class="btn btn-primary">Enregistrez vous pour commander</a>
				OU
				<a href="controleur?cde=login" class="btn btn-primary">Connectez vous</a>
			</c:if>
		</c:if>
	</div>
</div>
<%@ include file="footer.jsp" %>
