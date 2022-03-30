package com.company.controller.Items.Clerk;

import com.company.controller.Items.CarItem;
import com.company.dao.RentCompany;
import com.company.dto.CarsReturnCode;
import com.company.menu.InputOutput;
import com.company.menu.Item;

import java.time.LocalDate;

public class ItemReturnCar extends CarItem {
    public ItemReturnCar(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Возврат автомобиля";
    }

    @Override
    public void perform() {
//        CarsReturnCode returnCar(String regNumber, long licenseId, LocalDate returnDate, int gasTankPercent, int damages)
        String regNumber = inputOutput.getString("Введите регистрационный номер авто: ");
        long licenseId = inputOutput.getInteger("Введите номер водительского удостоверения: ");
        LocalDate returnDate = inputOutput.getDate("Введите дату завершения аренды: ");
        int gasTankPercent = inputOutput.getInteger("Введите процент бензобака: ");
        int damages = inputOutput.getInteger("Состояние авто (процент): ");
        CarsReturnCode carsReturnCode = company.returnCar(regNumber,licenseId,returnDate,gasTankPercent,damages);
        inputOutput.displayLine(carsReturnCode.toString());
    }
}
