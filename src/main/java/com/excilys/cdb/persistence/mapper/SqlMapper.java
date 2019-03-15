package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Abstract
 * 
 * @author excilys
 *
 * @param <T> the type of entity to be mapped.
 */
public abstract class SqlMapper<T> {

	/**
	 * Instantiate a T object from a query result.
	 * 
	 * @param result
	 * @return T
	 * @throws SQLException
	 */
	public abstract T queryResultToObject(ResultSet result) throws SQLException;

	/**
	 * Format a java.util.Date to java.sql.Timestamp
	 * 
	 * @param date Date
	 * @return Timestamp
	 */
	public Timestamp getSqlTimestampValue(Date date) {
		return (date != null ? new Timestamp(date.getTime()) : null);
	}

	/**
	 * Format a java.sql.Timestamp to a java.util.Date
	 * 
	 * @param timestamp Timestamp
	 * @return Date
	 */
	public Date getDateValue(Timestamp timestamp) {
		return (timestamp != null ? new Date(timestamp.getTime()) : null);
	}
}
