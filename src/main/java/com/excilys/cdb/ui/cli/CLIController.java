package com.excilys.cdb.ui.cli;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyFactory;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerFactory;
import com.excilys.cdb.persistence.PersistenceFacade;
import com.excilys.cdb.ui.Page;
import com.excilys.cdb.ui.PageProvider;
import com.excilys.cdb.ui.Presenter;
import com.excilys.cdb.ui.UIController;

/**
 * Enumeration Defines the commands accepted by the client.
 * 
 * @author excilys
 *
 */
enum Command {

	COMPUTERS("COMPUTERS", "List the computers."), COMPANIES("COMPANIES", "List the companies."),
	SHOW("SHOW", "Show a specific computer."), CREATE("CREATE", "Create a new computer."),
	UPDATE("UPDATE", "Update an existing computer."), DELETE("DELETE", "Delete an existing computer"),
	NEXT("NEXT", "Go to next page."), PREVIOUS("PREVIOUS", "Go to previous page."),
	MENU("MENU", "Go back to the main menu."), QUIT("QUIT", "Quit the application.");

	/**
	 * name String - Name of the command.
	 */
	private String name;

	/**
	 * action String - Action related to this command.
	 */
	private String action;

	/**
	 * Constructor
	 * 
	 * @param name   String
	 * @param action String
	 */
	private Command(String name, String action) {
		this.name = name;
		this.action = action;
	}

	/**
	 * Getter {@link Command#name}
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter {@link Command#action}
	 * 
	 * @return String
	 */
	public String getAction() {
		return action;
	}

	/**
	 * Checks if the specified string corresponds to one of the command's name.
	 * 
	 * @param input String
	 * @return boolean
	 */
	public static boolean contains(String input) {
		boolean found = false;
		for (Command c : values()) {
			found = c.getName().equals(input);
			if (found)
				break;
		}
		return found;
	}
}

/**
 * Concrete implementation of UIController and PageProvider Responsible for
 * handling user interactions and bonding with the persistence.
 * 
 * @author excilys
 *
 */
public class CLIController implements UIController, PageProvider {

	/**
	 * String actions.
	 */
	private static final String UNKNOWN_ACTION = "Unknown action.";
	private static final String WHAT_TO_DO = "\nWhat do you want to do?";

	/**
	 * itemPerPage int - Defines the limit of item presented in one page.
	 */
	private final int itemPerPage = 10;

	/**
	 * persistence PersistenceFacade - Fetches data from persistence.
	 */
	private PersistenceFacade persistence;

	/**
	 * presenter CLIPresenter - Presents data to the client.
	 */
	private CLIPresenter presenter;

	/**
	 * scanner Scanner - Reads clients inputs.
	 */
	private Scanner scanner;

	/**
	 * shouldStop boolean - Defines if the main loop should stop.
	 */
	boolean shouldStop = false;

	/**
	 * shouldShowMenu boolean - Defines if the menu should be shown in the next
	 * iteration.
	 */
	boolean shouldShowMenu = false;

	/**
	 * logger Logger
	 */
	private final Logger logger = LoggerFactory.getLogger(CLIController.class);
		
	/**
	 * Constructor {@link CLIController#persistence} {@link CLIController#presenter}
	 * {@link CLIController#scanner}
	 */
	public CLIController() {
		persistence = PersistenceFacade.getInstance();
		presenter = new CLIPresenter();
		scanner = new Scanner(System.in);
	}

	@Override
	public void start() {
		// TODO Auto-generated method stub
		logger.debug("Starting the application.");
		presenter.notify("Welcome to the application !");
		showMenu();
		do {
			if (shouldShowMenu) {
				showMenu();
			} else {
				presenter.notify(WHAT_TO_DO);
				String input = nextLine();
				if (input == null) {
					continue;
				}
				handleInput(input);
			}
		} while (!shouldStop);
		stop();
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		presenter.notify("Stopping the application.");
		logger.debug("Terminating the application.");
		System.exit(1);
	}

	@Override
	public List<?> fetchDataFor(Class<?> c, int page) {
		// TODO Auto-generated method stub
		if (c == Computer.class) {
			List<Computer> computers = persistence.listComputers(page, itemPerPage);
			return computers;
		}

		if (c == Company.class) {
			List<Company> companies = persistence.listCompanies(page, itemPerPage);
			return companies;
		}

		return null;
	}

	/**
	 * Custom nextLine() for handling QUIT and menu Command
	 * 
	 * @return String (null if the client's input is a QUIT or MENU command).
	 */
	private String nextLine() {
		String input = scanner.nextLine();
		if (input.equals(Command.QUIT.getName())) {
			shouldStop = true;
			return null;
		}
		if (input.equals(Command.MENU.getName())) {
			shouldShowMenu = true;
			return null;
		}
		return input;

	}

	/**
	 * Presents the main menu to the client.
	 */
	private void showMenu() {
		presenter.notify("Here is the list of the available commands : ");
		Command[] commands = Command.values();
		for (Command command : commands) {
			presenter.notify(command.getName() + " : " + command.getAction());
		}

		shouldShowMenu = false;
	}

	/**
	 * Dispatches according to the client's input.
	 * 
	 * @param input String
	 */
	private void handleInput(String input) {
		if (!Command.contains(input)) {
			presenter.notify(UNKNOWN_ACTION);
			return;
		}
		Command cmd = Command.valueOf(input);
		switch (cmd) {
		case COMPUTERS:
			handleListCommand(Computer.class);
			break;
		case COMPANIES:
			handleListCommand(Company.class);
			break;
		case SHOW:
			handleShowCommand();
			break;
		case CREATE:
			handleCreateCommand();
			break;
		case UPDATE:
			handleUpdateCommand();
			break;
		case DELETE:
			handleDeleteCommand();
			break;
		default:
			presenter.notify(UNKNOWN_ACTION);
			logger.info("Unkown action : " + cmd.getName());
		}
	}

	/**
	 * Responsible for handling a LIST command.
	 * 
	 * @param c Class of the objects to be listed.
	 */
	private void handleListCommand(Class<?> c) {
		Page<?> p;
		if (c == Computer.class) {
			p = new Page<Computer>(Computer.class, this);
		} else {
			p = new Page<Company>(Company.class, this);
		}
		presenter.presentMany(p.nextPage());

		boolean shouldGoBack = false;
		do {
			String input = nextLine();
			shouldGoBack = input == null;

			if (!Command.contains(input)) {
				presenter.notify(UNKNOWN_ACTION);
				continue;
			}
			Command cmd = Command.valueOf(input);
			switch (cmd) {
			case NEXT:
				presenter.presentMany(p.nextPage());
				break;
			case PREVIOUS:
				presenter.presentMany(p.previousPage());
				break;
			default:
				presenter.notify(UNKNOWN_ACTION);
			}

		} while (!shouldGoBack);
		return;

	}

	/**
	 * Handles show command
	 */
	private void handleShowCommand() {
		Long id = requestValidId();
		if (id == null)
			return;

		presenter.present(persistence.getComputer(id));
	}

	/**
	 * Handles computer creation command.
	 */
	private void handleCreateCommand() {
		String name = requestValidName();
		if (name == null)
			return;
		Computer c = ComputerFactory.getInstance().createWithName(name);

		Date intro = requestValidDate("Introduction");
		if (intro == null && (shouldStop || shouldShowMenu))
			return;
		c.setIntroductionDate(intro);

		Date disco = requestValidDate("Discontinuation");
		if (disco == null && (shouldStop || shouldShowMenu))
			return;
		c.setDiscontinuationDate(disco);

		Company comp = requestValidCompany();
		if (comp == null && (shouldStop || shouldShowMenu))
			return;
		c.setCompany(comp);

		presenter.present(persistence.create(c));
	}

	/**
	 * Handles computer update command.
	 */
	private void handleUpdateCommand() {
		Long id = requestValidId();
		if (id == null)
			return;

		String name = requestValidName();
		if (name == null)
			return;
		Computer c = ComputerFactory.getInstance().createWithAll(id, name, null, null, null);

		Date intro = requestValidDate("Introduction");
		if (intro == null && (shouldStop || shouldShowMenu))
			return;
		c.setIntroductionDate(intro);

		Date disco = requestValidDate("Discontinuation");
		if (disco == null && (shouldStop || shouldShowMenu))
			return;
		c.setDiscontinuationDate(disco);

		Company comp = requestValidCompany();
		if (comp == null && (shouldStop || shouldShowMenu))
			return;
		c.setCompany(comp);

		presenter.notify(persistence.update(c) ? Presenter.UPDATE_SUCCESS : Presenter.UPDATE_FAIL);
	}

	/**
	 * Handles computer deletion command.
	 */
	private void handleDeleteCommand() {
		Long id = requestValidId();
		if (id == null)
			return;

		presenter.notify(persistence.deleteComputer(id) ? Presenter.DELETE_SUCCESS : Presenter.UPDATE_FAIL);
	}

	/**
	 * Prompts the client to enter a valid Id (Number)
	 * 
	 * @return Long
	 */
	private Long requestValidId() {
		Long id = null;
		boolean idIsValid = false;
		do {
			presenter.notify("Enter the <ID> : ");
			String inputID = nextLine();

			if (inputID == null)
				break;

			try {
				id = Long.parseLong(inputID);
				idIsValid = true;
			} catch (NumberFormatException nfe) {
				presenter.notify("<ID> must be a number");
				logger.info(nfe.getMessage());
			}
		} while (!idIsValid);
		return id;
	}

	/**
	 * Prompts the client to enter a non empty name.
	 * 
	 * @return String
	 */
	private String requestValidName() {
		String name;
		boolean nameIsValid = false;
		do {
			presenter.notify("Enter the name : ");
			name = nextLine();

			if (name == null)
				break;

			if (name.equals("")) {
				presenter.notify("Name cannot be empty.");
			} else {
				nameIsValid = true;
			}
		} while (!nameIsValid);
		return name;
	}

	/**
	 * Prompts the client to enter a valid date
	 * 
	 * @param type String (type of date).
	 * @return Date
	 */
	private Date requestValidDate(String type) {
		Date d = null;
		boolean dateIsValid = false;
		do {
			presenter.notify(type + " date (YYYY-MM-DD) : ");
			String inputDate = nextLine();

			if (inputDate == null)
				break;

			SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");
			try {
				if (!inputDate.equals("")) {
					d = df.parse(inputDate);
				}
				dateIsValid = true;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				presenter.notify("Wrong date format.");
				logger.info(e.getMessage());
			}
		} while (!dateIsValid);

		return d;
	}

	/**
	 * Prompts the user to enter a valid company id.
	 * 
	 * @return Company
	 */
	private Company requestValidCompany() {
		Company comp = null;
		boolean companyIsValid = false;
		do {
			presenter.notify("Company id : ");
			String companyId = this.nextLine();

			if (companyId == null)
				break;

			try {
				if (!companyId.equals("")) {
					long cId = Long.parseLong(companyId);
					comp = CompanyFactory.getInstance().create(cId);
				}
				companyIsValid = true;
			} catch (NumberFormatException nfe) {
				logger.info(nfe.getMessage());
				presenter.notify("<ID> Must be a number.");
			}
		} while (!companyIsValid);

		return comp;
	}
}
