package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.company.CompanyFactory;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
import com.excilys.cdb.model.computer.ComputerFactory;

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
	 * @throws EmptyNameException         ene
	 * @throws NumberFormatException      nfe
	 * @throws DateTimeParseException     dtpe
	 * @throws UnconsistentDatesException ude
	 */
	public Computer toComputer(ComputerDTO dto)
			throws DateTimeParseException, NumberFormatException, EmptyNameException, UnconsistentDatesException {

		String intro = dto.getIntroduction();
		LocalDate introduction = intro == null || intro.isEmpty() ? null : LocalDate.parse(dto.getIntroduction(), df);

		String disco = dto.getDiscontinuation();
		LocalDate discontinuation = disco == null || disco.isEmpty() ? null
				: LocalDate.parse(dto.getDiscontinuation(), df);

		Company company = CompanyFactory.getInstance().create(dto.getCompanyId(), dto.getCompany());

		return ComputerFactory.getInstance().createWithAll(dto.getId(), dto.getName(), introduction, discontinuation,
				company);
	}

}
