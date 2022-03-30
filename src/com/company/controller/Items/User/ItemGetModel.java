package com.company.controller.Items.User;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Model;
import com.company.menu.InputOutput;
import com.company.menu.Item;

public class ItemGetModel extends CarItem {

    public ItemGetModel(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить модель";
    }

    @Override
    public void perform() {
        String modelName = inputOutput.getString("Введите название модели: ");
        Model model = company.getModel(modelName);
        inputOutput.displayLine(model.toString());
    }
}
