package com.excilys.cdb.controller;

import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/computers/add")
public class AddComputerController {

    /**
     * companyService CompanyService.
     */
    @Autowired
    private CompanyService companyService;

    /**
     * computerService ComputerService.
     */
    @Autowired
    private ComputerService computerService;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    public AddComputerController(CompanyService companySer, ComputerService computerSer) {
        companyService = companySer;
        computerService = computerSer;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String show(@RequestParam Optional<String> feedback, @RequestParam Optional<String> message, Model map) {

        map.addAttribute("feedback", new Feedback(feedback.orElse(""), message.orElse("")));
        map.addAttribute("companies", companyService.list(0, 0));
        return "add-computer";
    }

    @RequestMapping(method = RequestMethod.POST)
    protected RedirectView create(HttpServletRequest request, @RequestParam Map<String, String> body) {
        String status = "danger";
        String message = "";
        try {
            ComputerDTOBuilder builder = new ComputerDTOBuilder();
            builder.setName(body.get("name")).setIntroduction(body.get("introduced"))
                    .setDiscontinuation(body.get("discontinued"));

            if (!body.get("companyId").equals("")) {
                builder.setCompanyId(Long.valueOf(body.get("companyId")));
            }

            if (!computerService.create(builder.get())) {
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

        return new RedirectView(request.getContextPath() + "/computers/add?feedback=" + status + "&message=" + message);
    }
}
