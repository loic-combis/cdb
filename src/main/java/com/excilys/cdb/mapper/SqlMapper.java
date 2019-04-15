package com.excilys.cdb.mapper;

import java.sql.Timestamp;
import java.time.LocalDate;

/**
 * Abstract.
 *
 * @author excilys
 *
 */
public abstract class SqlMapper {

	/**
	 * Format a LocalDate to java.sql.Timestamp.
	 *
	 * @param date LocalDate
	 * @return Timestamp
	 */
	public Timestamp getSqlTimestampValue(LocalDate date) {
		return (date != null ? Timestamp.valueOf(date.atStartOfDay()) : null);
	}

	/**
	 * Format a java.sql.Timestamp to a LocalDate.
	 *
	 * @param timestamp Timestamp
	 * @return LocalDate
	 */
	public LocalDate getDateValue(Timestamp timestamp) {
		return (timestamp != null ? timestamp.toLocalDateTime().toLocalDate() : null);
	}
}
