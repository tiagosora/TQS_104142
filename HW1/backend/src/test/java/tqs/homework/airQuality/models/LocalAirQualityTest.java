package tqs.homework.airQuality.models;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LocalAirQualityTest {

    private final String code = "37", name = "Good", country = "39";
    private final Double[] geolocation = {41.274166666667, -8.3761111111111};
    private final String airQualityIndex = "37", pm25 = "39", pm10 = "2", no2 = "3.1", o3 = "37", waterGauge = "10", dominentPolutent = "o3";
    private final AirQuality airQuality = new AirQuality(airQualityIndex, pm25, pm10, no2, o3, waterGauge, dominentPolutent);
    private final Location location = new Location(code, name, country, geolocation);
    private final String day = "2023-04-05 15:00:00", timestamp = "1680706800";
    private LocalAirQuality localAirQuality;

    @Test
    public void whenGetMethod_thenReturnExpectedValue(){
        this.localAirQuality = new LocalAirQuality(location, airQuality, day, timestamp);
        assertEquals(this.localAirQuality.getAirQuality(), airQuality);
        assertEquals(this.localAirQuality.getLocation(), location);
        assertEquals(this.localAirQuality.getDay(), day);
        assertEquals(this.localAirQuality.getTimestamp(), timestamp);
    }

    @Test
    public void whenSetMethod_thenReturnExpectedValue(){
        this.localAirQuality = new LocalAirQuality();
        this.localAirQuality.setAirQuality(airQuality);
        this.localAirQuality.setLocation(location);
        this.localAirQuality.setDay(day);
        this.localAirQuality.setTimestamp(timestamp);

        assertEquals(this.localAirQuality.getAirQuality(), airQuality);
        assertEquals(this.localAirQuality.getLocation(), location);
        assertEquals(this.localAirQuality.getDay(), day);
        assertEquals(this.localAirQuality.getTimestamp(), timestamp);
    }

    @Test
    public void whenEmptyConstructor_thenReturnInvalidLocation(){
        this.localAirQuality = new LocalAirQuality();
        assertNull(localAirQuality.getLocation());
    }

    @Test
    public void whenEqualObject_AssertEquals(){
        LocalAirQuality localAirQuality0 = new LocalAirQuality();
        LocalAirQuality localAirQuality1 = new LocalAirQuality();
        LocalAirQuality localAirQuality2 = new LocalAirQuality(location, airQuality, day, timestamp);
        LocalAirQuality localAirQuality3 = new LocalAirQuality(location, airQuality, day, timestamp);
        assertEquals(localAirQuality0, localAirQuality1);
        assertEquals(localAirQuality2, localAirQuality3);
    }

    @Test
    public void testToString(){
        localAirQuality = new LocalAirQuality(location, airQuality, day, timestamp);
        assertEquals(localAirQuality.toString(), "{"+"location='"+location.toString()+ "', "+"airQuality='"+airQuality.toString()+ "', "+"day='"+day+"', "+"timestamp='"+timestamp+"'" +"}");
    }
}
