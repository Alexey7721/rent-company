package com.company.tests;

import com.company.dao.RentCompany;
import com.company.domain.Car;
import com.company.domain.Driver;
import com.company.domain.Model;
import com.company.domain.RentRecord;
import com.company.dto.CarsReturnCode;
import com.company.dto.State;
import com.company.dto.WrongArgumentException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RentCompanyTest {

    RentCompany rentCompany;

    static String driverName1 = "Олег";
    static String driverName2 = "Кирилл";
    static long licenseId1 = 111;
    static long licenseId2 = 22280;
    static int driverBirthYear1 = 1980;
    static int driverBirthYear2 = 1990;
    static String driverPhone1 = "899912312321";
    static String driverPhone2 = "877712312377";

    static Driver driver1 = new Driver(licenseId1, driverName1, driverBirthYear1, driverPhone1);
    static Driver driver2 = new Driver(licenseId2, driverName2, driverBirthYear2, driverPhone2);

//    Model(String modelName, int gasTank, String country, int priceDay, String company)
    static String modelName1 = "RIO";
    static String modelName2 = "CEED";
    static String modelName3 = "Optima";
    static int gasTank1 = 50;
    static int gasTank2 = 60;
    static String country = "Корея";
    static int priceDay1 = 700;
    static int priceDay2 = 600;
    static String company = "KIA";

    static Model model1 = new Model(modelName1, gasTank1, country, priceDay1, company);
    static Model model2 = new Model(modelName2, gasTank2, country, priceDay2, company);
    static Model model3 = new Model(modelName3, gasTank2, country, priceDay2, company);

    static String color1 = "Белый";
    static String color2 = "Черный";
    static String regNumber1 = "м666";
    static String regNumber2 = "м777";
    static String regNumber3 = "м888";
    static String regNumber4 = "м999";

    static LocalDate rentDate1 = LocalDate.of(2021, 5, 15);
    static int clearDays = 5;
    static int rentDays1 = 5;
    static int rentDays2 = 15;
    static LocalDate returnDateWrong = rentDate1.minusDays(1);
    static LocalDate returnDate1 = rentDate1.plusDays(rentDays1);
    static LocalDate returnDate2 = rentDate1.plusDays(rentDays2);
    static int delayDays = 2;
    static LocalDate returnDateDelay = returnDate1.plusDays(delayDays);
    static LocalDate currentDate = LocalDate.of(2021, 5, 30);

    private RentRecord rentRecord;

    private int damagesGood = 5;
    private int damagesBad = 15;
    private int damagesRemove = 35;
    private int gasTankPercent = 50;

    private Car car1;
    private Car car2;
    private Car car3;
    private Car car4;


    @BeforeEach
    public void setUp(){
        rentCompany = RentCompany.restoreFromFile();

        car1 = new Car(regNumber1, color1, modelName1);//rio
        car2 = new Car(regNumber2, color1, modelName1);//rio
        car3 = new Car(regNumber3, color2, modelName2);//ceed
        car4 = new Car(regNumber4, color2, modelName3);//Optima


        rentCompany.addModel(model1);
        rentCompany.addCar(car1);
        rentCompany.addDriver(driver1);

        rentCompany.rentCar(regNumber1,licenseId1, rentDate1, rentDays1);
        rentRecord = new RentRecord(licenseId1,regNumber1,rentDate1, rentDays1);
    }



    @Test
    public void addModel() {
        assertThrows(WrongArgumentException.class, () -> rentCompany.addModel(null));

        assertEquals(CarsReturnCode.MODEL_EXISTS, rentCompany.addModel(model1));

        assertEquals(CarsReturnCode.OK, rentCompany.addModel(model2));
        assertEquals(CarsReturnCode.MODEL_EXISTS, rentCompany.addModel(model2));
        assertEquals(CarsReturnCode.MODEL_EXISTS, rentCompany.addModel(model2));
    }

    @Test
    public void addCar() {
        assertThrows(WrongArgumentException.class, () -> rentCompany.addCar(null));

        assertEquals(CarsReturnCode.CAR_EXISTS, rentCompany.addCar(car1));

        assertEquals(CarsReturnCode.OK, rentCompany.addCar(car2));
        assertEquals(CarsReturnCode.CAR_EXISTS, rentCompany.addCar(car2));

        assertEquals(CarsReturnCode.NO_MODEL, rentCompany.addCar(car3));
        rentCompany.addModel(model2);
        assertEquals(CarsReturnCode.OK, rentCompany.addCar(car3));
        assertEquals(CarsReturnCode.CAR_EXISTS, rentCompany.addCar(car3));
    }

    @Test
    public void addDriver() {
        assertThrows(WrongArgumentException.class, () -> rentCompany.addDriver(null));

        assertEquals(CarsReturnCode.DRIVER_EXISTS, rentCompany.addDriver(driver1));

        assertEquals(CarsReturnCode.OK, rentCompany.addDriver(driver2));
        assertEquals(CarsReturnCode.DRIVER_EXISTS, rentCompany.addDriver(driver2));
        assertEquals(CarsReturnCode.DRIVER_EXISTS, rentCompany.addDriver(driver2));
    }

    @Test
    public void getModel() {
        assertEquals(model1, rentCompany.getModel(modelName1));
        assertNull(rentCompany.getModel(modelName2));
    }

    @Test
    public void getCar() {
        assertEquals(car1, rentCompany.getCar(regNumber1));
        assertNull(rentCompany.getCar(regNumber2));
        rentCompany.addCar(car2);
        assertEquals(car2, rentCompany.getCar(regNumber2));
    }

    @Test
    public void getDriver() {
        assertEquals(driver1, rentCompany.getDriver(licenseId1));
        assertNull(rentCompany.getDriver(licenseId2));
    }

    @Test
    public void rentCar() {
        assertEquals(CarsReturnCode.CAR_NOT_EXISTS, rentCompany.rentCar(regNumber2,licenseId1, rentDate1, rentDays1));
        rentCompany.addCar(car2);
        assertEquals(CarsReturnCode.NO_DRIVER, rentCompany.rentCar(regNumber2,licenseId2, rentDate1, rentDays1));
        rentCompany.addDriver(driver2);
        assertEquals(CarsReturnCode.OK, rentCompany.rentCar(regNumber2,licenseId2, rentDate1, rentDays1));
        assertEquals(CarsReturnCode.CAR_IN_USE, rentCompany.rentCar(regNumber2,licenseId2, rentDate1, rentDays1));
        assertTrue(car1.isInUse());
        assertTrue(car2.isInUse());

        assertEquals(rentRecord, getRentRecord(regNumber1));
    }

    private RentRecord getRentRecord(String regNumber){
        return rentCompany.getAllRecords().filter(r -> r.getRegNumber().equals(regNumber))
                .findFirst().orElse(null);
    }


    @Test
    public void testReturnCarReturnsCode() {
        assertEquals(CarsReturnCode.CAR_NOT_RENTED,
                rentCompany.returnCar(regNumber2, licenseId1, returnDate1, gasTankPercent, damagesGood));
        assertEquals(CarsReturnCode.NO_DRIVER,
                rentCompany.returnCar(regNumber1, licenseId2, returnDate1, gasTankPercent, damagesGood));
        assertEquals(CarsReturnCode.RETURN_DATE_WRONG,
                rentCompany.returnCar(regNumber1, licenseId1, returnDateWrong, gasTankPercent, damagesGood));
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood));
    }

    @Test
    public void returnCarWithoutPenalty() {//возврат машины БЕЗ штрафа (без задержки)
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood));

        RentRecord rentRecord = getRentRecord(regNumber1);
        double actualCost = rentRecord.getCost();

        double rentCost = rentDays1 * priceDay1;
        double gasCost = ((gasTank1 * (100.0 - gasTankPercent)) / 100.0) * rentCompany.getGasPrice();
        double expectedCost = rentCost + gasCost;
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void returnCarStateGood() {
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood));
        Car car = rentCompany.getCar(regNumber1);
        assertFalse(car.isInUse());
        assertFalse(car.isFlRemoved());
        assertEquals(State.GOOD, car.getState());
    }

    @Test
    public void returnCarStateBad() {
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesBad));
        Car car = rentCompany.getCar(regNumber1);
        assertFalse(car.isInUse());
        assertFalse(car.isFlRemoved());
        assertEquals(State.BAD, car.getState());
    }

    @Test
    public void returnCarStateFlRemove() {
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesRemove));
        Car car = rentCompany.getCar(regNumber1);
        assertFalse(car.isInUse());
        assertTrue(car.isFlRemoved());
        assertEquals(State.BAD, car.getState());
    }

    @Test
    public void returnCarWithPenalty() {
        assertEquals(CarsReturnCode.OK,
                rentCompany.returnCar(regNumber1, licenseId1, returnDateDelay, gasTankPercent, damagesGood));
        RentRecord rentRecord = getRentRecord(regNumber1);
        double actualCost = rentRecord.getCost();
//        double delayCost = delayDays * (priceDay1 + ((priceDay1 * rentCompany.getFinePercent())/100.0));
//        double delayCost = delayDays * (priceDay1 * (100.0 + rentCompany.getFinePercent()) / 100.0);
        double delayCost = delayDays * (priceDay1 * (1 + rentCompany.getFinePercent() / 100.0));
        double rentCost = rentDays1 * priceDay1;
        double gasCost = ((gasTank1 * (100.0 - gasTankPercent)) / 100.0) * rentCompany.getGasPrice();
        double expectedCost = rentCost + gasCost + delayCost;
        assertEquals(expectedCost, actualCost, 0.001);
    }

    @Test
    public void removeCar() {
        assertEquals(CarsReturnCode.CAR_NOT_EXISTS, rentCompany.removeCar(regNumber2));

        assertEquals(CarsReturnCode.CAR_IN_USE, rentCompany.removeCar(regNumber1));
        assertFalse(rentCompany.getCar(regNumber1).isFlRemoved());
        rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesRemove);
        assertEquals(CarsReturnCode.OK, rentCompany.removeCar(regNumber1));
        assertTrue(rentCompany.getCar(regNumber1).isFlRemoved());
    }

    @Test
    public void getCarDriver() {
        List<Driver> list = new ArrayList<>();
        assertEquals(list, rentCompany.getCarDriver(regNumber2));
        list.add(driver1);
        assertEquals(list, rentCompany.getCarDriver(regNumber1));
        rentCompany.returnCar(regNumber1,licenseId1, returnDate1,gasTankPercent,damagesGood);
        rentCompany.addDriver(driver2);
        rentCompany.rentCar(regNumber1,licenseId2, rentDate1, rentDays1);
        list.add(driver2);
        assertEquals(list, rentCompany.getCarDriver(regNumber1));
    }

    @Test
    public void getDriverCars() {
        List<Car> list = new ArrayList<>();
        assertEquals(list, rentCompany.getDriverCars(licenseId2));
        list.add(car1);
        assertEquals(list, rentCompany.getDriverCars(licenseId1));
        rentCompany.returnCar(regNumber1,licenseId1, returnDate1,gasTankPercent,damagesGood);
        rentCompany.addCar(car2);
        rentCompany.rentCar(regNumber2,licenseId1, rentDate1, rentDays1);
        list.add(car2);
        assertEquals(list, rentCompany.getDriverCars(licenseId1));
    }

    @Test
    public void clear() {

        rentCompany.addCar(car2);
        rentCompany.rentCar(regNumber2, licenseId1, rentDate1, rentDays2);
        rentCompany.returnCar(regNumber1,licenseId1, returnDate1,gasTankPercent,damagesRemove);
        rentCompany.returnCar(regNumber2,licenseId1, returnDate2,gasTankPercent,damagesRemove);

        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        assertEquals(cars, rentCompany.clear(currentDate, clearDays));
        List<Car> cars1 = new ArrayList<>();
        cars1.add(car2);
        assertEquals(cars1, rentCompany.getAllCars().collect(Collectors.toList()));

    }

    @Test
    public void getAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(car1);
        assertEquals(cars, rentCompany.getAllCars().collect(Collectors.toList()));
    }

    @Test
    public void getAllDrivers() {
        List<Driver> drivers = new ArrayList<>();
        drivers.add(driver1);
        assertEquals(drivers, rentCompany.getAllDrivers().collect(Collectors.toList()));
    }

    @Test
    public void getAllRecords() {
        List<RentRecord> records = new ArrayList<>();
        records.add(rentRecord);
        assertEquals(records, rentCompany.getAllRecords().collect(Collectors.toList()));
    }

    private void rentReturnCar(String regNumber, int countRents) {
        for (int i = 0; i < countRents; i++) {
            rentCompany.rentCar(regNumber, licenseId1, rentDate1, rentDays1);
            rentCompany.returnCar(regNumber, licenseId1, returnDate1, gasTankPercent, damagesGood);
        }
    }

    @Test
    public void getMostPopularModelNames() {
        rentCompany.addModel(model2);
        rentCompany.addModel(model3);
        rentCompany.addCar(car3);
        rentCompany.addCar(car4);

        rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood);
        rentReturnCar(regNumber1, 1);

        rentReturnCar(regNumber3, 2);
        rentReturnCar(regNumber4, 1);

        List<String> expected = Arrays.asList(modelName1, modelName2);
        List<String> actual = rentCompany.getMostPopularModelNames();
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);

        assertEquals(expected, actual);
    }

    @Test
    public void getModelProfit() {
        rentCompany.addModel(model3);
        rentCompany.addCar(car4);

        rentReturnCar(regNumber4, 2);

        double gasCost = ((gasTank1 * gasTankPercent) / 100.0) * rentCompany.getGasPrice();
        rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood);
        rentReturnCar(regNumber1, 2);
        double expectedOneDay = (rentDays1 * priceDay1) + gasCost;
        Double expected = expectedOneDay * 3;
        Double actual = rentCompany.getModelProfit(modelName1);
        assertEquals(expected, actual);

    }

    @Test
    public void getMostProfitModelNames() {
        rentCompany.returnCar(regNumber1, licenseId1, returnDate1, gasTankPercent, damagesGood);
        rentReturnCar(regNumber1, 1);
        rentCompany.addModel(model2);
        rentCompany.addCar(car3);
        rentReturnCar(regNumber3, 2);
        List<String> expected = Arrays.asList(modelName1);
        assertEquals(expected, rentCompany.getMostProfitModelNames());
        rentReturnCar(regNumber3, 1);
        rentCompany.addModel(model3);
        rentCompany.addCar(car4);
        rentReturnCar(regNumber4, 3);
        List<String> expected2 = Arrays.asList(modelName2, modelName3);
        List<String> actual = rentCompany.getMostProfitModelNames();
        expected.sort(String::compareTo);
        actual.sort(String::compareTo);
        assertEquals(expected2, actual);
    }
}