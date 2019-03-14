package com.excilys.cdb.ui;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public abstract class Presenter {

	public static final String CREATE_SUCCESS = "Creation successful";
	public static final String CREATE_FAIL = "Creation unsuccessful";
	public static final String DELETE_SUCCESS = "Deletion successful.";
	public static final String DELETE_FAIL = "Deletion unsuccessful";
	public static final String UPDATE_SUCCESS = "Update successful";
	public static final String UPDATE_FAIL = "Update unsuccessful";

	public abstract void notify(String s);

	public abstract void present(Computer c);

	public abstract void present(Company c);

	public abstract void presentMany(List<?> list);
}
