package tqs.homework.airquality.Controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.homework.airquality.cache.Cache;
import tqs.homework.airquality.models.AirQuality;
import tqs.homework.airquality.models.LocalAirQuality;
import tqs.homework.airquality.models.Location;
import tqs.homework.airquality.service.LocalAirQualityService;

@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
@AutoConfigureMockMvc
class LocalAirQualityControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private LocalAirQualityService service;

    @Test
    void whenGetCountries_thenReturnCountryList() throws Exception {
        List<String> countriesList = new ArrayList<String>(Arrays.asList("Alaska","Anshan","Anyang","Argentina","Austria","Beijing","Benxi","Brazil","California","Canada","Chaoyang","Chile","Colombia","CzechRepublic","Dandong","Denmark","Ecuador","Finland","France","Fushun","Fuxin","Georgia","Germany","Guatemala","HChMinh","HNi","Horsta","HuangdaoDistrict","Huangshan","Hubei","Huludao","Iceland","India","Iran","IranHSE","Ireland","Italy","Japan","Jiaozuo","Jinzhou","Jordan","JuCounty","Kaifeng","KenliDistrict","Liaoyang","LijinCounty","LisongDistrict","Luxembourg","Macedonia","Malaysia","Mexico","Nanping","Nepal","Ningbo","Norway","Panjin","Peru","Philippines","Poland","Portugal","Romania","Russia","Serbia","Shenyang","Slovakia","SouthAfrica","SouthKorea","SouthKoreaDMZ","Spain","Sudan","Sweden","Texas","Thailand","Tieling","UAE","USA","Utah","Vietnam","Xinyang","Yingkou","Zhengzhou"));

        when(service.getCountries()).thenReturn(countriesList);

        mvc.perform(
            get("/api/v1/countries")
            .contentType(MediaType.APPLICATION_JSON))
            // .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$", hasSize(equalTo(81))),
                jsonPath("$[0]", is("Alaska")),
                jsonPath("$[-1]", is("Zhengzhou")))
            ;
    }

    @Test
    void whenGetStations_thenStationsListForCountry() throws Exception {

        HashMap<String, String> stationsPortugal = new HashMap<>();
        stationsPortugal.put("8372", "Paços de Ferreira, Paços de Ferreira, Portugal");
        stationsPortugal.put("10520", "São João, Funchal, Portugal");

        when(service.getStations("Portugal")).thenReturn(stationsPortugal);

        mvc.perform(
            get("/api/v1/stations/Portugal")
            .contentType(MediaType.APPLICATION_JSON))
            // .andDo(print())
            .andExpectAll(
                status().isOk(),
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.8372", is("Paços de Ferreira, Paços de Ferreira, Portugal")),
                jsonPath("$.10520", is("São João, Funchal, Portugal")),
                jsonPath("$.9999").doesNotExist())
            ;
        ;
    }

    @Test
    void whenGetAirQuality_thenReturnAirQualityFromStations() throws Exception {

        // 8372, Paços de Ferreira, Paços de Ferreira, Portugal
        Double[] geolocation = {41.274166666667,-8.3761111111111};
        Location location = new Location("8372", "Paços de Ferreira, Paços de Ferreira, Portugal", "Portugal", geolocation);
        AirQuality airQuality = new AirQuality("37", "39", "2", "3.1", "37", "10", "o3");
        LocalAirQuality localAirQuality = new LocalAirQuality(location, airQuality, "day", "timestamp");
        
        when(service.getAirQualityByCode("8372")).thenReturn(localAirQuality);

        mvc.perform(
            get("/api/v1/airCode/8372")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.location.code", is("8372")),
                jsonPath("$.location.name", is("Paços de Ferreira, Paços de Ferreira, Portugal")),
                jsonPath("$.location.country", is("Portugal")),
                jsonPath("$.airQuality.airQualityString", is("Good")),
                jsonPath("$.airQuality.airQualityIndex", is("37")),
                jsonPath("$.airQuality.pm25", is("39")),
                jsonPath("$.airQuality.pm10", is("2")),
                jsonPath("$.airQuality.no2", is("3.1")),
                jsonPath("$.airQuality.o3", is("37")),
                jsonPath("$.airQuality.waterGauge", is("10")),
                jsonPath("$.airQuality.dominentPolutent", is("o3")))
            ;
    }

    @Test
    void whenGetAirQuality_thenReturnAirQualityFromGeolocation() throws Exception {

        // 8372, Paços de Ferreira, Paços de Ferreira, Portugal
        Double[] geolocation = {41.274166666667,-8.3761111111111};
        Location location = new Location("8372", "Paços de Ferreira, Paços de Ferreira, Portugal", "Portugal", geolocation);
        AirQuality airQuality = new AirQuality("37", "39", "2", "3.1", "37", "10", "o3");
        LocalAirQuality localAirQuality = new LocalAirQuality(location, airQuality, "day", "timestamp");
        
        when(service.getAirQualityByGeo("41.274166666667", "-8.3761111111111")).thenReturn(localAirQuality);

        mvc.perform(
            get("/api/v1/airGeo/lat/41.274166666667/lng/-8.3761111111111")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.location.code", is("8372")),
                jsonPath("$.location.name", is("Paços de Ferreira, Paços de Ferreira, Portugal")),
                jsonPath("$.location.country", is("Portugal")),
                jsonPath("$.airQuality.airQualityString", is("Good")),
                jsonPath("$.airQuality.airQualityIndex", is("37")),
                jsonPath("$.airQuality.pm25", is("39")),
                jsonPath("$.airQuality.pm10", is("2")),
                jsonPath("$.airQuality.no2", is("3.1")),
                jsonPath("$.airQuality.o3", is("37")),
                jsonPath("$.airQuality.waterGauge", is("10")),
                jsonPath("$.airQuality.dominentPolutent", is("o3")))
            ;
    }

    @Test
    void whenGetCache_theReturnCache() throws Exception{
        Cache cache = new Cache();

        when(service.getCache()).thenReturn(cache);

        mvc.perform(
            get("/api/v1/cache")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpectAll(
                content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON),
                jsonPath("$.nRequests", is(0)),
                jsonPath("$.nHits", is(0)),
                jsonPath("$.nMisses", is(0)),
                jsonPath("$.countriesCache", is(new ArrayList<>())),
                jsonPath("$.stationsCache", is(new HashMap<>())),
                jsonPath("$.airQualityCodeCache", is(new HashMap<>())),
                jsonPath("$.airQualityGeoCache", is(new HashMap<>()))
            )
            ;
    }
}
