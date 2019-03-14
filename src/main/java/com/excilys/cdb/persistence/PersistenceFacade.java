package com.excilys.cdb.persistence;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;

public class PersistenceFacade {

	private static PersistenceFacade instance;

	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;

	private PersistenceFacade() {
		computerDAO = DAOFactory.getInstance().getComputerDAO();
		companyDAO = (CompanyDAO) DAOFactory.getInstance().getCompanyDAO();
	}

	public static PersistenceFacade getInstance() {
		if (instance == null) {
			instance = new PersistenceFacade();
		}
		return instance;
	}

	public List<Computer> listComputers(int page, int itemPerPage) {
		return computerDAO.list(page, itemPerPage);
	}

	public List<Company> listCompanies(int page, int itemPerPage) {
		return companyDAO.list(page, itemPerPage);
	}

	public Computer getComputer(long id) {
		return computerDAO.get(id);
	}

	public Company getCompany(long id) {
		return companyDAO.get(id);
	}

	public Boolean deleteComputer(long id) {
		return computerDAO.delete(id);
	}

	public Computer create(Computer c) {
		return computerDAO.create(c);
	}

	public boolean update(Computer c) {
		return computerDAO.update(c);
	}
}
