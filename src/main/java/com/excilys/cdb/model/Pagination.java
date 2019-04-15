package com.excilys.cdb.model;

public class Pagination {

	private int currentPage;
	private int itemPerPage;
	private int itemCount;
	private int[] sizes = { 10, 20, 50, 100 };

	/**
	 * Constructor.
	 *
	 * @param currentPage int
	 * @param itemPerPage int
	 * @param itemCount   int
	 */
	public Pagination(int currentPage, int itemPerPage, int itemCount) {
		setCurrentPage(currentPage);
		setItemPerPage(itemPerPage);
		setItemCount(itemCount);
	}

	/**
	 * Getter.
	 *
	 * @return int
	 */
	public int getCurrentPage() {
		return currentPage;
	}

	/**
	 * Setter.
	 *
	 * @param currentPage int
	 */
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * Getter.
	 *
	 * @return int
	 */
	public int getItemPerPage() {
		return itemPerPage;
	}

	/**
	 * Setter.
	 *
	 * @param itemPerPage int
	 */
	public void setItemPerPage(int itemPerPage) {
		this.itemPerPage = itemPerPage;
	}

	/**
	 * Getter.
	 *
	 * @return int
	 */
	public int getItemCount() {
		return itemCount;
	}

	/**
	 * Setter.
	 *
	 * @param itemCount int
	 */
	public void setItemCount(int itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * Getter.
	 *
	 * @return int[]
	 */
	public int[] getSizes() {
		return sizes;
	}

	/**
	 * Get the first page of the currentPage area.
	 *
	 * @return int
	 */
	public int currentPageStart() {
		return Math.max(getCurrentPage() - 1, 2);
	}

	/**
	 * Get the last page of the currentPage area.
	 *
	 * @return int
	 */
	public int currentPageEnd() {
		return Math.max(Math.min(getCurrentPage() + 1, lastPage() - 1), 1);
	}

	/**
	 * Get the last page.
	 *
	 * @return int
	 */
	public int lastPage() {
		int potentialLastPage = getItemCount() / getItemPerPage();
		return (getItemCount() % getItemPerPage() == 0) ? potentialLastPage : potentialLastPage + 1;
	}

	/**
	 * Return the previous page.
	 *
	 * @return int
	 */
	public int previous() {
		return getCurrentPage() == 1 ? getCurrentPage() : getCurrentPage() - 1;
	}

	/**
	 * Return the next page.
	 *
	 * @return int
	 */
	public int next() {
		return getCurrentPage() >= lastPage() ? getCurrentPage() : getCurrentPage() + 1;
	}

	/**
	 * Return String containing 'current' if the specified page is the current page.
	 *
	 * @param page int
	 * @return String
	 */
	public String compareCurrent(int page) {
		return page == getCurrentPage() ? "current" : "";
	}

	/**
	 * Return "current" if the specified size corresponds to the number of item per
	 * page.
	 *
	 * @param size int
	 * @return String
	 */
	public String compareItemPerPage(int size) {
		return size == getItemPerPage() ? "current" : "";
	}
}
