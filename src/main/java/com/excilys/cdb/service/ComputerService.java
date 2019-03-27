package com.excilys.cdb.service;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.exception.UnsuccessfulTreatmentException;
import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerDTO;
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
    public static final String ADD_COMPUTER_SUCCESS = "Computer successfully added.";
    public static final String ADD_COMPUTER_FAILURE = "Couldn't create the computer.";
    public static final String WRONG_DATE_FORMAT = "Wrong date format.";
    public static final String EMPTY_NAME = "Name cannot be empty.";
    public static final String INVALID_COMPANY = "Provided company is invalid";
    public static final String UNCONSISTANT_DATES = "Introduction and discontinuation dates are unconsistent.";

    /**
     * computerDAO ComputerDAO.
     */
    private ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDAO();

    /**
     * mapper ComputerDTOMapper.
     */
    private ComputerDTOMapper mapper;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(ComputerService.class);

    /**
     * Constructor.
     */
    public ComputerService() {
        mapper = new ComputerDTOMapper();
    }

    /**
     * List a specific range of computers.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<ComputerDTO>
     * @throws UnsuccessfulTreatmentException  ute
     */
    public List<ComputerDTO> list(int page, int itemPerPage) throws UnsuccessfulTreatmentException {
        LinkedList<ComputerDTO> computers = new LinkedList<ComputerDTO>();
        try {
            computerDAO.list(page, itemPerPage).stream().forEach(c -> {
                computers.add(mapper.toDTO(c));
            });
        } catch (EmptyNameException ene) {
            // TODO Auto-generated catch block
            logger.error(ene.getMessage());
            throw new UnsuccessfulTreatmentException("Fetched computer with empty name from persistence.");

        } catch (UnconsistentDatesException ude) {
            // TODO Auto-generated catch block
           logger.error(ude.getMessage());
           throw new UnsuccessfulTreatmentException("Fetched computer with unconsistent dates from persistence.");
        }
        return computers;
    }

    /**
     * Fetches a specific computer.
     *
     * @param id Long
     * @return Optional<ComputerDTO>
     * @throws UnconsistentDatesException
     * @throws EmptyNameException
     */
    public Optional<ComputerDTO> get(Long id) throws EmptyNameException, UnconsistentDatesException {
        ComputerDTO dto = null;
        Optional<Computer> opt = computerDAO.get(id);
        if (opt.isPresent()) {
            dto = mapper.toDTO(opt.get());
        }
        return Optional.ofNullable(dto);
    }

    /**
     * Saves a new Computer.
     *
     * @param c computer to be saved.
     * @return Optional<ComputerDTO>.
     * @throws ParseException        pe
     * @throws EmptyNameException    ene
     * @throws NumberFormatException nfe
     * @throws UnconsistentDatesException
     */
    public Optional<ComputerDTO> create(ComputerDTO computerDto)
            throws NumberFormatException, EmptyNameException, DateTimeParseException, UnconsistentDatesException {
        Optional<ComputerDTO> dto = Optional.empty();
        Optional<Computer> opt = computerDAO.create(mapper.toComputer(computerDto));
        if (opt.isPresent()) {
            dto = Optional.of(mapper.toDTO(opt.get()));
        }
        return dto;
    }

    /**
     * Update a specific computer.
     *
     * @param c Computer
     * @return boolean
     * @throws ParseException        pe
     * @throws EmptyNameException    ene
     * @throws NumberFormatException nfe
     * @throws UnconsistentDatesException ude
     */
    public boolean update(ComputerDTO computerDto) throws NumberFormatException, EmptyNameException, DateTimeParseException, UnconsistentDatesException {

        return computerDAO.update(mapper.toComputer(computerDto));
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
