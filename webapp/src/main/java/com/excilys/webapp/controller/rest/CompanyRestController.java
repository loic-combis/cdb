package com.excilys.webapp.controller.rest;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.core.Feedback;
import com.excilys.core.User;
import com.excilys.core.company.Company;
import com.excilys.service.service.CompanyService;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/companies")
public class CompanyRestController extends AbstractRestController {

    private CompanyService companyService;

    private MessageSource source;

    private Logger logger = LoggerFactory.getLogger(CompanyRestController.class);

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
    public ResponseEntity<List<Company>> list(HttpServletRequest request, @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> itemPerPage, @RequestParam Optional<String> search) {

        if (!this.hasRole(request, User.ROLE_MANAGER, User.ROLE_USER)) {
            return new ResponseEntity<List<Company>>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(companyService.list(page.orElse(1), itemPerPage.orElse(10), search.orElse("")));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count(HttpServletRequest request, @RequestParam Optional<String> search) {
        return ResponseEntity.ok(companyService.count(search.orElse("")));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Company> get(HttpServletRequest request, @PathVariable("id") Long id) {
        if (!this.hasRole(request, User.ROLE_MANAGER, User.ROLE_USER)) {
            return new ResponseEntity<Company>(HttpStatus.UNAUTHORIZED);
        }

        Optional<Company> opt = companyService.get(id);
        if (opt.isEmpty()) {
            return new ResponseEntity<Company>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(opt.get());
    }

    @PostMapping
    public ResponseEntity<Feedback> create(HttpServletRequest request, @RequestBody Company company) {

        logger.error(company.toString());
        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }
        if (company.getName() == null || company.getName().trim().equals("")) {
            return new ResponseEntity<Feedback>(HttpStatus.BAD_REQUEST);
        }

        String status = "success";
        String message = CompanyService.CREATE_COMPANY_SUCCESS;

        if (!companyService.create(company)) {
            status = "danger";
            message = CompanyService.CREATE_COMPANY_FAILURE;
        }

        return ResponseEntity.ok(new Feedback(status, message));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Feedback> delete(HttpServletRequest request, @PathVariable("id") Long id) {

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
