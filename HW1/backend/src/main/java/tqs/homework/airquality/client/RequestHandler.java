package tqs.homework.airquality.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class RequestHandler {

    private static final String USER_ID = ConfigUtils.getPropertyFromConfig("id");
    private static final String API_1_BASEURL = ConfigUtils.getPropertyFromConfig("api.1.url");
    private static final String API_1_Q = ConfigUtils.getPropertyFromConfig("api.1.q");
    private static final String API_1_SUCCESS = "ok";

    private IHttpClient httpClient = new HttpClient();

    // GETS

    public JSONObject findCountries() throws URISyntaxException, IOException, org.json.simple.parser.ParseException{

        URIBuilder uriBuilder = new URIBuilder(API_1_BASEURL+"map/bounds");
        uriBuilder.addParameter(USER_ID, API_1_Q);
        uriBuilder.addParameter("latlng", "85,-180,-85,180");

        return callHttpClient(uriBuilder);
    }

    public JSONObject findStations(String country) throws URISyntaxException, IOException, ParseException {

        URIBuilder uriBuilder = new URIBuilder(API_1_BASEURL+"search/");
        uriBuilder.addParameter(USER_ID, API_1_Q);
        uriBuilder.addParameter("keyword", country);
        
        return callHttpClient(uriBuilder);
    }

    public JSONObject findAirQualityByCode(String stationCode) throws URISyntaxException, IOException, ParseException {

        URIBuilder uriBuilder = new URIBuilder(API_1_BASEURL+"feed/@"+stationCode+"/");
        uriBuilder.addParameter(USER_ID, API_1_Q);
        
        return callHttpClient(uriBuilder);
    }

    public JSONObject findAirQualityByGeo(String lat, String lng) throws URISyntaxException, IOException, ParseException {


        //https://api.waqi.info/feed/geo:41.274166666667;-8.3761111111111/?token=ba9aaf9bf6048e6ebe7d7e66c334bd243b5a658e
        URIBuilder uriBuilder = new URIBuilder(API_1_BASEURL+"feed/geo:"+lat+";"+lng+"/");
        uriBuilder.addParameter(USER_ID, API_1_Q);
        
        return callHttpClient(uriBuilder);
    }

    public JSONObject callHttpClient(URIBuilder uriBuilder) throws IOException, URISyntaxException, ParseException{
        String url = uriBuilder.build().toString();
        String apiResponse = httpClient.doHttpGet(url);

        String log = String.format("New API Request: %s", url);
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, log);

        JSONObject jsonObject = (JSONObject)(new JSONParser().parse(apiResponse));

        if(((String)jsonObject.get("status")).equals(API_1_SUCCESS)){
            return jsonObject;
        } else {
            String logError = "ERROR: Invalid Request!";
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, logError);
        }
        return new JSONObject();
    }
}