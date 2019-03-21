package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.dao.ComputerDAO;
import com.excilys.cdb.persistence.dao.DAOFactory;

public class ComputerService {

    ComputerDAO computerDAO = DAOFactory.getInstance().getComputerDAO();

    public List<Computer> list(int page, int itemPerPage){
        return computerDAO.list(page, itemPerPage);
    }

    public Optional<Computer> get(long id){
        return computerDAO.get(id);
    }

    public Optional<Computer> create(Computer c){
        return computerDAO.create(c);
    }

    public boolean update(Computer c) {
        return computerDAO.update(c);
    }

    public boolean delete(long id){
        return computerDAO.delete(id);
    }

    public int count() {
        return computerDAO.count();
    }

    public boolean deleteMany(String[] ids) {
        return computerDAO.deleteMany(ids);
    }
}
