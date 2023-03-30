package com.company.controller.Items.User;

import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.InputOutput;
import com.company.menu.items.Item;

public class UserItems extends CarItemSubmenu {
    public UserItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }
    @Override
    public String displayedName() {
        return "Пользователь (список машин, список моделей, водители по рег.номеру, машины по номеру прав)";
    }

}
