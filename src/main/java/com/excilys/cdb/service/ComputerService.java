package com.excilys.cdb.service;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;

/**
 * Manage the business logic for computers.
 *
 * @author excilys
 *
 */
@Lazy
@Service("computerService")
public class ComputerService {

    /**
     * Feedback messages.
     */
    public static final String DELETE_MANY_SUCCESS = "delete.many.success";
    public static final String DELETE_MANY_FAILURE = "delete.many.failure";
    public static final String ADD_COMPUTER_SUCCESS = "add.computer.success";
    public static final String ADD_COMPUTER_FAILURE = "add.computer.failure";
    public static final String EDIT_COMPUTER_SUCCESS = "edit.computer.success";
    public static final String EDIT_COMPUTER_FAILURE = "edit.computer.failure";

    /**
     * computerDAO ComputerDAO.
     */
    private ComputerDAO computerDAO;

    /**
     * Constructor.
     *
     * @param dao ComputerDAO
     */
    public ComputerService(ComputerDAO dao) {
        computerDAO = dao;
    }

    /**
     * List a specific range of computers.
     *
     * @param page        int
     * @param itemPerPage int
     * @param search      String
     * @param orderBy     String
     * @return List<Computer>
     */
    public List<Computer> list(int page, int itemPerPage, String search, String orderBy) {
        return computerDAO.list(page, itemPerPage, search, orderBy);
    }

    /**
     * Fetches a specific computer.
     *
     * @param id Long
     * @return Optional<Computer>
     */
    public Optional<Computer> get(Long id) {
        return computerDAO.get(id);
    }

    /**
     * Saves a new Computer.
     *
     * @param computer Computer
     * @return boolean
     * @throws DateTimeParseException pe
     * @throws NumberFormatException  nfe
     */
    public boolean create(Computer computer) throws NumberFormatException, DateTimeParseException {
        return computerDAO.create(computer);
    }

    /**
     * Update a specific computer.
     *
     * @param computer Computer
     * @return boolean
     */
    public boolean update(Computer computer) {
        return computerDAO.update(computer);
    }

    /**
     * Delete a specific computer.
     *
     * @param id Long
     * @return boolean
     */
    public boolean delete(Long id) {
        return computerDAO.delete(id);
    }

    /**
     * Count the number of computer stored.
     *
     * @param search String
     * @return Long
     */
    public Long count(String search) {
        return computerDAO.count(search);
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
