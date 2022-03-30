package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Car;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;
import com.company.menu.Item;

import java.util.*;

public class ItemAddCar extends CarItem {

    private Set<String> colors;

    public ItemAddCar(InputOutput inputOutput) {
        super(inputOutput);
        colors = new HashSet<>(Arrays.asList("ЧЕРНЫЙ", "БЕЛЫЙ", "ЗЕЛЕНЫЙ", "СИНИЙ", "КРАСНЫЙ", "ЖЕЛТЫЙ", "СЕРЫЙ", "ЗОЛОТОЙ", "КОРИЧНЕВЫЙ"));
    }

    @Override
    public String displayedName() {
        return "Добавить машину";
    }

    @Override
    public void perform() {
        String regNum = inputOutput.getString("Регистрационный номер авто: ");
        String color = inputOutput.getString(
                "Выберете цвет авто " + colors + ": ",
                (str) -> colors.contains(str.toUpperCase())
                ).toUpperCase();

        String modelName = inputOutput.getString("Название модели: ");

        Car car = new Car(regNum, color, modelName);

        CarsReturnCode carsReturnCode = company.addCar(car);

        inputOutput.displayLine(carsReturnCode.toString());
    }
}
