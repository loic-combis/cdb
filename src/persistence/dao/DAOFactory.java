package persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;

import persistence.dao.jdbc.JdbcCompanyDAO;
import persistence.dao.jdbc.JdbcComputerDAO;
import util.PropertyReader;

public class DAOFactory {

	private static DAOFactory instance;
	private Connection conn;
	
	private DAOFactory() {
		PropertyReader prop = new PropertyReader("resources/config.properties");
		// TODO Auto-generated method stub
		String dbURL = prop.get("dbName");
		String dbUser = prop.get("dbUser");
		String dbPassword = prop.get("dbPassword");
		
		try {
			Class.forName("com.mysql.jdbc.Driver").getConstructor().newInstance(); 
			this.conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
			System.out.println("Connected to database");
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static DAOFactory getInstance() {
		if(instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}
	
	
	public ComputerDAO getComputerDAO() {
		return JdbcComputerDAO.getInstance(this.conn);
	}
	
	public CompanyDAO getCompanyDAO() {
		return JdbcCompanyDAO.getInstance(this.conn);
	}
}
