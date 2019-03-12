package ui.cli;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;

import model.Company;
import model.Computer;
import ui.UILayer;
import ui.UIListener;

public class CLI implements UIListener {

	
	private PropertyChangeSupport pcs = new PropertyChangeSupport(this);
	private  UILayer uiLayer;
	
	public CLI() {
		uiLayer = new UILayer();
		uiLayer.registerListener(this);
		start();
	}
	@Override
	public void defineProperty(PropertyChangeListener pcl) {
		// TODO Auto-generated method stub
		pcs.addPropertyChangeListener(pcl);
	}
	
	@Override
	public void showComputer(Computer c) {
		// TODO Auto-generated method stub
		System.out.println(c);
	}

	@Override
	public void showCompany(Company c) {
		// TODO Auto-generated method stub
		System.out.println(c);
	}
	
	@Override
	public void showComputerList(LinkedList<Computer> computers) {
		// TODO Auto-generated method stub
		for(Computer c : computers) {
			System.out.println(c);
			System.out.println("-----------------------------------------------------");
		}
	}
	
	private void start(){
		System.out.println("Welcome to the Application CLI");
		//pcs.firePropertyChange("SHOW_COMPUTER", 0L, 50L);
		pcs.firePropertyChange("LIST_COMPUTERS", null, null);
		System.out.println("Coucou");
	}


}
