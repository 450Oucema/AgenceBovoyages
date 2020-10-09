<%@ page language="java" import="org.bovoyage.servlet.*,org.bovoyage.metier.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ include file="haut_page.jsp" %>
<div class="text-center">
	<div class="p-2">
		<h3 class="text-green logo">Les destinations du moment</h3>
		<p>Our choosen destination will make your holiday more exited and more unforgetable moment.</p>
	</div>
	<div class="row m-0" style="min-height: 300px; max-width: 100%">
		<c:forEach var="destination" items="${destinations}">
			<div class="col-3 col-xs-12 col-md-4 col-xl-4 grid-image" style="padding: 0;background: rgba(0,0,0,0) url('images/${destination.imagesDestinations[0].image}'); background-size: cover;">
				<div class="overlay">
					<div class="p-2 mt-3">
						<h3 class="logo text-white">${destination.pays}</h3>
						<p class="mt-2">${destination.description}</p>
						<a href="controleur?cde=det&id=${destination.identifiant}" class="btn btn-transparent"><i class="fas fa-eye"></i></a>
					</div>
				</div>
			</div>
		</c:forEach>
	</div>
</div>
<div class="jumbotron p-0 m-0 w-100 promojumbo">
	<div class="row m-0 text-center">
		<div class="col-md-6 text-white mt-5 promo-text">
			<h1>FAITES VITE!</h1>
			<h2>-60% EN CE MOMENT! SUR TOUTES LES DESTINATIONS*</h2>
			<p style="font-size: initial;">* offre soumise à condition.</p>
		</div>
		<div class="col-md-6">
			<img src="https://www.onlygfx.com/wp-content/uploads/2018/04/discount-stamp-2-1024x788.png" alt="" class="d-block img-fluid discount">
		</div>
	</div>
</div>
<div class="jumbotron p-0 m-0 w-100 testimonials" style="background-image: url('images/malibu-villa.jpg')">
	<div class="container">
		<div class="row m-0">
			<div class="col-md-6 text-white mt-5">
				<h1><i class="fas fa-quote-right"></i></h1>
				<h2>Tony Stark</h2>
				<p>"Used to go home when captain thoughts that he was the strongest Avenger. Very fine."</p>
			</div>
			<div class="col-md-6">
				<img src="https://upload.wikimedia.org/wikipedia/en/c/cb/Robert_Downey_Jr._as_Iron_Man_in_Avengers_Infinity_War.jpg" style="right: 20%;" class="d-block img-fluid testimonial-user-img">
			</div>
		</div>
	</div>
</div>
<div class="jumbotron p-0 m-0 w-100 testimonials" style="background-image: url('images/milan.jpg')">
	<div class="container">
		<div class="row m-0">
			<div class="col-md-6">
				<img src="https://static1.purepeople.com/articles/5/24/99/85/@/3510364-daniel-craig-premiere-mondiale-de-jame-624x600-3.jpg" style="left: 20%;" class="d-block img-fluid testimonial-user-img">
			</div>
			<div class="col-md-6 text-white mt-5">
				<h1><i class="fas fa-quote-right"></i></h1>
				<h2>James Bond</h2>
				<p>"Used to go home when captain thoughts that he was the strongest Avenger. Very fine."</p>
			</div>
		</div>
	</div>
</div>
<%@ include file="footer.jsp" %>
