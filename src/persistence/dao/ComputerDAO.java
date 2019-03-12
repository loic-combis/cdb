package persistence.dao;

import model.Computer;

public interface ComputerDAO extends DAO<Computer>{
	
	public Boolean delete(int id);
	public Computer update(Computer c);
	public Computer create(Computer c);
	
}
