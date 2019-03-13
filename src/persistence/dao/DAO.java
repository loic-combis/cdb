package persistence.dao;

import java.util.LinkedList;

public interface DAO<T> {
	
	public  LinkedList<T> list(int page);
	public  T get(long id);
}
