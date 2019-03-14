package ui;

import java.util.List;

public class Page<T> {

	private PageProvider listener;
	private int currentPage = 1;
	private List<T> data;
	private Class<T> pageItemClass;

	public Page(Class<T> c, PageProvider pl) {
		listener = pl;
		pageItemClass = c;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> newData) {
		data = newData;
	}

	public List<T> first() {
		currentPage = 1;
		List<T> data = (List<T>) listener.fetchDataFor(pageItemClass, currentPage);
		setData((List<T>) data);
		return getData();
	}

	public List<T> previousPage() {
		currentPage = Math.max(0, --currentPage);
		List<T> data = (List<T>) listener.fetchDataFor(pageItemClass, currentPage);
		setData((List<T>) data);
		return getData();
	}

	public List<T> nextPage() {
		currentPage++;
		List<T> data = (List<T>) listener.fetchDataFor(pageItemClass, currentPage);
		setData(data);
		return getData();
	}

}
