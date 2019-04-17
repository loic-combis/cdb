package com.excilys.cdb.controller;

import java.time.format.DateTimeParseException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
public class AddComputerController {

    /**
     * companyService CompanyService.
     */
    private CompanyService companyService;

    /**
     * computerService ComputerService.
     */
    private ComputerService computerService;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    public AddComputerController(CompanyService companySer, ComputerService computerSer) {
        companyService = companySer;
        computerService = computerSer;
    }

    @GetMapping(value = "/computers/add", produces = MediaType.TEXT_HTML_VALUE)
    protected String show(@RequestParam Optional<String> feedback, @RequestParam Optional<String> message, Model map) {
        map.addAttribute("feedback", new Feedback(feedback.orElse(""), message.orElse("")));
        map.addAttribute("companies", companyService.list(0, 0));
        return "add-computer";
    }

    @ModelAttribute
    public ComputerDTO computerDTO() {
        return new ComputerDTO();
    }

    @PostMapping("/computers/add")
    protected RedirectView create(@ModelAttribute("computerDTO") ComputerDTO computerDTO) {
        String status = "danger";
        String message = "";
        try {
            if (!computerService.create(computerDTO)) {
                message = ComputerService.ADD_COMPUTER_FAILURE;
            } else {
                status = "success";
                message = ComputerService.ADD_COMPUTER_SUCCESS;
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

        return new RedirectView("/computers/add?feedback=" + status + "&message=" + message);
    }
}
