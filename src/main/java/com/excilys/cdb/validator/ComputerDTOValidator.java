package com.excilys.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.model.computer.ComputerDTO;

public class ComputerDTOValidator implements Validator {

    public static final String BAD_DATE_FORMAT = "Wrong date format.";
    public static final String EMPTY_NAME = "Name cannot be empty.";
    public static final String INVALID_COMPANY = "Provided company is invalid.";
    public static final String INVALID_COMPUTER_ID = "Provided computer id is invalid.";
    public static final String UNCONSISTENT_DATES = "Introduction and discontinuation dates are unconsistent.";

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOValidator.class);

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return ComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        ComputerDTO dto = (ComputerDTO) target;
        if (StringUtils.isEmpty(dto.getName()) || dto.getName().trim().equals("")) {
            errors.rejectValue("name", EMPTY_NAME);
        }

        if (dto.getId() != null && dto.getId() < 1) {
            errors.rejectValue("id", INVALID_COMPUTER_ID);
        }
        try {
            if (!StringUtils.isEmpty(dto.getIntroduced()) && !StringUtils.isEmpty(dto.getDiscontinued())
                    && LocalDate.parse(dto.getIntroduced()).compareTo(LocalDate.parse(dto.getDiscontinued())) < 0) {
                errors.rejectValue("introduced", UNCONSISTENT_DATES);
                errors.rejectValue("discontinued", UNCONSISTENT_DATES);
            }
        } catch (DateTimeParseException dpe) {
            errors.rejectValue("introduced", BAD_DATE_FORMAT);
            LOGGER.warn(dpe.getMessage());
        }

        if (dto.getCompanyId() != null && dto.getCompanyId() < 1) {
            errors.rejectValue("companyId", INVALID_COMPANY);
        }

    }

}
