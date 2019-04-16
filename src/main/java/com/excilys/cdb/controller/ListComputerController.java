package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.exception.UnsuccessfulTreatmentException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/computers")
public class ListComputerController {

    private final Logger logger = LoggerFactory.getLogger(ListComputerController.class);

    private ComputerService computerService;

    private CompanyService companyService;

    public ListComputerController(ComputerService computerSer, CompanyService companySer) {
        computerService = computerSer;
        companyService = companySer;
    }

    @RequestMapping(method = RequestMethod.GET)
    protected String list(@RequestParam Optional<String> status, @RequestParam Optional<String> message,
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage,
            @RequestParam Optional<String> search, @RequestParam("orderby") Optional<String> orderBy, Model map)
            throws ServletException, IOException {

        map.addAttribute("feedback", new Feedback(status.orElse(""), message.orElse("")));

        map.addAttribute("orderby", orderBy.orElse(""));
        map.addAttribute("search", search.orElse(""));
        int computerCount = computerService.count(search.orElse(""));

        map.addAttribute("pagination", new Pagination(page.orElse(1), itemPerPage.orElse(10), computerCount));

        map.addAttribute("companies", companyService.list(0, 0));

        try {
            map.addAttribute("computers", computerService.list(page.orElse(1), itemPerPage.orElse(10),
                    search.orElse(""), orderBy.orElse("")));
            return "dashboard";

        } catch (UnsuccessfulTreatmentException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            return "500";
        }

    }
}
