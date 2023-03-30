package com.company.controller.Items.Technician;

import com.company.controller.Items.CarItem;
import com.company.domain.Driver;
import com.company.menu.InputOutput;

import java.util.List;
import java.util.stream.Collectors;

public class ItemGetAllDrivers extends CarItem {

    public ItemGetAllDrivers(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить всех водителей";
    }

    @Override
    public void perform() {
//        inputOutput.displayLine(company.getAllDrivers().collect(Collectors.toList()).toString());
        List<Driver> drivers = company.getAllDrivers().collect(Collectors.toList());
        for (Driver driver : drivers) inputOutput.displayLine(driver.toString());
    }
}
