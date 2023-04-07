package tqs.homework.airquality.client;

import java.io.IOException;

public interface IHttpClient {
    public String doHttpGet(String url) throws IOException;
}
