package com.excilys.cdb.binding.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

public class ComputerDTO {

    private Long id;

    private String name;

    private String introduced;

    private String discontinued;

    private String companyName;

    private Long companyId;

    /**
     * Getter.
     *
     * @return Long.
     */
    @JsonGetter("id")
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
    @JsonGetter("name")
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
    @JsonGetter("introduced")
    public String getIntroduced() {
        return introduced;
    }

    /**
     * Setter.
     *
     * @param introduced String
     */
    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    /**
     * Getter.
     *
     * @return String
     */
    @JsonGetter("discontinued")
    public String getDiscontinued() {
        return discontinued;
    }

    /**
     * Setter.
     *
     * @param discontinued String
     */
    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    /**
     * Getter.
     *
     * @return String
     */
    @JsonGetter("companyName")
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter.
     *
     * @param companyName String
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Getter.
     *
     * @return Long
     */
    @JsonGetter("companyId")
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
                .append(introduced != null ? introduced.toString() : "null").append("\t|\t")
                .append(discontinued != null ? discontinued.toString() : "null").append("\t|\t")
                .append(companyId + " : " + companyId);

        return sb.toString();

    }
}
