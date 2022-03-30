package com.company.controller.Items.RestoreSave;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.menu.InputOutput;

public class ItemRestoreDataFromFile extends CarItem {
    public ItemRestoreDataFromFile(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Восстановление данных из файла";
    }

    @Override
    public void perform() {
        String fileName = inputOutput.getString("Введите адрес расположения и имя файла: ");
        CarItem.company = RentCompany.restoreFromFile(fileName);
    }
}
