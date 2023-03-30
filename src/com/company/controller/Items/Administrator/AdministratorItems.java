package com.company.controller.Items.Administrator;

import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.InputOutput;
import com.company.menu.items.Item;

public class AdministratorItems extends CarItemSubmenu {
    public AdministratorItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }

    @Override
    public String displayedName() {
        return "Администратор(добавить автомобиль," +
                " добавить модель," +
                " удалить записи из архива," +
                " получить водителя," +
                " удалить автомобиль)";
    }

}
