package tqs.homework.airquality.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import tqs.homework.airquality.models.LocalAirQuality;

class CacheTest {

    private Cache cache;
    
    @Test
    void whenGetMethod_thenReturnExpectedValue(){
        this.cache = new Cache();
        assertEquals(0,this.cache.getnRequests());
        assertEquals(0, this.cache.getnHits());
        assertEquals(0, this.cache.getnMisses());
        assertTrue(this.cache.getCountriesCache().isEmpty());
        assertTrue(this.cache.getStationsCache().isEmpty());
        assertTrue(this.cache.getAirQualityCodeCache().isEmpty());
        assertTrue(this.cache.getAirQualityGeoCache().isEmpty());
    }

    @Test
    void whenSetMethod_thenReturnExpectedValue(){
        this.cache = new Cache();
        this.cache.setnRequests(1);
        this.cache.setnHits(2);
        this.cache.setnMisses(3);

        List<CacheData> countries = new ArrayList<>();
        countries.add(new CacheData());
        Map<CacheData, Map<CacheData, CacheData>> stations = new HashMap<>();
        stations.put(new CacheData(), new HashMap<>());
        Map<CacheData, CacheData> airqualitycode = new HashMap<>();
        airqualitycode.put(new CacheData(), new CacheData());
        Map<List<CacheData>, CacheData> airqualitygeo = new HashMap<>();
        airqualitygeo.put(new ArrayList<>(), new CacheData());

        this.cache.setCountriesCache(countries);
        this.cache.setStationsCache(stations);
        this.cache.setAirQualityCodeCache(airqualitycode);
        this.cache.setAirQualityGeoCache(airqualitygeo);

        assertEquals(1, this.cache.getnRequests());
        assertEquals(2, this.cache.getnHits());
        assertEquals(3, this.cache.getnMisses());
        assertFalse(this.cache.getCountriesCache().isEmpty());
        assertFalse(this.cache.getStationsCache().isEmpty());
        assertFalse(this.cache.getAirQualityCodeCache().isEmpty());
        assertFalse(this.cache.getAirQualityGeoCache().isEmpty());
    }

    @Test
    void whenNewEventInCache_RegisterEvent(){
        this.cache = new Cache();
        this.cache.newRequest();
        this.cache.newMiss();
        this.cache.newHit();

        assertEquals(1, this.cache.getnRequests());
        assertEquals(1, this.cache.getnHits());
        assertEquals(1, this.cache.getnMisses());
    }

    @Test
    void whenAddCoutriesListToCache_SaveNewList(){
        this.cache = new Cache();

        ArrayList<String> countriesList = new ArrayList<>(Arrays.asList("Portugal"));
        this.cache.addCountriesCache(countriesList);
        assertFalse(this.cache.getCountriesCache().isEmpty());
        this.cache.getCountriesCache().forEach(
            (CacheData cachedCountry) -> {
                assertTrue(countriesList.contains((String)cachedCountry.getData()));
            }
        );

        this.cache.clearCountriesCache();
        assertTrue(this.cache.getCountriesCache().isEmpty());
    }

    @Test
    void whenAddStationsMapToCache_SaveNewMap(){
        this.cache = new Cache();
        String country = "Portugal", stationCode = "1", stationName = "Aveiro, Portugal";
        HashMap<String, String> station = new HashMap<>();
        station.put(stationCode, stationName);

        this.cache.addStationsCache(country, station);
        assertFalse(this.cache.getStationsCache().isEmpty());
        this.cache.getStationsCacheFromCountry(country).forEach(
            (CacheData key, CacheData value) -> {
                assertNotNull(station.get(key.getData()));
                assertEquals(station.get(key.getData()), value.getData());
            }
        );

        this.cache.clearStationsCache(country);
        assertTrue(this.cache.getStationsCache().isEmpty());
    }

    @Test
    void whenAddAirQualityCodeMapToCache_SaveNewMap() {
        this.cache = new Cache();
        String country = "AveiroStationCode", timestamp = "TestTime";
        LocalAirQuality aveiroAirQuality = new LocalAirQuality();
        aveiroAirQuality.setTimestamp(timestamp);

        this.cache.addAirQualityCodeCache(country, aveiroAirQuality);
        assertFalse(this.cache.getAirQualityCodeCache().isEmpty());
        CacheData cachedData = this.cache.getAirQualityCodeCacheFromStation(country);
        
        assertEquals(timestamp, ((LocalAirQuality)cachedData.getData()).getTimestamp());

        this.cache.clearAirQualityCodeCache(country);;
        assertTrue(this.cache.getAirQualityCodeCache().isEmpty());
    }

    @Test
    void whenAddAirQualityGeoMapToCache_SaveNewMap() {
        this.cache = new Cache();
        String lat = "A", lng = "B", timestamp = "TestTime";
        LocalAirQuality aveiroAirQuality = new LocalAirQuality();
        aveiroAirQuality.setTimestamp(timestamp);

        this.cache.addAirQualityGeoCache(lat, lng, aveiroAirQuality);
        assertFalse(this.cache.getAirQualityGeoCache().isEmpty());
        CacheData cachedData = this.cache.getAirQualityGeoCacheFromStation(lat, lng);
        
        assertEquals(timestamp, ((LocalAirQuality)cachedData.getData()).getTimestamp());

        this.cache.clearAirQualityGeoCache(lat, lng);
        assertTrue(this.cache.getAirQualityCodeCache().isEmpty());
    }

    @Test
    void whenEqualsObject_AssertEquals(){
        Cache cache0 = new Cache();
        Cache cache1 = new Cache();
        Cache cache2 = new Cache();
        cache2.setnRequests(1);
        assertNotEquals(0, cache0);
        assertEquals(cache0, cache0);
        assertEquals(cache0, cache1);
        assertNotEquals(cache0, cache2);
        assertEquals(cache0.hashCode(), cache1.hashCode());
    }

    @Test
    void testToString(){
        this.cache = new Cache();
        assertEquals(cache.toString(), "{"+"nRequests='"+0+"', "+"nHits='"+0+"', "+"nMisses='"+0+"', "+"countriesCache='"+(new ArrayList<>()).toString()+"', "+"stationsCache='"+(new HashMap<>()).toString()+"', "+"airQualityCodeCache='"+(new HashMap<>()).toString()+"', "+"airQualityGeoCache='"+(new HashMap<>()).toString()+"'"+"}");
        
    }
}
