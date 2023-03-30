package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.domain.Model;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;

public class ItemAddModel extends CarItem {

    public ItemAddModel(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Добавить модель";
    }

    @Override
    public void perform() {
        String carBrand = inputOutput.getString("Компания: ");
        String modelName = inputOutput.getString("Название модели: ");
        String country = inputOutput.getString("Страна производитель: ");
        int gasTank = inputOutput.getInteger("Объем бака: ");
        int priceDay = inputOutput.getInteger("Стоимость аренды за сутки: ");
        Model model = new Model(modelName, gasTank, country, priceDay, carBrand);
        CarsReturnCode carsReturnCode = company.addModel(model);
        inputOutput.displayLine(carsReturnCode.toString());

    }
}
