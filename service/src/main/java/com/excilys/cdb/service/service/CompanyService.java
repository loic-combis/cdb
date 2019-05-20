package com.excilys.cdb.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.company.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;

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
    public List<Company> list(int page, int itemPerPage, String search) {
        return companyDAO.list(page, itemPerPage, search);
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
