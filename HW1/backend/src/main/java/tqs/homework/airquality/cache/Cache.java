package tqs.homework.airquality.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import tqs.homework.airquality.models.LocalAirQuality;

public class Cache {
    
    private int nRequests;
    private int nHits;
    private int nMisses;
    private List<CacheData> countriesCache;
    private Map<CacheData, Map<CacheData, CacheData>> stationsCache;
    private Map<CacheData, CacheData> airQualityCodeCache;
    private Map<List<CacheData>, CacheData> airQualityGeoCache;

    public Cache() {
        this.nRequests = 0;
        this.nHits = 0;
        this.nMisses = 0;
        this.countriesCache = new ArrayList<>();
        this.stationsCache = new HashMap<>();
        this.airQualityCodeCache = new HashMap<>();
        this.airQualityGeoCache = new HashMap<>();
    }

    // GETS

    public int getnRequests() {
        return nRequests;
    }
    public int getnHits() {
        return nHits;
    }
    public int getnMisses() {
        return nMisses;
    }
    public List<CacheData> getCountriesCache() {
        return countriesCache;
    }
    public Map<CacheData, Map<CacheData, CacheData>> getStationsCache() {
        return stationsCache;
    }
    public Map<CacheData, CacheData> getAirQualityCodeCache() {
        return airQualityCodeCache;
    }
    public Map<List<CacheData>, CacheData> getAirQualityGeoCache() {
        return airQualityGeoCache;
    }

    // SETS

    public void setnRequests(int nRequests) {
        this.nRequests = nRequests;
    }
    public void setnHits(int nHits) {
        this.nHits = nHits;
    }
    public void setnMisses(int nMisses) {
        this.nMisses = nMisses;
    }
    public void setAirQualityCodeCache(Map<CacheData, CacheData> airQualityCodeCache) {
        this.airQualityCodeCache = airQualityCodeCache;
    }
    public void setCountriesCache(List<CacheData> countriesCache) {
        this.countriesCache = countriesCache;
    }
    public void setStationsCache(Map<CacheData, Map<CacheData, CacheData>> stationsCache) {
        this.stationsCache = stationsCache;
    }
    public void setAirQualityGeoCache(Map<List<CacheData>, CacheData> airQualityGeoCache) {
        this.airQualityGeoCache = airQualityGeoCache;
    }

    // CUSTOMS

    public void newRequest(){
        this.setnRequests(this.getnRequests()+1);
    }
    public void newMiss(){
        this.setnMisses(this.getnMisses()+1);
    }
    public void newHit(){
        this.setnHits(this.getnHits()+1);
    }

    public void addCountriesCache(List<String> response){
        this.countriesCache = new ArrayList<>();
        for (String country : response){
            this.countriesCache.add(new CacheData(country));
        }
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    clearCountriesCache();
                }
            },
            60000
        );
    }
    protected void clearCountriesCache() {
        String log = "Clearing Cache from Countries.";
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
        this.countriesCache = new ArrayList<>();
    }

    public void addStationsCache(String country, Map<String, String> cache){
        Map<CacheData, CacheData> data = new HashMap<>();
        for (Entry<String, String> entry : cache.entrySet()){
            String stationCode = entry.getKey();
            String stationName = cache.get(stationCode);
            data.putIfAbsent(new CacheData(stationCode), new CacheData(stationName));
        }
        CacheData countryCache = new CacheData(country);
        this.stationsCache.putIfAbsent(countryCache, data);
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    clearStationsCache(country);
                }
            },
            60000
        );
    }
    protected void clearStationsCache(String country) {

        String log = String.format("Clearing Cache from Stations: %s", country);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
        for (Entry<CacheData, Map<CacheData, CacheData>> entry : this.stationsCache.entrySet()){
            if (country.equals(entry.getKey().getData())) {
                this.stationsCache.remove(entry.getKey());
            }
        }
    }

    public Map<CacheData, CacheData> getStationsCacheFromCountry(String country){
        for (Entry<CacheData, Map<CacheData, CacheData>> entry : this.stationsCache.entrySet()){
            CacheData data = entry.getKey();
            if(data.getData().equals(country)){
                return this.stationsCache.get(data);
            }
        }
        return new HashMap<>();
    }
    public void addAirQualityCodeCache(String stationCode, LocalAirQuality cache){
        this.airQualityCodeCache.putIfAbsent(new CacheData(stationCode), new CacheData(cache));
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    clearAirQualityCodeCache(stationCode);
                }
            },
            60000
        );
    }
    protected void clearAirQualityCodeCache(String stationCode) {
        String log = String.format("Clearing Cache from Air Quality: %s", stationCode);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
        for (Entry<CacheData, CacheData> entry : this.airQualityCodeCache.entrySet()){
            CacheData data = entry.getKey();
            if (stationCode.equals(data.getData())) {
                this.airQualityCodeCache.remove(data);
            }
        }
    }

    public void addAirQualityGeoCache(String lat, String lng, LocalAirQuality cache){
        List<CacheData> geolocation = new ArrayList<>();
        geolocation.add(new CacheData(lat));
        geolocation.add(new CacheData(lng));
        this.airQualityGeoCache.putIfAbsent(geolocation, new CacheData(cache));
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    clearAirQualityGeoCache(lat, lng);
                }
            },
            60000
        );
    }
    protected void clearAirQualityGeoCache(String lat, String lng) {
        String log = String.format("Clearing Cache from Air Quality: [%s, %s]", lat, lng);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);
        for (Entry<List<CacheData>, CacheData> entry : this.airQualityGeoCache.entrySet()){
            List<CacheData> key = entry.getKey();
            if(key.get(0).getData().equals(lat) && key.get(1).getData().equals(lng)){
                this.airQualityGeoCache.remove(key);
            }
        }
    }

    public CacheData getAirQualityCodeCacheFromStation(String stationCode){
        for (Entry<CacheData, CacheData> entry : this.airQualityCodeCache.entrySet()){
            CacheData data = entry.getKey();
            if(data.getData().equals(stationCode)){
                return this.airQualityCodeCache.get(data);
            }
        }
        return null;
    }

    public CacheData getAirQualityGeoCacheFromStation(String lat, String lng){
        for (Entry<List<CacheData>, CacheData> entry : this.airQualityGeoCache.entrySet()){
            List<CacheData> key = entry.getKey();
            if(key.get(0).getData().equals(lat) && key.get(1).getData().equals(lng)){
                return this.airQualityGeoCache.get(key);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cache)) {
            return false;
        }
        Cache cache = (Cache) o;
        return  Objects.equals(nRequests, cache.nRequests) && 
                Objects.equals(nHits, cache.nHits) && 
                Objects.equals(nMisses, cache.nMisses) && 
                Objects.equals(countriesCache, cache.countriesCache) && 
                Objects.equals(stationsCache, cache.stationsCache) && 
                Objects.equals(airQualityCodeCache, cache.airQualityCodeCache) && 
                Objects.equals(airQualityGeoCache, cache.airQualityGeoCache);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nRequests, nHits, nMisses, countriesCache, stationsCache, airQualityCodeCache, airQualityGeoCache);
    }

    @Override
    public String toString() {
        return  "{" +
                "nRequests='" + Integer.toString(getnRequests()) + "', " +
                "nHits='" + Integer.toString(getnHits()) + "', " +
                "nMisses='" + Integer.toString(getnMisses()) + "', " +
                "countriesCache='" + getCountriesCache().toString() + "', " +
                "stationsCache='" + getStationsCache().toString() + "', " +
                "airQualityCodeCache='" + getAirQualityCodeCache().toString() + "', " +
                "airQualityGeoCache='" + getAirQualityGeoCache().toString() + "'" +
                "}";
    }
}
