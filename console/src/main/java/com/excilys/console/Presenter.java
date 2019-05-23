package com.excilys.console;

import java.util.List;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.core.company.Company;

/**
 * Abstract Responsible for defining the contract a Presenter must respect
 * within the application.
 *
 * @author excilys
 */
public abstract class Presenter {

    public static final String CREATE_SUCCESS = "Creation successful";
    public static final String CREATE_FAIL = "Creation unsuccessful";
    public static final String DELETE_SUCCESS = "Deletion successful.";
    public static final String DELETE_FAIL = "Deletion unsuccessful";
    public static final String UPDATE_SUCCESS = "Update successful";
    public static final String UPDATE_FAIL = "Update unsuccessful";
    public static final String DELETE_COMPANY_SUCCESS = "Company successfully deleted.";
    public static final String DELETE_COMPANY_FAIL = "Company couldn't be deleted.";
    public static final String COMPANY_NOT_FOUND = "Company not found.";
    public static final String COMPUTER_NOT_FOUND = "Computer not found.";
    public static final String UNSUCCESSFUL_TREATMENT = "Something wrong happened.";

    /**
     * Notifies the client of the specified message.
     *
     * @param s String
     */
    public abstract void notify(String s);

    /**
     * Presents a computer to the client.
     *
     * @param c Computer
     */
    public abstract void present(ComputerDTO c);

    /**
     * Presents a company to the client.
     *
     * @param c Company
     */
    public abstract void present(Company c);

    /**
     * Presents many items to the client.
     *
     * @param list List<?>
     */
    public abstract void presentMany(List<?> list);
}
