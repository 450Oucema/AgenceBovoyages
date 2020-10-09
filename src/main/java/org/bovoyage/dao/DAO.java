package org.bovoyage.dao;


import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DAO
{
	DataSource dataSource=null;
	private static DAO instance = null;
	
	private DAO() 
				throws ClassNotFoundException, Exception
	{
		try {
			InitialContext ic = new InitialContext();
			Context envCtx = (Context) ic.lookup("java:comp/env");
			dataSource = (DataSource) envCtx.lookup("jdbc/BoVoyageDS");
			System.out.println("datasource initialized...");
		} catch (Exception ex) {
			throw new Exception("Datasource not found : " + ex.getMessage());
		}
	}
	
	public static DAO getInstance() {
		if (instance==null) {
			try {
				instance = new DAO();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (Exception e) {
				System.out.println("DAO getInstance exception");
			}
		}
		return instance;
	}
	
	public Connection getConnection() throws SQLException
	{
		Connection connexion = null;
		connexion = this.dataSource.getConnection();
		return connexion;
	}
	
	public void releaseConnection(Connection con) throws SQLException
	{
		con.close();
	}
	
	public static void log(String message)
	{
		System.err.println(message);
	}
}
