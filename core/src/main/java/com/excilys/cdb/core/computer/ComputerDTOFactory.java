package com.excilys.cdb.core.computer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Singleton Class responsible for instantiating the Company object.
 *
 * @author excilys
 *
 */
public class ComputerDTOFactory {

    private static ComputerDTOFactory instance;

    /**
     * Constructor Prevent from being instantiated outside the class.
     */
    private ComputerDTOFactory() {
    }

    /**
     * Accesses the unique instance of the singleton (Lazy instantiation).
     *
     * @return ComputerDTOFactory
     */
    public static ComputerDTOFactory getInstance() {
        if (instance == null) {
            instance = new ComputerDTOFactory();
        }
        return instance;
    }

    @JsonCreator
    public ComputerDTO create(@JsonProperty("name") String name) {
        ComputerDTO dto = new ComputerDTO();
        dto.setName(name);
        return dto;
    }

    @JsonCreator
    public ComputerDTO create(@JsonProperty("id") Long id, @JsonProperty("name") String name,
            @JsonProperty("introduced") String introduced, @JsonProperty("discontinued") String discontinued,
            @JsonProperty("companyName") String companyName, @JsonProperty("companyId") Long companyId) {
        ComputerDTO dto = create(name);
        dto.setId(id);
        dto.setIntroduced(introduced);
        dto.setDiscontinued(discontinued);
        dto.setCompanyName(companyName);
        dto.setCompanyId(companyId);
        return dto;
    }

}
