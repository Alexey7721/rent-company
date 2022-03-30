package com.company.controller.Items.User;

import com.company.controller.Items.CarItem;
import com.company.domain.Car;
import com.company.menu.InputOutput;

public class ItemGetCar extends CarItem {

    public ItemGetCar(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить автомобиль";
    }

    @Override
    public void perform() {
        String regNumber = inputOutput.getString("Введите регистрационный номер авто: ");
        Car car = company.getCar(regNumber);

        inputOutput.displayLine(car == null ? "такой номер не найден" : car.toString());
    }
}
