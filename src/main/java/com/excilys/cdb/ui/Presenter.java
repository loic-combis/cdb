package com.excilys.cdb.ui;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

/**
 * Abstract Responsible for defining the contract a Presenter must respect
 * within the application.
 * 
 * @author excilys
 *
 */
public abstract class Presenter {

	public static final String CREATE_SUCCESS = "Creation successful";
	public static final String CREATE_FAIL = "Creation unsuccessful";
	public static final String DELETE_SUCCESS = "Deletion successful.";
	public static final String DELETE_FAIL = "Deletion unsuccessful";
	public static final String UPDATE_SUCCESS = "Update successful";
	public static final String UPDATE_FAIL = "Update unsuccessful";

	/**
	 * Notifies the client of the specified message.
	 * 
	 * @param s String
	 */
	public abstract void notify(String s);

	/**
	 * Presents a computer to the client
	 * 
	 * @param c Computer
	 */
	public abstract void present(Computer c);

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
