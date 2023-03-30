package com.company.controller.Items.Clerk;

import com.company.controller.Items.CarItem;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;

import java.time.LocalDate;

public class ItemRentCar extends CarItem {

    public ItemRentCar(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Аренда автомобиля";
    }

    @Override
    public void perform() {
//        CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays)
        String regNumber = inputOutput.getString("Введите регистрационный номер авто: ");
        long licenseId = inputOutput.getInteger("Введите номер водительского удостоверения: ");
        LocalDate rentDate = inputOutput.getDate("Введите дату начала аренды: ");
        int rentDays = inputOutput.getInteger("Введите срок аренды(дней): ");
        CarsReturnCode carsReturnCode = company.rentCar(regNumber,licenseId, rentDate, rentDays);
        inputOutput.displayLine(carsReturnCode.toString());
    }
}
