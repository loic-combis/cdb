package com.excilys.cdb.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.service.ComputerService;

@Controller
public class DeleteComputerController {

    private ComputerService computerService;

    /**
     * Constructor.
     *
     * @param computerSer ComputerService
     */
    public DeleteComputerController(ComputerService computerSer) {
        computerService = computerSer;
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
            message = ComputerService.DELETE_MANY_SUCCESS;
        } else {
            status = "danger";
            message = ComputerService.DELETE_MANY_FAILURE;
        }

        return new RedirectView("/computers?feedback=" + status + "&message=" + message);

    }
}
