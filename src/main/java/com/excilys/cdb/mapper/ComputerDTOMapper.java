package com.excilys.cdb.mapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
import com.excilys.cdb.model.computer.ComputerFactory;
import com.excilys.cdb.model.computer.EmptyNameException;

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
        ComputerDTOBuilder builder = new ComputerDTOBuilder();

        builder.setId(computer.getId()).setName(computer.getName())
                .setIntroduction(
                        computer.getIntroductionDate() == null ? null : df.format(computer.getIntroductionDate()))
                .setDiscontinuation(
                        computer.getDiscontinuationDate() == null ? null : df.format(computer.getDiscontinuationDate()))
                .setCompanyName(computer.getCompany() != null ? computer.getCompany().getName() : null)
                .setCompanyId(computer.getCompany() != null ? computer.getCompany().getId() : null);

        return builder.get();
    }

    /**
     * Map a ComputerDTO into a Computer.
     *
     * @param dto ComputerDTO
     * @return Computer
     * @throws EmptyNameException    ene
     * @throws NumberFormatException nfe
     * @throws ParseException        pe
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
