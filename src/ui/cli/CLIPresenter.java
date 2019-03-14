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
	public void present(Company c) {
		// TODO Auto-generated method stub
		System.out.println(c);
	}

	@Override
	public void presentMany(List<?> list) {
		// TODO Auto-generated method stub
		for (Object element : list) {
			System.out.println(element);
			System.out.println("----------------------------------------------------");
		}
		System.out.println("\nPREVIOUS | NEXT | MENU ?");
	}

	@Override
	public void notify(String s) {
		// TODO Auto-generated method stub
		System.out.println(s);
	}

}
