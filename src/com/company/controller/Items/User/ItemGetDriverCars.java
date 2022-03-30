package com.company.controller.Items.User;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Car;
import com.company.menu.InputOutput;
import com.company.menu.Item;

import java.util.List;

public class ItemGetDriverCars extends CarItem {

    public ItemGetDriverCars(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить машины с водителем";
    }

    @Override
    public void perform() {
        long licenseId = inputOutput.getInteger("Введите номер водительского удостовереня: ");
        List<Car> cars = company.getDriverCars(licenseId);
        inputOutput.displayLine(cars.toString());
    }
}
