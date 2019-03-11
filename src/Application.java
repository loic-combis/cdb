import java.util.ArrayList;

import model.Computer;
import persistence.PersistenceFacade;

public class Application {

	public static void main(String[] args) {
		
		ArrayList<Computer> computers = new ArrayList<Computer>();
		computers = PersistenceFacade.getInstance().listComputers();
		for(Computer c : computers) {
			System.out.println(c.getName());
		}
	}
}
