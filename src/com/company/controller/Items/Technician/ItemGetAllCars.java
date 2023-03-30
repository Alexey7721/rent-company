package com.company.controller.Items.Technician;

import com.company.controller.Items.CarItem;
import com.company.domain.Car;
import com.company.menu.InputOutput;

import java.util.List;
import java.util.stream.Collectors;

public class ItemGetAllCars extends CarItem {

    public ItemGetAllCars(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить все автомобили";
    }

    @Override
    public void perform() {
        List<Car> cars = company.getAllCars().collect(Collectors.toList());
        for (Car car : cars) inputOutput.displayLine(car.toString());

//        inputOutput.getString()
    }
}
