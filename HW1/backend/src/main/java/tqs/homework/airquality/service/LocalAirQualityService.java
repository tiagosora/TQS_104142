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
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Countries from Cache");
            List<CacheData> cachedData = cache.getCountriesCache();
            cachedData.forEach((CacheData data) -> 
                countriesList.add((String)data.getData())
            );
            
        } else {
            cache.newMiss();
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Countries from API");
            
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
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Stations for {} from Cache",country);
            Map<CacheData, CacheData> cachedData = cache.getStationsCacheFromCountry(country);
            
            for(Entry<CacheData, CacheData> entry : cachedData.entrySet()){
                CacheData data = entry.getKey();
                String stationCode = (String)data.getData();
                String stationName = (String)(cachedData.get(data).getData());
                stationsList.putIfAbsent(stationCode, stationName);
            }
        } else {
            cache.newMiss();
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Stations for {} from API",country);

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

    public LocalAirQuality getAirQuality(String stationCode) throws URISyntaxException, IOException, ParseException {
        cache.newRequest();
        LocalAirQuality localAirQuality = null;
        if(cache.getAirQualityCacheFromStation(stationCode) != null){
            cache.newHit();
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Air Quality for {} from Cache",stationCode);
            CacheData cachedData = cache.getAirQualityCacheFromStation(stationCode);
            localAirQuality = (LocalAirQuality)cachedData.getData();
        } else {
            cache.newMiss();
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Fetched Air Quality for for {} from from API",stationCode);

            JSONObject jsonObject = requestHandler.findAirQuality(stationCode);

            if (jsonObject == null){
                return new LocalAirQuality();
            }

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
            String pm25;
            String pm10;
            String no2;
            String o3;
            String wg;
            try {
                pm25 = ((Number)((JSONObject)iaqiObject.get("pm25")).get("v")).toString();
            } catch (NullPointerException e){ pm25 = ""; }
            try {
                pm10 = ((Number)((JSONObject)iaqiObject.get("pm10")).get("v")).toString();
            } catch (NullPointerException e){ pm10 = ""; }
            try {
                no2 = ((Number)((JSONObject)iaqiObject.get("no2")).get("v")).toString();
            } catch (NullPointerException e){ no2 = ""; }
            try {
                o3 = ((Number)((JSONObject)iaqiObject.get("o3")).get("v")).toString();
            } catch (NullPointerException e){ o3 = ""; }
            try {
                wg = ((Number)((JSONObject)iaqiObject.get("wg")).get("v")).toString();
            } catch (NullPointerException e){ wg = ""; }

            JSONObject timeObject = (JSONObject)dataObject.get("time");
            String day = (String)timeObject.get("s");
            String timestamp = Long.toString((Long)timeObject.get("v"));

            Location location = new Location(locationCode, locationName, locationCountry, geolocation);
            AirQuality airQuality = new AirQuality(aqi, pm25, pm10, no2, o3, wg, dominentPolutent);
            localAirQuality = new LocalAirQuality(location, airQuality, day, timestamp);

            this.cache.addAirQualityCache(stationCode, localAirQuality);
        }
        return localAirQuality;
    }
}