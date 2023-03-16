package tqs.cars_service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.cars_service.Controller.CarController;
import tqs.cars_service.Model.Car;
import tqs.cars_service.Service.CarManagerService;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {}

    @Test
    public void whenPostCar_thenCreateCar() throws Exception {

        Car car = new Car("Marker1","ModelA");

        when(carManagerService.save(Mockito.any())).thenReturn(car);

        mockMvc.perform(
            post("/api/create").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.marker", is("Marker1")))
            .andExpect(jsonPath("$.model", is("ModelA")))
        ;
        
        verify(carManagerService, times(1)).save(Mockito.any());
    }

    @Test
    public void whenGetAllCars_thenReturnListCars() throws Exception {
        Car car1 = new Car("Marker1", "ModelA");
        Car car2 = new Car("Marker2", "ModelB");
        Car car3 = new Car("Marker3", "ModelC");
        List<Car> carsList = Arrays.asList(car1, car2, car3);

        when(carManagerService.getAllCars()).thenReturn(carsList);

        mockMvc.perform(
            get("/api/cars").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(3)))
            .andExpect(jsonPath("$[0].marker", is("Marker1")))
            .andExpect(jsonPath("$[1].marker", is("Marker2")))
            .andExpect(jsonPath("$[2].marker", is("Marker3")))
            .andExpect(jsonPath("$[0].model", is("ModelA")))
            .andExpect(jsonPath("$[1].model", is("ModelB")))
            .andExpect(jsonPath("$[2].model", is("ModelC")))
        ;

        verify(carManagerService, times(1)).getAllCars();
    }

    @Test
    public void whenGetValidCarById_thenRetunCarDetails() throws Exception {
        Car car = new Car("Marker1", "ModelA");
        
        when(carManagerService.getCarDetails(anyLong())).thenReturn(Optional.of(car));

        mockMvc.perform(get("/api/car/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.marker", is("Marker1")))
            .andExpect(jsonPath("$.model", is("ModelA")))
        ;

        verify(carManagerService, times(1)).getCarDetails(anyLong());   
    }

    @Test
    public void whenGetInvalidCarById_thenReturnNotFound() throws Exception {

        when(carManagerService.getCarDetails(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(
            get("/api/car/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound())
        ;

        verify(carManagerService, times(1)).getCarDetails(anyLong());
    }
}