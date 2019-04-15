package com.excilys.cdb.ui;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Responsible for managing the flow of pagination.
 *
 * @author excilys
 *
 * @param <T> The type of item paginated.
 */
public class Page<T> {

	/**
	 * provider PageProvider - Providing the data to be paginated.
	 */
	private PageProvider provider;

	/**
	 * currentPage int.
	 */
	private int currentPage = 0;

	/**
	 * data List<T>.
	 */
	private List<T> data;

	/**
	 * pageItemClass Class<T> - Class of the object paginated.
	 */
	private Class<T> pageItemClass;

	/**
	 * Constructor.
	 *
	 * {@link Page#pageItemClass} {@link Page#provider}
	 *
	 * @param c  Class<T>
	 * @param pl PageProvider
	 */
	public Page(Class<T> c, PageProvider pl) {
		provider = pl;
		pageItemClass = c;
	}

	/**
	 * Getter.
	 *
	 * {@link Page#data}
	 *
	 * @return List<T>
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * Setter.
	 *
	 * @param newData List<T>
	 */
	public void setData(List<T> newData) {
		data = newData;
	}

	/**
	 * Updates the current state of the page towards the previous one.
	 *
	 * @return List<T>
	 */
	public List<T> previousPage() {
		currentPage = Math.max(0, --currentPage);
		List<?> data = provider.fetchDataFor(pageItemClass, currentPage);
		setData(filter(pageItemClass, data));
		return getData();
	}

	/**
	 * Updates the current state of the page towards the next one.
	 *
	 * @return List<T>
	 */
	public List<T> nextPage() {
		currentPage++;
		List<?> data = provider.fetchDataFor(pageItemClass, currentPage);
		if (data.isEmpty()) {
			currentPage--;
		} else {
			setData(filter(pageItemClass, data));
		}
		return getData();
	}

	/**
	 * Cast a List<?> to a List<T> Prevents from unchecked cast warnings.
	 *
	 * @param <T>   The class to be casted to.
	 * @param clazz Class<T>
	 * @param items List<?>
	 * @return List<T>
	 */
	static <T> List<T> filter(Class<T> clazz, List<?> items) {
		return items.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
	}

}
