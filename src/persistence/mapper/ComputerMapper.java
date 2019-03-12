package persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import model.Company;
import model.Computer;
import model.ComputerFactory;

public class ComputerMapper extends SqlMapper<Computer> {
	
	
	/**
	 * Format a query result to a Computer object
	 * @param result ResultSet
	 * @return Computer
	 * @throws SQLException
	 */
	@Override
	public Computer queryResultToObject(ResultSet result) throws SQLException {
		int computerId = result.getInt(1);
		String computerName = result.getString(2);
		Date introduction = this.getDateValue(result.getTimestamp(3));
		Date discontinuation = this.getDateValue(result.getTimestamp(4));
		
		int companyId = result.getInt(5);
		String companyName = result.getString(7);
		Company company = new Company(companyId, companyName);
		
		return ComputerFactory.getInstance().createWithAll(computerId,computerName,introduction, discontinuation, company);
	}
}
