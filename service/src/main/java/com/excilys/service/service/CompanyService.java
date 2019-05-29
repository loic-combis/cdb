package com.excilys.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.excilys.core.company.Company;
import com.excilys.persistence.dao.CompanyDAO;

/**
 * Manage the business logic of companies.
 *
 * @author excilys
 *
 */
@Lazy
@Service("companyService")
public class CompanyService {

    /**
     * companyDAO CompanyDAO.
     */
    CompanyDAO companyDAO;

    public static final String CREATE_COMPANY_SUCCESS = "company.create.success";

    public static final String CREATE_COMPANY_FAILURE = "company.create.failure";

    public static final String DELETE_COMPANY_SUCCESS = "company.delete.success";

    public static final String DELETE_COMPANY_FAILURE = "company.delete.failure";

    public static final String EDIT_COMPANY_SUCCESS = "company.edit.success";

    public static final String EDIT_COMPANY_FAILURE = "company.edit.failure";

    /**
     * Constructor.
     *
     * @param dao CompanyDAO
     */
    public CompanyService(CompanyDAO dao) {
        companyDAO = dao;
    }

    /**
     * List a specific range of companies according to the page and the number of
     * items.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<Company>
     */
    public List<Company> list(int page, int itemPerPage, String search, String orderBy, Boolean reverse) {
        return companyDAO.list(page, itemPerPage, search, orderBy, reverse);
    }

    /**
     * Fetch a specific company according to its id.
     *
     * @param id
     * @return
     */
    public Optional<Company> get(Long id) {
        return companyDAO.get(id);
    }

    public Long count(String search) {
        return companyDAO.count(search);
    }

    public boolean create(Company company) {
        return companyDAO.create(company);
    }

    public boolean edit(Company company) {
        return companyDAO.update(company);
    }

    /**
     * Delete the specified company and all the related computers.
     *
     * @param id Long
     * @return boolean
     */
    public boolean delete(Long id) {
        return companyDAO.delete(id);
    }
}
