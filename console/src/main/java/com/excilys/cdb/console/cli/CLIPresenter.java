package com.excilys.cdb.console.cli;

import java.util.List;

import com.excilys.cdb.console.Presenter;
import com.excilys.cdb.core.company.Company;
import com.excilys.cdb.core.computer.ComputerDTO;

public class CLIPresenter extends Presenter {

    @Override
    public void present(ComputerDTO c) {
        // TODO Auto-generated method stub
        System.out.println(c);
    }

    @Override
    public void present(Company c) {
        // TODO Auto-generated method stub
        System.out.println(c);
    }

    @Override
    public void presentMany(List<?> list) {
        // TODO Auto-generated method stub
        list.stream().forEach(element -> {
            System.out.println(element);
            System.out.println("----------------------------------------------------");
        });
        System.out.println("\nPREVIOUS | NEXT | MENU ?");
    }

    @Override
    public void notify(String s) {
        // TODO Auto-generated method stub
        System.out.println(s);
    }
}
