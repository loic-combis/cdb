package com.excilys.cdb.model.computer;

public class ComputerDTO {

    private Long id;
    private String name;
    private String introduction;
    private String discontinuation;
    private String company;
    private Long companyId;

    /**
     * Getter.
     *
     * @return Long.
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter.
     *
     * @param id Long
     */
    public void setId(Long id) {
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
     * @return Long
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Setter.
     *
     * @param companyId Long
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Describe the computer object in a string.
     *
     * @return String
     */
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder(100);
        sb.append(id).append("\t|\t").append(name).append("\t|\t")
                .append(introduction != null ? introduction.toString() : "null").append("\t|\t")
                .append(discontinuation != null ? discontinuation.toString() : "null").append("\t|\t")
                .append(companyId + " : " + company);

        return sb.toString();

    }
}
