package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyFactory;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerDTO;
import com.excilys.cdb.model.ComputerFactory;
import com.excilys.cdb.model.EmptyNameException;

public class ComputerDTOMapper {

    /**
     * df SimpleDateFormat.
     */
    private SimpleDateFormat df;

    /**
     * Constructor.
     */
    public ComputerDTOMapper() {
        df = new SimpleDateFormat("yyyy-mm-dd");
    }

    /**
     * Map a Computer into a ComputerDTO.
     *
     * @param computer Computer
     * @return ComputerDTO
     */
    public ComputerDTO toDTO(Computer computer) {
        ComputerDTO dto = new ComputerDTO();

        dto.setId(computer.getId());
        dto.setName(computer.getName());
        dto.setIntroduction(computer.getIntroductionDate() == null ? null : df.format(computer.getIntroductionDate()));
        dto.setDiscontinuation(
                computer.getDiscontinuationDate() == null ? null : df.format(computer.getDiscontinuationDate()));
        dto.setCompany(computer.getCompany() != null ? computer.getCompany().getName() : null);
        dto.setCompanyId(computer.getCompany() != null ? computer.getCompany().getId() : null);
        return dto;
    }

    /**
     * Map a ComputerDTO into a Computer.
     *
     * @param dto ComputerDTO
     * @return Computer
     * @throws EmptyNameException ene
     * @throws NumberFormatException nfe
     * @throws ParseException pe
     */
    public Computer toComputer(ComputerDTO dto) throws ParseException, NumberFormatException, EmptyNameException {
        Date introduction = dto.getIntroduction() == null || dto.getIntroduction() == "" ? null
                : df.parse(dto.getIntroduction());
        Date discontinuation = dto.getDiscontinuation() == null || dto.getIntroduction() == "" ? null
                : df.parse(dto.getDiscontinuation());
        Company company = CompanyFactory.getInstance().create(dto.getCompanyId(), dto.getCompany());
        return ComputerFactory.getInstance().createWithAll(dto.getId(), dto.getName(), introduction, discontinuation,
                company);
    }

}
