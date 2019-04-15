package com.excilys.cdb.persistence.dao;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
@Lazy
@Repository("computerDAO")
public class ComputerDAO {

	/**
	 * mapper ComputerMapper.
	 */
	private ComputerSQLMapper mapper;

	private NamedParameterJdbcTemplate template;

	private final Logger LOGGER = LoggerFactory.getLogger(ComputerDAO.class);

	/**
	 * String base SQL request.
	 */
	private static final String LIST_REQUEST = "SELECT *, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id %s %s %s";
	private static final String FIND_BY_ID = "SELECT * FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = :id";
	private static final String DELETE_ONE = "DELETE FROM computer WHERE id = :id";
	private static final String CREATE_ONE = "INSERT INTO computer(name, introduced, discontinued, company_id) VALUES(:name, :introduced, :discontinued, :company_id)";
	private static final String UPDATE_ONE = "UPDATE computer SET name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id WHERE id = :id";
	private static final String COUNT_ALL = "SELECT COUNT(*) AS total FROM computer LEFT JOIN company ON computer.company_id = company.id %s";
	private static final String DELETE_MANY = "DELETE FROM computer WHERE id IN (:ids)";

	private static final String SEARCH_WHERE_CLAUSE = "WHERE computer.name LIKE '%%%s%%' OR company.name LIKE '%%%s%%'";
	private static final String ORDER_BY_CLAUSE = "ORDER BY %s";

	/**
	 * Constructor.
	 *
	 * @param ds        DataSource
	 * @param sqlMapper ComputerSQLMapper
	 */
	public ComputerDAO(NamedParameterJdbcTemplate jdbcTemplate, ComputerSQLMapper sqlMapper) {
		mapper = sqlMapper;
		template = jdbcTemplate;
	}

	/**
	 * Fetch a specific computer.
	 *
	 * @param id Long
	 * @return Optional<Computer>
	 * @throws EmptyNameException         ene
	 * @throws UnconsistentDatesException ude
	 */
	@Transactional(readOnly = true)
	public Optional<Computer> get(Long id) throws EmptyNameException, UnconsistentDatesException {
		Computer computer = null;
		try {
			computer = template.queryForObject(FIND_BY_ID, new MapSqlParameterSource("id", id), mapper);
		} catch (EmptyResultDataAccessException e) {
			LOGGER.debug("Tried to fetch non existing computer with id : " + id);
		}

		return Optional.ofNullable(computer);
	}

	/**
	 * Create a new computer.
	 *
	 * @param computer Computer
	 * @return Optional<Computer>
	 */
	@Transactional(readOnly = false)
	public boolean create(Computer computer) {

		// TODO Auto-generated method stub
		Long companyId = (computer.getCompany() != null ? computer.getCompany().getId() : null);

		Timestamp introductionDate = mapper.getSqlTimestampValue(computer.getIntroductionDate());
		Timestamp discontinuationDate = mapper.getSqlTimestampValue(computer.getDiscontinuationDate());

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", computer.getName());
		parameters.addValue("introduced", introductionDate);
		parameters.addValue("discontinued", discontinuationDate);
		parameters.addValue("company_id", companyId);

		template.update(CREATE_ONE, parameters);

		return true;
	}

	/**
	 * Delete a specific computer.
	 *
	 * @param id Long
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean delete(Long id) {
		// TODO Auto-generated method stub
		int affectedRowsCount = template.update(DELETE_ONE, new MapSqlParameterSource("id", id));
		return affectedRowsCount > 0;
	}

	/**
	 * Update a specific computer.
	 *
	 * @param computer Computer
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean update(Computer computer) {
		// TODO Auto-generated method stub
		Long companyId = (computer.getCompany() != null ? computer.getCompany().getId() : null);
		Timestamp introductionDate = mapper.getSqlTimestampValue(computer.getIntroductionDate());
		Timestamp discontinuationDate = mapper.getSqlTimestampValue(computer.getDiscontinuationDate());

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", computer.getName());
		parameters.addValue("introduced", introductionDate);
		parameters.addValue("discontinued", discontinuationDate);
		parameters.addValue("company_id", companyId);
		parameters.addValue("id", computer.getId());

		int affectedRowsCount = template.update(UPDATE_ONE, parameters);

		return affectedRowsCount > 0;
	}

	/**
	 * List a specific range of computers.
	 *
	 * @param page        int
	 * @param itemPerPage int
	 * @param search      String
	 * @param orderBy     String
	 * @return List<Computer>
	 * @throws EmptyNameException         ene
	 * @throws UnconsistentDatesException ude
	 */
	@Transactional(readOnly = true)
	public List<Computer> list(int page, int itemPerPage, String search, String orderBy) {
		// TODO Auto-generated method stub

		// Add limit / Offset clause.
		String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
		offsetClause += (page > 0 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

		// Add where clause
		String whereClause = search != null && !search.equals("") ? String.format(SEARCH_WHERE_CLAUSE, search, search)
				: "";

		// OrderBy clause.
		String orderByClause = "";
		if (orderBy != null && !orderBy.equals("")) {
			switch (orderBy) {
			case "name":
				orderByClause = String.format(ORDER_BY_CLAUSE, "computer.name");
				break;
			case "introduced":
				orderByClause = String.format(ORDER_BY_CLAUSE, "computer.introduced");
				break;
			case "discontinued":
				orderByClause = String.format(ORDER_BY_CLAUSE, "computer.discontinued");
				break;
			case "company":
				orderByClause = String.format(ORDER_BY_CLAUSE, "company.name");
				break;
			}
		}

		return template.query(String.format(LIST_REQUEST, whereClause, orderByClause, offsetClause), mapper);
	}

	/**
	 * Count the number of computers.
	 *
	 * @param search String
	 * @return int
	 */
	@Transactional(readOnly = true)
	public int count(String search) {
		// TODO Auto-generated method stub
		String whereClause = search != null && !search.equals("") ? String.format(SEARCH_WHERE_CLAUSE, search, search)
				: "";
		return template.queryForObject(String.format(COUNT_ALL, whereClause), new MapSqlParameterSource(),
				Integer.class);
	}

	/**
	 * Delete a set of computers.
	 *
	 * @param ids String[]
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean deleteMany(String[] ids) {
		// TODO Auto-generated method stub
		LOGGER.error(ids.toString());
		int affectedRowsCount = template.update(DELETE_MANY, Collections.singletonMap("ids", Arrays.asList(ids)));

		return affectedRowsCount > 0;
	}

}
