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

    /**
     * List a specific range of items.
     *
     * @param page int - Page to be fecthed.
     * @param itemPerPage int - Number of item per page.
     * @return List<T> - The lost of items fetched.
     */
    List<T> list(int page, int itemPerPage);

    /**
     * Fetch a specific item from the database.
     *
     * @param id long - ID of the item in the database.
     * @return T - An instance of the item.
     */
    T get(long id);
}
