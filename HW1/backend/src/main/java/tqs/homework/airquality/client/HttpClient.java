package tqs.homework.airquality.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpClient implements IHttpClient{
    
    @Override
    public String doHttpGet(String url) throws IOException {
        try (   CloseableHttpClient client = HttpClients.createDefault();
                CloseableHttpResponse response = client.execute(new HttpGet(url))
            ){
            return EntityUtils.toString(response.getEntity());
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "ERROR : Exception fetching from API!");
            return null;
        }
    }
}