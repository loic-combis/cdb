package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.service.ComputerService;

@Controller
public class DeleteComputerController {

    private ComputerService computerService;

    public DeleteComputerController(ComputerService computerSer) {
        computerService = computerSer;
    }

    @PostMapping(value = "/computers/delete")
    protected RedirectView deleteMany(@RequestParam Map<String, String> body) throws ServletException, IOException {

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
