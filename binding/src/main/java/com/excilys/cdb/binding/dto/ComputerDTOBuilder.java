package com.excilys.cdb.binding.dto;

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
        dto.setIntroduced(introduction);
        return this;
    }

    /**
     * Setter.
     *
     * @param discontinuation String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setDiscontinuation(String discontinuation) {
        dto.setDiscontinued(discontinuation);
        return this;
    }

    /**
     * Setter.
     *
     * @param name String
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setCompanyName(String name) {
        dto.setCompanyName(name);
        return this;
    }

    /**
     * Setter.
     *
     * @param id Long
     * @return ComputerDTOBuilder
     */
    public ComputerDTOBuilder setCompanyId(Long id) {
        dto.setCompanyId(id);
        return this;
    }

}
