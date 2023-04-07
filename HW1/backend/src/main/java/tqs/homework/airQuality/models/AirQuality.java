package tqs.homework.airQuality.models;

import java.util.Objects;

public class AirQuality {
    private String airQualityString;
    private String airQualityIndex;
    private String pm25; 
    private String pm10; 
    private String no2;
    private String o3;
    private String waterGauge;
    private String dominentPolutent;

    public AirQuality() {}
    public AirQuality(String airQualityIndex, String pm25, String pm10, String no2, String o3, String waterGauge, String dominentPolutent) {
        this.airQualityIndex = airQualityIndex;
        this.airQualityString = airQualityMeter(airQualityIndex);
        this.pm25 = pm25;
        this.pm10 = pm10;
        this.no2 = no2;
        this.o3 = o3;
        this.waterGauge = waterGauge;
        this.dominentPolutent = dominentPolutent;
    }

    public String airQualityMeter(String airQualityIndex) {
        Long aqi = Long.parseLong(airQualityIndex);
        if (aqi < 0) {
            return null;
        } else if (aqi <= 50) {
            return "Good";
        } else if (aqi <= 100) {
            return "Moderate";
        } else if (aqi <= 150) {
            return "Unhealthy for Sensitive Groups";
        } else if (aqi <= 200) {
            return "Unhealthy";
        } else if (aqi <= 300) {
            return "Very Unhealthy";
        } else {
            return "Hazardous";
        }
    }

    // GETS

    public String getAirQualityString() {
        return airQualityString;
    }
    public String getAirQualityIndex() {
        return airQualityIndex;
    }
    public String getDominentPolutent() {
        return dominentPolutent;
    }
    public String getNo2() {
        return no2;
    }
    public String getO3() {
        return o3;
    }
    public String getPm10() {
        return pm10;
    }
    public String getPm25() {
        return pm25;
    }
    public String getWaterGauge() {
        return waterGauge;
    }
    
    // SETS

    public void setAirQualityIndex(String airQualityIndex) {
        this.airQualityIndex = airQualityIndex;
        this.airQualityString = airQualityMeter(airQualityIndex);
    }
    public void setDominentPolutent(String dominentPolutent) {
        this.dominentPolutent = dominentPolutent;
    }
    public void setNo2(String no2) {
        this.no2 = no2;
    }
    public void setO3(String o3) {
        this.o3 = o3;
    }
    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }
    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }
    public void setWaterGauge(String waterGauge) {
        this.waterGauge = waterGauge;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AirQuality)) {
            return false;
        }
        AirQuality airQuality = (AirQuality) o;
        return  Objects.equals(airQualityIndex, airQuality.airQualityIndex) && 
                Objects.equals(pm25, airQuality.pm25) && 
                Objects.equals(pm10, airQuality.pm10) && 
                Objects.equals(no2, airQuality.no2) && 
                Objects.equals(o3, airQuality.o3) && 
                Objects.equals(waterGauge, airQuality.waterGauge) && 
                Objects.equals(airQualityString, airQuality.airQualityString) && 
                Objects.equals(dominentPolutent, airQuality.dominentPolutent);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(airQualityIndex, pm25, pm10, no2, o3, waterGauge, airQualityString, dominentPolutent);
    }

    @Override
    public String toString() {
        return  "{" +
                "airQualityIndex='" + getAirQualityIndex() + "', " +
                "airQualityString='" + getAirQualityString() + "', " +
                "pm25='" + getPm25() + "', " +
                "pm10='" + getPm10() + "', " +
                "no2='" + getNo2() + "', " +
                "o3='" + getO3() + "', " +
                "waterGauge='" + getWaterGauge() + "', " +
                "dominentPolutent='" + getDominentPolutent() + "'" +
                "}";
    }
}
