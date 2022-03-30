package com.company.dao;

import com.company.domain.Driver;
import com.company.domain.Car;
import com.company.domain.Model;
import com.company.domain.RentRecord;
import com.company.dto.CarsReturnCode;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public interface IRentCompany {
    CarsReturnCode addModel(Model model);
    CarsReturnCode addCar(Car car);
    CarsReturnCode addDriver(Driver driver);
    Model getModel(String modelName);
    Car getCar(String regNumber);
    Driver getDriver(long licenseId);
    CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays);
    CarsReturnCode returnCar(String regNumber, long licenseId, LocalDate returnDate, int gasTankPercent, int damages);
    CarsReturnCode removeCar(String regNumber);
    List<Car> clear(LocalDate currentDate, int days);
    List<Driver> getCarDriver(String regNumber);
    List<Car> getDriverCars(long licenseId);
    Stream<Car> getAllCars();
    Stream<Driver> getAllDrivers();
    Stream<RentRecord> getAllRecords();
    List<String> getMostPopularModelNames();
    Double getModelProfit(String modelNames);
    List<String> getMostProfitModelNames();

    void save(String fileName);

}
