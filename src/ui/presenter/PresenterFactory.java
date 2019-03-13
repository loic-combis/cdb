package ui.presenter;

import java.beans.PropertyChangeListener;

public class PresenterFactory {

	private static PresenterFactory instance;
	
	private PresenterFactory() {}
	
	public static PresenterFactory getInstance() {
		if(instance == null) {
			instance = new PresenterFactory();
		}
		return instance;
	}
	
	public Presenter create(PropertyChangeListener pcl) {
		return new CLIPresenter(pcl);
	}
}
