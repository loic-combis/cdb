package com.excilys.cdb.persistence.dao.jdbc;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;
import com.excilys.cdb.persistence.mapper.ComputerMapper;

/**
 * Singleton Concrete implementation of ComputerDAO Responsible for bonding
 * Computer objects to the database thanks to JDBC
 * 
 * @author excilys
 *
 */
public class JdbcComputerDAO implements ComputerDAO {

	/**
	 * mapper ComputerMapper
	 */
	private ComputerMapper mapper = new ComputerMapper();

	/**
	 * instance JdbcComputerDAO - Unique instance of JdbcComputerDAO.
	 */
	private static JdbcComputerDAO instance;

	/**
	 * logger Logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(JdbcComputerDAO.class);

	/**
	 * String base SQL request.
	 */
	private static final String LIST_REQUEST = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id %s";
	private static final String FIND_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = %d";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id = %d";
	private static final String CREATE_ONE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES('%s', ?, ?, ?)";
	private static final String UPDATE_ONE = "UPDATE computer SET name='%s', introduced = ?, discontinued = ?, company_id = ? WHERE id = %d";

	/**
	 * Constructor Prevents from being instantiated outside the class.
	 * {@link JdbcComputerDAO#instance}
	 * 
	 * @param conn
	 */
	private JdbcComputerDAO() {
	}

	/**
	 * Creates or returns the unique instance of JdbcComputerDAO
	 * 
	 * @param conn Connection
	 * @return JdbcComputerDAO
	 */
	public static JdbcComputerDAO getInstance() {
		if (instance == null) {
			instance = new JdbcComputerDAO();
			logger.debug("JdbcComputerDAO instantiated");
		}
		return instance;
	}

	@Override
	public Computer get(long id) {
		// TODO Auto-generated method stub
		try (Connection conn = DAOFactory.getConnection()) {

			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
			Computer computer = result.next() ? mapper.queryResultToObject(result) : null;

			result.close();
			state.close();
			return computer;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Computer create(Computer c) {
		// TODO Auto-generated method stub
		if(c == null) {
			return null;
		}
		
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
				logger.warn("Computer not created.");
				return null;
			}

			ResultSet generatedKeys = state.getGeneratedKeys();
			generatedKeys.next();
			c.setId(generatedKeys.getLong(1));
			logger.info("Computer created with id : " + generatedKeys.getLong(1));
			return c;

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
	}

	@Override
	public Boolean delete(long id) {
		// TODO Auto-generated method stub
		try (Connection conn = DAOFactory.getConnection()) {

			Statement state = conn.createStatement();
			int result = state.executeUpdate(String.format(DELETE_ONE, id));
			state.close();
			if (result == 1) {
				logger.info("Computer " + id + " deleted.");
			} else {
				logger.warn("Computer " + id + " could not be deleted.");
			}
			return result == 1;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
			return false;
		}
	}

	@Override
	public Boolean update(Computer c) {
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
				logger.info("Computer " + c.getId() + " updated.");
			} else {
				logger.warn("Computer " + c.getId() + " might not have been updated. Affected Rows : " + affectedRows);
			}

			return affectedRows == 1;

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
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
				Computer c = mapper.queryResultToObject(result);
				computers.add(c);
			}
			result.close();
			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return computers;
	}

}
