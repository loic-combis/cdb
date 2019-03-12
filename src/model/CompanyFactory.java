package model;

public class CompanyFactory {

	private static CompanyFactory instance;
	
	private CompanyFactory() {}
	
	public static CompanyFactory getInstance() {
		if(instance == null) {
			instance = new CompanyFactory();
		}
		return instance;
	}
	
	public Company create(long id, String name) {
		return new Company(id, name);
	}
	
}
