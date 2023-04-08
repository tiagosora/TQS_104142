package tqs.homework.airquality.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tqs.homework.airquality.client.RequestHandler;
import tqs.homework.airquality.models.AirQuality;
import tqs.homework.airquality.models.LocalAirQuality;
import tqs.homework.airquality.models.Location;

@ExtendWith(MockitoExtension.class)
class LocalAirQualityServiceTest {
    
    @Mock(lenient = true)
    private RequestHandler requestHandler;

    @InjectMocks
    private LocalAirQualityService localAirQualityService;

    @BeforeEach
    void setUp() throws ParseException, URISyntaxException, IOException{
        String response0 = "{\"status\":\"ok\",\"data\":[{\"station\":{\"name\":\"Joaquim Magalhães, Faro, Portugal\"}},{\"station\":{\"name\":\"Caletillas, Canarias, Spain\"}},{\"station\":{\"name\":\"Roubaix Serres, Nord-Pas-de-Calais, France\"}}]}";
        JSONObject countriesJsonObject = (JSONObject)new JSONParser().parse(response0);
        Mockito.when(requestHandler.findCountries()).thenReturn(countriesJsonObject);

        String response1 = "{\"status\":\"ok\",\"data\":[{\"uid\":8383,\"station\":{\"name\":\"Joaquim Magalhães, Faro, Portugal\"}},{\"uid\":10513,\"station\":{\"name\":\"Olivais, Lisboa, Portugal\"}},{\"uid\":8379,\"station\":{\"name\":\"Entrecampos, Lisboa, Portugal\"}}]}";
        JSONObject stationsJsonObject = (JSONObject)new JSONParser().parse(response1);
        Mockito.when(requestHandler.findStations("Portugal")).thenReturn(stationsJsonObject);

        String response2 = "{\"status\":\"ok\",\"data\":{\"aqi\":28,\"idx\":8372,\"city\":{\"geo\":[41.274166666667,-8.3761111111111],\"name\":\"Paços de Ferreira, Paços de Ferreira, Portugal\"},\"dominentpol\":\"o3\",\"iaqi\":{\"h\":{\"v\":23},\"no2\":{\"v\":5.7},\"o3\":{\"v\":27.7},\"p\":{\"v\":1014.8},\"pm10\":{\"v\":2},\"pm25\":{\"v\":39},\"t\":{\"v\":21.6},\"w\":{\"v\":8.2},\"wg\":{\"v\":8.2}},\"time\":{\"s\":\"2023-04-06 11:00:00\",\"v\":1680778800}}}";
        JSONObject airQualityJsonObject = (JSONObject)new JSONParser().parse(response2);
        Mockito.when(requestHandler.findAirQuality("8383")).thenReturn(airQualityJsonObject);

    }

    @Test
    void whenGetCountries_thenReturnListOfCountries() throws URISyntaxException, IOException, ParseException {
        List<String> countriesList = new ArrayList<>();
        countriesList.addAll(Arrays.asList("Portugal", "Spain", "France"));

        assertTrue(localAirQualityService.getCountries().containsAll(countriesList));
        assertTrue(localAirQualityService.getCountries().containsAll(countriesList));
    }

    @Test
    void whenGetStationsByCountry_thenReturnsListOfStations() throws ParseException, URISyntaxException, IOException{
       HashMap<String, String> stations = new HashMap<>();
       stations.put("8383", "Joaquim Magalhães, Faro, Portugal");
       stations.put("10513", "Olivais, Lisboa, Portugal");
       stations.put("8379", "Entrecampos, Lisboa, Portugal");

       assertEquals(stations, localAirQualityService.getStations("Portugal"));
       assertEquals(stations, localAirQualityService.getStations("Portugal"));
    }

    @Test
    void whenGetStationsByInvalidCountry_thenReturnsEmptyList() throws URISyntaxException, IOException, ParseException{
        HashMap<String, String> stationsEmpty = new HashMap<>();
        assertEquals(stationsEmpty, localAirQualityService.getStations("Invalid"));
    }

    @Test 
    void whenGetAirQualityByStation_thenReturnAQI() throws URISyntaxException, IOException, ParseException{
        Double [] geolocation = {41.274166666667, -8.3761111111111};
        AirQuality airQuality = new AirQuality("28", "39", "2", "5.7", "27.7", "8.2", "o3");
        Location location = new Location("8372", "Paços de Ferreira, Paços de Ferreira, Portugal", "Portugal", geolocation);
        LocalAirQuality localAirQuality = new LocalAirQuality(location, airQuality, "2023-04-06 11:00:00", "1680778800");

        assertEquals(localAirQuality.toString(), localAirQualityService.getAirQuality("8383").toString());
        assertEquals(localAirQuality.toString(), localAirQualityService.getAirQuality("8383").toString());
    }

    @Test
    void whenGetAirQualityByInvalidStation_thenReturnsErrorMessage() throws URISyntaxException, IOException, ParseException{
        LocalAirQuality localAirQuality = new LocalAirQuality();
        assertEquals(localAirQuality, localAirQualityService.getAirQuality("Invalid"));
    }
}
