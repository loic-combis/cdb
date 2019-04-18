package com.excilys.cdb.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.computer.ComputerDTO;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerDTOValidator;

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

    private ComputerDTOMapper mapper;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    public AddComputerController(CompanyService companySer, ComputerService computerSer, ComputerDTOMapper dtoMapper) {
        companyService = companySer;
        computerService = computerSer;
        mapper = dtoMapper;
    }

    @InitBinder("computerDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(new ComputerDTOValidator());
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
    protected RedirectView create(@Validated @ModelAttribute("computerDTO") ComputerDTO computerDTO,
            BindingResult result) {
        String status = "danger";
        String message = "";

        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getDefaultMessage();

        } else if (computerService.create(mapper.toComputer(computerDTO))) {
            status = "success";
            message = ComputerService.ADD_COMPUTER_SUCCESS;
            logger.info("Computer created : " + computerDTO);

        } else {
            message = ComputerService.ADD_COMPUTER_FAILURE;
        }

        return new RedirectView("/computers/add?feedback=" + status + "&message=" + message);
    }
}
