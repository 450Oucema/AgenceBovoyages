<%@ page language="java" import="java.util.*,org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="haut_page.jsp" %>
  <div class="container">
      <div class="card">
          <div class="card-header">
              <h3>Inscription</h3>
          </div>
          <div class="card-body">
              <form action="controleur?cde=formRegister" method="POST">
                  <div class="form-row">
                      <div class="form-group col-md-6">
                          <label for="email">Email</label>
                          <input type="email" class="form-control" id="email" name="email">
                      </div>
                      <div class="form-group col-md-6">
                          <label for="password">Password</label>
                          <input type="password" class="form-control" id="password" name="password">
                      </div>
                  </div>
                  <div class="form-group">
                      <label for="inputAddress">Adresse</label>
                      <input type="text" class="form-control" id="inputAddress" name="adresse">
                  </div>
                  <div class="form-group">
                      <label for="nom">Nom</label>
                      <input type="text" class="form-control" id="nom" name="nom">
                  </div>
                  <div class="form-group">
                      <label for="prenom">Prénom</label>
                      <input type="text" class="form-control" id="prenom" name="prenom">
                  </div>

                  <div class="form-group">
                      <div class="form-check">
                          <input class="form-check-input" required type="checkbox" id="gridCheck">
                          <label class="form-check-label" for="gridCheck">
                              J'accepte les conditions d'utilisations
                          </label>
                      </div>
                  </div>
                  <button type="submit" class="btn btn-primary">M'enregistrer</button>
              </form>
          </div>
      </div>
  </div>
<%@ include file="footer.jsp" %>
