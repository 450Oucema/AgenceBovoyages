package org.bovoyage.servlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bovoyage.dao.DAO;
import org.bovoyage.dao.DestinationDAO;
import org.bovoyage.dao.UserDAO;
import org.bovoyage.metier.*;
import org.bovoyage.panier.Panier;
import org.bovoyage.repositories.DestinationRepository;
import org.bovoyage.repositories.ImageRepository;
import org.bovoyage.repositories.UserRepository;
import org.bovoyage.security.Password;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import static java.util.Objects.isNull;


public class ControleurServlet extends HttpServlet
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8437305835510027544L;
	
	private DAO dao;
	private ServletContext application;
	private Log log = LogFactory.getLog(ControleurServlet.class);
	private static DestinationRepository destinationRepository = DestinationRepository.getInstance();
	private static UserRepository userRepository = UserRepository.getInstance();

	
	public void init() throws ServletException
	{
	}

	//TODO: Remplacer tous les if par un switch case
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		if(request.getSession().getAttribute(Constantes.ATTR_SESSION_CADDIE) == null) {
			Panier panier = new Panier();
			request.getSession().setAttribute(Constantes.ATTR_SESSION_CADDIE, panier);
		}

		String page = "/accueil.jsp";
		log.info("info entree dans doget");
		application = getServletContext();
		String cde = request.getParameter("cde");
		log.info("Commande : " + cde);
		dao = DAO.getInstance();
		if (cde == null)
			cde = Constantes.CDE_ACCUEIL;
		if (cde.equals(""))
			cde = Constantes.CDE_ACCUEIL;
		
		if (cde.equalsIgnoreCase(Constantes.CDE_ACCUEIL))
			page = this.actionAccueil(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_DETAILS))
			page = this.actionDetails(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_COMMANDE))
			page = this.actionCommande(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_PANIER))
			page = this.actionAfficherVoyage(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_CHANGER_NB_PERSONNES))
			page = this.actionChangerVoyage(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_LOGIN))
			page = this.actionLogin(request, response);
		if (cde.equalsIgnoreCase(Constantes.CDE_CONNEXION)) {
				page = this.actionConnexion(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.CDE_COMMANDER)) {
				page = this.actionCommander(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.CDE_ENREGISTRER)) {
			page = this.actionEnregistrer(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.CDE_FORM_ENREGISTRER)) {
				page = this.actionFormEnregistrer(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.CDE_ACCOUNT)) {
			page = this.actionAccount(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.APROPOS)) {
			page = this.actionApropos(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.LOGOUT)) {
			page = this.actionLogout(request, response);
		}
		if (cde.equalsIgnoreCase(Constantes.EDITUSER)) {
			page = this.actionEditUser(request, response);
		}

		RequestDispatcher rd = application.getRequestDispatcher(page);
		rd.forward(request, response);
	}

	private String actionEditUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = this.hydrateUser(request);
		User currentUser = (User) request.getSession().getAttribute("user");
		UserDAO userDAO = new UserDAO(dao);

		currentUser.setEmail(user.getEmail());
		currentUser.setAdresse(user.getAdresse());
		currentUser.setNom(user.getNom());
		currentUser.setPrenom(user.getPrenom());

		if(userDAO.updateUser(currentUser)) {
			request.getSession().setAttribute("user", currentUser);
			request.setAttribute("success", "Vos modifications ont bien été prises en compte");
		} else {
			request.setAttribute("error", "Vos modifications n'ont pas été prises en compte");
		}

		return this.actionAccount(request, response);
	}

	private String actionLogout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getSession().removeAttribute("user");

		return this.actionAccueil(request, response);
	}

	private String actionApropos(HttpServletRequest request, HttpServletResponse response) {
		return "/apropos.jsp";
	}

	private String actionAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DestinationDAO destinationDAO = new DestinationDAO(dao);
		Panier panier = (Panier) request.getSession().getAttribute(Constantes.ATTR_SESSION_CADDIE);
		User user = (User) request.getSession().getAttribute(Constantes.USER);

		if(isNull(user)) {
			return this.actionAccueil(request, response);
		}

		ArrayList<Voyage> commandes = destinationDAO.getCommandes(user);
		request.setAttribute("commandes", commandes);

		return "/account.jsp";
	}

	private String actionFormEnregistrer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = this.hydrateUser(request);

		if(userRepository.register(user)) {
			log.info("je menregistre");
			request.getSession().setAttribute("user", user);

			return this.actionAccueil(request, response);
		}

		return this.actionEnregistrer(request, response);
	}

	private String actionEnregistrer(HttpServletRequest request, HttpServletResponse response)
	{
		return "/register.jsp";
	}

	private String actionCommander(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO(dao);
		Panier panier = (Panier) request.getSession().getAttribute(Constantes.ATTR_SESSION_CADDIE);
		User user = (User) request.getSession().getAttribute(Constantes.USER);
		Integer items = panier.getItems();

		if(isNull(user) || items.equals(0)) {
			return this.actionAccueil(request, response);
		}

		userDAO.order(panier, user);

		Panier newPanier = new Panier();
		request.getSession().setAttribute(Constantes.ATTR_SESSION_CADDIE, newPanier);
		request.setAttribute("oldCaddie", panier);

		return "/ordered.jsp";
	}

	private String actionConnexion(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDAO = new UserDAO(dao);
		log.info(request.getParameter("email") + " " + request.getParameter("password"));
		if(userDAO.authenticate(request.getParameter("email"), request.getParameter("password"))) {
			User user = userDAO.getUserByEmail(request.getParameter("email"));
			request.getSession().setAttribute("user", user);

			return this.actionAccueil(request, response);
		} else {
			request.getSession().setAttribute("errorConnexion", true);
			return "/login.jsp";
		}
	}

	private String actionLogin(HttpServletRequest request, HttpServletResponse response) {
		return "/login.jsp";
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		doGet(request, response);
	}

	private String actionAccueil(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<Destination> listdestinations = destinationRepository.findAll();
		Destination uneDest = destinationRepository.find(1);
		log.debug(uneDest.toString());

		request.setAttribute(Constantes.ATTR_REQ_DESTINATIONS, listdestinations);
		return "/accueil.jsp";
	}

	private String actionDetails(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String idDestination = request.getParameter("id");
		Destination destination = destinationRepository.find(Integer.parseInt(idDestination));

		if(isNull(destination)) {
			return this.actionAccueil(request, response);
		}


		request.getSession().setAttribute(Constantes.ATTR_SESSION_DESTINATION, destination);

		return "/details_destination.jsp";
	}

	private String actionCommande(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		Panier panier = (Panier) request.getSession().getAttribute(Constantes.ATTR_SESSION_CADDIE);
		String idSejour = request.getParameter("id");
		Destination destination = (Destination)request.getSession().getAttribute(Constantes.ATTR_SESSION_DESTINATION);
		Sejour sejour = destination.getSejour(idSejour);
		Voyage voyage = new Voyage(sejour,destination.getPays(),1);
		int actualItems = panier.getItems();

		panier.addVoyage(voyage);
		panier.setItems(++ actualItems);

		request.getSession().setAttribute(Constantes.ATTR_SESSION_CADDIE, panier);

		return "/details_destination.jsp";
	}

	private String actionAfficherVoyage(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		return "/caddie.jsp";
	}
	
	private String actionChangerVoyage(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException
	{
		Panier panier = (Panier)request.getSession().getAttribute(Constantes.ATTR_SESSION_CADDIE);
		Voyage voyage = panier.findVoyage(request.getParameter("sejour"));
		voyage.setNbPersonnes(request.getParameter("nb_personnes"));

		return this.actionAfficherVoyage(request, response);
	}

	private User hydrateUser(HttpServletRequest request) {
		User user = new User();
		user.setPrenom(request.getParameter("prenom"));
		user.setEmail(request.getParameter("email"));
		user.setNom(request.getParameter("nom"));
		user.setAdresse(request.getParameter("adresse"));

		if(request.getParameter("password") != null) {
			try {
				user.setPassword(Password.passwordHash(request.getParameter("password")));
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				log.info("erreurhash" + e.getMessage());
			}
		}

		return user;
	}
}
