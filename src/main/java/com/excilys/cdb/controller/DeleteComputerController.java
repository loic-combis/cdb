package com.excilys.cdb.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/computers/delete")
public class DeleteComputerController {

    private ComputerService computerService;

    public DeleteComputerController(ComputerService computerSer) {
        computerService = computerSer;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected RedirectView deleteMany(HttpServletRequest request, @RequestParam Map<String, String> body)
            throws ServletException, IOException {

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

        return new RedirectView(request.getContextPath() + "/computers?feedback=" + status + "&message=" + message);

    }
}
