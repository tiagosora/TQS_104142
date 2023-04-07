package tqs.homework.airQuality.models;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

class LocationTest {
    
    private final String code = "37", name = "Good", country = "39";
    private final Double[] geolocation = {41.274166666667, -8.3761111111111};
    private Location location;
    
    @Test
    void whenGetMethod_thenReturnExpectedValue(){
        this.location = new Location(code, name, country, geolocation);
        assertEquals(this.location.getCode(), code);
        assertEquals(this.location.getName(), name);
        assertEquals(this.location.getCountry(), country);
        assertEquals(this.location.getGeolocation(), geolocation);
    }

    @Test
    void whenSetMethod_thenReturnExpectedValue(){
        this.location = new Location();
        this.location.setCode(code);
        this.location.setCountry(country);
        this.location.setName(name);
        this.location.setGeolocation(geolocation);

        assertEquals(this.location.getCode(), code);
        assertEquals(this.location.getName(), name);
        assertEquals(this.location.getCountry(), country);
        assertEquals(this.location.getGeolocation(), geolocation);
    }

    @Test
    void whenEmptyConstructor_thenReturnInvalidCode(){
        this.location = new Location();
        assertNull(location.getCode());
    }

    @Test
    void whenEqualObject_AssertEquals(){
        Location location0 = new Location();
        Location location1 = new Location();
        Location location2 = new Location(code, name, country, geolocation);
        Location location3 = new Location(code, name, country, geolocation);
        assertTrue(location0.equals(location1));
        assertTrue(location2.equals(location3));
        assertEquals(location0.hashCode(), location1.hashCode());
        assertEquals(location2.hashCode(), location3.hashCode());
    }

    @Test
    void testToString(){
        Location location = new Location(code, name, country, geolocation);
        assertEquals(location.toString(), "{"+"code='" +code+ "', "+"name='" +name+ "', "+"country='" +country+ "', "+"geolocation='" +Arrays.toString(geolocation)+ "'"+"}");
        
    }
}
