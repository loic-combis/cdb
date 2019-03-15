package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;

/**
 * Singleton Responsible for mapping the client calls to the good DAO.
 * 
 * @author excilys
 *
 */
public class PersistenceFacade {

	/**
	 * instance PersistenceFacade - Unique instance of PersistenceFacade
	 */
	private static PersistenceFacade instance;

	/**
	 * computerDao ComputerDAO Responsible for bonding the Computer instances to the
	 * database.
	 */
	private ComputerDAO computerDAO;

	/**
	 * companyDAO CompanyDAO Responsible for bonding the Company instances to the
	 * database.
	 */
	private CompanyDAO companyDAO;

	/**
	 * Constructor Get the instance of all the DAO Prevent from being instantiated
	 * outside the class. {@link PersistenceFacade#computerDAO}
	 * {@link PersistenceFacade#companyDAO}
	 */
	private PersistenceFacade() {
		computerDAO = DAOFactory.getInstance().getComputerDAO();
		companyDAO = (CompanyDAO) DAOFactory.getInstance().getCompanyDAO();
	}

	/**
	 * Creates or return the only instance of PersistenceFacade (Lazy instantiation)
	 * 
	 * @return PersistenceFacade
	 */
	public static PersistenceFacade getInstance() {
		if (instance == null) {
			instance = new PersistenceFacade();
		}
		return instance;
	}

	/**
	 * List the computers in a specified range.
	 * 
	 * @param page        int - Specifies the offset while fetching the computers.
	 * @param itemPerPage int - Specifies the max number of computers in the list.
	 * @return List<Computer>
	 */
	public List<Computer> listComputers(int page, int itemPerPage) {
		return computerDAO.list(page, itemPerPage);
	}

	/**
	 * List the companies in a specified range
	 * 
	 * @param page        int - Specifies the offset while fetching the companies
	 * @param itemPerPage int - Specifies the max number of companies in the list.
	 * @return List<Company>
	 */
	public List<Company> listCompanies(int page, int itemPerPage) {
		return companyDAO.list(page, itemPerPage);
	}

	/**
	 * Fetches the computer with the specified id.
	 * 
	 * @param id long - Id of the computer to be fetched.
	 * @return Computer.
	 */
	public Computer getComputer(long id) {
		return computerDAO.get(id);
	}

	/**
	 * Fetches the company with the specified id.
	 * 
	 * @param id long - Id of the company to be fetched.
	 * @return Company
	 */
	public Company getCompany(long id) {
		return companyDAO.get(id);
	}

	/**
	 * Deletes the computer with the specified id in the database.
	 * 
	 * @param id long - Id of the computer to be deleted.
	 * @return Boolean - True if the deletion is successful, false otherwise.
	 */
	public Boolean deleteComputer(long id) {
		return computerDAO.delete(id);
	}

	/**
	 * Saves the specified computer to the database.
	 * 
	 * @param c Computer - Computer to be saved.
	 * @return Computer - Computer with its unique id in the database.
	 */
	public Computer create(Computer c) {
		return computerDAO.create(c);
	}

	/**
	 * Update the specified computer in the database.
	 * 
	 * @param c Computer - The computer to be updated.
	 * @return boolean - True if the update is successful, false otherwise.
	 */
	public boolean update(Computer c) {
		return computerDAO.update(c);
	}
}
