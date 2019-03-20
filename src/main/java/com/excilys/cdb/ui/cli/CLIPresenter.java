package com.excilys.cdb.ui.cli;

import java.util.List;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.ui.Presenter;

public class CLIPresenter extends Presenter {

    @Override
    public void present(Computer c) {
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
