package com.excilys.cdb.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.PersistenceFacade;

public class PageTest {

    private Page<Computer> computerPage;

    private Page<Company> companyPage;
    private final int ITEM_PER_PAGE = 10;

    @Before
    public void setUp() {
        computerPage = new Page<Computer>(Computer.class, new PageProvider() {

            @Override
            public List<?> fetchDataFor(Class<?> c, int page) {
                // TODO Auto-generated method stub
                return PersistenceFacade.getInstance().listComputers(page, ITEM_PER_PAGE);
            }

        });

        companyPage = new Page<Company>(Company.class, new PageProvider() {

            @Override
            public List<?> fetchDataFor(Class<?> c, int page) {
                // TODO Auto-generated method stub
                return PersistenceFacade.getInstance().listCompanies(page, ITEM_PER_PAGE);
            }
        });
    }

    @Test
    public void computersFirstPageSizeTest() {
        assertTrue(computerPage.nextPage().size() <= ITEM_PER_PAGE);
    }

    @Test
    public void companiesFirstPageSizeTest() {
        assertTrue(companyPage.nextPage().size() <= ITEM_PER_PAGE);
    }

    @Test
    public void computersListFilteringTest() {
        List<?> items = PersistenceFacade.getInstance().listComputers(1, ITEM_PER_PAGE);
        List<Computer> computers = Page.filter(Computer.class, items);
        assertTrue(computers.size() <= ITEM_PER_PAGE);
    }

    @Test
    public void companiesListFilteringTest() {
        List<?> items = PersistenceFacade.getInstance().listCompanies(1, ITEM_PER_PAGE);
        List<Company> companies = Page.filter(Company.class, items);
        assertTrue(companies.size() <= ITEM_PER_PAGE);
    }

    @Test
    public void computersFilteringWithWrongClassTest() {
        List<?> items = PersistenceFacade.getInstance().listCompanies(1, ITEM_PER_PAGE);
        List<Computer> computers = Page.filter(Computer.class, items);
        assertEquals(computers.size(), 0);
    }

    @Test
    public void companiesFilteringWithWrongClassTest() {
        List<?> items = PersistenceFacade.getInstance().listComputers(1, ITEM_PER_PAGE);
        List<Company> companies = Page.filter(Company.class, items);
        assertEquals(companies.size(), 0);
    }
}
