package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Singleton Responsible for instantiating the DAO among the application.
 *
 * @author excilys
 *
 */
@Lazy
@Component("daoFactory")
public class DAOFactory {

    private static HikariConfig config = new HikariConfig("/config.properties");
    private static HikariDataSource dataSource;

    @Autowired
    ComputerDAO computerDAO;

    @Autowired
    CompanyDAO companyDAO;

    static {
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaxLifetime(28000);
        dataSource = new HikariDataSource(config);
    }

    /**
     * Creates a new instance of Connection.
     *
     * @return Connection
     * @throws SQLException sqle
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
        return computerDAO;
    }

    /**
     * Getter Returns the instance of the concrete company DAO.
     *
     * @return CompanyDAO
     */
    public CompanyDAO getCompanyDAO() {
        return companyDAO;
    }
}
