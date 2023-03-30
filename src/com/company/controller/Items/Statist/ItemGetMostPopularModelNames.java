package com.company.controller.Items.Statist;

import com.company.controller.Items.CarItem;
import com.company.menu.InputOutput;

import java.util.List;

public class ItemGetMostPopularModelNames extends CarItem {

    public ItemGetMostPopularModelNames(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить самые популярные названия моделей";
    }

    @Override
    public void perform() {
        List<String> modelNames = company.getMostPopularModelNames();
        inputOutput.displayLine(modelNames.toString());
    }
}
