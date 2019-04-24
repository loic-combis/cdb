package com.excilys.cdb.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.cdb.mapper.ComputerDTOMapper;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.ComputerService;

@Controller
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
    @GetMapping(value = "/computers")
    protected String list(@RequestParam Optional<String> status, @RequestParam Optional<String> message,
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage,
            @RequestParam Optional<String> search, @RequestParam("orderby") Optional<String> orderBy, Model map) {

        map.addAttribute("feedback", new Feedback(status.orElse(""), message.orElse("")));
        map.addAttribute("orderby", orderBy.orElse(""));
        map.addAttribute("search", search.orElse(""));
        Long computerCount = computerService.count(search.orElse(""));

        map.addAttribute("pagination", new Pagination(page.orElse(1), itemPerPage.orElse(10), computerCount));

        map.addAttribute("computers", mapper.mapList(
                computerService.list(page.orElse(1), itemPerPage.orElse(10), search.orElse(""), orderBy.orElse(""))));
        return "dashboard";

    }
}
