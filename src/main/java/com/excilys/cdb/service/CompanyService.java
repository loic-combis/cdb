package com.excilys.cdb.service;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.excilys.cdb.model.company.Company;
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
    public List<Company> list(int page, int itemPerPage) {
        return companyDAO.list(page, itemPerPage);
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
