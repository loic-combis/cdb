package com.excilys.cdb.webapp.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.excilys.cdb.core.CreateUserFormData;

@Component
public class UserValidator implements Validator {

    public static final String EMPTY_LOGIN = "login.is.empty";
    public static final String PASSWORD_IS_INVALID = "password.is.invalid";
    public static final String PASSWORDS_DO_NOT_MATCH = "passwords.do.not.match";

    @Override
    public boolean supports(Class<?> clazz) {
        // TODO Auto-generated method stub
        return CreateUserFormData.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // TODO Auto-generated method stub
        CreateUserFormData formData = (CreateUserFormData) target;

        if (StringUtils.isEmpty(formData.getLogin()) || formData.getLogin().trim().equals("")) {
            errors.rejectValue("login", EMPTY_LOGIN);
        }

        if (StringUtils.isEmpty(formData.getPassword()) || formData.getLogin().trim().length() < 6) {
            errors.rejectValue("password", PASSWORD_IS_INVALID);
        }

        if (!formData.getPassword().equals(formData.getConfirmation())) {
            errors.rejectValue("confirmation", PASSWORDS_DO_NOT_MATCH);
        }
    }

}
