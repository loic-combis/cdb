package com.excilys.cdb.ui;

import java.util.List;

public interface PageProvider {

	public List<?> fetchDataFor(Class<?> c, int page);
}
