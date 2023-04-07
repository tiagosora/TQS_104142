package tqs.homework.airquality.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.homework.airquality.cache.Cache;
import tqs.homework.airquality.models.LocalAirQuality;
import tqs.homework.airquality.service.LocalAirQualityService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/v1/")
public class LocalAirQualityController {

    @Autowired
    private LocalAirQualityService localAirQualityService;

    @GetMapping("countries")
    public ResponseEntity<List<String>> getCountries() throws IOException, URISyntaxException, org.json.simple.parser.ParseException{
        List<String> data = new ArrayList<>(localAirQualityService.getCountries());
        if (data.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("stations/{country}")
    public ResponseEntity<HashMap<String, String>> getStations(@PathVariable(value="country") String country) throws IOException, URISyntaxException, org.json.simple.parser.ParseException{
        HashMap<String, String> data = new HashMap<>(localAirQualityService.getStations(country));
        if (data.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("air/{stationCode}")
    public ResponseEntity<LocalAirQuality> getAirQuality(@PathVariable(value="stationCode") String stationCode) throws IOException, URISyntaxException, org.json.simple.parser.ParseException{
        LocalAirQuality localAirQuality = localAirQualityService.getAirQuality(stationCode);
        if (localAirQuality.getAirQuality() == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(localAirQuality);
    }

    @GetMapping("cache")
    public ResponseEntity<Cache> getCache(){
        Cache data = localAirQualityService.getCache();
        return ResponseEntity.ok().body(data);
    }
}
