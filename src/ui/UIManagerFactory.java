package ui;

import java.beans.PropertyChangeListener;

import ui.cli.CLIManager;

public class UIManagerFactory {

	private static UIManagerFactory instance;
	
	private UIManagerFactory(){}
	
	public static UIManagerFactory getInstance() {
		if(instance == null){
			instance = new UIManagerFactory();
		}
		return instance;
	}
	
	
	public UIManager create(PropertyChangeListener pcl) {
		return new CLIManager(pcl);
	}
}
