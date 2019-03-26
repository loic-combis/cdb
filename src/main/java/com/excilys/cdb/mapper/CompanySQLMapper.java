package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;

/**
 * Concrete implementation of SqlMapper for Companies. Responsible for building
 * a Company object from a SQL query result.
 *
 * @author excilys
 *
 */
public class CompanySQLMapper extends SqlMapper<Company> {

    @Override
    public Company queryResultToObject(ResultSet result) throws SQLException {
        // TODO Auto-generated method stub
        Company company = null;
        if (result != null) {
            company = CompanyFactory.getInstance().create(result.getLong(1), result.getString(2));
        }
        return company;
    }

}
