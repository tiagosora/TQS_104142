package tqs.cars_service.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.cars_service.Model.Car;
import tqs.cars_service.Repository.CarRepository;

@Service
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car){
        return this.carRepository.save(car);
    }

    public List<Car> getAllCars(){
        return this.carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId){
        return this.carRepository.findByCarId(carId);
    }

}
