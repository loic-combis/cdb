package com.excilys.webapp.controller.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.core.CreateUserFormData;
import com.excilys.core.Feedback;
import com.excilys.core.User;
import com.excilys.persistence.exception.UserAlreadyExistsException;
import com.excilys.service.service.UserService;
import com.excilys.webapp.validator.UserValidator;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/users")
public class UserRestController extends AbstractRestController {

    private UserService userService;

    private UserValidator validator;

    private MessageSource source;

    private PasswordEncoder encoder;

    public UserRestController(UserService service, UserValidator userValidator, MessageSource messageSource,
            PasswordEncoder pwdEncoder) {
        userService = service;
        validator = userValidator;
        source = messageSource;
        encoder = pwdEncoder;
    }

    /**
     * Init the createUserFormValidator.
     *
     * @param binder WebDataBinder
     */
    @InitBinder("createUserFormData")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    @PostMapping("/admin")
    public ResponseEntity<Feedback> createAdmin(HttpServletRequest request,
            @Validated @RequestBody CreateUserFormData userFormData, BindingResult result) {

        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }

        String message = "";
        String status = "danger";

        if (result.hasErrors()) {
            message = source.getMessage(result.getAllErrors().get(0).getCode(), null, LocaleContextHolder.getLocale());

        } else {
            try {
                userFormData.setPassword(encoder.encode(userFormData.getPassword()));
                if (userService.create(User.ofCreateForm(userFormData), User.ROLE_MANAGER)) {
                    message = source.getMessage(UserService.CREATE_USER_SUCCESS, null, LocaleContextHolder.getLocale());
                    status = "success";
                } else {
                    message = source.getMessage(UserService.CREATE_USER_FAILURE, null, LocaleContextHolder.getLocale());
                }
            } catch (UserAlreadyExistsException e) {
                // TODO Auto-generated catch block
                message = source.getMessage(UserService.USER_ALREADY_EXISTS, null, LocaleContextHolder.getLocale());
            }
        }
        return ResponseEntity.ok(new Feedback(status, message));
    }

    @PostMapping
    public ResponseEntity<Feedback> create(@Validated @RequestBody CreateUserFormData userFormData,
            BindingResult result) {
        String message = "";
        String status = "danger";

        if (result.hasErrors()) {
            message = source.getMessage(result.getAllErrors().get(0).getCode(), null, LocaleContextHolder.getLocale());

        } else {
            try {
                userFormData.setPassword(encoder.encode(userFormData.getPassword()));
                if (userService.create(User.ofCreateForm(userFormData), User.ROLE_USER)) {
                    message = source.getMessage(UserService.CREATE_USER_SUCCESS, null, LocaleContextHolder.getLocale());
                    status = "success";
                } else {
                    message = source.getMessage(UserService.CREATE_USER_FAILURE, null, LocaleContextHolder.getLocale());
                }
            } catch (UserAlreadyExistsException e) {
                // TODO Auto-generated catch block
                message = source.getMessage(UserService.USER_ALREADY_EXISTS, null, LocaleContextHolder.getLocale());
            }
        }
        return ResponseEntity.ok(new Feedback(status, message));
    }
}
