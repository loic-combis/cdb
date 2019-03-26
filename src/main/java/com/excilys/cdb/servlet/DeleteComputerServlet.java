package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;

@WebServlet(name = "Delete Computers", urlPatterns = {"/delete-computers"})
public class DeleteComputerServlet extends HttpServlet{

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = -8188514775798686421L;

    /**
     * computerService ComputerService.
     */
    private ComputerService computerService = new ComputerService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String[] selection = request.getParameter("selection").split(",");
        boolean success = computerService.deleteMany(selection);

        String message, status;
        if (success) {
            status = "success";
            message = ComputerService.DELETE_MANY_SUCCESS;
        } else {
            status = "danger";
            message = ComputerService.DELETE_MANY_FAILURE;
        }

        response.sendRedirect(request.getContextPath() + "/list-computers?status=" + status + "&message=" + message);
    }
}
