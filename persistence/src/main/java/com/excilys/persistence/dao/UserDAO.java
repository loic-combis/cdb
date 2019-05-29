package com.excilys.persistence.dao;

import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;

import com.excilys.core.User;
import com.excilys.persistence.exception.UserAlreadyExistsException;

@Lazy
@Repository(value = "userDAO")
public class UserDAO {

    private SessionFactory factory;

    private Logger logger = LoggerFactory.getLogger(UserDAO.class);

    public static final String FIND_BY_LOGIN = "from User where BINARY(login) = BINARY(:login)";

    /**
     * Constructor.
     *
     * @param sessionFactory LocalSessionFactoryBean
     */
    public UserDAO(LocalSessionFactoryBean sessionFactory) {
        factory = sessionFactory.getObject();
    }

    public boolean create(User user, String role) throws UserAlreadyExistsException {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            user.setRole(role);
            Long id = (Long) session.save(user);

            session.getTransaction().commit();

            logger.info("User created with id : " + id);

            return true;

        } catch (ConstraintViolationException e) {

            throw new UserAlreadyExistsException();
        }
    }

    public Optional<User> findByLogin(String login) {

        Optional<User> opt = Optional.empty();

        try (Session session = factory.openSession()) {

            Query<User> query = session.createQuery(FIND_BY_LOGIN, User.class);
            query.setParameter("login", login);

            User user = query.getSingleResult();
            opt = Optional.ofNullable(user);

            logger.info("User fetched : " + user.getLogin());
        } catch (NoResultException nre) {

            logger.warn(nre.getMessage());
        }
        return opt;
    }
}
