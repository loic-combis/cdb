package com.excilys.cdb.webapp.controller.rest;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.util.PropertyReader;
import com.excilys.cdb.service.service.UserService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/authenticate")
public class AuthenticationRestController extends AbstractRestController {

    private UserService service;
    private PasswordEncoder encoder;

    public AuthenticationRestController(UserService userService, PasswordEncoder pwdEncoder) {
        service = userService;
        encoder = pwdEncoder;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody String stringBody) {
        JSONObject response = new JSONObject();
        JSONObject body = new JSONObject(stringBody);

        Optional<User> opt = service.find(body.getString("login"));

        if (opt.isPresent() && encoder.matches(body.getString("password"), opt.get().getPassword())) {

            try {
                Algorithm algorithm = Algorithm.HMAC256(new PropertyReader("config").get("secret_salt"));
                JSONObject subject = new JSONObject().put("id", opt.get().getId()).put("role", opt.get().getRole());
                response.put("token", JWT.create().withIssuer("auth0").withSubject(subject.toString()).sign(algorithm));
                return ResponseEntity.ok(response.toString());

            } catch (JWTCreationException exception) {
                return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } else {
            return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
        }

    }
}