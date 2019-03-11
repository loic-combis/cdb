package persistence;

import java.util.ArrayList;

import model.Computer;
//import persistence.dao.CompanyDAO;
import persistence.dao.ComputerDAO;
import persistence.dao.DAOFactory;

public class PersistenceFacade {
	
	private static PersistenceFacade instance;
	
	private ComputerDAO computerDAO;
	//private CompanyDAO companyDAO;
	
	private PersistenceFacade() {
		computerDAO = DAOFactory.getInstance().getComputerDAO();
		//companyDAO = (CompanyDAO) DAOFactory.getInstance().getCompanyDAO();	
	}
	
	public static PersistenceFacade getInstance() {
		if(instance == null) {
			instance = new PersistenceFacade();
		}
		return instance;
	}
	
	
	public ArrayList<Computer> listComputers(){
		return computerDAO.list();
	}
	
}
