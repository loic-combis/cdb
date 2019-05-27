package com.excilys.webapp.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    private MessageSource source;

    /**
     * Constructor.
     *
     * @param messageSource MessageSource;
     */
    public ErrorController(MessageSource messageSource) {
        source = messageSource;
    }

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
            errorMsg = source.getMessage("400", null, LocaleContextHolder.getLocale());
            break;

        case 401:
            errorMsg = source.getMessage("401", null, LocaleContextHolder.getLocale());
            break;

        case 403:
            errorMsg = source.getMessage("403", null, LocaleContextHolder.getLocale());
            break;

        case 404:
            errorMsg = source.getMessage("404", null, LocaleContextHolder.getLocale());
            break;

        case 500:
            errorMsg = source.getMessage("500", null, LocaleContextHolder.getLocale());
            break;

        default:
            errorMsg = source.getMessage("default", null, LocaleContextHolder.getLocale());
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
        Object code = httpRequest.getAttribute("javax.servlet.error.status_code");

        if (code == null) {
            code = httpRequest.getParameter("code");
            if (code != null) {
                try {
                    return Integer.valueOf((String) code);
                } catch (NumberFormatException e) {
                    return 400;
                }
            } else {
                return 500;
            }
        } else {
            return (Integer) code;
        }
    }
}
