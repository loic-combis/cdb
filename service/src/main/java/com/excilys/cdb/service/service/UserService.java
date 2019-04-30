package com.excilys.cdb.service.service;

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

    private UserDAO userDAO;

    public UserService(UserDAO dao) {
        userDAO = dao;
    }

    public boolean create(User user) throws UserAlreadyExistsException {
        return userDAO.create(user);
    }
}
