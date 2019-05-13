package com.excilys.cdb.webapp.controller.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.core.Feedback;
import com.excilys.cdb.core.User;
import com.excilys.cdb.core.company.Company;
import com.excilys.cdb.service.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyRestController extends AbstractRestController {

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
    protected ResponseEntity<List<Company>> list(HttpServletRequest request, @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> itemPerPage) {

        if (!this.hasRole(request, User.ROLE_MANAGER, User.ROLE_USER)) {
            return new ResponseEntity<List<Company>>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(companyService.list(page.orElse(1), itemPerPage.orElse(10)));
    }

    @DeleteMapping(value = "/{id}")
    protected ResponseEntity<Feedback> delete(HttpServletRequest request, @PathVariable("id") Long id) {

        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }

        String status = "success";
        String message = source.getMessage(CompanyService.DELETE_COMPANY_SUCCESS, null,
                LocaleContextHolder.getLocale());
        if (!companyService.delete(id)) {
            status = "danger";
            message = source.getMessage(CompanyService.DELETE_COMPANY_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return ResponseEntity.ok(new Feedback(status, message));
    }
}
