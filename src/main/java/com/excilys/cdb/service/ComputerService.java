package com.excilys.cdb.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerDTO;
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
     * Feedback messages.
     */
    public static final String DELETE_MANY_SUCCESS = "Deletion successful.";
    public static final String DELETE_MANY_FAILURE = "Deletion unsuccessfull.";

    /**
     * computerDAO ComputerDAO.
     */
    private ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDAO();

    /**
     * mapper ComputerDTOMapper.
     */
    private ComputerDTOMapper mapper;

    /**
     * Constructor.
     */
    public ComputerService() {
        mapper = new ComputerDTOMapper();
    }

    /**
     * List a specific range of computers.
     *
     * @param page int
     * @param itemPerPage int
     * @return List<ComputerDTO>
     */
    public List<ComputerDTO> list(int page, int itemPerPage) {
        LinkedList<ComputerDTO> computers = new LinkedList<ComputerDTO>();
        computerDAO.list(page, itemPerPage).stream().forEach(c -> {
            computers.add(mapper.toDTO(c));
        });
        return computers;
    }

    /**
     * Fetches a specific computer.
     *
     * @param id long
     * @return Optional<ComputerDTO>
     */
    public Optional<ComputerDTO> get(long id) {
        ComputerDTO dto = null;
        Optional<Computer> opt = computerDAO.get(id);
        if(opt.isPresent()) {
            dto = mapper.toDTO(opt.get());
        }
        return Optional.ofNullable(dto);
    }

    /**
     * Saves a new Computer.
     *
     * @param c computer to be saved.
     * @return Optional<ComputerDTO>.
     */
    public Optional<ComputerDTO> create(ComputerDTO c) {
        Optional<ComputerDTO> dto = null;
        Optional<Computer> opt = computerDAO.create(mapper.toComputer(c));
        if(opt.isPresent()) {
            dto = Optional.of(mapper.toDTO(opt.get()));
        }
        return dto;
    }

    /**
     * Update a specific computer.
     *
     * @param c Computer
     * @return boolean
     */
    public boolean update(ComputerDTO c) {

        return computerDAO.update(mapper.toComputer(c));
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
