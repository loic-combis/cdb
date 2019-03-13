package ui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public abstract class UIManager {
	
	public static final String FETCH_COMPUTER = "Fetch the computer";
	public static final String CREATE_COMPUTER = "Create a new computer.";
	public static final String UPDATE_COMPUTER = "Update a computer.";
	public static final String DELETE_COMPUTER = "Delete the computer";
	public static final String LIST_COMPUTERS = "List the computers.";
	public static final String LIST_COMPANIES = "List the companies.";
	public static final String UNKNOWN_ACTION = "Action is unknown.";
	
	protected PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	
	public UIManager(PropertyChangeListener pcl) {
		this.pcs.addPropertyChangeListener(pcl);
	}
	
	public abstract void start();
	public abstract void stop();
	
	public abstract Presenter getPresenter();
}
