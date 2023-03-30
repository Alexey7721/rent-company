package com.company.controller.Items;

import com.company.dao.RentCompany;
import com.company.menu.InputOutput;
import com.company.menu.items.Item;

public abstract class CarItem extends Item {

    protected static RentCompany company = RentCompany.restoreFromFile();

    public CarItem(InputOutput inputOutput) {
        super(inputOutput);
    }
}
