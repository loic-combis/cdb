package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
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
@Lazy
@Component("computerSQLMapper")
public class ComputerSQLMapper extends SqlMapper<Computer> {

	@Override
	public Computer queryResultToObject(ResultSet result)
			throws SQLException, EmptyNameException, UnconsistentDatesException {

		Computer computer = null;

		if (result != null) {

			Long computerId = result.getLong(1);
			String computerName = result.getString(2);
			LocalDate introduction = getDateValue(result.getTimestamp(3));
			LocalDate discontinuation = getDateValue(result.getTimestamp(4));

			Long companyId = result.getLong(5) == 0L ? null : result.getLong(5);
			String companyName = result.getString(7);
			Company company = CompanyFactory.getInstance().create(companyId, companyName);
			computer = ComputerFactory.getInstance().createWithAll(computerId, computerName, introduction,
					discontinuation, company);
		}

		return computer;
	}
}
