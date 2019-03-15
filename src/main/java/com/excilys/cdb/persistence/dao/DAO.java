package com.excilys.cdb.persistence.dao;

import java.util.List;

/**
 * Responsible for defining the common methods shared among all the DAO.
 * 
 * @author excilys
 *
 * @param <T> The entity the DAO manages.
 */
public interface DAO<T> {

	public List<T> list(int page, int itemPerPage);

	public T get(long id);
}
