package com.excilys.cdb.webapp.controller.rest;

import static com.excilys.cdb.core.User.ROLE_MANAGER;

import java.util.List;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.core.Feedback;
import com.excilys.cdb.core.company.Company;
import com.excilys.cdb.service.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController {

    private CompanyService companyService;

    private MessageSource source;

    public CompanyRestController(CompanyService companyS, MessageSource messageSource) {
        companyService = companyS;
        source = messageSource;
    }

    /**
     * Endpoint to list the specified range of companies.
     *
     * @param page        int
     * @param itemPerPage int
     * @return List<Company>
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    protected List<Company> list(@RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage) {
        return companyService.list(page.orElse(1), itemPerPage.orElse(10));
    }

    @DeleteMapping(value = "/{id}")
    @Secured(ROLE_MANAGER)
    protected Feedback delete(@PathVariable("id") Long id) {
        String status = "success";
        String message = source.getMessage(CompanyService.DELETE_COMPANY_SUCCESS, null,
                LocaleContextHolder.getLocale());
        if (!companyService.delete(id)) {
            status = "danger";
            message = source.getMessage(CompanyService.DELETE_COMPANY_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return new Feedback(status, message);
    }
}
