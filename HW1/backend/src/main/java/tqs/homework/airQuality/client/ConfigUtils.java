package tqs.homework.airQuality.client;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Properties;

public class ConfigUtils {

    private ConfigUtils(){}

    static public String getPropertyFromConfig(String property) {

        try (InputStream input = ConfigUtils.class.getClassLoader().getResourceAsStream("application.properties")) {

            Properties prop = new Properties();

            if (input == null) {
                throw new FileNotFoundException("Unable to find application.properties!");
            }

            prop.load(input);

            return prop.getProperty(property);

        } catch (IOException ex) {
            throw new NoSuchElementException("Unable to get properties from config!");
        }
    }
}