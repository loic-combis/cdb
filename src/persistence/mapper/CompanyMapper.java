package persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.Company;
import model.CompanyFactory;

public class CompanyMapper extends SqlMapper<Company> {

	@Override
	public Company queryResultToObject(ResultSet result) throws SQLException {
		// TODO Auto-generated method stub
		return CompanyFactory.getInstance().create(result.getInt(1), result.getString(2));
	}

}
