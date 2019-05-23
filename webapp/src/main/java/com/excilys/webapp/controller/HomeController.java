package com.excilys.webapp.controller;

import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.excilys.core.CreateUserFormData;
import com.excilys.core.Feedback;
import com.excilys.core.User;
import com.excilys.persistence.exception.UserAlreadyExistsException;
import com.excilys.service.service.UserService;
import com.excilys.webapp.validator.UserValidator;

@Controller
public class HomeController {

    private UserService userService;

    private UserValidator validator;

    private MessageSource source;

    private PasswordEncoder encoder;

    public HomeController(UserService service, UserValidator userValidator, MessageSource messageSource,
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

    @GetMapping({ "/", "/home" })
    public String show(@RequestParam Optional<String> feedback, @RequestParam Optional<String> message, Model map) {

        map.addAttribute("feedback", new Feedback(feedback.orElse(""), message.orElse("")));

        return "home";
    }

    @PostMapping("/users")
    public RedirectView createUser(@Validated @ModelAttribute("createUserFormData") CreateUserFormData userFormData,
            BindingResult result) {
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

        return new RedirectView("/home?feedback=" + status + "&message=" + message);
    }
}
