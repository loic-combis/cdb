package com.excilys.cdb.model.computer;

/**
 * Build ComputerDTO by chaining.
 *
 * @author excilys
 *
 */
public class ComputerDTOBuilder {

    /**
     * dto ComputerDTO.
     */
    private ComputerDTO dto;

    /**
     * Constructor.
     */
    public ComputerDTOBuilder() {
        dto = new ComputerDTO();
    }

    /**
     * Getter.
     *
     * @return dto ComputerDTO
     */
    public ComputerDTO get() {
        return dto;
    }

    /**
     * Setter.
     *
     * @param id Long
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setId(Long id) {
        dto.setId(id);
        return this;
    }

    /**
     * Setter.
     *
     * @param name String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setName(String name) {
        dto.setName(name);
        return this;
    }

    /**
     * Setter.
     *
     * @param introduction String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setIntroduction(String introduction) {
        dto.setIntroduction(introduction);
        return this;
    }

    /**
     * Setter.
     *
     * @param discontinuation String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setDiscontinuation(String discontinuation) {
        dto.setDiscontinuation(discontinuation);
        return this;
    }

    /**
     * Setter.
     *
     * @param name String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setCompanyName(String name) {
        dto.setCompany(name);
        return this;
    }

    /**
     * Setter.
     *
     * @param id long
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setCompanyId(Long id) {
        dto.setCompanyId(id);
        return this;
    }

}
