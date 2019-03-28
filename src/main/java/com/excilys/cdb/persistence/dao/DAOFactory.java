package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton Responsible for instantiating the DAO among the application.
 *
 * @author excilys
 *
 */
public class DAOFactory {

    private static HikariConfig config = new HikariConfig("/config.properties");
    private static HikariDataSource dataSource;

    /**
     * instance DAOFactory - Unique instance of DAOFactory.
     */
    private static DAOFactory instance;

    /**
     * logger Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DAOFactory.class);

    static {
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

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
     * @throws SQLException           sqle
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Getter Returns the instance of the concrete computer DAO.
     *
     * @return ComputerDAO
     */
    public ComputerDAO getComputerDAO() {
        return ComputerDAO.getInstance();
    }

    /**
     * Getter Returns the instance of the concrete company DAO.
     *
     * @return CompanyDAO
     */
    public CompanyDAO getCompanyDAO() {
        return CompanyDAO.getInstance();
    }
}
