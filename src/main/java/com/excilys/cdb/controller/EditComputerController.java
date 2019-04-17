package com.excilys.cdb.controller;

import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class EditComputerController {

    private ComputerService computerService;

    private CompanyService companyService;

    private Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    public EditComputerController(ComputerService computerSer, CompanyService companySer) {
        computerService = computerSer;
        companyService = companySer;
    }

    @ModelAttribute
    public ComputerDTO computerDTO() {
        return new ComputerDTO();
    }

    @GetMapping(value = "/computers/{id}/edit")
    protected Object show(@PathVariable(value = "id") Long id, Model map) {
        try {
            Optional<ComputerDTO> computer = computerService.get(id);
            if (computer.isPresent()) {
                map.addAttribute("computerDTO", computer.get());
                map.addAttribute("companies", companyService.list(0, 0));

                return "edit-computer";
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

        } catch (EmptyNameException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (UnconsistentDatesException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/computers/{id}/edit")
    protected RedirectView edit(@ModelAttribute ComputerDTO computerDTO, @PathVariable(value = "id") Long id) {
        String status = "danger";
        String message = "";
        try {

            if (computerService.update(computerDTO)) {
                status = "success";
                message = ComputerService.EDIT_COMPUTER_SUCCESS;
            } else {
                message = ComputerService.EDIT_COMPUTER_FAILURE;
            }

        } catch (DateTimeParseException e) {
            // TODO Auto-generated catch block
            logger.warn(e.getMessage());
            message = ComputerService.WRONG_DATE_FORMAT;

        } catch (EmptyNameException ene) {
            logger.warn(ene.getMessage());
            message = ComputerService.EMPTY_NAME;

        } catch (NumberFormatException nfe) {
            logger.warn(nfe.getMessage());
            message = ComputerService.INVALID_COMPANY;

        } catch (UnconsistentDatesException ude) {
            logger.warn(ude.getMessage());
            message = ComputerService.UNCONSISTENT_DATES;

        }

        return new RedirectView("/computers?status=" + status + "&message=" + message);
    }

}
