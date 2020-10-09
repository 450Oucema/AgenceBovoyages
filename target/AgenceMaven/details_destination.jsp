<%@ page language="java" import="java.util.*,org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ include file="haut_page.jsp" %>
<div class="container">
	<h2>Votre futur séjour : ${destination.pays }</h2>
	<div class="row">
		<div class="col-md-6">
			<c:forEach items="${destination.imagesDestinations }" var="img" >
				<img src="images/${img.image}" class="img-fluid" />
			</c:forEach>
		</div>
		<div class="col-md-6">
			<table class="table">
				<thead>
				<tr>
					<th>Départ</th>
					<th>Retour</th>
					<th>Prix TTC</th>
					<th></th>
				</tr>
				</thead>
				<tbody>
				<c:forEach items="${ destination.sejours}" var="sejour" varStatus="st">
					<fmt:setLocale value="fr" />
					<tr>
						<td><fmt:formatDate value="${sejour.depart }"  dateStyle="full" /></td>
						<td><fmt:formatDate value="${sejour.retour }"  dateStyle="full" /></td>
						<td><fmt:formatNumber value="${sejour.prix }"  type="currency" currencySymbol="&euro;" /></td>
						<td><a href="controleur?cde=cde&id=${sejour.id }" class="btn btn-success">Commander</a></td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>
