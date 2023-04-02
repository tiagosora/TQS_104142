package tqs.cars_service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.cars_service.Model.Car;
import tqs.cars_service.Repository.CarRepository;

@DataJpaTest
public class CarRepositoryTest {
    
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {}

    @Test
    void whenFindValidCarById_thenReturnCar() {
        Car car = new Car("Marker1","Model0");
        testEntityManager.persistAndFlush(car);

        Car fromDB = carRepository.findByCarId(car.getCarId()).orElse(null);
        assertThat(fromDB).isEqualTo(car);
    }

    @Test
    public void whenGetInvalidId_thenReturnNull() {
        Car car = carRepository.findById(-99L).orElse(null);
        assertThat(car).isNull();
    }

    @Test
    void testFindAllValidCars() {
        Car car1 = new Car("Marker1", "ModelA");
        Car car2 = new Car("Marker2", "ModelB");
        Car car3 = new Car("Marker3", "ModelC");
        
        testEntityManager.persist(car1);
        testEntityManager.persist(car2);
        testEntityManager.persist(car3);
        testEntityManager.flush();

        List<Car> allCars = carRepository.findAll();

        assertThat(allCars)
            .hasSize(3)
            .extracting(Car::getCarId)
            .containsOnly(car1.getCarId(), car2.getCarId(), car3.getCarId())
        ;
    }
}
