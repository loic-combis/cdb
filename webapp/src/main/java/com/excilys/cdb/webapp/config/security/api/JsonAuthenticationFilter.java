package com.excilys.cdb.webapp.config.security.api;

import java.io.IOException;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JsonAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(JsonAuthenticationFilter.class);

    public JsonAuthenticationFilter() {
        this.setFilterProcessesUrl("/api/authenticate");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if ("application/json".equals(request.getContentType())) {
            UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
            setDetails(request, authRequest);
            return getAuthenticationManager().authenticate(authRequest);

        } else {
            return super.attemptAuthentication(request, response);
        }
    }

    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
        String username = request.getParameter("login");
        String password = request.getParameter("password");

        try {
            JSONObject body = new JSONObject(
                    request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
            username = body.getString("login");
            password = body.getString("password");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}