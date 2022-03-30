package com.company.controller.Items.Clerk;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.domain.Driver;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;
import com.company.menu.Item;

public class ItemAddDriver extends CarItem {

    public ItemAddDriver(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Добавить водителя";
    }

    @Override
    public void perform() {
//        Driver(long licenseId, String name, int birthYear, String phone) {
        long licenseId = inputOutput.getInteger("Введите номер удостоверения: ");
        String name = inputOutput.getString("Введите имя водителя: ");
        int birthYear = inputOutput.getInteger("Введите год рождения: ");
        String phone = inputOutput.getString("Введите номер телефона: ");
        Driver driver = new Driver(licenseId, name, birthYear, phone);
        CarsReturnCode carsReturnCode = company.addDriver(driver);
        inputOutput.displayLine(carsReturnCode.toString());
    }
}
