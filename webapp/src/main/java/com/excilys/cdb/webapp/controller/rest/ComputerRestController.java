package com.excilys.cdb.webapp.controller.rest;

import static com.excilys.cdb.core.User.ROLE_MANAGER;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.mapper.ComputerDTOMapper;
import com.excilys.cdb.core.Feedback;
import com.excilys.cdb.core.computer.Computer;
import com.excilys.cdb.core.computer.ComputerDTO;
import com.excilys.cdb.service.service.ComputerService;
import com.excilys.cdb.webapp.controller.AddComputerController;
import com.excilys.cdb.webapp.validator.ComputerDTOValidator;

@RestController
@RequestMapping("/api/computers")
public class ComputerRestController {

    private ComputerService computerService;

    private ComputerDTOMapper mapper;

    private ComputerDTOValidator validator;

    private MessageSource source;

    /**
     * logger Logger.
     */
    private Logger logger = LoggerFactory.getLogger(AddComputerController.class);

    /**
     * Constructor.
     *
     * @param computerSer ComputerService
     * @param dtoMapper   ComputerDTOMapper
     */
    public ComputerRestController(ComputerService computerSer, ComputerDTOMapper dtoMapper,
            ComputerDTOValidator computerValidator, MessageSource messageSource) {
        computerService = computerSer;
        mapper = dtoMapper;
        validator = computerValidator;
        source = messageSource;
    }

    /**
     * Init the computerDTOValidator.
     *
     * @param binder WebDataBinder
     */
    @InitBinder("computerDTO")
    protected void initBinder(WebDataBinder binder) {
        binder.setValidator(validator);
    }

    /**
     * Endpoint to fetch the specified computer.
     *
     * @param id Long
     * @return ComputerDTO
     */
    @GetMapping(value = "/{id}")
    @PreAuthorize("isAuthenticated()")
    protected ComputerDTO get(@PathVariable(value = "id") Long id) {
        return mapper.toDTO(computerService.get(id).orElse(null));
    }

    /**
     * Endpoint to list a set of computers.
     *
     * @param status      String
     * @param message     String
     * @param page        int
     * @param itemPerPage int
     * @param search      String
     * @param orderBy     String
     * @param map         Model
     * @return List<ComputerDTO>
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    protected List<ComputerDTO> list(@RequestParam Optional<String> status, @RequestParam Optional<String> message,
            @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> itemPerPage,
            @RequestParam Optional<String> search, @RequestParam("orderby") Optional<String> orderBy, Model map) {

        List<Computer> computers = computerService.list(page.orElse(1), itemPerPage.orElse(10), search.orElse(""),
                orderBy.orElse(""));

        return mapper.mapList(computers);

    }

    /**
     * Endpoint to create a new computer.
     *
     * @param computerDTO ComputerDTO
     * @param result      BindingResult
     * @return Feedback
     */
    @PostMapping
    @Secured(ROLE_MANAGER)
    protected Feedback create(@Validated @RequestBody ComputerDTO computerDTO, BindingResult result) {

        String status = "danger";
        String message = "";

        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getCode();

        } else if (computerService.create(mapper.toComputer(computerDTO))) {
            status = "success";
            message = source.getMessage(ComputerService.ADD_COMPUTER_SUCCESS, null, LocaleContextHolder.getLocale());
            logger.info("Computer created : " + computerDTO);

        } else {
            message = source.getMessage(ComputerService.ADD_COMPUTER_FAILURE, null, LocaleContextHolder.getLocale());
        }
        return new Feedback(status, message);
    }

    /**
     * Endpoint to edit a computer.
     *
     * @param computerDTO ComputerDTO
     * @param id          Long
     * @param result      BindingResult
     * @return Feedback
     */
    @PutMapping(value = "/{id}")
    @Secured(ROLE_MANAGER)
    protected Feedback edit(@Validated @RequestBody ComputerDTO computerDTO, @PathVariable(value = "id") Long id,
            BindingResult result) {
        String status = "danger";
        String message = "";

        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getCode();

        } else if (computerService.update(mapper.toComputer(computerDTO))) {
            status = "success";
            message = source.getMessage(ComputerService.EDIT_COMPUTER_SUCCESS, null, LocaleContextHolder.getLocale());

        } else {
            message = source.getMessage(ComputerService.EDIT_COMPUTER_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return new Feedback(status, message);
    }

    /**
     * Endpoint to delete a specific computer.
     *
     * @param id Long
     * @return Feedback
     */
    @DeleteMapping(value = "/{id}")
    @Secured(ROLE_MANAGER)
    protected Feedback deleteMany(@PathVariable("id") Long id) {

        boolean success = computerService.delete(id);

        String message, status;
        if (success) {
            status = "success";
            message = source.getMessage(ComputerService.DELETE_MANY_SUCCESS, null, LocaleContextHolder.getLocale());
        } else {
            status = "danger";
            message = source.getMessage(ComputerService.DELETE_MANY_FAILURE, null, LocaleContextHolder.getLocale());
        }
        return new Feedback(status, message);
    }

}
