import java.util.LinkedList;

import model.Company;
import model.Computer;
import persistence.PersistenceFacade;

public class Application {

	public static void main(String[] args) {
		
		Computer computer = PersistenceFacade.getInstance().getComputer(1);
		Company company = PersistenceFacade.getInstance().getCompany(1);
		System.out.println(computer.toString());
		System.out.println(company.toString());
	}
}
