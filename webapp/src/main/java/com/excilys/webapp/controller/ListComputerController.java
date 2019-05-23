package com.excilys.webapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.binding.mapper.ComputerDTOMapper;
import com.excilys.core.Feedback;
import com.excilys.core.Pagination;
import com.excilys.core.computer.Computer;
import com.excilys.service.service.ComputerService;

@Controller
@RequestMapping("/computers")
public class ListComputerController {

    private ComputerService computerService;

    private ComputerDTOMapper mapper;

    /**
     * Constructor.
     *
     * @param computerSer ComputerService
     * @param dtoMapper   ComputerDTOMapper
     */
    public ListComputerController(ComputerService computerSer, ComputerDTOMapper dtoMapper) {
        computerService = computerSer;
        mapper = dtoMapper;
    }

    /**
     * Endpoint to list a set of computers.
     *
     * @param status      String
     * @param message     String
     * @param page        int
     * @param itemPerPage int
     * @param search      String
     * @param orderBy     String
     * @param map         Model
     * @return String
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    protected String list(@RequestParam Optional<String> status, @RequestParam Optional<String> message,
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage,
            @RequestParam Optional<String> search, @RequestParam("orderby") Optional<String> orderBy, Model map) {

        map.addAttribute("feedback", new Feedback(status.orElse(""), message.orElse("")));
        map.addAttribute("orderby", orderBy.orElse(""));
        map.addAttribute("search", search.orElse(""));

        List<Computer> computers = computerService.list(page.orElse(1), itemPerPage.orElse(10), search.orElse(""),
                orderBy.orElse(""));

        map.addAttribute("pagination",
                new Pagination(page.orElse(1), itemPerPage.orElse(10), computerService.count(search.orElse(""))));

        map.addAttribute("computers", mapper.mapList(computers));
        return "dashboard";

    }
}
