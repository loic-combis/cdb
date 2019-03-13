package ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import model.Computer;
import persistence.PersistenceFacade;

public class UILayer implements PropertyChangeListener {

	private PersistenceFacade persistenceFacade;
	private Presenter presenter;
	private UIManager manager;
		
	public UILayer() {
		persistenceFacade = PersistenceFacade.getInstance();
		manager = UIManagerFactory.getInstance().create(this);
		presenter = manager.getPresenter();
		
	}
	
	public void start() {
		manager.start();
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

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		switch(event.getPropertyName()) {
		case UIManager.LIST_COMPUTERS : this.listComputers((int) event.getNewValue());break;
		case UIManager.LIST_COMPANIES : this.listCompanies((int) event.getNewValue());break;
 		case UIManager.FETCH_COMPUTER : this.getComputer((long) event.getNewValue());break;
		case UIManager.DELETE_COMPUTER : this.deleteComputer((long) event.getNewValue());break;
		case UIManager.UPDATE_COMPUTER : this.updateComputer((Computer)event.getNewValue());break;
		case UIManager.CREATE_COMPUTER : this.createComputer((Computer) event.getNewValue());break;
		default : presenter.notify(UIManager.UNKNOWN_ACTION);break;
		}
	}
}
