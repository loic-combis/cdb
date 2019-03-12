package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import model.Company;
import model.Computer;
import persistence.PersistenceFacade;

public class UILayer implements PropertyChangeListener {

	private PersistenceFacade persistenceFacade;
	
	private UIListener uiListener;
	
	public UILayer() {
		persistenceFacade = PersistenceFacade.getInstance();
	}
	
	public void registerListener(UIListener uiListener) {
		this.uiListener = uiListener;
		this.uiListener.defineProperty(this);
	}
	
	private void getComputer(long id) {
		Computer c = persistenceFacade.getComputer(id);
		uiListener.showComputer(c);
	}
	
	private void getCompany(long id) {
		Company c = persistenceFacade.getCompany(id);
		uiListener.showCompany(c);
	}

	private void listComputers() {
		LinkedList<Computer> computers = persistenceFacade.listComputers();
		uiListener.showComputerList(computers);
	}
	@Override
	public void propertyChange(PropertyChangeEvent e) {
		// TODO Auto-generated method stub
		switch(e.getPropertyName()) {
		
		case "SHOW_COMPUTER" : this.getComputer((long) e.getNewValue());break;
		case "LIST_COMPUTERS" : this.listComputers(); break;
		
		}
	}
}
