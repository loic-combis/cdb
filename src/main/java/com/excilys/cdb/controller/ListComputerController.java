package com.excilys.cdb.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.excilys.cdb.exception.UnsuccessfulTreatmentException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ListComputerController {

    private final Logger logger = LoggerFactory.getLogger(ListComputerController.class);

    private ComputerService computerService;

    public ListComputerController(ComputerService computerSer) {
        computerService = computerSer;
    }

    @GetMapping(value = "/computers")
    protected String list(@RequestParam Optional<String> status, @RequestParam Optional<String> message,
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage,
            @RequestParam Optional<String> search, @RequestParam("orderby") Optional<String> orderBy, Model map) {

        map.addAttribute("feedback", new Feedback(status.orElse(""), message.orElse("")));
        map.addAttribute("orderby", orderBy.orElse(""));
        map.addAttribute("search", search.orElse(""));
        int computerCount = computerService.count(search.orElse(""));

        map.addAttribute("pagination", new Pagination(page.orElse(1), itemPerPage.orElse(10), computerCount));

        try {
            map.addAttribute("computers", computerService.list(page.orElse(1), itemPerPage.orElse(10),
                    search.orElse(""), orderBy.orElse("")));
            return "dashboard";

        } catch (UnsuccessfulTreatmentException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
