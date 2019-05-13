package com.excilys.cdb.webapp.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.binding.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {

    public static final String BAD_DATE_FORMAT = "bad.date.format";
    public static final String EMPTY_NAME = "empty.name";
    public static final String INVALID_COMPANY = "invalid.company";
    public static final String INVALID_COMPUTER_ID = "invalid.computer.id";
    public static final String UNCONSISTENT_DATES = "unconsistent.dates";

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDTOValidator.class);

    private MessageSource source;

    public ComputerDTOValidator(MessageSource source) {
        this.source = source;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return ComputerDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        ComputerDTO dto = (ComputerDTO) target;
        LOGGER.error(dto.toString());
        if (StringUtils.isEmpty(dto.getName()) || dto.getName().trim().equals("")) {
            errors.rejectValue("name", source.getMessage(EMPTY_NAME, null, LocaleContextHolder.getLocale()));
        }

        if (dto.getId() != null && dto.getId() < 1) {
            errors.rejectValue("id", source.getMessage(INVALID_COMPUTER_ID, null, LocaleContextHolder.getLocale()));
        }
        try {
            if (!StringUtils.isEmpty(dto.getIntroduced()) && !StringUtils.isEmpty(dto.getDiscontinued())
                    && LocalDate.parse(dto.getIntroduced()).compareTo(LocalDate.parse(dto.getDiscontinued())) > 0) {
                errors.rejectValue("introduced",
                        source.getMessage(UNCONSISTENT_DATES, null, LocaleContextHolder.getLocale()));
                errors.rejectValue("discontinued",
                        source.getMessage(UNCONSISTENT_DATES, null, LocaleContextHolder.getLocale()));
            }
        } catch (DateTimeParseException dpe) {
            errors.rejectValue("introduced", source.getMessage(BAD_DATE_FORMAT, null, LocaleContextHolder.getLocale()));
            LOGGER.warn(dpe.getMessage());
        }

        if (dto.getCompanyId() != null && dto.getCompanyId() < 1) {
            errors.rejectValue("companyId", source.getMessage(INVALID_COMPANY, null, LocaleContextHolder.getLocale()));
        }

    }

}
