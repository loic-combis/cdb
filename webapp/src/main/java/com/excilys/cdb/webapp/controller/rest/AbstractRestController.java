package com.excilys.cdb.webapp.controller.rest;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.excilys.cdb.persistence.util.PropertyReader;

public class AbstractRestController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    protected boolean hasRole(HttpServletRequest request, String... roles) {
        String auth = request.getHeader("Authorization");
        if (auth == null) {
            return false;
        }
        String token = auth.split(" ")[1];
        if (token == null) {
            return false;
        }

        try {
            Algorithm algorithm = Algorithm.HMAC256(new PropertyReader("config").get("secret_salt"));
            JWTVerifier verifier = JWT.require(algorithm).withIssuer("auth0").build(); // Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            return Arrays.asList(roles).contains(new JSONObject(jwt.getSubject()).getString("role"));

        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            logger.error(exception.getMessage());
            return false;
        }
    }
}
