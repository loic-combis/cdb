package persistence.dao;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
		String driver = prop.get("driver");

		try {
			Class.forName(driver).getConstructor().newInstance();
			this.conn = DriverManager.getConnection(dbURL, dbUser, dbPassword);

		} catch (SQLException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
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

	public static DAOFactory getInstance() {
		if (instance == null) {
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
