package com.excilys.cdb.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerDTOValidator;

@Controller
public class EditComputerController {

    private ComputerService computerService;

    private CompanyService companyService;

    private ComputerDTOMapper mapper;

    private ComputerDTOValidator validator;

    private MessageSource source;

    /**
     * Constructor.
     *
     * @param computerSer ComputerService
     * @param companySer  CompanyService
     * @param dtoMapper   ComputerDTOMapper
     */
    public EditComputerController(ComputerService computerSer, CompanyService companySer, ComputerDTOMapper dtoMapper,
            ComputerDTOValidator computerValidator, MessageSource messageSource) {
        computerService = computerSer;
        companyService = companySer;
        mapper = dtoMapper;
        validator = computerValidator;
        source = messageSource;
    }

    /**
     * Init ComputerDTO validator.
     *
     * @param binder WebDatabinder
     */
    @InitBinder("computerDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * Init the computerDTO model attribute.
     *
     * @return ComputerDTO
     */
    @ModelAttribute
    public ComputerDTO computerDTO() {
        return new ComputerDTO();
    }

    /**
     * Endpoint to show the edit computer page.
     *
     * @param id  Long
     * @param map Model
     * @return String
     */
    @GetMapping(value = "/computers/{id}/edit")
    protected String show(@PathVariable(value = "id") Long id, Model map) {
        Optional<Computer> computer = computerService.get(id);
        if (computer.isPresent()) {
            map.addAttribute("computerDTO", mapper.toDTO(computer.get()));
            map.addAttribute("companies", companyService.list(0, 0));

            return "edit-computer";
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint to edit a computer.
     *
     * @param computerDTO ComputerDTO
     * @param id          Long
     * @param result      BindingResult
     * @return RedirectView
     */
    @PostMapping(value = "/computers/{id}/edit")
    protected RedirectView edit(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
            @PathVariable(value = "id") Long id, BindingResult result) {
        String status = "danger";
        String message = "";

        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getCode();

        } else if (computerService.update(mapper.toComputer(computerDTO))) {
            status = "success";
            message = source.getMessage(ComputerService.EDIT_COMPUTER_SUCCESS, null, LocaleContextHolder.getLocale());

        } else {
            message = source.getMessage(ComputerService.EDIT_COMPUTER_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return new RedirectView("/computers?status=" + status + "&message=" + message);
    }

}
