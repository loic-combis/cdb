package persistence.dao;

import model.Computer;

public interface ComputerDAO extends DAO<Computer> {

	public Boolean delete(long id);

	public Boolean update(Computer c);

	public Computer create(Computer c);

}
