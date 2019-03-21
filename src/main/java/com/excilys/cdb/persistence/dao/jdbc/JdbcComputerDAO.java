package com.excilys.cdb.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

/**
 * Singleton Concrete implementation of ComputerDAO Responsible for bonding
 * Computer objects to the database thanks to JDBC.
 *
 * @author excilys
 *
 */
public class JdbcComputerDAO implements ComputerDAO {

    /**
     * mapper ComputerMapper.
     */
    private ComputerMapper mapper = new ComputerMapper();

    /**
     * instance JdbcComputerDAO - Unique instance of JdbcComputerDAO.
     */
    private static JdbcComputerDAO instance;

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcComputerDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id %s";
    private static final String FIND_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = %d";
    private static final String DELETE_ONE = "DELETE FROM computer WHERE id = %d";
    private static final String CREATE_ONE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES('%s', ?, ?, ?)";
    private static final String UPDATE_ONE = "UPDATE computer SET name='%s', introduced = ?, discontinued = ?, company_id = ? WHERE id = %d";
    private static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM computer";
    private static final String DELETE_MANY = "DELETE FROM computer WHERE id IN (%s)";

    /**
     * Constructor.
     *
     * Prevents from being instantiated outside the class.
     *
     * {@link JdbcComputerDAO#instance}
     */
    private JdbcComputerDAO() {
    }

    /**
     * Creates or returns the unique instance of JdbcComputerDAO.
     *
     * @return JdbcComputerDAO
     */
    public static JdbcComputerDAO getInstance() {
        if (instance == null) {
            instance = new JdbcComputerDAO();
            LOGGER.debug("JdbcComputerDAO instantiated");
        }
        return instance;
    }

    @Override
    public Optional<Computer> get(long id) {
        // TODO Auto-generated method stub
        Optional<Computer> computer = null;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
            computer = result.next() ? mapper.queryResultToObject(result) : null;

            result.close();
            state.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        return computer;
    }

    @Override
    public Optional<Computer> create(Computer c) {
        if (c == null) {
            return Optional.ofNullable(c);
        }
        // TODO Auto-generated method stub
        Long companyId = (c.getCompany() != null ? c.getCompany().getId() : null);

        Timestamp introductionDate = mapper.getSqlTimestampValue(c.getIntroductionDate());
        Timestamp discontinuationDate = mapper.getSqlTimestampValue(c.getDiscontinuationDate());

        try (Connection conn = DAOFactory.getConnection()) {

            PreparedStatement state = conn.prepareStatement(String.format(CREATE_ONE, c.getName()),
                    Statement.RETURN_GENERATED_KEYS);

            state.setTimestamp(1, introductionDate);
            state.setTimestamp(2, discontinuationDate);

            state.setObject(3, companyId, java.sql.Types.INTEGER);

            int affectedRows = state.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.warn("Computer not created.");
            }

            ResultSet generatedKeys = state.getGeneratedKeys();
            generatedKeys.next();
            c.setId(generatedKeys.getLong(1));
            LOGGER.info("Computer created with id : " + generatedKeys.getLong(1));

        } catch (SQLException e) {
            e.printStackTrace();
            LOGGER.error(e.getMessage());
        }

        return Optional.ofNullable(c);
    }

    @Override
    public boolean delete(long id) {
        // TODO Auto-generated method stub
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            int result = state.executeUpdate(String.format(DELETE_ONE, id));
            state.close();
            if (result == 1) {
                LOGGER.info("Computer " + id + " deleted.");
            } else {
                LOGGER.warn("Computer " + id + " could not be deleted.");
            }
            return result == 1;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Computer c) {
        // TODO Auto-generated method stub
        Long companyId = (c.getCompany() != null ? c.getCompany().getId() : null);
        Timestamp introductionDate = mapper.getSqlTimestampValue(c.getIntroductionDate());
        Timestamp discontinuationDate = mapper.getSqlTimestampValue(c.getDiscontinuationDate());

        try (Connection conn = DAOFactory.getConnection()) {

            PreparedStatement state = conn.prepareStatement(String.format(UPDATE_ONE, c.getName(), c.getId()));

            state.setTimestamp(1, introductionDate);
            state.setTimestamp(2, discontinuationDate);
            state.setObject(3, companyId, java.sql.Types.INTEGER);

            int affectedRows = state.executeUpdate();
            if (affectedRows == 1) {
                LOGGER.info("Computer " + c.getId() + " updated.");
            } else {
                LOGGER.warn("Computer " + c.getId() + " might not have been updated. Affected Rows : " + affectedRows);
            }

            return affectedRows == 1;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            return false;
        }
    }

    @Override
    public List<Computer> list(int page, int itemPerPage) {
        // TODO Auto-generated method stub
        LinkedList<Computer> computers = new LinkedList<Computer>();

        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
            offsetClause += (page > 0 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

            ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
            while (result.next()) {
                Optional<Computer> c = mapper.queryResultToObject(result);
                if (c.isPresent()) {
                    computers.add(c.get());
                }
            }
            result.close();
            state.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        return computers;
    }

    @Override
    public int count() {
        // TODO Auto-generated method stub
        int count = -1;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();

            ResultSet result = state.executeQuery(COUNT_ALL);
            result.next();
            count = result.getInt("total");
            result.close();
            state.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }

        return count;
    }

    @Override
    public boolean deleteMany(String[] ids) {
        // TODO Auto-generated method stub
        try (Connection conn = DAOFactory.getConnection()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < ids.length; i++) {
                if (i != 0) {
                    sb.append(", ");
                }
                sb.append("?");
            }
            PreparedStatement state = conn.prepareStatement(String.format(DELETE_MANY, sb.toString()));
            for (int i = 0; i < ids.length; i++) {
                state.setInt(i + 1, Integer.parseInt(ids[i]));
            }

            int result = state.executeUpdate();
            state.close();
            return result > 0;

        } catch (NumberFormatException nfe) {
            LOGGER.error("deleteMany : " + nfe.getMessage());
            return false;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
