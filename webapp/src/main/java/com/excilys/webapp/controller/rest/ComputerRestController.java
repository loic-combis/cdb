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
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
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

import com.excilys.binding.dto.ComputerDTO;
import com.excilys.binding.mapper.ComputerDTOMapper;
import com.excilys.core.Feedback;
import com.excilys.core.User;
import com.excilys.core.computer.Computer;
import com.excilys.service.service.ComputerService;
import com.excilys.webapp.controller.AddComputerController;
import com.excilys.webapp.validator.ComputerDTOValidator;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/computers")
public class ComputerRestController extends AbstractRestController {

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

    @GetMapping("/count")
    public ResponseEntity<Long> count(HttpServletRequest request, @RequestParam Optional<String> search) {
        return ResponseEntity.ok(computerService.count(search.orElse("")));
    }

    /**
     * Endpoint to fetch the specified computer.
     *
     * @param id Long
     * @return ComputerDTO
     */
    @GetMapping(value = "/{id}")
    public ResponseEntity<ComputerDTO> get(HttpServletRequest request, @PathVariable(value = "id") Long id) {

        if (!this.hasRole(request, User.ROLE_MANAGER, User.ROLE_USER)) {
            return new ResponseEntity<ComputerDTO>(HttpStatus.UNAUTHORIZED);
        }
        return ResponseEntity.ok(mapper.toDTO(computerService.get(id).orElse(null)));
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
    public ResponseEntity<List<ComputerDTO>> list(HttpServletRequest request, @RequestParam Optional<String> status,
            @RequestParam Optional<String> message, @RequestParam Optional<Integer> page,
            @RequestParam Optional<Integer> itemPerPage, @RequestParam Optional<String> search,
            @RequestParam("orderby") Optional<String> orderBy, Model map) {

        if (!this.hasRole(request, User.ROLE_MANAGER, User.ROLE_USER)) {
            return new ResponseEntity<List<ComputerDTO>>(HttpStatus.UNAUTHORIZED);
        }

        List<Computer> computers = computerService.list(page.orElse(1), itemPerPage.orElse(10), search.orElse(""),
                orderBy.orElse(""));
        logger.error(computers.toString());
        return ResponseEntity.ok(mapper.mapList(computers));

    }

    /**
     * Endpoint to create a new computer.
     *
     * @param computerDTO ComputerDTO
     * @param result      BindingResult
     * @return Feedback
     */
    @PostMapping
    public ResponseEntity<Feedback> create(HttpServletRequest request, @Validated @RequestBody ComputerDTO computerDTO,
            BindingResult result) {
        logger.error(computerDTO.toString());
        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }

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
        return ResponseEntity.ok(new Feedback(status, message));
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
    public ResponseEntity<Feedback> edit(HttpServletRequest request, @Validated @RequestBody ComputerDTO computerDTO,
            @PathVariable(value = "id") Long id, BindingResult result) {

        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }

        String status = "danger";
        String message = "";

        logger.error(computerDTO.toString());
        if (result.hasErrors()) {
            message = result.getAllErrors().get(0).getCode();

        } else if (computerService.update(mapper.toComputer(computerDTO))) {
            status = "success";
            message = source.getMessage(ComputerService.EDIT_COMPUTER_SUCCESS, null, LocaleContextHolder.getLocale());

        } else {
            message = source.getMessage(ComputerService.EDIT_COMPUTER_FAILURE, null, LocaleContextHolder.getLocale());
        }

        return ResponseEntity.ok(new Feedback(status, message));
    }

    /**
     * Endpoint to delete a specific computer.
     *
     * @param id Long
     * @return Feedback
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Feedback> deleteMany(HttpServletRequest request, @PathVariable("id") Long id) {

        if (!this.hasRole(request, User.ROLE_MANAGER)) {
            return new ResponseEntity<Feedback>(HttpStatus.UNAUTHORIZED);
        }

        boolean success = computerService.delete(id);

        String message, status;
        if (success) {
            status = "success";
            message = source.getMessage(ComputerService.DELETE_MANY_SUCCESS, null, LocaleContextHolder.getLocale());
        } else {
            status = "danger";
            message = source.getMessage(ComputerService.DELETE_MANY_FAILURE, null, LocaleContextHolder.getLocale());
        }
        return ResponseEntity.ok(new Feedback(status, message));
    }

}
