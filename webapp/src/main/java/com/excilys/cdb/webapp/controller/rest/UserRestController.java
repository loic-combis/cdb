package com.excilys.cdb.webapp.controller.rest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.core.CreateUserFormData;
import com.excilys.cdb.core.Feedback;
import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.exception.UserAlreadyExistsException;
import com.excilys.cdb.service.service.UserService;
import com.excilys.cdb.webapp.validator.UserValidator;

@RestController
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
     * Init the model attribute user.
     *
     * @return CreateUserFormData
     */
    @ModelAttribute
    public CreateUserFormData createUserFormData() {
        return new CreateUserFormData();
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

    @PostMapping
    public ResponseEntity<Feedback> createUser(
            @Validated @ModelAttribute("createUserFormData") CreateUserFormData userFormData, BindingResult result) {
        String message = "";
        String status = "danger";

        if (result.hasErrors()) {
            message = source.getMessage(result.getAllErrors().get(0).getCode(), null, LocaleContextHolder.getLocale());

        } else {
            try {
                userFormData.setPassword(encoder.encode(userFormData.getPassword()));
                if (userService.create(User.ofCreateForm(userFormData))) {
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
