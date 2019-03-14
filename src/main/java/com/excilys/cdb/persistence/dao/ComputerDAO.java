package com.excilys.cdb.persistence.dao;

import com.excilys.cdb.model.Computer;

public interface ComputerDAO extends DAO<Computer> {

	public Boolean delete(long id);

	public Boolean update(Computer c);

	public Computer create(Computer c);

}
