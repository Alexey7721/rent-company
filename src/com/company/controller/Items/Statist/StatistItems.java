package com.company.controller.Items.Statist;

import com.company.controller.Items.CarItem;
import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.ExitItem;
import com.company.menu.InputOutput;
import com.company.menu.Item;
import com.company.menu.Menu;

public class StatistItems extends CarItemSubmenu {
    public StatistItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }

    @Override
    public String displayedName() {
        return "Статистика";
    }

}
