package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;

/**
 * Manage the business logic for computers.
 *
 * @author excilys
 *
 */
public class ComputerService {

    /**
     * computerDAO ComputerDAO.
     */
    ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDAO();

    /**
     * List a specific range of computers.
     *
     * @param page int
     * @param itemPerPage int
     * @return List<Computer>
     */
    public List<Computer> list(int page, int itemPerPage) {
        return computerDAO.list(page, itemPerPage);
    }

    /**
     * Fetches a specific computer.
     *
     * @param id long
     * @return Optional<Computer>
     */
    public Optional<Computer> get(long id) {
        return computerDAO.get(id);
    }

    /**
     * Saves a new Computer.
     *
     * @param c computer to be saved.
     * @return Optional<Computer>.
     */
    public Optional<Computer> create(Computer c) {
        return computerDAO.create(c);
    }

    /**
     * Update a specific computer.
     *
     * @param c Computer
     * @return boolean
     */
    public boolean update(Computer c) {
        return computerDAO.update(c);
    }

    /**
     * Delete a specific computer.
     *
     * @param id long
     * @return boolean
     */
    public boolean delete(long id) {
        return computerDAO.delete(id);
    }

    /**
     * Count the number of computer stored.
     *
     * @return int
     */
    public int count() {
        return computerDAO.count();
    }

    /**
     * Delete a set of computers.
     *
     * @param ids String[]
     * @return boolean.
     */
    public boolean deleteMany(String[] ids) {
        return computerDAO.deleteMany(ids);
    }
}
