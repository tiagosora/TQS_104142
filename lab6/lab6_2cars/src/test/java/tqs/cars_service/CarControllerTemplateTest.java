package tqs.cars_service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import tqs.cars_service.Model.Car;
import tqs.cars_service.Repository.CarRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase                                                              // lab3_2
// @TestPropertySource(locations = "application-integrationtest.properties")            // lab3_3
public class CarControllerTemplateTest {
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired 
    private CarRepository repository;

    @AfterEach
    public void setUp() {
        repository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car car = new Car("Marker1", "ModelA");
        restTemplate
            .postForEntity(("/api/create"), car, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found)
            .extracting(Car::getMarker)
            .containsOnly("Marker1");
    }

    @Test
    void whenInvalidId_thenReturnNotFound() {
        ResponseEntity<Car> response = restTemplate
            .getForEntity("/api/car/1", Car.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void whenGetAllCars_thenStatus200() {
        Car car1 = new Car("Marker1", "ModelA");
        Car car2 = new Car("Marker2", "ModelB");
        
        repository.saveAndFlush(car1);
        repository.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
            .exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("ModelA", "ModelB");
    }
}