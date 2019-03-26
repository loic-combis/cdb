package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerFactory;

/**
 * Concrete implementation of SQLMapper for computers.
 *
 * @author excilys
 *
 */
public class ComputerSQLMapper extends SqlMapper<Computer> {

    @Override
    public Computer queryResultToObject(ResultSet result) throws SQLException {

        Computer computer = null;

        if (result != null) {

            Long computerId = result.getLong(1);
            String computerName = result.getString(2);
            Date introduction = getDateValue(result.getTimestamp(3));
            Date discontinuation = getDateValue(result.getTimestamp(4));

            Long companyId = result.getLong(5);
            String companyName = result.getString(7);
            Company company = CompanyFactory.getInstance().create(companyId, companyName);

            computer = ComputerFactory.getInstance().createWithAll(computerId, computerName, introduction,
                    discontinuation, company);
        }

        return computer;
    }
}
