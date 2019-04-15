package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	private CompanySQLMapper mapper;

	private NamedParameterJdbcTemplate template;

	/**
	 * LOGGER Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

	/**
	 * String base SQL request.
	 */
	private static final String LIST_REQUEST = "SELECT * FROM company %s";
	private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = :id";
	private static final String DELETE = "DELETE FROM company WHERE id = :id";
	private static final String DELETE_RELATED_COMPUTERS = "DELETE FROM computer WHERE company_id = :id";

	/**
	 * Constructor.
	 *
	 * @param ds        DataSource
	 * @param sqlMapper CompanySQLMapper
	 */
	public CompanyDAO(NamedParameterJdbcTemplate jdbcTemplate, CompanySQLMapper sqlMapper) {
		template = jdbcTemplate;
		mapper = sqlMapper;

	}

	/**
	 * Fetch the specified computer from the persistence.
	 *
	 * @param id Long.
	 * @return Optional<Company>
	 */
	@Transactional(readOnly = true)
	public Optional<Company> get(Long id) {
		// TODO Auto-generated method stub

		Company company = template.queryForObject(FIND_BY_ID, new MapSqlParameterSource("id", id), mapper);
		LOGGER.debug("Company fetched : " + company);
		return Optional.ofNullable(company);
	}

	/**
	 * Fetch a specific range of companies.
	 *
	 * @param page        int
	 * @param itemPerPage int
	 * @return List<Company>
	 */
	@Transactional(readOnly = true)
	public List<Company> list(int page, int itemPerPage) {
		// TODO Auto-generated method stub
		String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
		offsetClause += (page > 1 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

		return template.query(String.format(LIST_REQUEST, offsetClause), mapper);

	}

	/**
	 * Delete the specified company and all the related computers.
	 *
	 * @param id Long
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean delete(Long id) {

		template.update(DELETE_RELATED_COMPUTERS, new MapSqlParameterSource("id", id));
		int affectRowsCount = template.update(DELETE, new MapSqlParameterSource("id", id));

		LOGGER.debug("Company deleted : " + id);

		return affectRowsCount > 0;

	}
}