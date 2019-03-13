package ui.cli;

import java.util.List;

import model.Company;
import model.Computer;
import ui.Presenter;

public class CLIPresenter extends Presenter {
	
	@Override
	public void present(Computer c) {
		// TODO Auto-generated method stub
		System.out.println(c);
	}

	@Override
	public void presentComputers(List<Computer> computers) {
		// TODO Auto-generated method stub
		for(Computer c  : computers) {
			System.out.println(c);
			System.out.println("---------------------------------------------------------------------------------------------------");
		}
	}

	@Override
	public void present(Company c) {
		// TODO Auto-generated method stub
		System.out.println(c);
	}

	@Override
	public void presentCompanies(List<Company> c) {
		// TODO Auto-generated method stub
		for(Company comp  : c) {
			System.out.println(comp);
			System.out.println("----------------------------------------------------");
		}
	}
	
	@Override
	public void notify(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

}
