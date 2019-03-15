package com.excilys.cdb.ui;

import java.util.List;
import java.util.stream.Collectors;

public class Page<T> {

	private PageProvider listener;
	private int currentPage = 0;
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

	public List<T> previousPage() {
		currentPage = Math.max(0, --currentPage);
		List<?> data = listener.fetchDataFor(pageItemClass, currentPage);
		setData(filter(pageItemClass, data));
		return getData();
	}

	public List<T> nextPage() {
		currentPage++;
		List<?> data = listener.fetchDataFor(pageItemClass, currentPage);
		if (data.isEmpty()) {
			currentPage--;
		} else {
			setData(filter(pageItemClass, data));
		}
		return getData();
	}

	static <T> List<T> filter(Class<T> clazz, List<?> items) {
		return items.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
	}

}
