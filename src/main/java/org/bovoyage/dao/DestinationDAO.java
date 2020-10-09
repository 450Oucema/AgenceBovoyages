package org.bovoyage.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bovoyage.metier.Destination;
import org.bovoyage.metier.Sejour;
import org.bovoyage.metier.User;
import org.bovoyage.metier.Voyage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class DestinationDAO
{
	private Log log = LogFactory.getLog(DestinationDAO.class);
	private DAO dao = null;
	
	public DestinationDAO(DAO dao)
	{
		this.dao = dao;
	}
	
	public Destination getDestination(int id) throws SQLException
	{
		Destination destination = null;
		String sql = "SELECT * FROM destinations WHERE IDdestination=?;";
		
		Connection con = dao.getConnection();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next())
			destination = this.construireDestination(rs);
		rs.close();
		dao.releaseConnection(con);
		
		return destination;
	}
	
	public Destination getDestination(String id) throws SQLException
	{
		return this.getDestination(Integer.parseInt(id));
	}
	
	public Vector<Destination> getDestinations() throws SQLException
	{
		Vector<Destination> destinations = new Vector<Destination>();
		String sql = "SELECT * FROM destinations;";
		log.debug("debut getDestinations");
		Connection con = dao.getConnection();
		ResultSet rs = con.createStatement().executeQuery(sql);
		log.debug("executeQuery getDestinations");
		while(rs.next())
		{
			log.debug("next getDestinations");
			Destination d = this.construireDestination(rs);
			destinations.add(d);
			log.debug("done next getDestinations");
		}
		log.debug("fin getDestinations");
		rs.close();
		dao.releaseConnection(con);
		return destinations;
	}
	
	private Destination construireDestination(ResultSet rs) throws SQLException
	{
		Destination d = new Destination();
		log.debug("construireDestination");
		d.setDescription(rs.getString("description"));
		d.setPays(rs.getString("destination"));
		d.setIdentifiant(rs.getInt("IDdestination"));
		log.debug("avant getSejours");
		//d.setDates(this.getSejours(d));
		log.debug("avant getImages");
		d.setImages(this.getImages(d));
		return d;
	}
	
	public Vector<Sejour> getSejours(int id) throws SQLException
	{
		Vector<Sejour> sejours = new Vector<Sejour>();
		String sql = "SELECT * FROM sejour WHERE IDdestination=?;";
		Connection con = dao.getConnection();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		while(rs.next())
		{
			Sejour s = this.construireSejour(rs);
			sejours.add(s);
		}
		rs.close();
		dao.releaseConnection(con);
		return sejours;
	}
	
	private Sejour construireSejour(ResultSet rs) throws SQLException
	{
		Sejour s = new Sejour();
		s.setDepart(rs.getDate("dateDepart"));
		s.setRetour(rs.getDate("dateRetour"));
		s.setPrix(rs.getDouble("prixHTDestination"));
		s.setId(rs.getInt("idSejour"));
		return s;
	}

	private Voyage construireVoyage(Sejour sejour, ResultSet rs) throws SQLException {
		Voyage voyage = new Voyage();
		voyage.setId(rs.getString("idCommander"));
		voyage.setNbPersonnes(rs.getInt("nbPersonnes"));
		voyage.setPays(rs.getString("destination"));
		voyage.setSejour(sejour);

		return voyage;
	}
	
	public Vector<Sejour> getSejours(Destination destination) throws SQLException
	{
		return getSejours(destination.getIdentifiant());
	}
	
	public Vector<Sejour> getSejours(String id) throws SQLException
	{
		return getSejours(Integer.parseInt(id));
	}
	
	public Sejour getSejour(int id) throws SQLException
	{
		Sejour sejour = null;
		String sql = "SELECT * FROM sejour WHERE idSejour=?;";
		
		Connection con = dao.getConnection();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next())
			sejour = this.construireSejour(rs);
		rs.close();
		dao.releaseConnection(con);
			
		return sejour;
	}
	
	public Sejour getSejour(String id) throws SQLException
	{
		return this.getSejour(Integer.parseInt(id));
	}

	public Vector<String> getImages(int id) throws SQLException
	{
		Vector<String> images = new Vector<String>();
		log.debug("getImages " + id);
		String sql = "SELECT * FROM imagesDestinations WHERE IDdestination=?;";
		Connection con = dao.getConnection();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		log.debug("getImages");
		while(rs.next())
		{
			String image = rs.getString("image");
			images.add(image);
			log.debug(image);
		}
		log.debug("done");
		rs.close();
		dao.releaseConnection(con);

		return images;
	}
	
	public Vector<String> getImages(Destination destination) throws SQLException
	{
		return getImages(destination.getIdentifiant());
	}

	public ArrayList<Voyage> getCommandes(User user) {
		String sql = "SELECT *\n" +
				"FROM commande_item, commandes, sejour, destinations\n" +
				"WHERE \"user\" = ? \n" +
				"AND commandes.id = commande_item.commande\n" +
				"AND commande_item.sejour = sejour.idsejour\n" +
				"AND destinations.iddestination = sejour.iddestination;";

		ArrayList<Voyage> voyages = new ArrayList<Voyage>();

		try {
			Connection con = dao.getConnection();
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, user.getId());

			ResultSet resultSet = statement.executeQuery();

			while(resultSet.next())
			{
				Sejour sejour = this.construireSejour(resultSet);
				log.info("sejour =>" + sejour.toString());
				Voyage voyage = this.construireVoyage(sejour, resultSet);
				log.info("voyage: " + voyage.toString());
				voyages.add(voyage);
			}

			resultSet.close();
			dao.releaseConnection(con);
		} catch (SQLException sqlException) {
			log.info(sqlException.getMessage());
		}

 		return voyages;
	}
}
