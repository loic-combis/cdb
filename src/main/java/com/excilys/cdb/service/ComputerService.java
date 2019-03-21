package com.excilys.cdb.service;

import java.util.List;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.PersistenceFacade;

public class ComputerService {

    PersistenceFacade persistence = PersistenceFacade.getInstance();

    public List<Computer> list(int page, int itemPerPage){
        return persistence.listComputers(page, itemPerPage);
    }

    public int count() {
        return persistence.countComputers();
    }
}
