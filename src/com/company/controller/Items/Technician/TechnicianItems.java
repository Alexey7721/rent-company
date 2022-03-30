package com.company.controller.Items.Technician;


import com.company.controller.Items.CarItem;
import com.company.controller.Items.CarItemSubmenu;
import com.company.menu.ExitItem;
import com.company.menu.InputOutput;
import com.company.menu.Item;
import com.company.menu.Menu;

public class TechnicianItems extends CarItemSubmenu {
    public TechnicianItems(InputOutput inputOutput, Item[] items) {
        super(inputOutput, items);
    }

    @Override
    public String displayedName() {
        return "Информация (авто, водители, записи)";
    }
}
