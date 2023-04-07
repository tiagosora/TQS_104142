package tqs.homework.airQuality.models;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


class AirQualityTest {

    private final String airQualityIndex = "37", airQualityString = "Good", pm25 = "39", pm10 = "2", no2 = "3.1", o3 = "37", waterGauge = "10", dominentPolutent = "o3";
    private AirQuality airQuality;
    
    @Test
    void whenGetMethod_thenReturnExpectedValue(){
        airQuality = new AirQuality(airQualityIndex, pm25, pm10, no2, o3, waterGauge, dominentPolutent);
        assertEquals(airQuality.getAirQualityIndex(), airQualityIndex);
        assertEquals(airQuality.getAirQualityString(), airQualityString);
        assertEquals(airQuality.getPm25(), pm25);
        assertEquals(airQuality.getPm10(), pm10);
        assertEquals(airQuality.getNo2(), no2);
        assertEquals(airQuality.getO3(), o3);
        assertEquals(airQuality.getWaterGauge(), waterGauge);
        assertEquals(airQuality.getDominentPolutent(), dominentPolutent);
    }

    @Test
    void whenSetMethod_thenReturnExpectedValue(){
        airQuality = new AirQuality();
        this.airQuality.setAirQualityIndex(airQualityIndex);
        this.airQuality.setDominentPolutent(dominentPolutent);
        this.airQuality.setNo2(no2);
        this.airQuality.setO3(o3);
        this.airQuality.setPm10(pm10);
        this.airQuality.setPm25(pm25);
        this.airQuality.setWaterGauge(waterGauge);

        assertEquals(airQuality.getAirQualityIndex(), airQualityIndex);
        assertEquals(airQuality.getAirQualityString(), airQualityString);
        assertEquals(airQuality.getPm25(), pm25);
        assertEquals(airQuality.getPm10(), pm10);
        assertEquals(airQuality.getNo2(), no2);
        assertEquals(airQuality.getO3(), o3);
        assertEquals(airQuality.getWaterGauge(), waterGauge);
        assertEquals(airQuality.getDominentPolutent(), dominentPolutent);
    }

    @Test
    void whenEmptyConstructor_thenReturnInvalidAQI(){
        airQuality = new AirQuality();
        assertNull(airQuality.getAirQualityIndex());
        assertNull(airQuality.getAirQualityString());
    }

    @Test
    void whenEqualObject_AssertEquals(){
        AirQuality airQuality0 = new AirQuality();
        AirQuality airQuality1 = new AirQuality();
        AirQuality airQuality2 = new AirQuality(airQualityIndex, pm25, pm10, no2, o3, waterGauge, dominentPolutent);
        AirQuality airQuality3 = new AirQuality(airQualityIndex, pm25, pm10, no2, o3, waterGauge, dominentPolutent);
        assertEquals(airQuality0, airQuality1);
        assertEquals(airQuality2, airQuality3);
        assertEquals(airQuality0.hashCode(), airQuality1.hashCode());
        assertEquals(airQuality2.hashCode(), airQuality3.hashCode());
    }

    @Test
    void testToString(){
        AirQuality airQuality2 = new AirQuality(airQualityIndex, pm25, pm10, no2, o3, waterGauge, dominentPolutent);
        assertEquals(airQuality2.toString(), "{"+"airQualityIndex='"+airQualityIndex+"', "+"airQualityString='"+airQualityString+"', "+"pm25='"+pm25 +"', "+"pm10='"+pm10+"', "+"no2='"+no2+"', "+"o3='"+o3+"', "+"waterGauge='"+waterGauge+"', "+"dominentPolutent='"+dominentPolutent+"'"+"}");
    }
}
