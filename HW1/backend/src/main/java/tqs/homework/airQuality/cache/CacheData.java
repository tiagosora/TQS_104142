package tqs.homework.airQuality.cache;


import java.time.LocalDateTime;
import java.util.Objects;

public class CacheData {

    private Object data;
    private LocalDateTime timestamp;

    CacheData(){}
    CacheData(Object data){
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // GETS

    public Object getData() {
        return data;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    // SETS

    public void setData(Object data) {
        this.data = data;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Cache)) {
            return false;
        }
        CacheData cacheData = (CacheData) o;
        return  Objects.equals(data, cacheData.data) && 
                Objects.equals(timestamp, cacheData.timestamp);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(data, timestamp);
    }

    @Override
    public String toString() {
        return  "{" +
                "data='" + getData().toString() + "'" +
                "}";
    }
}
