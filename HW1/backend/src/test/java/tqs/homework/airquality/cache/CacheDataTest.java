package tqs.homework.airquality.cache;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class CacheDataTest {
    private final Object data = new Object();
    private CacheData cacheData;
    
    @Test
    void whenGetMethod_thenReturnExpectedValue(){
        cacheData = new CacheData(data);
        assertEquals(cacheData.getData(), data);
    }

    @Test
    void whenSetMethod_thenReturnExpectedValue(){
        cacheData = new CacheData();
        this.cacheData.setData(data);

        assertEquals(cacheData.getData(), data);
    }

    @Test
    void whenEmptyConstructor_thenReturnInvalidAQI(){
        cacheData = new CacheData();
        assertNull(cacheData.getData());
        assertNull(cacheData.getTimestamp());
    }

    @Test
    void whenEqualObject_AssertEquals(){
        CacheData cacheData0 = new CacheData();
        CacheData cacheData1 = new CacheData();
        CacheData cacheData2 = new CacheData(data);
        CacheData cacheData3 = new CacheData(data);
        assertEquals(cacheData0, cacheData1);
        assertEquals(cacheData2, cacheData3);
        assertEquals(cacheData0.hashCode(), cacheData1.hashCode());
        assertEquals(cacheData2.hashCode(), cacheData3.hashCode());
    }

    @Test
    void testToString(){
        cacheData = new CacheData(data);
        assertEquals(cacheData.toString(), "{"+"data='"+data.toString()+"'"+"}");
    }
}
