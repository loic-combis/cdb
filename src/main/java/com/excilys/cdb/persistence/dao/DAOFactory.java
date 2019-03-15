package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.excilys.cdb.persistence.dao.jdbc.JdbcCompanyDAO;
import com.excilys.cdb.persistence.dao.jdbc.JdbcComputerDAO;
import com.excilys.cdb.util.PropertyReader;

/**
 * Singleton Responsible for instantiating the DAO among the application.
 * 
 * @author excilys
 *
 */
public class DAOFactory {

	/**
	 * instance DAOFactory - Unique instance of DAOFactory.
	 */
	private static DAOFactory instance;

	/**
	 * conn Connection - Instance of the connection to the database.
	 */
	private Connection conn;

	/**
	 * Constructor {@link DAOFactory#conn} Prevent from being instantiated outside
	 * the class.
	 */
	private DAOFactory() {
		PropertyReader prop = new PropertyReader();
		// TODO Auto-generated method stub
		String dbURL = prop.get("dbName");
		String dbUser = prop.get("dbUser");
		String dbPassword = prop.get("dbPassword");

		try {
			this.conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

		} catch (SQLException | IllegalArgumentException | SecurityException e) {
			// TODO Auto-generated catch block
			if (this.conn != null) {
				try {
					this.conn.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.exit(1);
				}
			}
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Creates or return the unique instance of DAOFactory.
	 * {@link DAOFactory#instance}
	 * 
	 * @return DAOFactory
	 */
	public static DAOFactory getInstance() {
		if (instance == null) {
			instance = new DAOFactory();
		}
		return instance;
	}

	/**
	 * Getter Returns the instance of the concrete computer DAO.
	 * 
	 * @return ComputerDAO
	 */
	public ComputerDAO getComputerDAO() {
		return JdbcComputerDAO.getInstance(this.conn);
	}

	/**
	 * Getter Returns the instance of the concrete company DAO.
	 * 
	 * @return CompanyDAO
	 */
	public CompanyDAO getCompanyDAO() {
		return JdbcCompanyDAO.getInstance(this.conn);
	}
}
