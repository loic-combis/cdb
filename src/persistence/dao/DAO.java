package persistence.dao;

import java.util.ArrayList;

public interface DAO<T> {
	
	public  ArrayList<T> list();
	public  T get(int id);
}
