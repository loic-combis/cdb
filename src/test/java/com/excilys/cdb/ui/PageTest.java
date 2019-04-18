package com.excilys.cdb.ui;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import com.excilys.cdb.model.company.Company;
import com.excilys.cdb.model.computer.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@RunWith(SpringRunner.class)
public class PageTest {

    private Page<Computer> computerPage;

    private AnnotationConfigApplicationContext ctx;

    @Autowired
    private ComputerService computerService;

    @Autowired
    private CompanyService companyService;

    private Page<Company> companyPage;
    private final int ITEM_PER_PAGE = 10;

    @BeforeTest
    public void setUp() {

        ctx = new AnnotationConfigApplicationContext();
        ctx.scan("com.excilys.cdb");
        ctx.refresh();
        computerService = ctx.getBean(ComputerService.class);
        companyService = ctx.getBean(CompanyService.class);

        computerPage = new Page<Computer>(Computer.class, new PageProvider() {

            @Override
            public List<?> fetchDataFor(Class<?> c, int page) {
                // TODO Auto-generated method stub
                List<?> list = new ArrayList<>();
                list = computerService.list(page, ITEM_PER_PAGE, null, null);
                return list;
            }

        });

        companyPage = new Page<Company>(Company.class, new PageProvider() {

            @Override
            public List<?> fetchDataFor(Class<?> c, int page) {
                // TODO Auto-generated method stub
                return companyService.list(page, ITEM_PER_PAGE);
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
        List<?> items;
        items = computerService.list(1, ITEM_PER_PAGE, null, null);
        List<Computer> computers = Page.filter(Computer.class, items);
        assertTrue(computers.size() <= ITEM_PER_PAGE);
    }

    @Test
    public void companiesListFilteringTest() {
        List<?> items = companyService.list(1, ITEM_PER_PAGE);
        List<Company> companies = Page.filter(Company.class, items);
        assertTrue(companies.size() <= ITEM_PER_PAGE);
    }

    @Test
    public void computersFilteringWithWrongClassTest() {
        List<?> items = companyService.list(1, ITEM_PER_PAGE);
        List<Computer> computers = Page.filter(Computer.class, items);
        assertEquals(computers.size(), 0);
    }

    @Test
    public void companiesFilteringWithWrongClassTest() {
        List<?> items;
        items = computerService.list(1, ITEM_PER_PAGE, null, null);
        List<Company> companies = Page.filter(Company.class, items);
        assertEquals(companies.size(), 0);
    }

    @AfterTest
    public void end() {
        ctx.close();
    }
}
