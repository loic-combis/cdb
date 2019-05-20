package com.excilys.cdb.persistence.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.company.Company;

/**
 * Singleton. Responsible for bonding the application to the database thanks to
 * JDBC.
 *
 * @author excilys
 *
 */
@Lazy
@Repository("companyDAO")
public class CompanyDAO {

    private SessionFactory factory;

    /**
     * LOGGER Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * String base SQL request.
     */
    private static final String LIST_REQUEST = "from Company";
    public static final String FIND_BY_ID = "from Company where id = :id";
    private static final String DELETE = "delete Company where id = :id";
    private static final String DELETE_RELATED_COMPUTERS = "delete Computer where company_id = :id";

    private static final String COUNT_ALL = "select count(c) from Company c %s";
    private static final String SEARCH_WHERE_CLAUSE = "where name like '%%%s%%' or name like '%%%s%%'";

    /**
     * Constructor.
     *
     * @param sessionFactory LocalSessionFactoryBean
     */
    public CompanyDAO(LocalSessionFactoryBean sessionFactory) {
        factory = sessionFactory.getObject();
    }

    /**
     * Fetch the specified computer from the persistence.
     *
     * @param id Long.
     * @return Optional<Company>
     */
    @Transactional
    public Optional<Company> get(Long id) {
        // TODO Auto-generated method stub
        Optional<Company> opt = Optional.empty();

        try (Session session = factory.openSession()) {

            Query<Company> query = session.createQuery(FIND_BY_ID, Company.class);
            query.setParameter("id", id);

            Company company = query.getSingleResult();
            opt = Optional.ofNullable(company);

            LOGGER.info("Company fetched : " + company);
        } catch (NoResultException nre) {

            LOGGER.warn(nre.getMessage());
        }
        return opt;
    }

    /**
     * Fetch a specific range of companies.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<Company>
     */
    @Transactional
    public List<Company> list(int page, int itemPerPage) {
        // TODO Auto-generated method stub
        try (Session session = factory.openSession()) {

            Query<Company> query = session.createQuery(LIST_REQUEST, Company.class);
            if (page > 1 && itemPerPage > 0) {
                query.setFirstResult((page - 1) * itemPerPage);
            }
            if (itemPerPage > 0) {
                query.setMaxResults(itemPerPage);
            }
            return query.list();
        }
    }

    @Transactional
    public Long count(String search) {
        try (Session session = factory.openSession()) {
            String whereClause = search != null && !search.equals("")
                    ? String.format(SEARCH_WHERE_CLAUSE, search, search)
                    : "";
            Query<?> query = session.createQuery(String.format(COUNT_ALL, whereClause));
            return (Long) query.list().get(0);
        }
    }

    @Transactional
    public boolean create(Company company) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            Long id = (Long) session.save(company);
            session.getTransaction().commit();
            return true;

        } catch (NoResultException nre) {

            return false;
        }
    }

    /**
     * Delete the specified company and all the related computers.
     *
     * @param id Long
     * @return boolean
     */
    @Transactional(readOnly = false)
    public boolean delete(Long id) {

        try (Session session = factory.openSession()) {
            Transaction tx = session.beginTransaction();
            Query<?> deleteRelatedComputersQuery = session.createQuery(DELETE_RELATED_COMPUTERS);
            deleteRelatedComputersQuery.setParameter("id", id);
            deleteRelatedComputersQuery.executeUpdate();

            Query<?> deleteCompanyQuery = session.createQuery(DELETE);
            deleteCompanyQuery.setParameter("id", id);
            int affectedRowsCount = deleteCompanyQuery.executeUpdate();

            tx.commit();
            LOGGER.info("Company deleted : " + id + " affected rows : " + affectedRowsCount);

            return affectedRowsCount > 0;
        }

    }
}