package tqs.homework.airQuality.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
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

import tqs.homework.airQuality.models.LocalAirQuality;
import tqs.homework.airQuality.service.LocalAirQualityService;

@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/api/v1/")
public class LocalAirQualityController {

    @Autowired
    private LocalAirQualityService localAirQualityService;

    @GetMapping("countries")
    public ResponseEntity<List<String>> getCountries() throws IOException, InterruptedException, ParseException, URISyntaxException, org.json.simple.parser.ParseException{
        List<String> data = new ArrayList<>(localAirQualityService.getCountries());
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("stations/{country}")
    public ResponseEntity<HashMap<String, String>> getStations(@PathVariable(value="country") String country) throws IOException, InterruptedException, ParseException, URISyntaxException, org.json.simple.parser.ParseException{
        HashMap<String, String> data = new HashMap<>(localAirQualityService.getStations(country));
        return ResponseEntity.ok().body(data);
    }

    @GetMapping("air/{stationCode}")
    public ResponseEntity<LocalAirQuality> getAirQuality(@PathVariable(value="stationCode") String stationCode) throws IOException, InterruptedException, ParseException, URISyntaxException, org.json.simple.parser.ParseException{
        return ResponseEntity.ok().body(localAirQualityService.getAirQuality(stationCode));
    }
}
