package com.company.controller.Items;

import com.company.menu.InputOutput;
import com.company.menu.items.Item;
import com.company.menu.menu.MenuWithExit;

public abstract class CarItemSubmenu extends CarItem{
    private Item[] items;

    public CarItemSubmenu(InputOutput inputOutput, Item[] items) {
        super(inputOutput);
        this.items = items;
    }

    @Override
    public void perform() {
        MenuWithExit menu = new MenuWithExit(inputOutput, items);
        menu.runMenu();
    }
}
