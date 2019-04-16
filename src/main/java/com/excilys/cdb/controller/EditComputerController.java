package com.excilys.cdb.controller;

import java.time.format.DateTimeParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/computers/edit")
public class EditComputerController {

    private ComputerService computerService;

    private Logger logger = LoggerFactory.getLogger(EditComputerController.class);

    public EditComputerController(ComputerService computerSer) {
        computerService = computerSer;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected RedirectView edit(HttpServletRequest request, @RequestParam Map<String, String> body) {
        String status = "danger";
        String message = "";
        ComputerDTOBuilder builder = new ComputerDTOBuilder();

        try {
            builder.setId(Long.valueOf(body.get("computerId"))).setName(body.get("name"))
                    .setIntroduction(body.get("introduced")).setDiscontinuation(body.get("discontinued"));

            if (!body.get("companyId").equals("")) {
                builder.setCompanyId(Long.valueOf(body.get("companyId")));
            }

            if (computerService.update(builder.get())) {
                status = "success";
                message = ComputerService.EDIT_COMPUTER_SUCCESS;
            } else {
                message = ComputerService.EDIT_COMPUTER_FAILURE;
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
            message = builder.get().getId() == null ? ComputerService.INVALID_COMPUTER_ID
                    : ComputerService.INVALID_COMPANY;

        } catch (UnconsistentDatesException ude) {
            logger.warn(ude.getMessage());
            message = ComputerService.UNCONSISTENT_DATES;

        }

        return new RedirectView(request.getContextPath() + "/computers?status=" + status + "&message=" + message);
    }

}
