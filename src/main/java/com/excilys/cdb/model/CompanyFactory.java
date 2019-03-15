package com.excilys.cdb.model;

/**
 * Singleton Class responsible for instantiating the Company object.
 * 
 * @author excilys
 *
 */
public class CompanyFactory {

	/**
	 * instance CompanyFactory - Unique instance of this class.
	 */
	private static CompanyFactory instance;

	/**
	 * Constructor Prevent from being instantiated outside the class.
	 */
	private CompanyFactory() {
	}

	/**
	 * Accesses the unique instance of the singleton (Lazy instantiation)
	 * {@link CompanyFactory#instance}
	 * 
	 * @return CompanyFactory
	 */
	public static CompanyFactory getInstance() {
		if (instance == null) {
			instance = new CompanyFactory();
		}
		return instance;
	}

	/**
	 * Creates a new instance of Company
	 * 
	 * @param id   long - the id to be set.
	 * @param name String - the name to be set.
	 * @return Company
	 */
	public Company create(long id, String name) {
		return new Company(id, name);
	}

	/**
	 * Creates a new instance of Company with only the id.
	 * 
	 * @param id long - the id to be set.
	 * @return Company
	 */
	public Company create(long id) {
		return new Company(id);
	}

}
