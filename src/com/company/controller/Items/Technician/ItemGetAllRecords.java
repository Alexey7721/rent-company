package com.company.controller.Items.Technician;

import com.company.controller.Items.CarItem;
import com.company.domain.RentRecord;
import com.company.menu.InputOutput;


import java.util.List;
import java.util.stream.Collectors;

public class ItemGetAllRecords extends CarItem {

    public ItemGetAllRecords(InputOutput inputOutput) {
        super(inputOutput);
    }

    @Override
    public String displayedName() {
        return "Получить все записи";
    }

    @Override
    public void perform() {
//        inputOutput.displayLine(company.getAllRecords().collect(Collectors.toList()).toString());
        List<RentRecord> records = company.getAllRecords().collect(Collectors.toList());
        for (RentRecord record : records) inputOutput.displayLine(record.toString());
    }
}
