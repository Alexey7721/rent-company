package com.company.controller.Items.Statist;

import com.company.controller.Items.CarItem;
import com.company.menu.InputOutput;

import java.util.List;

public class ItemGetMostProfitModelNames extends CarItem {

    public ItemGetMostProfitModelNames(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить названия моделей с наибольшей прибылью";
    }

    @Override
    public void perform() {
        List<String> modelNames = company.getMostProfitModelNames();
        inputOutput.displayLine(modelNames.toString());
    }
}
