package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Add Computer", urlPatterns = "/add-computer")
public class AddComputerServlet extends HttpServlet {

    /**
     * serialVeID long.
     */
    private static final long serialVersionUID = 909640541027872558L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");

        request.setAttribute("contextPath", request.getContextPath());
        request.getRequestDispatcher("/WEB-INF/jsp/addComputer.jsp").forward(request, response);
    }

}
