package com.excilys.cdb.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.CompanySQLMapper;
import com.excilys.cdb.model.company.Company;

/**
 * Singleton. Responsible for bonding the application to the database thanks to
 * JDBC.
 *
 * @author excilys
 *
 */
@Lazy
@Repository("companyDAO")
public class CompanyDAO {

    /**
     * mapper CompanyMapper.
     */
    @Autowired
    private CompanySQLMapper mapper;

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "SELECT * FROM company %s";
    private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = ?";
    private static final String DELETE = "DELETE FROM company WHERE id = ?";
    private static final String DELETE_RELATED_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";

    /**
     * Fetch the specified computer from the persistence.
     *
     * @param id Long.
     * @return Optional<Company>
     */
    public Optional<Company> get(Long id) {
        // TODO Auto-generated method stub
        Company company = null;
        try (Connection conn = DAOFactory.getConnection()) {

            PreparedStatement state = conn.prepareStatement(FIND_BY_ID);
            state.setLong(1, id);
            ResultSet result = state.executeQuery();
            company = result.next() ? mapper.queryResultToObject(result) : null;
            result.close();
            state.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(company);
    }

    /**
     * Fetch a specific range of companies.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<Company>
     */
    public List<Company> list(int page, int itemPerPage) {
        // TODO Auto-generated method stub
        LinkedList<Company> companies = new LinkedList<Company>();

        try (Connection conn = DAOFactory.getConnection()) {
            Statement state = conn.createStatement();
            String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
            offsetClause += (page > 1 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

            ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
            while (result.next()) {
                Company c = mapper.queryResultToObject(result);
                if (c != null) {
                    companies.add(c);
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

    /**
     * Delete the specified company and all the related computers.
     *
     * @param id Long
     * @return boolean
     */
    public boolean delete(Long id) {
        boolean isSuccess = false;
        try (Connection conn = DAOFactory.getConnection()) {

            conn.setAutoCommit(false);

            // Delete all the related computers.
            PreparedStatement deleteRelatedComputersStatement = conn.prepareStatement(DELETE_RELATED_COMPUTERS);
            deleteRelatedComputersStatement.setLong(1, id);
            deleteRelatedComputersStatement.executeUpdate();

            // Delete the company
            PreparedStatement deleteCompanyStatement = conn.prepareStatement(DELETE);
            deleteCompanyStatement.setLong(1, id);
            deleteCompanyStatement.executeUpdate();

            conn.commit();
            isSuccess = true;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        }
        return isSuccess;
    }
}