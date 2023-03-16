package tqs.cars_service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.cars_service.Model.Car;
import tqs.cars_service.Repository.CarRepository;
import tqs.cars_service.Service.CarManagerService;

@ExtendWith(MockitoExtension.class)
public class CarManagerServiceTest {
    
    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp(){
        Car car1 = new Car("Marker1", "ModelA");
        Car car2 = new Car("Marker2", "ModelB");
        Car car3 = new Car("Marker3", "ModelC");
        Car car4 = new Car("Marker4", "ModelD");
        Car car5 = new Car("Marker5", "ModelE");

        List<Car> carsList = Arrays.asList(car1,car2,car3,car4,car5);

        Mockito.when(carRepository.findAll()).thenReturn(carsList);
        Mockito.when(carRepository.findByCarId(car1.getCarId())).thenReturn(Optional.of(car1));
        Mockito.when(carRepository.findByCarId(car2.getCarId())).thenReturn(Optional.of(car2));
        Mockito.when(carRepository.findByCarId(car3.getCarId())).thenReturn(Optional.of(car3));
        Mockito.when(carRepository.findByCarId(car4.getCarId())).thenReturn(Optional.of(car4));
        Mockito.when(carRepository.findByCarId(car5.getCarId())).thenReturn(Optional.of(car5));
        Mockito.when(carRepository.findByCarId(-99L)).thenReturn(Optional.empty());
    }

    @Test
    void testGetAllCars() {
        List<Car> cars = carManagerService.getAllCars();

        assertThat(cars.size()).isEqualTo(5);
        
        assertThat(cars.get(0).getModel()).isEqualTo("ModelA");
        assertThat(cars.get(1).getModel()).isEqualTo("ModelB");
        assertThat(cars.get(2).getModel()).isEqualTo("ModelC");
        assertThat(cars.get(3).getModel()).isEqualTo("ModelD");
        assertThat(cars.get(4).getModel()).isEqualTo("ModelE");

        verify(carRepository, times(1)).findAll();
    }

    @Test
    void testCarDoesntExist() {
        assertThat(carManagerService.getCarDetails(-99L)).isEmpty();

        verify(carRepository, times(1)).findByCarId(-99L);
    }

    @Test
    void testSaveCar() {
        Car car = new Car("NewMarker", "NewModel");

        Mockito.when(carRepository.save(car)).thenReturn(car);

        assertThat(carManagerService.save(car).getModel()).isEqualTo("NewModel");
        verify(carRepository, times(1)).save(car);
    }
}
