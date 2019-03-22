package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;

@WebServlet(name = "List Computers", urlPatterns = {"/list-computers", "/delete-computers"})
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

        int page, itemPerPage;
        try {
            page = Integer.parseInt(request.getParameter("page"));
            itemPerPage = Integer.parseInt(request.getParameter("itemPerPage"));

        } catch (NumberFormatException nfe) {
            page = 1;
            itemPerPage = 10;
        }
        request.setAttribute("feedbackStyle", request.getParameter("feedback"));
        request.setAttribute("message", request.getParameter("message"));
        int computerCount = computerService.count();
        request.setAttribute("sizes", new int[]{10, 20, 50, 100});
        request.setAttribute("computerCount", computerCount);
        request.setAttribute("itemPerPage", itemPerPage);
        request.setAttribute("currentPage", page);
        request.setAttribute("minPage", Math.max(page - 3, 1));
        request.setAttribute("maxPage", Math.min(page + 3, computerCount / itemPerPage));
        request.setAttribute("computers", computerService.list(page, itemPerPage));
        request.setAttribute("contextPath", request.getContextPath());
        request.getRequestDispatcher("/WEB-INF/jsp/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] selection = request.getParameter("selection").split(",");
        boolean success = computerService.deleteMany(selection);

        if (success) {
            response.sendRedirect(request.getContextPath() + "/list-computers?feedback=success&message=" + ComputerService.DELETE_MANY_SUCCESS);
        } else {
            response.sendRedirect(request.getContextPath() + "/list-computers?feedback=danger&message=" + ComputerService.DELETE_MANY_FAILURE);
        }
    }
}
