package tqs.homework.airQuality.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import tqs.homework.airQuality.models.LocalAirQuality;

public class Cache {
    
    private int nRequests, nHits, nMisses;
    private List<CacheData> countriesCache;
    private HashMap<CacheData, HashMap<CacheData, CacheData>> stationsCache;
    private HashMap<CacheData, CacheData> airQualityCache;

    public Cache() {
        this.nRequests = 0;
        this.nHits = 0;
        this.nMisses = 0;
        this.countriesCache = new ArrayList<>();
        this.stationsCache = new HashMap<>();
        this.airQualityCache = new HashMap<>();
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
    public HashMap<CacheData, HashMap<CacheData, CacheData>> getStationsCache() {
        return stationsCache;
    }
    public HashMap<CacheData, CacheData> getAirQualityCache() {
        return airQualityCache;
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
    public void setAirQualityCache(HashMap<CacheData, CacheData> airQualityCache) {
        this.airQualityCache = airQualityCache;
    }
    public void setCountriesCache(List<CacheData> countriesCache) {
        this.countriesCache = countriesCache;
    }
    public void setStationsCache(HashMap<CacheData, HashMap<CacheData, CacheData>> stationsCache) {
        this.stationsCache = stationsCache;
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
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Clearing Cache from Countries.");
        this.countriesCache = new ArrayList<>();
    }

    public void addStationsCache(String country, HashMap<String, String> cache){
        HashMap<CacheData, CacheData> data = new HashMap<>();
        for (String stationCode : cache.keySet()){
            String stationName = cache.get(stationCode);
            data.putIfAbsent(new CacheData(stationCode), new CacheData(stationName));
        }
        CacheData countryCache = new CacheData(country);
        this.stationsCache.putIfAbsent(countryCache, data);
        new java.util.Timer().schedule(
            new java.util.TimerTask() {
                @Override
                public void run() {
                    clearStationsCache(countryCache);
                }
            },
            60000
        );
    }
    protected void clearStationsCache(CacheData country) {
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Clearing Cache from Stations: "+country.getData());
        if (this.stationsCache.get(country)!=null){
            this.stationsCache.remove(country);
        }
    }

    public HashMap<CacheData, CacheData> getStationsCacheFromCountry(String country){
        for (CacheData data : this.stationsCache.keySet()){
            if(data.getData().equals(country)){
                return this.stationsCache.get(data);
            }
        }
        return null;
    }
    public void addAirQualityCache(String stationCode, LocalAirQuality cache){
        this.airQualityCache.putIfAbsent(new CacheData(stationCode), new CacheData(cache));
    }
    public CacheData getAirQualityCacheFromStation(String stationCode){
        for (CacheData data : this.airQualityCache.keySet()){
            if(data.getData().equals(stationCode)){
                return this.airQualityCache.get(data);
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
                Objects.equals(airQualityCache, cache.airQualityCache);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nRequests, nHits, nMisses, countriesCache, stationsCache, airQualityCache);
    }

    @Override
    public String toString() {
        return  "{" +
                "nRequests='" + Integer.toString(getnRequests()) + "', " +
                "nHits='" + Integer.toString(getnHits()) + "', " +
                "nMisses='" + Integer.toString(getnMisses()) + "', " +
                "countriesCache='" + getCountriesCache().toString() + "', " +
                "stationsCache='" + getStationsCache().toString() + "', " +
                "airQualityCache='" + getAirQualityCache().toString() + "'" +
                "}";
    }
}
