package com.excilys.cdb.service.service;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.User;
import com.excilys.cdb.persistence.dao.UserDAO;
import com.excilys.cdb.persistence.exception.UserAlreadyExistsException;

@Lazy
@Service("userService")
public class UserService {

    public static final String CREATE_USER_SUCCESS = "user.created";
    public static final String CREATE_USER_FAILURE = "user.not.created";
    public static final String USER_ALREADY_EXISTS = "user.already.exists";
    public static final String USER_NOT_FOUND = "user.not.found";
    public static final String WRONG_CREDENTIALS = "wrong.credentials";

    private UserDAO userDAO;

    public UserService(UserDAO dao) {
        userDAO = dao;
    }

    public boolean create(User user) throws UserAlreadyExistsException {
        return userDAO.create(user);
    }

    public Object login(String string, Function<User, Boolean> verificator) {
        Optional<User> opt = userDAO.findByLogin(string);
        if (opt.isEmpty()) {
            return USER_NOT_FOUND;
        }

        if (verificator.apply(opt.get())) {
            return opt.get();
        }

        return WRONG_CREDENTIALS;
    }
}
