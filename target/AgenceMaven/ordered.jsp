<%@ page language="java" import="java.util.*,org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="haut_page.jsp" %>
  <div class="container" style="display: block; margin: auto;">
      <div class="jumbotron">
          <h3>Merci de votre commande !</h3>
          <div class="card">
              <div class="card-header">
                  <h5>Récapitulatif de la commande :</h5>
              </div>
              <div class="card-body">
                  <ul class="list-group">
                      <c:forEach items="${oldCaddie.voyages }" var="voyage" >
                          <li class="list-group-item">
                                  ${voyage.pays } -  Départ le <fmt:formatDate value="${voyage.sejour.depart }" dateStyle="full" />
                              / Retour le <fmt:formatDate value="${voyage.sejour.retour }" dateStyle="full" />
                          </li>
                      </c:forEach>
                  </ul>
              </div>
          </div>
      </div>
  </div>
<%@ include file="footer.jsp" %>
