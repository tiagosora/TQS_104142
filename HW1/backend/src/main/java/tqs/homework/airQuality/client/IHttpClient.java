package tqs.homework.airQuality.client;

import java.io.IOException;

public interface IHttpClient {
    public String doHttpGet(String url) throws IOException;
}
