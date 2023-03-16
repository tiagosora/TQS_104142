package tqs.cars_service.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.cars_service.Model.Car;
import tqs.cars_service.Service.CarManagerService;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private CarManagerService carManagerService;
    
    @PostMapping("/create")
    public ResponseEntity<Car> createCar(@RequestBody Car car){
        return ResponseEntity.status(HttpStatus.CREATED).body(this.carManagerService.save(car));
    }

    @GetMapping("/cars")
    public List<Car> getAllCars(){
        return this.carManagerService.getAllCars();
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "carId") Long carId){
        Optional<Car> car = this.carManagerService.getCarDetails(carId);
        if (car.isPresent()) {
            return ResponseEntity.ok().body(car.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
