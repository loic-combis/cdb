package model;

import java.util.*;

public class Computer {
	private long id;
	private String name;
	private Date introductionDate;
	private Date discontinuationDate;
	private Company company;
	
	public Computer(String name) {
		this.setName(name);
	}
	
	public Computer(long id, String name, Date introduction, Date discontinuation, Company company) {
		this.id = id;
		this.setName(name);
		this.setIntroductionDate(introduction);
		this.setDiscontinuationDate(discontinuation);
		this.setCompany(company);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(Date introductionDate) {
		this.introductionDate = introductionDate;
	}

	public Date getDiscontinuationDate() {
		return discontinuationDate;
	}

	public void setDiscontinuationDate(Date discontinuedDate) {
		this.discontinuationDate = discontinuedDate;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder(100);
		sb	.append(id).append("\t|\t")
			.append(name).append("\t|\t")
			.append(introductionDate != null ? introductionDate.toString() : "null").append("\t|\t")
			.append(discontinuationDate != null ? discontinuationDate.toString(): "null").append("\t|\t")
			.append(company != null ? company.getName() : "null");
		return sb.toString();
		
	}
}
