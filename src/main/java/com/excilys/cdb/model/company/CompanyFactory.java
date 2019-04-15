package com.excilys.cdb.model.company;

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
	 * Accesses the unique instance of the singleton (Lazy instantiation).
	 *
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
	 * Creates a new instance of Company.
	 *
	 * @param id   Long - the id to be set.
	 * @param name String - the name to be set.
	 * @return Company
	 */
	public Company create(Long id, String name) {
		Company company = new Company();
		company.setId(id);
		company.setName(name);
		return company;

	}

	/**
	 * Creates a new instance of Company with only the id.
	 *
	 * @param id Long - the id to be set.
	 * @return Company
	 */
	public Company create(Long id) {
		Company company = new Company();
		company.setId(id);
		return company;
	}

	/**
	 * Create a company with the specified name.
	 *
	 * @param name String
	 * @return Company
	 */
	public Company create(String name) {
		Company company = new Company();
		company.setName(name);
		return company;
	}

}
