package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

import com.excilys.cdb.exception.UnconsistentDatesException;

/**
 * Abstract.
 *
 * @author excilys
 *
 * @param <T> the type of entity to be mapped.
 */
public abstract class SqlMapper<T> {

    /**
     * Instantiate a T object from a query result.
     *
     * @param result ResultSet
     * @return T
     * @throws SQLException               sqle
     * @throws UnconsistentDatesException ude
     */
    public abstract T queryResultToObject(ResultSet result) throws SQLException, UnconsistentDatesException;

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
