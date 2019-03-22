package com.excilys.cdb.model;

public class ComputerDTO {

    private long id;
    private String name;
    private String introduction;
    private String discontinuation;
    private String company;
    private long companyId;

    /**
     * Getter.
     *
     * @return long.
     */
    public long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id long
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter.
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * Setter.
     *
     * @param introduction String
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getDiscontinuation() {
        return discontinuation;
    }

    /**
     * Setter.
     *
     * @param discontinuation String
     */
    public void setDiscontinuation(String discontinuation) {
        this.discontinuation = discontinuation;
    }

    /**
     * Getter.
     *
     * @return String
     */
    public String getCompany() {
        return company;
    }

    /**
     * Setter.
     *
     * @param company String
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Getter.
     *
     * @return long
     */
    public long getCompanyId() {
        return companyId;
    }

    /**
     * Setter.
     *
     * @param companyId long
     */
    public void setCompanyId(long companyId) {
        this.companyId = companyId;
    }
}
