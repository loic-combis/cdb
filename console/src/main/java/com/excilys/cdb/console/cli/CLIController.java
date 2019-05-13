package com.excilys.cdb.console.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.console.Page;
import com.excilys.cdb.console.PageProvider;
import com.excilys.cdb.console.Presenter;
import com.excilys.cdb.console.UIController;
import com.excilys.cdb.core.company.Company;
import com.excilys.cdb.core.company.CompanyFactory;

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
    DELETE_COMPANY("DELETE_COMPANY", "Delete the specified company and all the related fields."),
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
     * Constructor.
     *
     * @param name   String
     * @param action String
     */
    Command(String name, String action) {
        this.name = name;
        this.action = action;
    }

    /**
     * Getter.
     *
     * {@link Command#name}
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     *
     * {@link Command#action}
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
            if (found) {
                break;
            }
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
@Component("cliController")
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

    private PersistenceService persistenceService;

    /**
     * logger Logger.
     */
    private final Logger logger = LoggerFactory.getLogger(CLIController.class);

    /**
     * Constructor.
     */
    public CLIController(PersistenceService ps) {
        presenter = new CLIPresenter();
        scanner = new Scanner(System.in);
        persistenceService = ps;
    }

    @Override
    public void start() {
        // TODO Auto-generated method stub
        logger.debug("Starting the application.");
        if (!persistenceService.login("lolo", "coucou")) {
            presenter.notify("Couldn't login... Stopping the application.");
            stop();
            return;
        }
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
        return persistenceService.list(c, page, itemPerPage);
    }

    /**
     * Custom nextLine() for handling QUIT and menu Command.
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

        Stream.of(Command.values())
                .forEach(command -> presenter.notify(command.getName() + " : " + command.getAction()));

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
            handleListCommand(ComputerDTO.class);
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
        case DELETE_COMPANY:
            handleDeleteCompanyCommand();
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
        if (c == ComputerDTO.class) {
            p = new Page<ComputerDTO>(ComputerDTO.class, this);
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
     * Handles show command.
     */
    private void handleShowCommand() {
        Optional<Long> id = requestValidId();
        if (!id.isPresent()) {
            return;
        }

        ComputerDTO computer = persistenceService.get(id.get());

        if (computer != null) {
            presenter.present(computer);

        } else {
            presenter.notify(Presenter.COMPUTER_NOT_FOUND);
        }

    }

    /**
     * Handles computer creation command.
     */
    private void handleCreateCommand() {
        Optional<String> name = requestValidName();
        if (!name.isPresent()) {
            return;
        }
        ComputerDTO c = new ComputerDTO();
        c.setName(name.get());

        Optional<String> intro = requestValidDate("Introduction");
        if (!intro.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }
        if (intro.isPresent()) {
            c.setIntroduced(intro.get());
        }

        Optional<String> disco = requestValidDate("Discontinuation");
        if (!disco.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }
        if (disco.isPresent()) {
            c.setDiscontinued(disco.get());
        }

        Optional<Company> comp = requestValidCompany();
        if (!comp.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }

        if (comp.isPresent()) {
            c.setCompanyName(comp.get().getName());
            c.setCompanyId(comp.get().getId());
        }
        boolean isSuccess = false;
        try {
            isSuccess = persistenceService.create(c);

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } catch (DateTimeParseException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } finally {
            if (isSuccess) {
                presenter.notify(Presenter.CREATE_SUCCESS);

            } else {
                presenter.notify(Presenter.CREATE_FAIL);
            }
        }
    }

    /**
     * Handles computer update command.
     */
    private void handleUpdateCommand() {
        Optional<Long> id = requestValidId();
        if (!id.isPresent()) {
            return;
        }

        ComputerDTO computer = persistenceService.get(id.get());

        if (computer == null) {
            presenter.notify(Presenter.COMPUTER_NOT_FOUND);
            return;
        }

        Optional<String> name = requestValidName();
        if (!name.isPresent()) {
            return;
        }
        computer.setName(name.get());

        Optional<String> intro = requestValidDate("Introduction");
        if (!intro.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }
        if (intro.isPresent()) {
            computer.setIntroduced(intro.get());
        }

        Optional<String> disco = requestValidDate("Discontinuation");
        if (!disco.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }
        if (disco.isPresent()) {
            computer.setDiscontinued(disco.get());
        }

        Optional<Company> comp = requestValidCompany();
        if (!comp.isPresent() && (shouldStop || shouldShowMenu)) {
            return;
        }
        if (comp.isPresent()) {
            computer.setCompanyId(comp.get().getId());
        }

        boolean isSuccess = false;
        try {
            isSuccess = persistenceService.update(computer);

        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());

        } catch (DateTimeParseException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());

        } finally {
            presenter.notify(isSuccess ? Presenter.UPDATE_SUCCESS : Presenter.UPDATE_FAIL);
        }

    }

    /**
     * Handles computer deletion command.
     */
    private void handleDeleteCommand() {
        Optional<Long> id = requestValidId();
        if (!id.isPresent()) {
            return;
        }
        presenter.notify(persistenceService.delete(ComputerDTO.class, id.get()) ? Presenter.DELETE_SUCCESS
                : Presenter.UPDATE_FAIL);
    }

    /**
     * Handle the deletion of a company and all the related computers.
     */
    private void handleDeleteCompanyCommand() {
        Optional<Long> id = requestValidId();
        if (!id.isPresent()) {
            return;
        }
        presenter.notify(persistenceService.delete(Company.class, id.get()) ? Presenter.DELETE_COMPANY_SUCCESS
                : Presenter.DELETE_COMPANY_FAIL);
    }

    /**
     * Prompts the client to enter a valid Id (Number).
     *
     * @return Long
     */
    private Optional<Long> requestValidId() {
        Long id = null;
        boolean idIsValid = false;
        do {
            presenter.notify("Enter the <ID> : ");
            String inputID = nextLine();

            if (inputID == null) {
                break;
            }

            try {
                id = Long.valueOf(inputID);
                idIsValid = true;
            } catch (NumberFormatException nfe) {
                presenter.notify("<ID> must be a number");
                logger.info(nfe.getMessage());
            }
        } while (!idIsValid);

        return Optional.ofNullable(id);
    }

    /**
     * Prompts the client to enter a non empty name.
     *
     * @return String
     */
    private Optional<String> requestValidName() {
        String name;
        boolean nameIsValid = false;
        do {
            presenter.notify("Enter the name : ");
            name = nextLine();

            if (name == null) {
                break;
            }

            if (name.equals("")) {
                presenter.notify("Name cannot be empty.");
            } else {
                nameIsValid = true;
            }
        } while (!nameIsValid);

        return Optional.ofNullable(name);
    }

    /**
     * Prompts the client to enter a valid date.
     *
     * @param type String (type of date).
     * @return String
     */
    private Optional<String> requestValidDate(String type) {
        String date = null;
        boolean dateIsValid = false;
        do {
            presenter.notify(type + " date (YYYY-MM-DD) : ");
            String inputDate = nextLine();

            if (inputDate == null) {
                break;
            }
            DateTimeFormatter df = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
            try {
                if (!inputDate.equals("")) {
                    LocalDate.parse(inputDate, df);
                }
                dateIsValid = true;
            } catch (DateTimeParseException dtpe) {
                presenter.notify("Wrong date format.");
            }

        } while (!dateIsValid);

        return Optional.ofNullable(date);
    }

    /**
     * Prompts the user to enter a valid company id.
     *
     * @return Company
     */
    private Optional<Company> requestValidCompany() {
        Company comp = null;
        boolean companyIsValid = false;
        do {
            presenter.notify("Company id : ");
            String companyId = nextLine();

            if (companyId == null) {
                break;
            }

            try {
                if (!companyId.equals("")) {
                    Long cId = Long.valueOf(companyId);
                    comp = CompanyFactory.getInstance().create(cId);
                }
                companyIsValid = true;
            } catch (NumberFormatException nfe) {
                logger.info(nfe.getMessage());
                presenter.notify("<ID> Must be a number.");
            }
        } while (!companyIsValid);

        return Optional.ofNullable(comp);
    }
}
