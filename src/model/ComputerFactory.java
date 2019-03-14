package model;

import java.util.Date;

public class ComputerFactory {

	private static ComputerFactory instance;

	private ComputerFactory() {
	}

	public static ComputerFactory getInstance() {
		if (instance == null) {
			instance = new ComputerFactory();
		}
		return instance;
	}

	public Computer createWithName(String name) {
		Computer c;
		try {
			c = new Computer(name);
		} catch (EmptyNameException e) {
			e.printStackTrace();
			c = null;
		}
		return c;
	}

	public Computer createWithAll(long id, String name, Date introduction, Date discontinuation, Company company) {
		Computer c = this.createWithName(name);
		if (c == null) {
			return c;
		}
		c.setId(id);
		c.setIntroductionDate(introduction);
		c.setDiscontinuationDate(discontinuation);
		c.setCompany(company);
		return c;
	}
}
