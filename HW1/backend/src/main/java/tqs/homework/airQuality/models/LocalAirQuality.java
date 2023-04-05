package tqs.homework.airQuality.models;

import java.util.Objects;

public class LocalAirQuality {
    private Location location;
    private AirQuality airQuality;      
    private String day, timestamp;      
    public LocalAirQuality() {}
    public LocalAirQuality(Location location, AirQuality airQuality, String day, String timestamp){
        this.location = location;
        this.airQuality = airQuality;
        this.day = day;
        this.timestamp = timestamp;
    }

    // GETS

    public String getDay() {
        return day;
    }
    public Location getLocation() {
        return location;
    }
    public String getTimestamp() {
        return timestamp;
    }
    public AirQuality getAirQuality() {
        return airQuality;
    }

    // SETS

    // public void setDay(String day) {
    //     this.day = day;
    // }
    // public void setLocation(Location location) {
    //     this.location = location;
    // }
    // public void setTimestamp(String timestamp) {
    //     this.timestamp = timestamp;
    // }
    // public void setAirQuality(AirQuality airQuality) {
    //     this.airQuality = airQuality;
    // }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof LocalAirQuality)) {
            return false;
        }
        LocalAirQuality localAirQuality = (LocalAirQuality) o;
        return  Objects.equals(location, localAirQuality.location) && 
                Objects.equals(airQuality, localAirQuality.airQuality) && 
                Objects.equals(day, localAirQuality.day) && 
                Objects.equals(timestamp, localAirQuality.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(location, airQuality, day, timestamp);
    }

    @Override
    public String toString() {
        return  "{" +
                "location='" + getLocation().toString() + "', " +
                "airQuality='" + getAirQuality().toString() + "', " +
                "day='" + getDay() + "', " +
                "timestamp='" + getTimestamp() + "'" +
                "}";
    }
}


