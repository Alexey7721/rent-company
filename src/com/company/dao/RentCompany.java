package com.company.dao;

import com.company.domain.Car;
import com.company.domain.Driver;
import com.company.domain.Model;
import com.company.domain.RentRecord;
import com.company.dto.CarsReturnCode;
import com.company.dto.State;
import com.company.dto.WrongArgumentException;

import java.io.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RentCompany extends AbstractRentCompany implements Serializable {

    private HashMap<String, Car> cars;
    private HashMap<Long, Driver> drivers;
    private HashMap<String, Model> models;

    private HashMap<String, List<RentRecord>> carRecords;
    private HashMap<Long, List<RentRecord>> driverRecords;
    private TreeMap<LocalDate, List<RentRecord>> returnedRecords;
    private static String DEFAULT_FILE_NAME = "data";

    public static RentCompany restoreFromFile(String fileName) {
        File file = new File(fileName);
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object obj = objectInputStream.readObject();
            return (RentCompany) obj;
        } catch (Exception e){
            return new RentCompany();
        }
    }

    public static RentCompany restoreFromFile(){
        return restoreFromFile(DEFAULT_FILE_NAME);}

    private RentCompany() {
        super(15, 10);
        cars = new HashMap<>();
        drivers = new HashMap<>();
        models = new HashMap<>();
        carRecords = new HashMap<>();
        driverRecords = new HashMap<>();
        returnedRecords = new TreeMap<>();
    }


    @Override
    public CarsReturnCode addModel(Model model) {
        if (model == null) throw new WrongArgumentException();

        if(models.containsKey(model.getModelName())) return CarsReturnCode.MODEL_EXISTS;
        models.put(model.getModelName(), model);
        return CarsReturnCode.OK;
    }

    @Override
    public CarsReturnCode addCar(Car car) {
        if (car == null) throw new WrongArgumentException();
        if(cars.containsKey(car.getRegNumber())) return CarsReturnCode.CAR_EXISTS;
        if(!models.containsKey(car.getModelName())) return CarsReturnCode.NO_MODEL;
        cars.put(car.getRegNumber(), car);
        return CarsReturnCode.OK;
    }

    @Override
    public CarsReturnCode addDriver(Driver driver) {
        if (driver == null) throw new WrongArgumentException();
        if(drivers.containsKey(driver.getLicenseId())) return CarsReturnCode.DRIVER_EXISTS;
        drivers.put(driver.getLicenseId(), driver);
        return CarsReturnCode.OK;
    }

    @Override
    public Model getModel(String modelName) {
        return models.containsKey(modelName) ? models.get(modelName) : null;
    }

    @Override
    public Car getCar(String regNumber) {
        return cars.get(regNumber);
    }

    @Override
    public Driver getDriver(long licenseId) {
        return drivers.get(licenseId);
    }

    @Override
    public CarsReturnCode rentCar(String regNumber, long licenseId, LocalDate rentDate, int rentDays) {
        CarsReturnCode code = checkRentCar(regNumber, licenseId);
        if(checkRentCar(regNumber, licenseId) != CarsReturnCode.OK) return code;

        RentRecord rentRecord = new RentRecord(licenseId, regNumber, rentDate, rentDays);

        addCarRecord(rentRecord);
        addDriverRecord(rentRecord);
        setInUse(regNumber);

        return code;
    }
    private void addDriverRecord(RentRecord record) {
        long licenseId = record.getLicenseId();
        if(!driverRecords.containsKey(licenseId)) driverRecords.put(licenseId, new ArrayList<>());
        driverRecords.get(licenseId).add(record);
    }
    private void addCarRecord(RentRecord record) {
        String regNumber = record.getRegNumber();
        if(!carRecords.containsKey(regNumber)) carRecords.put(regNumber, new ArrayList<>());
        carRecords.get(regNumber).add(record);
    }

    private void setInUse(String regNumber) {
        cars.get(regNumber).setInUse(true);
    }

    private CarsReturnCode checkRentCar(String regNumber, long licenseId) {
        Car car = cars.get(regNumber);
        if(car == null || car.isFlRemoved()) return CarsReturnCode.CAR_NOT_EXISTS;
        if(!drivers.containsKey(licenseId)) return CarsReturnCode.NO_DRIVER;
        if(car.isInUse()) return CarsReturnCode.CAR_IN_USE;
        return CarsReturnCode.OK;
    }

    @Override
    public CarsReturnCode returnCar(String regNumber, long licenseId, LocalDate returnDate, int gasTankPercent, int damages) {
        RentRecord record = getRentRecord(regNumber, licenseId);
        CarsReturnCode code = checkReturnCar(licenseId, record, returnDate);
        if(code != CarsReturnCode.OK) return code;

        record.setReturnDate(returnDate);
        record.setGasTankPercent(gasTankPercent);
        record.setDamages(damages);

        calcCost(record);
        carSettings(regNumber, damages);

        if(!returnedRecords.containsKey(returnDate)) returnedRecords.put(returnDate, new ArrayList<>());
        returnedRecords.get(returnDate).add(record);

        return code;
    }

    private void carSettings(String regNumber, int damages) {
        Car car = cars.get(regNumber);
        if(damages > 0 && damages <= 10) car.setState(State.GOOD);
        if(damages > 10) car.setState(State.BAD);
        if(damages > 30) car.setFlRemoved(true);
        car.setInUse(false);
    }

    private void calcCost(RentRecord record){
        Car car = cars.get(record.getRegNumber());
        Model model = models.get(car.getModelName());
        int gasTank = model.getGasTank();
        double gasCost = ((gasTank * (100 - record.getGasTankPercent())) / 100.0) * getGasPrice();

        double fineCost = 0.0;
        long countDays = ChronoUnit.DAYS.between(record.getRentDate(), record.getReturnDate());
        if(countDays > record.getRentDays()) {
            double finePriceDay = (model.getPriceDay() * (100 + getFinePercent())) / 100.0;
            fineCost = finePriceDay * (countDays - record.getRentDays());
        }
        double rentCost = record.getRentDays() * model.getPriceDay();
        record.setCost(gasCost + fineCost + rentCost);
    }

    private RentRecord getRentRecord(String regNumber, long licenceId) {
        if(!carRecords.containsKey(regNumber)) return null;
        return carRecords.get(regNumber).stream()
                .filter(record -> record.getReturnDate() == null && licenceId == record.getLicenseId())
                .findFirst().orElse(null);
    }

    private CarsReturnCode checkReturnCar(long licenseId, RentRecord record, LocalDate returnDate) {
        if(!driverRecords.containsKey(licenseId)) return CarsReturnCode.NO_DRIVER;
        if(record == null) return CarsReturnCode.CAR_NOT_RENTED;
        if(record.getRentDate().isAfter(returnDate)) return CarsReturnCode.RETURN_DATE_WRONG;

        return CarsReturnCode.OK;
    }

    @Override
    public CarsReturnCode removeCar(String regNumber) {
        Car car = cars.get(regNumber);
        if(car == null) return CarsReturnCode.CAR_NOT_EXISTS;
        if(car.isInUse()) return CarsReturnCode.CAR_IN_USE;
        cars.get(regNumber).setFlRemoved(true);
        return CarsReturnCode.OK;
    }


    @Override
    public List<Driver> getCarDriver(String regNumber) {
        if (!carRecords.containsKey(regNumber)) return new ArrayList<>();

        return carRecords.get(regNumber)
                .stream()
                .map(RentRecord::getLicenseId)
                .distinct()
                .map(id -> drivers.get(id))
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> getDriverCars(long licenseId) {
        if(!driverRecords.containsKey(licenseId)) return new ArrayList<>();

        return driverRecords.get(licenseId).stream()
                .map(RentRecord::getRegNumber)
                .distinct()
                .map(this::getCar)
                .collect(Collectors.toList());
    }


    @Override
    public List<Car> clear(LocalDate currentDate, int days) {
        LocalDate date = currentDate.minusDays(days);

        List<String> victims = returnedRecords.headMap(date).entrySet().stream()
                .map(entry -> entry.getValue())
                .flatMap(list -> list.stream())
                .map(rec -> rec.getRegNumber())
                .distinct()
                .filter(number -> cars.get(number).isFlRemoved())
                .collect(Collectors.toList());

//        List<String> victims = returnedRecords.entrySet().stream()
//                .filter(entry -> entry.getKey().isBefore(date))
//                .map(entry -> entry.getValue())
//                .flatMap(list -> list.stream())
//                .map(rec -> rec.getRegNumber())
//                .distinct()
//                .filter(number -> cars.get(number).isFlRemoved())
//                .collect(Collectors.toList());
        List<Car> carsToRemove = victims.stream()
                .map(number -> cars.get(number))
                .collect(Collectors.toList());

        for (String number : victims) {
            carRecords.remove(number);
            cars.remove(number);
            for (List<RentRecord> list : driverRecords.values()) {
                list.removeIf(rn -> rn.getRegNumber().equals(number));
            }
//                Iterator<RentRecord> iterator = list.iterator();
//                while (iterator.hasNext()){
//                    RentRecord next = iterator.next();
//                    if(next.getRegNumber().equals(number)){
//                        iterator.remove();
//                    }
//                }

        }
        return carsToRemove;
    }

    @Override
    public Stream<Car> getAllCars() {
        return cars.values().stream();
    }

    @Override
    public Stream<Driver> getAllDrivers() {
        return drivers.values().stream();
    }

    @Override
    public Stream<RentRecord> getAllRecords() {
        return carRecords.values().stream().flatMap(list -> list.stream());
    }

    @Override
    public List<String> getMostPopularModelNames() {
//        List<String> listCars = new ArrayList<>();
//        HashMap<String, Integer> numberOfLeasesByModel = new HashMap<>();
//
//        for (List<RentRecord> list : carRecords.values()) {
//            String modelName = cars.get(list.get(0).getRegNumber()).getModelName();
//            if (!numberOfLeasesByModel.containsKey(modelName)) numberOfLeasesByModel.put(modelName, 0);
//            numberOfLeasesByModel.replace(modelName, numberOfLeasesByModel.get(modelName) + list.size());
//        }
//
//        int max = 0;
//        for (Integer integer : numberOfLeasesByModel.values()) if(integer > max) max = integer;
//        for (Model model : models.values()) {
//            if(!numberOfLeasesByModel.containsKey(model.getModelName())) continue;
//            if(numberOfLeasesByModel.get(model.getModelName()) == max) listCars.add(model.getModelName());
//        }
//        return listCars;

        return carRecords.values().stream()
                .flatMap(list -> list.stream())
                .map(rec -> cars.get(rec.getRegNumber()).getModelName())
                .collect(Collectors.groupingBy(name -> name,
                        Collectors.counting()))

                .entrySet().stream()
                .collect(
                        Collectors.groupingBy(entry -> entry.getValue().intValue(),
                                Collectors.mapping(Map.Entry::getKey, Collectors.toList())))

                .entrySet().stream()
                .max((entry1, entry2) -> (entry1.getKey() - entry2.getKey()))
                .orElse(new AbstractMap.SimpleEntry<>(0, new ArrayList<>()))
                .getValue();
    }

    @Override
    public Double getModelProfit(String modelName) {
        return returnedRecords.values().stream()
                .flatMap(Collection::stream)
                .filter(rec -> cars.get(rec.getRegNumber()).getModelName().equals(modelName))
                .mapToDouble(RentRecord::getCost)
                .sum();
    }

    @Override
    public List<String> getMostProfitModelNames() {
//        List<String> mostProfitableModels = new ArrayList<>();
//        HashMap<String, Double> numberOfLeasesByModel = new HashMap<>();
//        for (Model model : models.values()) {
//            numberOfLeasesByModel.put(model.getModelName(), getModelProfit(model.getModelName()));
//        }
//        Double max = 0.0;
//        for (Double aDouble : numberOfLeasesByModel.values()) if(aDouble > max) max = aDouble;
//        for (Model model : models.values()) {
//            if(!numberOfLeasesByModel.containsKey(model.getModelName())) continue;
//            if(numberOfLeasesByModel.get(model.getModelName()) == max) mostProfitableModels.add(model.getModelName());
//        }
//        return mostProfitableModels;
        Map<String, Double> numberOfLeasesByModel = returnedRecords.values().stream()
                .flatMap(list -> list.stream())
                .collect(
                        Collectors.groupingBy(rec -> cars.get(rec.getRegNumber()).getModelName(),
                                Collectors.summingDouble(rec -> rec.getCost()))
                );
        Map<Double, List<String>> collect = numberOfLeasesByModel.entrySet().stream()
                .collect(
                        Collectors.groupingBy(entry -> entry.getValue(),
                                Collectors.mapping(entry -> entry.getKey(), Collectors.toList()))
                );
        Optional<Map.Entry<Double, List<String>>> max = collect.entrySet().stream()
                .max((entry1, entry2) -> (int) (100 * (entry1.getKey() - entry2.getKey())));

        return max.isPresent() ? max.get().getValue() : new ArrayList<>();
    }




    @Override
    public void save(String fileName) {
        File file = new File(fileName);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (NotSerializableException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
