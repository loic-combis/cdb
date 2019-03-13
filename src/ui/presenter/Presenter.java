package ui.presenter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import model.Company;
import model.Computer;

public abstract class Presenter {

	public static final String FETCH_COMPUTER = "Fetch the computer";
	public static final String CREATE_COMPUTER = "Create a new computer.";
	public static final String UPDATE_COMPUTER = "Update a computer.";
	public static final String DELETE_COMPUTER = "Delete the computer";
	public static final String LIST_COMPUTERS = "List the computers.";
	public static final String LIST_COMPANIES = "List the companies.";
	public static final String UNKNOWN_ACTION = "Action is unknown.";
	
	public static final String CREATE_SUCCESS = "Creation successful";
	public static final String CREATE_FAIL = "Creation unsuccessful";
	public static final String DELETE_SUCCESS = "Deletion successful.";
	public static final String DELETE_FAIL = "Deletion unsuccessful";
	public static final String UPDATE_SUCCESS = "Update successful";
	public static final String UPDATE_FAIL = "Update unsuccessful";
	
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public Presenter(PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}
	
	public abstract void start();
	public abstract void stop();
	
	public abstract void notify(String s);
	public abstract void present(Computer c);
	public abstract void presentComputers(List<Computer> computers);
	
	public abstract void present(Company c);
	public abstract void presentCompanies(List<Company> c);
	
	 

}
