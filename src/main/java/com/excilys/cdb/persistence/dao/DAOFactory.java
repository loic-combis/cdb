package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
     * logger Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);

    /**
     * Constructor Prevent from being instantiated outside the class.
     */
    private DAOFactory() {
    }

    /**
     * Creates or return the unique instance of DAOFactory.
     *
     * {@link DAOFactory#instance}
     *
     * @return DAOFactory
     */
    public static DAOFactory getInstance() {
        if (instance == null) {
            instance = new DAOFactory();
            LOGGER.debug("DAOFactory instantiated.");
        }
        return instance;
    }

    /**
     * Creates a new instance of Connection.
     *
     * @return Connection
     * @throws SQLException sqle
     */
    public static Connection getConnection() throws SQLException {

        PropertyReader prop = new PropertyReader();

        String dbURL = prop.get("dbName");
        String dbUser = prop.get("dbUser");
        String dbPassword = prop.get("dbPassword");

        return DriverManager.getConnection(dbURL, dbUser, dbPassword);
    }

    /**
     * Getter Returns the instance of the concrete computer DAO.
     *
     * @return ComputerDAO
     */
    public ComputerDAO getComputerDAO() {
        return JdbcComputerDAO.getInstance();
    }

    /**
     * Getter Returns the instance of the concrete company DAO.
     *
     * @return CompanyDAO
     */
    public CompanyDAO getCompanyDAO() {
        return JdbcCompanyDAO.getInstance();
    }
}
