package com.excilys.cdb.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Static Servlet", urlPatterns = {"*.js", "*.css", "*.html"})
public class StaticServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 1604616046051120420L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println();
        request.getRequestDispatcher("/WEB-INF/assets/" + request.getRequestURI());
    }
}
