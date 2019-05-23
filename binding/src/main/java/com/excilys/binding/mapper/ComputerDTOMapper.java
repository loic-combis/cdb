package com.excilys.binding.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.dto.ComputerDTOBuilder;
import com.excilys.core.company.Company;
import com.excilys.core.company.CompanyFactory;
import com.excilys.core.computer.Computer;
import com.excilys.core.computer.ComputerFactory;

@Lazy
@Component("computerDTOMapper")
public class ComputerDTOMapper {

    /**
     * df DateTimeFormatter.
     */
    private DateTimeFormatter df;

    /**
     * Constructor.
     */
    public ComputerDTOMapper() {
        df = DateTimeFormatter.ofPattern("uuuu-MM-dd").withResolverStyle(ResolverStyle.STRICT);
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
                .setIntroduction(computer.getIntroduced() == null ? null : df.format(computer.getIntroduced()))
                .setDiscontinuation(computer.getDiscontinued() == null ? null : df.format(computer.getDiscontinued()))
                .setCompanyName(computer.getCompany() != null ? computer.getCompany().getName() : null)
                .setCompanyId(computer.getCompany() != null ? computer.getCompany().getId() : null);

        return builder.get();
    }

    /**
     * Map a ComputerDTO into a Computer.
     *
     * @param dto ComputerDTO
     * @return Computer
     * @throws NumberFormatException  nfe
     * @throws DateTimeParseException dtpe
     */
    public Computer toComputer(ComputerDTO dto) throws DateTimeParseException, NumberFormatException {

        String intro = dto.getIntroduced();
        LocalDate introduction = intro == null || intro.isEmpty() ? null : LocalDate.parse(dto.getIntroduced(), df);

        String disco = dto.getDiscontinued();
        LocalDate discontinuation = disco == null || disco.isEmpty() ? null
                : LocalDate.parse(dto.getDiscontinued(), df);

        Company company = CompanyFactory.getInstance().create(dto.getCompanyId(), dto.getCompanyName());
        if (company.getId() == null) {
            company = null;
        }

        return ComputerFactory.getInstance().createWithAll(dto.getId(), dto.getName(), introduction, discontinuation,
                company);
    }

    /**
     * Map a list of computers into a list of dto.
     *
     * @param computers List<Computer>
     * @return List<ComputerDTO>
     */
    public List<ComputerDTO> mapList(List<Computer> computers) {
        List<ComputerDTO> dtos = new LinkedList<ComputerDTO>();

        computers.stream().forEach(computer -> {
            dtos.add(toDTO(computer));
        });

        return dtos;
    }

}
