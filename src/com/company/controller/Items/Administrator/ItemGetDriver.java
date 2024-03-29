package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItem;
import com.company.domain.Driver;
import com.company.menu.InputOutput;

public class ItemGetDriver extends CarItem {

        public ItemGetDriver(InputOutput inputOutput) {
                super(inputOutput);

        }

        @Override
        public String displayedName() {
                return "Получить водителя";
        }

        @Override
        public void perform() {
                Driver driver = company.getDriver(inputOutput.getInteger("Введите номер удостоверения: "));
                inputOutput.displayLine(driver.toString());
        }
}
