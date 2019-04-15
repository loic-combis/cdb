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
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.exception.EmptyNameException;
import com.excilys.cdb.exception.UnconsistentDatesException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.computer.ComputerDTOBuilder;
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
    @Autowired
    private CompanyService companyService;

    /**
     * computerService ComputerService.
     */
    @Autowired
    private ComputerService computerService;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerServlet.class);

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        request.setAttribute("feedback",
                new Feedback(request.getParameter("feedback"), request.getParameter("message")));
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("companies", companyService.list(0, 0));

        request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String status = "danger";
        String message = "";
        try {
            ComputerDTOBuilder builder = new ComputerDTOBuilder();
            builder.setName(request.getParameter("name")).setIntroduction(request.getParameter("introduced"))
                    .setDiscontinuation(request.getParameter("discontinued"));

            Long companyId = Long.valueOf(request.getParameter("company"));
            if (companyId != -1) {
                builder.setCompanyId(companyId);
            }

            if (!computerService.create(builder.get())) {
                message = ComputerService.ADD_COMPUTER_FAILURE;
            } else {
                status = "success";
                message = ComputerService.ADD_COMPUTER_SUCCESS;
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
            message = ComputerService.INVALID_COMPANY;

        } catch (UnconsistentDatesException ude) {
            logger.warn(ude.getMessage());
            message = ComputerService.UNCONSISTENT_DATES;

        } finally {
            response.sendRedirect(
                    request.getContextPath() + "/add-computer?feedback=" + status + "&message=" + message);
        }
    }
}
