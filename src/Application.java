
import java.util.LinkedList;

import model.Company;
import model.Computer;
import persistence.PersistenceFacade;

public class Application {

	public static void main(String[] args) {
		
		/*Computer computer = ComputerFactory.getInstance().createWithAll(0L, "Another Test", null,null,null);		
		computer = PersistenceFacade.getInstance().create(computer);
		
		try {
			computer.setName("Mac Book Pro 21 inches");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		long computerId = computer.getId();
		
		if(PersistenceFacade.getInstance().update(computer)) {
			computer = PersistenceFacade.getInstance().getComputer(computerId);
			System.out.println(computer);
		}
		
		if(PersistenceFacade.getInstance().deleteComputer(computerId)) {
			System.out.println(PersistenceFacade.getInstance().getComputer(computerId));
		}*/
		
		/*LinkedList<Computer> computers = PersistenceFacade.getInstance().listComputers();
		for(Computer c : computers) {
			System.out.println(c);
			System.out.println("---------------------------------------------------------------------");
		}*/
		
		LinkedList<Company> companies = PersistenceFacade.getInstance().listCompanies();
		for(Company c : companies) {
			System.out.println(c);
			System.out.println("----------------------------------------------------------");
		}
			
		System.out.println(PersistenceFacade.getInstance().getCompany(12));
	}
}
