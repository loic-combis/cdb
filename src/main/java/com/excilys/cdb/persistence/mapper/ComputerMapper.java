package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerFactory;

/**
 * Concrete implementation of SQLMapper for computers.
 * 
 * @author excilys
 *
 */
public class ComputerMapper extends SqlMapper<Computer> {

	@Override
	public Computer queryResultToObject(ResultSet result) throws SQLException {
		if(result == null) {
			return null;
		}
		int computerId = result.getInt(1);
		String computerName = result.getString(2);
		Date introduction = this.getDateValue(result.getTimestamp(3));
		Date discontinuation = this.getDateValue(result.getTimestamp(4));

		int companyId = result.getInt(5);
		String companyName = result.getString(7);
		Company company = new Company(companyId, companyName);

		return ComputerFactory.getInstance().createWithAll(computerId, computerName, introduction, discontinuation,
				company);
	}
}
