package com.company.controller.Items.User;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Driver;
import com.company.menu.InputOutput;
import com.company.menu.Item;

import java.util.List;

public class ItemGetCarDrivers extends CarItem {

    public ItemGetCarDrivers(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить водителей машины";
    }

    @Override
    public void perform() {
        String regNumber = inputOutput.getString("Введите регистрационный номер авто: ");
        List<Driver> drivers = company.getCarDriver(regNumber);
        inputOutput.displayLine(drivers.toString());
    }
}
