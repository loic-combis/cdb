package persistence.dao;

import java.util.LinkedList;

public interface DAO<T> {
	
	public  LinkedList<T> list();
	public  T get(long id);
}
