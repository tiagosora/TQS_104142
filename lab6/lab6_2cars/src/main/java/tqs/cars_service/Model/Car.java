package tqs.cars_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long carId;
    
    private String marker;
    private String model;
    
    public Car(){}
    public Car(String marker, String model){
        this.marker = marker;
        this.model = model;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    @Override
    public int hashCode() {
        return super.hashCode();
    }

    public Long getCarId() {
        return carId;
    }
    public String getMarker() {
        return marker;
    }
    public String getModel() {
        return model;
    }
    public void setCarId(Long carId) {
        this.carId = carId;
    }
    public void setMarker(String marker) {
        this.marker = marker;
    }
    public void setModel(String model) {
        this.model = model;
    }

    @Override
    public String toString() {
        return "Car "+this.carId.toString()+", Marker: "+this.marker+", Model: "+this.model+";";
    }
}
