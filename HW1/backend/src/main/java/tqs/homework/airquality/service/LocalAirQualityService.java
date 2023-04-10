package tqs.homework.airquality.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import tqs.homework.airquality.cache.Cache;
import tqs.homework.airquality.cache.CacheData;
import tqs.homework.airquality.client.RequestHandler;
import tqs.homework.airquality.models.AirQuality;
import tqs.homework.airquality.models.LocalAirQuality;
import tqs.homework.airquality.models.Location;

@Service
public class LocalAirQualityService {
    
    private Cache cache = new Cache();
    private RequestHandler requestHandler = new RequestHandler();

    public Cache getCache() {
        return cache;
    }

    public List<String> getCountries() throws URISyntaxException, IOException, ParseException{
        cache.newRequest();
        List<String> countriesList = new ArrayList<>();
        if(!(cache.getCountriesCache().isEmpty())){
            cache.newHit();
            String log = "Fetched Countries from Cache";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            List<CacheData> cachedData = cache.getCountriesCache();
            cachedData.forEach((CacheData data) -> 
                countriesList.add((String)data.getData())
            );
            
        } else {
            cache.newMiss();
            String log = "Fetched Countries from API";
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            
            JSONObject jsonObject = requestHandler.findCountries();

            if (jsonObject == null){
                return new ArrayList<>();
            }
            
            JSONArray dataArray = (JSONArray)jsonObject.get("data");

            for (int i = 0; i < dataArray.size(); i ++) {
                JSONObject dataResults = (JSONObject)dataArray.get(i);
                JSONObject stationData = (JSONObject)dataResults.get("station");
                String stationName = (String)stationData.get("name");
                String[] stationNameSplited = stationName.split(",");
                if(stationNameSplited.length > 2){
                    String country = stationNameSplited[stationNameSplited.length-1].replaceAll("[^A-Za-z]", "");
                    if(!(countriesList.contains(country)) && !(country.equals(""))){
                        countriesList.add(country);
                    }
                }
            }
            countriesList.sort(Comparable::compareTo);
            this.cache.addCountriesCache(countriesList);
        }
        return countriesList;
    }

    public Map<String, String> getStations(String country) throws URISyntaxException, IOException, ParseException {
        cache.newRequest();
        Map<String, String> stationsList = new HashMap<>();
        if(!(cache.getStationsCacheFromCountry(country).isEmpty())){
            cache.newHit();
            String log = String.format("Fetched Stations for %s from Cache", country);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            Map<CacheData, CacheData> cachedData = cache.getStationsCacheFromCountry(country);
            
            for(Entry<CacheData, CacheData> entry : cachedData.entrySet()){
                CacheData data = entry.getKey();
                String stationCode = (String)data.getData();
                String stationName = (String)(cachedData.get(data).getData());
                stationsList.putIfAbsent(stationCode, stationName);
            }
        } else {
            cache.newMiss();
            String log = String.format("Fetched Stations for %s from API", country);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            JSONObject jsonObject = requestHandler.findStations(country);

            if (jsonObject == null){
                return new HashMap<>();
            }

            JSONArray dataArray = (JSONArray)jsonObject.get("data");

            for (int i = 0; i < dataArray.size(); i ++) {
                JSONObject dataResults = (JSONObject)dataArray.get(i);
                String stationCode = Long.toString((Long)dataResults.get("uid"));
                JSONObject stationData = (JSONObject)dataResults.get("station");
                String stationName = (String)stationData.get("name");
                if(stationsList.get(stationCode) == null && !(stationName.equals(""))){
                    stationsList.putIfAbsent(stationCode, stationName);
                }
            }
            this.cache.addStationsCache(country, stationsList);
        }
        return stationsList;
    }

    public LocalAirQuality getAirQualityByCode(String stationCode) throws URISyntaxException, IOException, ParseException {
        cache.newRequest();
        LocalAirQuality localAirQuality = new LocalAirQuality();
        try {
            Long.parseLong(stationCode);
        } catch(NumberFormatException e){
            return localAirQuality;
        }
        if(cache.getAirQualityCodeCacheFromStation(stationCode) != null){
            cache.newHit();
            String log = String.format("Fetched Air Quality for %s from Cache", stationCode);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            CacheData cachedData = cache.getAirQualityCodeCacheFromStation(stationCode);
            localAirQuality = (LocalAirQuality)cachedData.getData();
        } else {
            cache.newMiss();
            String log = String.format("Fetched Air Quality for %s from API", stationCode);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);

            JSONObject jsonObject = requestHandler.findAirQualityByCode(stationCode);

            localAirQuality = processAirQualityFromJson(jsonObject);
            if (localAirQuality.getAirQuality() != null) {
                this.cache.addAirQualityCodeCache(stationCode, localAirQuality);
            }
        }
        return localAirQuality;
    }
    
    public LocalAirQuality getAirQualityByGeo(String lat, String lng) throws URISyntaxException, IOException, ParseException {
        cache.newRequest();
        LocalAirQuality localAirQuality = new LocalAirQuality();
        try {
            Double.parseDouble(lat);
            Double.parseDouble(lng);
        } catch(NumberFormatException e){
            return localAirQuality;
        }
        if(cache.getAirQualityGeoCacheFromStation(lat, lng) != null){
            cache.newHit();
            String log = String.format("Fetched Air Quality for [%s, %s] from Cache", lat, lng);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
            CacheData cachedData = cache.getAirQualityGeoCacheFromStation(lat, lng);
            localAirQuality = (LocalAirQuality)cachedData.getData();
        } else {
            cache.newMiss();
            String log = String.format("Fetched Air Quality for [%s, %s] from API", lat, lng);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);

            JSONObject jsonObject = requestHandler.findAirQualityByGeo(lat, lng);

            localAirQuality = processAirQualityFromJson(jsonObject);
            if (localAirQuality.getAirQuality() != null){
                this.cache.addAirQualityGeoCache(lat, lng, localAirQuality);
            }
        }
        return localAirQuality;
    }

    public LocalAirQuality processAirQualityFromJson(JSONObject jsonObject)  {
        try{

            LocalAirQuality localAirQuality;

            JSONObject dataObject = (JSONObject)jsonObject.get("data");
            String locationCode = Long.toString((Long)dataObject.get("idx"));
            String aqi = Long.toString((Long)dataObject.get("aqi"));

            JSONObject cityObject = (JSONObject)dataObject.get("city");
            JSONArray geoArray = (JSONArray)cityObject.get("geo");
            Double[] geolocation = {(Double)geoArray.get(0), (Double)geoArray.get(1)};
            String locationName = (String)cityObject.get("name");

            String[] locationNameSplited = locationName.split(",");
            String locationCountry = locationNameSplited[locationNameSplited.length-1].replaceAll("[^A-Za-z]", "");

            String dominentPolutent = (String)dataObject.get("dominentpol");

            JSONObject iaqiObject = (JSONObject)dataObject.get("iaqi");
            String pm25 = processAIQIObject(iaqiObject, "pm25");
            String pm10 = processAIQIObject(iaqiObject, "pm10");
            String no2 = processAIQIObject(iaqiObject, "no2");
            String o3 = processAIQIObject(iaqiObject, "o3");
            String wg = processAIQIObject(iaqiObject, "wg");

            JSONObject timeObject = (JSONObject)dataObject.get("time");
            String day = (String)timeObject.get("s");
            String timestamp = Long.toString((Long)timeObject.get("v"));

            Location location = new Location(locationCode, locationName, locationCountry, geolocation);
            AirQuality airQuality = new AirQuality(aqi, pm25, pm10, no2, o3, wg, dominentPolutent);
            localAirQuality = new LocalAirQuality(location, airQuality, day, timestamp);

            return localAirQuality;

        } catch (NullPointerException exception){
            return new LocalAirQuality();
        }
    }

    public String processAIQIObject(JSONObject iaqiObject, String attribute){
        try {
            return ((Number)((JSONObject)iaqiObject.get(attribute)).get("v")).toString();
        } catch (NullPointerException e){ 
            return "";
        }
    }
}