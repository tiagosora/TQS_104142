package tqs.homework.airQuality.models;

import java.util.Arrays;
import java.util.Objects;

public class Location {
    private String code, name, country;
    private Double[] geolocation;
    
    public Location() {}
    public Location(String code, String name, String country, Double[] geolocation){
        this.code = code;
        this.name = name;
        this.country = country;
        this.geolocation = geolocation;
    }

    // GETS

    public String getCode() {
        return code;
    }
    public String getCountry() {
        return country;
    }
    public Double[] getGeolocation() {
        return geolocation;
    }
    public String getName() {
        return name;
    }

    // SETS

    // public void setCode(String code) {
    //     this.code = code;
    // }
    // public void setCountry(String country) {
    //     this.country = country;
    // }
    // public void setGeolocation(Double[] geolocation) {
    //     this.geolocation = geolocation;
    // }
    // public void setName(String name) {
    //     this.name = name;
    // }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Location)) {
            return false;
        }
        Location location = (Location) o;
        return  Objects.equals(code, location.code) && 
                Objects.equals(name, location.name) && 
                Objects.equals(country, location.country) && 
                Objects.equals(geolocation, location.geolocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name, country, geolocation);
    }

    @Override
    public String toString() {
        return  "{" +
                "code='" + getCode() + "', " +
                "name='" + getName() + "', " +
                "region='" + getName() + "', " +
                "geolocation='" + Arrays.toString(getGeolocation()) + "'" +
                "}";
    }
}
