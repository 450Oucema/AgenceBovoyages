package org.bovoyage.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bovoyage.metier.User;
import org.bovoyage.metier.Voyage;
import org.bovoyage.panier.Panier;
import org.bovoyage.security.Password;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.Vector;


public class UserDAO
{
	private Log log = LogFactory.getLog(UserDAO.class);
	private DAO dao = null;

	public UserDAO(DAO dao)
	{
		this.dao = dao;
	}
	
	public User getUser(int id) throws SQLException
	{
		User user = null;
		String sql = "SELECT * FROM users WHERE IDuser=?;";
		
		Connection con = dao.getConnection();
		PreparedStatement st = con.prepareStatement(sql);
		st.setInt(1, id);
		ResultSet rs = st.executeQuery();
		if(rs.next())
			user = this.construireUser(rs);
		rs.close();
		dao.releaseConnection(con);
		
		return user;
	}
	
	public User getUserByEmail(String email)
	{
		User user = null;
		String sql = "SELECT DISTINCT * FROM users WHERE email = ?;";
		log.info(sql);
		try {
			Connection con = dao.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);
			ResultSet rs = st.executeQuery();
			if(rs.next()) {
				user = this.construireUser(rs);
				log.info(user.toString());
			}
			rs.close();
			dao.releaseConnection(con);
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
		}

		return user;
	}
	
	public Vector<User> getusers() throws SQLException
	{
		Vector<User> users = new Vector<User>();
		String sql = "SELECT * FROM users;";
		log.debug("debut getusers");
		Connection con = dao.getConnection();
		ResultSet rs = con.createStatement().executeQuery(sql);
		log.debug("executeQuery getusers");
		while(rs.next())
		{
			log.debug("next getusers");
			User d = this.construireUser(rs);
			users.add(d);
			log.debug("done next getusers");
		}
		log.debug("fin getusers");
		rs.close();
		dao.releaseConnection(con);
		return users;
	}
	
	private User construireUser(ResultSet rs) throws SQLException
	{
		User u = new User();
		u.setId(rs.getString("id"));
		u.setEmail(rs.getString("email"));
		u.setPassword(rs.getString("password"));
		u.setPrenom(rs.getString("prenom"));
		u.setNom(rs.getString("nom"));
		u.setAdresse(rs.getString("adresse"));
		return u;
	}

	public boolean authenticate(String email, String password) {
		String sql = "SELECT * FROM users WHERE email = ?;";
		log.info(sql);
		try {
			Connection con = dao.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, email);

			ResultSet rs = st.executeQuery();

			if(rs.next()) {

				log.info("hashed : " + rs.getString("password") + " plain: " + password);
				boolean success =  Password.passwordVerify(password, rs.getString("password"));

				rs.close();
				dao.releaseConnection(con);

				return success;
			}

			rs.close();
			dao.releaseConnection(con);
		} catch (SQLException | NoSuchAlgorithmException sqlException) {
			sqlException.printStackTrace();
			log.info(sqlException.getMessage());
		}

		return false;
	}

	public boolean register(User user) {
		String sql = "INSERT INTO users (id, email, password, nom, prenom, adresse) VALUES (?, ?, ?, ?, ?, ?);";
		try {
			Connection con = dao.getConnection();
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, user.getId());
			st.setString(2, user.getEmail());
			st.setString(3, user.getPassword());
			st.setString(4, user.getNom());
			st.setString(5, user.getPrenom());
			st.setString(6, user.getAdresse());
			st.executeUpdate();
			dao.releaseConnection(con);

			return true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();

			return false;
		}
	}

	public void order(Panier panier, User user)
	{
		String commande  = "INSERT INTO commandes VALUES (?, ?, ?);";
		String commander = "INSERT INTO commander VALUES (?, ?, ?, ?);";

		try {
			String commandeId = UUID.randomUUID().toString();
			log.info("id commande" + commandeId);
			Connection con = dao.getConnection();
			PreparedStatement preparedCommande = con.prepareStatement(commande);

			preparedCommande.setString(1, commandeId);
			preparedCommande.setString(2, user.getId());
			preparedCommande.setBoolean(3, true);
			log.info(preparedCommande.toString());
			preparedCommande.executeUpdate();

			for(Voyage voyage : panier.getVoyages()) {
				log.info("je rentre dans les voyages");

				PreparedStatement preCommander = con.prepareStatement(commander);
				preCommander.setString(1, UUID.randomUUID().toString());
				preCommander.setString(2, commandeId);
				preCommander.setInt(3, voyage.getSejour().getId());
				preCommander.setInt(4, voyage.getNbPersonnes());
				preCommander.executeUpdate();
				log.info(voyage.getSejour().getId());
			}

			dao.releaseConnection(con);

		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			log.info(sqlException.getMessage());
		}
	}

	public boolean updateUser(User user)
	{
		String sql  = "UPDATE users SET email = ? , nom = ?, prenom = ?, adresse = ? WHERE id = ?;";

		try {
			Connection connection = dao.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(sql);

			preparedStatement.setString(1, user.getEmail());
			preparedStatement.setString(2, user.getNom());
			preparedStatement.setString(3, user.getPrenom());
			preparedStatement.setString(4, user.getAdresse());
			preparedStatement.setString(5, user.getId());

			log.info("requete update: " + preparedStatement.toString());
			preparedStatement.executeUpdate();

			dao.releaseConnection(connection);

			return true;
		} catch (SQLException sqlException) {
			sqlException.printStackTrace();
			log.info(sqlException.getMessage());

			return false;
		}
	}
}
