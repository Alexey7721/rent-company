package com.company.controller.Items.RestoreSave;

import com.company.controller.Items.CarItem;
import com.company.menu.InputOutput;

public class ItemSaveData extends CarItem {
    public ItemSaveData(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Сохранение данных";
    }

    @Override
    public void perform() {
        String fileName =inputOutput.getString("Введите адрес расположения и имя файла: ");
        company.save(fileName);
    }
}
