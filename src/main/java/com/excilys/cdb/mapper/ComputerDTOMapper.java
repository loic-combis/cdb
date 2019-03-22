package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.CompanyFactory;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.ComputerDTO;
import com.excilys.cdb.model.ComputerFactory;

public class ComputerDTOMapper {

    /**
     * df SimpleDateFormat.
     */
    private SimpleDateFormat df;

    /**
     * LOGGER Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOMapper.class);

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
        dto.setDiscontinuation(computer.getDiscontinuationDate() == null ? null : df.format(computer.getDiscontinuationDate()));
        dto.setCompany(computer.getCompany() != null ? computer.getCompany().getName() : null);
        dto.setCompanyId(computer.getCompany() != null ? computer.getCompany().getId() : null);
        return dto;
    }

    /**
     * Map a ComputerDTO into a Computer.
     *
     * @param dto ComputerDTO
     * @return Computer
     */
    public Computer toComputer(ComputerDTO dto) {
        Date introduction = null, discontinuation = null;
        try {
            introduction = dto.getIntroduction() == null ? null : df.parse(dto.getIntroduction());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        try {
            discontinuation = dto.getDiscontinuation() == null ? null : df.parse(dto.getDiscontinuation());
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            LOGGER.error(e.getMessage());
        }
        Company company = CompanyFactory.getInstance().create(dto.getCompanyId(), dto.getCompany());
        return ComputerFactory.getInstance().createWithAll(dto.getId(), dto.getName(), introduction, discontinuation, company);
    }



}
