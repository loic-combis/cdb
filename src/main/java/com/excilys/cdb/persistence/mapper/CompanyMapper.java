package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyFactory;

/**
 * Concrete implementation of SqlMapper for Companies. Responsible for building
 * a Company object from a SQL query result.
 *
 * @author excilys
 *
 */
public class CompanyMapper extends SqlMapper<Company> {

    @Override
    public Optional<Company> queryResultToObject(ResultSet result) throws SQLException {
        // TODO Auto-generated method stub
        Company company = null;
        if (result != null) {
            company = CompanyFactory.getInstance().create(result.getInt(1), result.getString(2));
        }
        return Optional.ofNullable(company);
    }

}
