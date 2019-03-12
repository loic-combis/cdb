package model;

import java.util.*;

public class Computer {
	private long id;
	private String name;
	private Date introductionDate;
	private Date discontinuationDate;
	private Company company;
	
	public Computer(String name) throws Exception {
		this.setName(name);
	}
	
	public Computer(long id, String name, Date introduction, Date discontinuation, Company company) throws Exception {
		this.id = id;
		this.setName(name);
		this.setIntroductionDate(introduction);
		this.setDiscontinuationDate(discontinuation);
		this.setCompany(company);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) throws Exception {
		if(name == null || name.equals("")) {
			throw new Exception("Computer name cannot be empty");
		}
		this.name = name;
	}

	public Date getIntroductionDate() {
		return introductionDate;
	}

	public void setIntroductionDate(Date introduction) {
		if(this.getDiscontinuationDate() == null || introduction == null || (this.getDiscontinuationDate().getTime() - introduction.getTime()) > 0){
			this.introductionDate = introduction;
		}
	}

	public Date getDiscontinuationDate() {
		return discontinuationDate;
	}

	public void setDiscontinuationDate(Date discontinued) {
		if(this.getIntroductionDate() == null || discontinued == null || (this.getIntroductionDate().getTime() - discontinued.getTime()) < 0){
			this.discontinuationDate = discontinued;
		}
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
