package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.dao.CompanyDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;

public class CompanyService {

    CompanyDAO companyDAO = DAOFactory.getInstance().getCompanyDAO();

    public List<Company> list(int page, int itemPerPage){
        return companyDAO.list(page, itemPerPage);
    }
}
