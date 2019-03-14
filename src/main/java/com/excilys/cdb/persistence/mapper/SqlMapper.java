package com.excilys.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public abstract class SqlMapper<T> {

	public abstract T queryResultToObject(ResultSet result) throws SQLException;

	public Timestamp getSqlTimestampValue(Date date) {
		return (date != null ? new Timestamp(date.getTime()) : null);
	}

	public Date getDateValue(Timestamp timestamp) {
		return (timestamp != null ? new Date(timestamp.getTime()) : null);
	}
}
