package com.company.controller;

import com.company.controller.Items.Administrator.*;
import com.company.controller.Items.Clerk.ClerkItems;
import com.company.controller.Items.Clerk.ItemRentCar;
import com.company.controller.Items.RestoreSave.ItemRestoreDataFromFile;
import com.company.controller.Items.RestoreSave.ItemSaveData;
import com.company.controller.Items.Statist.ItemGetModelProfit;
import com.company.controller.Items.Statist.ItemGetMostPopularModelNames;
import com.company.controller.Items.Statist.ItemGetMostProfitModelNames;
import com.company.controller.Items.Statist.StatistItems;
import com.company.controller.Items.Technician.ItemGetAllCars;
import com.company.controller.Items.Technician.ItemGetAllDrivers;
import com.company.controller.Items.Technician.ItemGetAllRecords;
import com.company.controller.Items.Technician.TechnicianItems;
import com.company.controller.Items.User.*;
import com.company.menu.*;

public class CompanyAppl {

    public static void main(String[] args) {

        InputOutput inputOutput = new ConsoleInputOutput();

        UserItems user = new UserItems(inputOutput, new Item[]{
                new ItemGetCar(inputOutput),
                new ItemGetModel(inputOutput),
                new ItemGetCarDrivers(inputOutput),
                new ItemGetDriverCars(inputOutput)});

        TechnicianItems technician = new TechnicianItems(inputOutput, new Item[]{
                new ItemGetAllCars(inputOutput),
                new ItemGetAllDrivers(inputOutput),
                new ItemGetAllRecords(inputOutput),});

        StatistItems statist = new StatistItems(inputOutput, new Item[]{
                new ItemGetModelProfit(inputOutput),
                new ItemGetMostPopularModelNames(inputOutput),
                new ItemGetMostProfitModelNames(inputOutput),});

        ClerkItems clerk = new ClerkItems(inputOutput, new Item[]{
                new ItemAddCar(inputOutput),
                new ItemRentCar(inputOutput),
                new ItemRemoveCar(inputOutput),});

        AdministratorItems administrator = new AdministratorItems(inputOutput, new Item[]{
                new ItemAddCar(inputOutput),
                new ItemAddModel(inputOutput),
                new ItemClear(inputOutput),
                new ItemGetDriver(inputOutput),
                new ItemRemoveCar(inputOutput),});

        ItemSaveData saveData = new ItemSaveData(inputOutput);
        ItemRestoreDataFromFile restoreDataFromFile = new ItemRestoreDataFromFile(inputOutput);

        Item[] items = new Item[] {administrator, clerk, statist, technician, user, saveData, restoreDataFromFile};

        MenuWithExit menu = new MenuWithExit(inputOutput, items);
        menu.runMenu();
    }
}
