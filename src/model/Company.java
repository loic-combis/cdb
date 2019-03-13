package model;

public class Company {

	private long id;
	private String name;
	
	public Company(long id) {
		this.id = id;
	}
	
	public Company(String name) {
		this.setName(name);
	}
	
	public Company(long id, String name) {
		this.id = id;
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}
	
	public String toString() {
		return id + "\t|\t" + name;
	}
}
