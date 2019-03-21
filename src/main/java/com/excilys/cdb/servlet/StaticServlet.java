package com.excilys.cdb.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "stop", urlPatterns = "/test")
public class StaticServlet extends HttpServlet {

    /**
     * serialVersionUID long.
     */
    private static final long serialVersionUID = 1604616046051120420L;

    /**
     * LOGGER Logger.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(StaticServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType(request.getRequestURI().endsWith(".js") ? "text/javascript" : "text/css");

        String filePath = "/WEB-INF/assets" + removeContextPath(request.getRequestURI(), request.getContextPath());
        try {
            getServletContext().getRequestDispatcher(filePath).forward(request, response);

        } catch(NullPointerException npe) {
            LOGGER.error("NullPointerException : The file '" + filePath + "' does not exist.");
            //response.sendError(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().print(filePath);
        }
    }

    /**
     * Remove the contextPath of the uri.
     *
     * @param uri String
     * @param contextPath String
     * @return String
     */
    private String removeContextPath(String uri, String contextPath) {
        return uri.replace(contextPath, "");
    }
}
