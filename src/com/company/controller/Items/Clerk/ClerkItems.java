package com.company.controller.Items.Clerk;

import com.company.controller.Items.Administrator.*;
import com.company.controller.Items.CarItem;
import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.ExitItem;
import com.company.menu.InputOutput;
import com.company.menu.Item;
import com.company.menu.Menu;

public class ClerkItems extends CarItemSubmenu {
    public ClerkItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }

    @Override
    public String displayedName() {
        return "Сотрудник(аренда авто, возврат авто, добавить автомобиль)";
    }

}
