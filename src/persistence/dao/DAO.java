package persistence.dao;

import java.util.List;

public interface DAO<T> {

	public List<T> list(int page);

	public T get(long id);
}
