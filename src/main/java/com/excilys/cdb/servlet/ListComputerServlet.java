package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.model.Feedback;
import com.excilys.cdb.model.Pagination;
import com.excilys.cdb.service.ComputerService;

@WebServlet(name = "List Computers", urlPatterns = { "/list-computers" })
public class ListComputerServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 4009417829257782424L;

    private ComputerService computerService = new ComputerService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

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
        request.setAttribute("contextPath", request.getContextPath());
        int computerCount = computerService.count();

        request.setAttribute("pagination", new Pagination(page, itemPerPage, computerCount));
        request.setAttribute("computers", computerService.list(page, itemPerPage));
        request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }
}
