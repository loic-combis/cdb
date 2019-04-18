package com.excilys.cdb.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    /**
     * Endpoint to handle error on requests.
     *
     * @param request HttpServletRequest
     * @param map     Model
     * @return String
     */
    @GetMapping(value = "/error")
    public String show(HttpServletRequest request, Model map) {

        String errorMsg = "";
        int httpErrorCode = getErrorCode(request);

        switch (httpErrorCode) {
        case 400:
            errorMsg = "Bad Request dumbass!";
            break;

        case 401:
            errorMsg = "Unauthorized, get out!";
            break;

        case 404:
            errorMsg = "Resource not found, too bad bitch!";
            break;

        case 500:
            errorMsg = "Internal Server Error, my bad bro...";
            break;

        default:
            errorMsg = "Unknown reason.";
            break;
        }
        map.addAttribute("errorCode", httpErrorCode);
        map.addAttribute("errorMessage", errorMsg);

        return "error";
    }

    /**
     * Returns the HTTP error Code.
     *
     * @param httpRequest HttpServletRequest
     * @return int
     */
    private int getErrorCode(HttpServletRequest httpRequest) {
        return (Integer) httpRequest.getAttribute("javax.servlet.error.status_code");
    }
}
