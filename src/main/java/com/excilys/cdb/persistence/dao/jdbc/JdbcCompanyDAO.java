package com.excilys.cdb.persistence.dao.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.mapper.CompanyMapper;

public class JdbcCompanyDAO implements CompanyDAO {

	private static JdbcCompanyDAO instance;
	private Connection conn;
	private CompanyMapper mapper = new CompanyMapper();

	private static final String LIST_REQUEST = "SELECT * FROM company %s";
	private static final String FIND_BY_ID = "SELECT * FROM company WHERE id = %d";

	private JdbcCompanyDAO(Connection conn) {
		this.conn = conn;
	}

	public static JdbcCompanyDAO getInstance(Connection conn) {
		if (instance == null) {
			instance = new JdbcCompanyDAO(conn);
		}
		return instance;
	}

	@Override
	public Company get(long id) {
		// TODO Auto-generated method stub
		try {
			Statement state = conn.createStatement();
			ResultSet result = state.executeQuery(String.format(FIND_BY_ID, id));
			Company company = result.next() ? mapper.queryResultToObject(result) : null;
			result.close();
			state.close();
			return company;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Company> list(int page, int itemPerPage) {
		// TODO Auto-generated method stub
		LinkedList<Company> companies = new LinkedList<Company>();
		try {
			Statement state = conn.createStatement();
			String offsetClause = itemPerPage > 0 ? "LIMIT " + itemPerPage : "";
			offsetClause += (page > 1 && itemPerPage > 0) ? " OFFSET " + ((page - 1) * itemPerPage) : "";

			ResultSet result = state.executeQuery(String.format(LIST_REQUEST, offsetClause));
			while (result.next()) {
				Company c = mapper.queryResultToObject(result);
				companies.add(c);
			}
			result.close();
			state.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return companies;
	}
}