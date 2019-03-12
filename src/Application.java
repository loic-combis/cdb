
import model.Computer;
import persistence.PersistenceFacade;

public class Application {

	public static void main(String[] args) {
		
		Computer computer = PersistenceFacade.getInstance().getComputer(2);
		if(computer != null) {
			System.out.println(computer.toString());
		}	
	}
}
