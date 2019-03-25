package com.excilys.cdb.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.model.ComputerDTO;
import com.excilys.cdb.model.EmptyNameException;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@WebServlet(name = "Add Computer", urlPatterns = "/add-computer")
public class AddComputerServlet extends HttpServlet {

    /**
     * serialVeID long.
     */
    private static final long serialVersionUID = 909640541027872558L;

    /**
     * companyService CompanyService.
     */
    private CompanyService companyService = new CompanyService();

    /**
     * computerService ComputerService.
     */
    private ComputerService computerService = new ComputerService();

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        request.setAttribute("feedbackStyle", request.getParameter("feedback"));
        request.setAttribute("message", request.getParameter("message"));
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("companies", companyService.list(0, 0));
        request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = "success";
        String message = ComputerService.ADD_COMPUTER_SUCCESS;
        try {
            ComputerDTO dto = new ComputerDTO(request.getParameter("name"), request.getParameter("introduced"),
                    request.getParameter("discontinued"), Long.parseLong(request.getParameter("company")));
            Optional<ComputerDTO> opt = computerService.create(dto);
            if (!opt.isPresent()) {
                status = "danger";
                message = ComputerService.ADD_COMPUTER_FAILURE;
            }

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            logger.warn(e.getMessage());
            status = "danger";
            message = ComputerService.WRONG_DATE_FORMAT;

        } catch (EmptyNameException ene) {
            logger.warn(ene.getMessage());
            status = "danger";
            message = ComputerService.EMPTY_NAME;

        } catch (NumberFormatException nfe) {
            logger.warn(nfe.getMessage());
            status = "danger";
            message = ComputerService.INVALID_COMPANY;
        } finally {
            response.sendRedirect(
                    request.getContextPath() + "/add-computer?feedback=" + status + "&message=" + message);
        }
    }

}
