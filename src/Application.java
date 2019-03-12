
import model.Computer;
import persistence.PersistenceFacade;

public class Application {

	public static void main(String[] args) {
		
		Computer testComputer = new Computer("Ubuntu qui fait mal aux yeux");
		System.out.println(testComputer);
		testComputer = PersistenceFacade.getInstance().createComputer(testComputer);
		System.out.println(testComputer);
	}
}
