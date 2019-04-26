package com.excilys.cdb.webapp.controller;

import java.util.Map;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.service.service.ComputerService;

@Controller
public class DeleteComputerController {

    private ComputerService computerService;

    private MessageSource source;

    /**
     * Constructor.
     *
     * @param computerSer ComputerService
     */
    public DeleteComputerController(ComputerService computerSer, MessageSource messageSource) {
        computerService = computerSer;
        source = messageSource;
    }

    /**
     * Endpoint to delete a set of computers.
     *
     * @param body Map<String, String>
     * @return RedirectView
     */
    @PostMapping(value = "/computers/delete")
    protected RedirectView deleteMany(@RequestParam Map<String, String> body) {
        String[] selection = body.get("selection").split(",");
        boolean success = computerService.deleteMany(selection);

        String message, status;
        if (success) {
            status = "success";
            message = source.getMessage(ComputerService.DELETE_MANY_SUCCESS, null, LocaleContextHolder.getLocale());
        } else {
            status = "danger";
            message = source.getMessage(ComputerService.DELETE_MANY_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return new RedirectView("/computers?status=" + status + "&message=" + message);

    }
}
