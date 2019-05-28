package com.excilys.persistence.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.company.Company;
import com.excilys.core.computer.Computer;

/**
 * Singleton Concrete implementation of ComputerDAO Responsible for bonding
 * Computer objects to the database thanks to JDBC.
 *
 * @author excilys
 *
 */
@Lazy
@Repository("computerDAO")
public class ComputerDAO {

    private SessionFactory factory;

    private Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "from Computer %s %s";
    private static final String FIND_BY_ID = "from Computer where id = :id";
    private static final String DELETE_ONE = "delete Computer where id = :id";
    private static final String UPDATE_ONE = "update Computer set name = :name, introduced = :introduced, discontinued = :discontinued, company_id = :company_id where id = :id";
    private static final String COUNT_ALL = "select count(c) from Computer c %s";
    private static final String DELETE_MANY = "delete Computer where id in (:ids)";

    private static final String SEARCH_WHERE_CLAUSE = "where name like '%%%s%%' or name like '%%%s%%'";
    private static final String ORDER_BY_CLAUSE = "order by %s %s";

    /**
     * Constructor.
     *
     * @param sessionFactory LocalSessionFactoryBean
     */
    public ComputerDAO(LocalSessionFactoryBean sessionFactory) {
        factory = sessionFactory.getObject();
    }

    /**
     * Fetch a specific computer.
     *
     * @param id Long
     * @return Optional<Computer>
     */
    @Transactional(readOnly = true)
    public Optional<Computer> get(Long id) {

        Optional<Computer> opt = Optional.empty();
        try (Session session = factory.openSession()) {

            Query<Computer> query = session.createQuery(FIND_BY_ID, Computer.class);
            query.setParameter("id", id);
            opt = Optional.of(query.getSingleResult());

            logger.info("Computer fetched : " + opt.get());
        } catch (NoResultException nre) {

            logger.warn(nre.getMessage());
        }

        return opt;
    }

    /**
     * Create a new computer.
     *
     * @param computer Computer
     * @return Optional<Computer>
     */
    @Transactional(readOnly = false)
    public boolean create(Computer computer) {

        try (Session session = factory.openSession()) {
            session.beginTransaction();

            if (computer.getCompany() != null) {
                Query<Company> query = session.createQuery(CompanyDAO.FIND_BY_ID, Company.class);
                query.setParameter("id", computer.getCompany().getId());
                computer.setCompany(query.getSingleResult());
            }
            Long id = (Long) session.save(computer);

            session.getTransaction().commit();

            logger.info("Computer created with id : " + id);

            return true;

        } catch (NoResultException nre) {

            return false;
        }

    }

    /**
     * Delete a specific computer.
     *
     * @param id Long
     * @return boolean
     */
    @Transactional(readOnly = false)
    public boolean delete(Long id) {
        // TODO Auto-generated method stub
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query<?> query = session.createQuery(DELETE_ONE);
            query.setParameter("id", id);
            int affectedRowsCount = query.executeUpdate();

            session.getTransaction().commit();

            logger.info("Computer deleted with id : " + id);

            return affectedRowsCount > 0;
        }
    }

    /**
     * Update a specific computer.
     *
     * @param computer Computer
     * @return boolean
     */
    @Transactional(readOnly = false)
    public boolean update(Computer computer) {
        // TODO Auto-generated method stub
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query<?> query = session.createQuery(UPDATE_ONE);
            query.setParameter("name", computer.getName());
            query.setParameter("introduced", computer.getIntroduced());
            query.setParameter("discontinued", computer.getDiscontinued());
            query.setParameter("company_id", computer.getCompany() != null ? computer.getCompany().getId() : null);
            query.setParameter("id", computer.getId());

            int affectedRowsCount = query.executeUpdate();

            session.getTransaction().commit();

            logger.info("Computer updated with id : " + computer.getId());

            return affectedRowsCount > 0;
        }
    }

    /**
     * List a specific range of computers.
     *
     * @param page        int
     * @param itemPerPage int
     * @param search      String
     * @param orderBy     String
     * @return List<Computer>
     * @throws EmptyNameException         ene
     * @throws UnconsistentDatesException ude
     */
    @Transactional(readOnly = true)
    public List<Computer> list(int page, int itemPerPage, String search, String orderBy, Boolean reverse) {
        // TODO Auto-generated method stub
        try (Session session = factory.openSession()) {
            // Add where clause
            String whereClause = search != null && !search.equals("")
                    ? String.format(SEARCH_WHERE_CLAUSE, search, search)
                    : "";

            // OrderBy clause.
            String orderByClause = "";
            if (orderBy != null && !orderBy.equals("")) {
                String reverseOrder = reverse ? "DESC" : "ASC";
                switch (orderBy) {
                case "name":
                    orderByClause = String.format(ORDER_BY_CLAUSE, "name", reverseOrder);
                    break;
                case "introduced":
                    orderByClause = String.format(ORDER_BY_CLAUSE, "introduced", reverseOrder);
                    break;
                case "discontinued":
                    orderByClause = String.format(ORDER_BY_CLAUSE, "discontinued", reverseOrder);
                    break;
                case "company":
                    orderByClause = String.format(ORDER_BY_CLAUSE, "company", reverseOrder);
                    break;
                }
            }

            Query<Computer> query = session.createQuery(String.format(LIST_REQUEST, whereClause, orderByClause),
                    Computer.class);

            if (itemPerPage > 0) {
                query.setMaxResults(itemPerPage);
            }
            if (page > 0 && itemPerPage > 0) {
                query.setFirstResult((page - 1) * itemPerPage);
            }

            return query.list();
        }
    }

    /**
     * Count the number of computers.
     *
     * @param search String
     * @return int
     */
    @Transactional(readOnly = true)
    public Long count(String search) {
        // TODO Auto-generated method stub
        try (Session session = factory.openSession()) {
            String whereClause = search != null && !search.equals("")
                    ? String.format(SEARCH_WHERE_CLAUSE, search, search)
                    : "";
            Query<?> query = session.createQuery(String.format(COUNT_ALL, whereClause));
            return (Long) query.list().get(0);

        }
    }

    /**
     * Delete a set of computers.
     *
     * @param ids String[]
     * @return boolean
     */
    @Transactional(readOnly = false)
    public boolean deleteMany(String[] ids) {
        // TODO Auto-generated method stub

        Long[] longIds = new Long[ids.length];
        for (int i = 0; i < ids.length; i++) {
            longIds[i] = Long.parseLong(ids[i]);
        }
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            Query<?> query = session.createQuery(DELETE_MANY);
            query.setParameterList("ids", longIds);
            int affectedRowsCount = query.executeUpdate();

            session.getTransaction().commit();
            logger.info("Computer deleted with ids : " + ids);

            return affectedRowsCount > 0;
        }
    }
}
