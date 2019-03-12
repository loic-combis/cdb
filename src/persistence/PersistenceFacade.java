package persistence;

import java.util.LinkedList;

import model.Company;
import model.Computer;
import persistence.dao.CompanyDAO;
import persistence.dao.ComputerDAO;
import persistence.dao.DAOFactory;

public class PersistenceFacade {
	
	private static PersistenceFacade instance;
	
	private ComputerDAO computerDAO;
	private CompanyDAO companyDAO;
	
	private PersistenceFacade() {
		computerDAO = DAOFactory.getInstance().getComputerDAO();
		companyDAO = (CompanyDAO) DAOFactory.getInstance().getCompanyDAO();	
	}
	
	public static PersistenceFacade getInstance() {
		if(instance == null) {
			instance = new PersistenceFacade();
		}
		return instance;
	}
	
	
	public LinkedList<Computer> listComputers(){
		return computerDAO.list();
	}
	
	public LinkedList<Company> listCompanies() {
		return companyDAO.list();
	}
	
	public Computer getComputer(int id) {
		return computerDAO.get(id);
	}
	
	public Company getCompany(int id) {
		return companyDAO.get(id);
	}
	
	public Boolean deleteComputer(int id) {
		return computerDAO.delete(id);
	}
	
	public Computer createComputer(Computer c) {
		return computerDAO.create(c);
	}
}
