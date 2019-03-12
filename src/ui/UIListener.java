package ui;

import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import model.Company;
import model.Computer;

public interface UIListener {

	public void defineProperty(PropertyChangeListener pcl);
	public void showComputer(Computer c);
	public void showCompany(Company c);
	public void showComputerList(LinkedList<Computer> computers);
}
