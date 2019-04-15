package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;

/**
 * Concrete implementation of SqlMapper for Companies. Responsible for building
 * a Company object from a SQL query result.
 *
 * @author excilys
 *
 */
@Lazy
@Component("companySQLMapper")
public class CompanySQLMapper extends SqlMapper implements RowMapper<Company> {

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Company company = null;
		if (rs != null) {
			company = CompanyFactory.getInstance().create(rs.getLong(1), rs.getString(2));
		}
		return company;
	}

}
