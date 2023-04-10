package tqs.homework.airquality.client;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@ExtendWith(MockitoExtension.class)
class RequestHandlerTest {
    
    @Mock(lenient = true)
    private IHttpClient httpClient;

    @InjectMocks
    private RequestHandler requestHandler;

    private static final String RESPONSE = "{\"status\":\"ok\"}";
    private static final String USER_ID = ConfigUtils.getPropertyFromConfig("id");
    private static final String API_1_Q = ConfigUtils.getPropertyFromConfig("api.1.q");

    @Test
    void whenFindCountries_HandleRequestCorrecly() throws URISyntaxException, IOException, ParseException{
        String request = "https://api.waqi.info/map/bounds?"+USER_ID+"="+API_1_Q+"&latlng=85%2C-180%2C-85%2C180";
        System.out.println(request);
        Mockito.when(httpClient.doHttpGet(request)).thenReturn(RESPONSE);

        JSONObject result = requestHandler.findCountries();
        JSONObject expectedResult = (JSONObject)(new JSONParser().parse(RESPONSE));
        assertEquals(expectedResult, result);
    }

    @Test
    void whenFindStations_HandleRequestCorrecly() throws URISyntaxException, IOException, ParseException{
        String request = "https://api.waqi.info/search/?"+USER_ID+"="+API_1_Q+"&keyword=Portugal";
        System.out.println(request);
        Mockito.when(httpClient.doHttpGet(request)).thenReturn(RESPONSE);

        JSONObject result = requestHandler.findStations("Portugal");
        JSONObject expectedResult = (JSONObject)(new JSONParser().parse(RESPONSE));
        assertEquals(expectedResult, result);
    }

    @Test
    void whenFindAirQualityByCode_HandleRequestCorrecly() throws URISyntaxException, IOException, ParseException{
        String request = "https://api.waqi.info/feed/@8372/?"+USER_ID+"="+API_1_Q;
        Mockito.when(httpClient.doHttpGet(request)).thenReturn(RESPONSE);

        JSONObject result = requestHandler.findAirQualityByCode("8372");
        JSONObject expectedResult = (JSONObject)(new JSONParser().parse(RESPONSE));
        assertEquals(expectedResult, result);
    }

    @Test
    void whenFindAirQualityByGeo_HandleRequestCorrecly() throws URISyntaxException, IOException, ParseException{
        String request = "https://api.waqi.info/feed/geo:41.274166666667;-8.3761111111111/?"+USER_ID+"="+API_1_Q;
        Mockito.when(httpClient.doHttpGet(request)).thenReturn(RESPONSE);

        JSONObject result = requestHandler.findAirQualityByGeo("41.274166666667", "-8.3761111111111");
        JSONObject expectedResult = (JSONObject)(new JSONParser().parse(RESPONSE));
        assertEquals(expectedResult, result);
    }
}
