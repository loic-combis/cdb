package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.UnsuccessfulTreatmentException;
import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@WebServlet(name = "List Computers", urlPatterns = { "/list-computers" })
public class ListComputerServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 4009417829257782424L;

    private final Logger logger = LoggerFactory.getLogger(ListComputerServlet.class);

    private ComputerService computerService = new ComputerService();

    private CompanyService companyService = new CompanyService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        request.setAttribute("contextPath", request.getContextPath());
        request.setAttribute("feedback", new Feedback(request.getParameter("status"), request.getParameter("message")));

        int page, itemPerPage;
        try {
            page = Integer.parseInt(request.getParameter("page"));

        } catch (NumberFormatException nfe) {
            page = 1;
        }
        try {
            itemPerPage = Integer.parseInt(request.getParameter("itemPerPage"));

        } catch (NumberFormatException nfe) {
            itemPerPage = 10;
        }

        String search = request.getParameter("search");
        request.setAttribute("search", search);
        request.setAttribute("orderBy", request.getParameter("orderby"));

        int computerCount = computerService.count(search);
        request.setAttribute("pagination", new Pagination(page, itemPerPage, computerCount));

        request.setAttribute("companies", companyService.list(0, 0));

        try {
            request.setAttribute("computers",
                    computerService.list(page, itemPerPage, search, request.getParameter("orderby")));
            request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);

        } catch (UnsuccessfulTreatmentException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
            response.sendError(500);
        }

    }
}
