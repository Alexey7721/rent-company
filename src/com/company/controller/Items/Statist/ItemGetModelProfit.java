package com.company.controller.Items.Statist;

import com.company.controller.Items.CarItem;
import com.company.menu.InputOutput;

public class ItemGetModelProfit extends CarItem {

    public ItemGetModelProfit(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить прибыль от модели";
    }

    @Override
    public void perform() {
        String modelName = inputOutput.getString("Введите название модели: ");
        Double profit = company.getModelProfit(modelName);
        inputOutput.displayLine(String.valueOf(profit));
    }
}
