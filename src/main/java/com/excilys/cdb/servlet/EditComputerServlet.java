package com.excilys.cdb.servlet;

import java.io.IOException;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
import com.excilys.cdb.service.ComputerService;

@Configurable
@WebServlet(name = "Edit Computer", urlPatterns = { "/edit-computer" })
public class EditComputerServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 3345158907466408519L;

    @Autowired
    private ComputerService computerService;

    private Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String status = "danger";
        String message = "";
        ComputerDTOBuilder builder = new ComputerDTOBuilder();

        try {
            builder.setId(Long.valueOf(request.getParameter("computerId"))).setName(request.getParameter("name"))
                    .setIntroduction(request.getParameter("introduced"))
                    .setDiscontinuation(request.getParameter("discontinued"));

            Long companyId = Long.valueOf(request.getParameter("company"));
            if (companyId != -1) {
                builder.setCompanyId(companyId);
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

        } finally {
            response.sendRedirect(
                    request.getContextPath() + "/list-computers?status=" + status + "&message=" + message);
        }
    }

}
