package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Model;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;
import com.company.menu.Item;

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
//        Model(String modelName, int gasTank, String country, int priceDay, String company)
        String modelName = inputOutput.getString("Название модели: ");
        int gasTank = inputOutput.getInteger("Объем бака: ");
        String country = inputOutput.getString("Страна производитель: ");
        int priceDay = inputOutput.getInteger("Стоимость аренды за сутки: ");
        String carBrand = inputOutput.getString("Компания: ");
        Model model = new Model(modelName, gasTank, country, priceDay, carBrand);
        CarsReturnCode carsReturnCode = company.addModel(model);
        inputOutput.displayLine(carsReturnCode.toString());

    }
}
