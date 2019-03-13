package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.Computer;
import persistence.PersistenceFacade;
import ui.presenter.Presenter;
import ui.presenter.PresenterFactory;

public class UILayer implements PropertyChangeListener {

	private PersistenceFacade persistenceFacade;
	private Presenter presenter;
		
	public UILayer() {
		persistenceFacade = PersistenceFacade.getInstance();
		presenter = PresenterFactory.getInstance().create(this);
	}
	
	private void getComputer(long id) {
		Computer c = persistenceFacade.getComputer(id);
		presenter.present(c);
	}
	
	private void deleteComputer(long id) {
	
		if(persistenceFacade.deleteComputer(id)) {
			presenter.notify(Presenter.DELETE_SUCCESS);
		}
		else {
			presenter.notify(Presenter.DELETE_FAIL);
		}
	}
	
	private void updateComputer(Computer c) {
		
		if(persistenceFacade.update(c)) {
			presenter.notify(Presenter.UPDATE_SUCCESS);
		}else {
			presenter.notify(Presenter.UPDATE_FAIL);
		}
	}
	
	private void createComputer(Computer c) {
		Computer newComp = persistenceFacade.create(c);
		if(newComp != null) {
			presenter.notify(Presenter.CREATE_SUCCESS);
			presenter.present(newComp);
		}
		else {
			presenter.notify(Presenter.CREATE_FAIL);
		}
	}

	private void listComputers(int page) {
		presenter.presentComputers(persistenceFacade.listComputers(page));
	}
	
	private void listCompanies(int page) {
		presenter.presentCompanies(persistenceFacade.listCompanies(page));
	}
	
	public void start() {
		presenter.start();
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		switch(event.getPropertyName()) {
		case Presenter.LIST_COMPUTERS : this.listComputers((int) event.getNewValue());break;
		case Presenter.LIST_COMPANIES : this.listCompanies((int) event.getNewValue());break;
 		case Presenter.FETCH_COMPUTER : this.getComputer((long) event.getNewValue());break;
		case Presenter.DELETE_COMPUTER : this.deleteComputer((long) event.getNewValue());break;
		case Presenter.UPDATE_COMPUTER : this.updateComputer((Computer)event.getNewValue());break;
		case Presenter.CREATE_COMPUTER : this.createComputer((Computer) event.getNewValue());break;
		default : presenter.notify(Presenter.UNKNOWN_ACTION);break;
		}
	}
}
