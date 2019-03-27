package com.excilys.cdb.persistence.dao;

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

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.mapper.ComputerSQLMapper;
import com.excilys.cdb.model.computer.Computer;

/**
 * Singleton Concrete implementation of ComputerDAO Responsible for bonding
 * Computer objects to the database thanks to JDBC.
 *
 * @author excilys
 *
 */
public class ComputerDAO {

    /**
     * mapper ComputerMapper.
     */
    private ComputerSQLMapper mapper = new ComputerSQLMapper();

    /**
     * instance JdbcComputerDAO - Unique instance of JdbcComputerDAO.
     */
    private static ComputerDAO instance;

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

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
     * {@link ComputerDAO#instance}
     */
    private ComputerDAO() {
    }

    /**
     * Creates or returns the unique instance of JdbcComputerDAO.
     *
     * @return JdbcComputerDAO
     */
    public static ComputerDAO getInstance() {
        if (instance == null) {
            instance = new ComputerDAO();
            LOGGER.debug("JdbcComputerDAO instantiated");
        }
        return instance;
    }

    /**
     * Fetch a specific computer.
     *
     * @param id Long
     * @return Optional<Computer>
     * @throws EmptyNameException ene
     * @throws UnconsistentDatesException ude
     */
    public Optional<Computer> get(Long id) throws EmptyNameException, UnconsistentDatesException {
        Computer computer = null;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
            computer = result.next() ? mapper.queryResultToObject(result) : null;

            result.close();
            state.close();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }
        return Optional.ofNullable(computer);
    }

    /**
     * Create a new computer.
     *
     * @param c Computer
     * @return Optional<Computer>
     */
    public Optional<Computer> create(Computer computer) {
        Optional<Computer> savedComputer = Optional.empty();

        if (computer == null) {
            return savedComputer;
        }
        // TODO Auto-generated method stub
        Long companyId = (computer.getCompany() != null ? computer.getCompany().getId() : null);

        Timestamp introductionDate = mapper.getSqlTimestampValue(computer.getIntroductionDate());
        Timestamp discontinuationDate = mapper.getSqlTimestampValue(computer.getDiscontinuationDate());

        try (Connection conn = DAOFactory.getConnection()) {

            PreparedStatement state = conn.prepareStatement(String.format(CREATE_ONE, computer.getName()),
                    Statement.RETURN_GENERATED_KEYS);

            state.setTimestamp(1, introductionDate);
            state.setTimestamp(2, discontinuationDate);

            state.setObject(3, companyId, java.sql.Types.INTEGER);

            int affectedRows = state.executeUpdate();
            if (affectedRows == 0) {
                LOGGER.warn("Computer not created.");
                return savedComputer;
            }

            ResultSet generatedKeys = state.getGeneratedKeys();
            generatedKeys.next();
            computer.setId(generatedKeys.getLong(1));
            savedComputer = Optional.ofNullable(computer);
            LOGGER.info("Computer created with id : " + generatedKeys.getLong(1));

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }

        return savedComputer;
    }

    /**
     * Delete a specific computer.
     *
     * @param id Long
     * @return boolean
     */
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        boolean isSuccess = false;
        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            int result = state.executeUpdate(String.format(DELETE_ONE, id));
            state.close();
            if (result == 1) {
                LOGGER.info("Computer " + id + " deleted.");
            } else {
                LOGGER.warn("Computer " + id + " could not be deleted.");
            }
            isSuccess = result == 1;

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }

        return isSuccess;
    }

    /**
     * Update a specific computer.
     *
     * @param computer Computer
     * @return
     */
    public boolean update(Computer computer) {
        // TODO Auto-generated method stub
        boolean isSuccess = false;
        Long companyId = (computer.getCompany() != null ? computer.getCompany().getId() : null);
        Timestamp introductionDate = mapper.getSqlTimestampValue(computer.getIntroductionDate());
        Timestamp discontinuationDate = mapper.getSqlTimestampValue(computer.getDiscontinuationDate());

        try (Connection conn = DAOFactory.getConnection()) {

            PreparedStatement state = conn.prepareStatement(String.format(UPDATE_ONE, computer.getName(), computer.getId()));

            state.setTimestamp(1, introductionDate);
            state.setTimestamp(2, discontinuationDate);
            state.setObject(3, companyId, java.sql.Types.INTEGER);

            int affectedRows = state.executeUpdate();
            if (affectedRows == 1) {
                LOGGER.info("Computer " + computer.getId() + " updated.");
            } else {
                LOGGER.warn("Computer " + computer.getId() + " might not have been updated. Affected Rows : " + affectedRows);
            }

            isSuccess = affectedRows == 1;

        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }

        return isSuccess;
    }

    /**
     * List a specific range of computers.
     *
     * @param page int
     * @param itemPerPage int
     * @return List<Computer>
     * @throws EmptyNameException ene
     * @throws UnconsistentDatesException ude
     */
    public List<Computer> list(int page, int itemPerPage) throws EmptyNameException, UnconsistentDatesException {
        // TODO Auto-generated method stub
        LinkedList<Computer> computers = new LinkedList<Computer>();

        try (Connection conn = DAOFactory.getConnection()) {

            Statement state = conn.createStatement();
            String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
            offsetClause += (page > 0 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

            ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
            while (result.next()) {
                Computer c = mapper.queryResultToObject(result);
                if (c != null) {
                    computers.add(c);
                }
            }
            result.close();
            state.close();

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }
        return computers;
    }

    /**
     * Count the number of computers.
     *
     * @return int
     */
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

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }

        return count;
    }

    /**
     * Delete a set of computers.
     *
     * @param ids String[]
     * @return boolean
     */
    public boolean deleteMany(String[] ids) {
        // TODO Auto-generated method stub
        boolean isSuccess = false;
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
            isSuccess = result > 0;

        } catch (NumberFormatException nfe) {
            LOGGER.error("deleteMany : " + nfe.getMessage());
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());

        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            LOGGER.error(e1.getMessage());
        }

        return isSuccess;
    }

}
