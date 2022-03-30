package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;
import com.company.menu.Item;

public class ItemRemoveCar extends CarItem {

    public ItemRemoveCar(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Удалить машину";
    }

    @Override
    public void perform() {
        String regNumber = inputOutput.getString("Введите регистрационный номер авто: ");
        CarsReturnCode carsReturnCode = company.removeCar(regNumber);
        inputOutput.displayLine(carsReturnCode.toString());
    }
}
