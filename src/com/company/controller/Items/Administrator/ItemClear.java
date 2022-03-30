package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Car;
import com.company.menu.InputOutput;
import com.company.menu.Item;

import java.time.LocalDate;
import java.util.List;

public class ItemClear extends CarItem {

    public ItemClear(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Удалить записи из архива";
    }

    @Override
    public void perform() {
        LocalDate date = inputOutput.getDate("Введите дату: ");
        int numberOfDays = inputOutput.getInteger("Количество дней: ");
        List<Car> cars = company.clear(date, numberOfDays);
        inputOutput.displayLine(cars.toString());
    }
}
