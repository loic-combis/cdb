package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.jdbc.core.RowMapper;
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
public class ComputerSQLMapper extends SqlMapper implements RowMapper<Computer> {

	private final Logger LOGGER = LoggerFactory.getLogger(ComputerSQLMapper.class);

	@Override
	public Computer mapRow(ResultSet result, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		Computer computer = null;

		if (result != null) {

			Long computerId = result.getLong(1);
			String computerName = result.getString(2);
			LocalDate introduction = getDateValue(result.getTimestamp(3));
			LocalDate discontinuation = getDateValue(result.getTimestamp(4));

			Long companyId = result.getLong(5) == 0L ? null : result.getLong(5);
			String companyName = result.getString(7);
			Company company = CompanyFactory.getInstance().create(companyId, companyName);
			try {
				computer = ComputerFactory.getInstance().createWithAll(computerId, computerName, introduction,
						discontinuation, company);
			} catch (EmptyNameException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			} catch (UnconsistentDatesException e) {
				// TODO Auto-generated catch block
				LOGGER.error(e.getMessage());
			}
		}

		return computer;
	}
}
