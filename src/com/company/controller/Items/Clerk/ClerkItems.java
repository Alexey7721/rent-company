package com.company.controller.Items.Clerk;

import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.InputOutput;
import com.company.menu.items.Item;

public class ClerkItems extends CarItemSubmenu {
    public ClerkItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }

    @Override
    public String displayedName() {
        return "Сотрудник(аренда авто, возврат авто, добавить автомобиль)";
    }

}
