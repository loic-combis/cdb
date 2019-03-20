package com.excilys.cdb.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;
import com.excilys.cdb.persistence.mapper.CompanyMapper;

/**
 * Singleton Concrete implementation of CompanyDAO. Responsible for bonding the
 * application to the database thanks to JDBC.
 *
 * @author excilys
 *
 */
public class JdbcCompanyDAO implements CompanyDAO {

    /**
     * instance JdbcCompanyDAO - Unique instance of JdbcCompanyDAO.
     */
    private static JdbcCompanyDAO instance;

    /**
     * mapper CompanyMapper.
     */
    private CompanyMapper mapper = new CompanyMapper();

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcCompanyDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "SELECT * FROM company %s";
    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = %d";

    /**
     * Constructor.
     *
     * Prevents from being instantiated outside the class.
     */
    private JdbcCompanyDAO() {
    }

    /**
     * Creates or returns the unique instance of JdbcCompanyDAO.
     *
     * {@link JdbcCompanyDAO#instance}
     *
     * @return JdbcCompanyDAO
     */
    public static JdbcCompanyDAO getInstance() {
        if (instance == null) {
            instance = new JdbcCompanyDAO();
            LOGGER.debug("JdbcComputerDAO instantiated.");
        }
        return instance;
    }

    @Override
    public Optional<Company> get(long id) {
        // TODO Auto-generated method stub
        Optional<Company> company = null;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
            company = result.next() ? mapper.queryResultToObject(result) : null;
            result.close();
            state.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        return company;
    }

    @Override
    public List<Company> list(int page, int itemPerPage) {
        // TODO Auto-generated method stub
        LinkedList<Company> companies = new LinkedList<Company>();

        try (Connection conn = DAOFactory.getConnection()) {
            Statement state = conn.createStatement();
            String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
            offsetClause += (page > 1 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

            ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
            while (result.next()) {
                Optional<Company> c = mapper.queryResultToObject(result);
                if (c.isPresent()) {
                    companies.add(c.get());
                }
            }
            result.close();
            state.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        return companies;
    }
}