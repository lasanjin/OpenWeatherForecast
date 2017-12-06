package lasanjin.openweatherforecast.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * This class obtains JSON files with weather data from OpenWeatherMap
 */
public class OpenWeatherMapClient {
    private static final String REQUEST_METHOD = "GET";

    private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?";
    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String IMAGE_URL = "http://openweathermap.org/img/w/";
    private static final String API_KEY = "&APPID=511a2304bbfdb284ad18a964231b057e";

    private static final String UNIT_METRIC = "&units=metric";
    private static final String LANGUAGE_SE = "&lang=se";
    private static final String LATITUDE = "lat=";
    private static final String LONGITUDE = "&lon=";
    private static final String IMAGE_FORMAT = ".png";

    private OpenWeatherMapClient() {
    }

    public static String getWeatherData(String latitude, String longitude, boolean forecast) {
        try {
            String stringUrl =
                    selectUrl(forecast)
                            + LATITUDE + latitude
                            + LONGITUDE + longitude
                            + UNIT_METRIC
                            + LANGUAGE_SE
                            + API_KEY;

            URL url = new URL(stringUrl);//Create URL object

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection(); //Create connection
            connection.setRequestMethod(REQUEST_METHOD); //URL request
            connection.connect();//Connect to url

            //Read bytes and decode them into characters
            InputStreamReader streamReader =
                    new InputStreamReader(connection.getInputStream());

            //Read text from a character-input stream
            BufferedReader reader = new BufferedReader(streamReader);

            StringBuilder builder = new StringBuilder();

            String inputLine;
            //Check if the line we are reading is not null
            while ((inputLine = reader.readLine()) != null) {
                builder.append(inputLine);
            }

            streamReader.close();
            reader.close();
            connection.disconnect();

            return builder.toString();

        } catch (IOException ioe) {
            //Print this throwable and its backtrace to the standard error stream.
            ioe.printStackTrace();
        }

        return null;
    }

    private static String selectUrl(boolean forecast) {
        return forecast ? FORECAST_URL : CURRENT_WEATHER_URL;
    }

    public static byte[] getImage(String code) {
        try {
            URL url = new URL(IMAGE_URL + code + IMAGE_FORMAT);//Create URL object

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection(); //Create connection
            connection.setRequestMethod(REQUEST_METHOD); //URL request
            connection.connect();//Connect to url

            //Read the next byte of data from the input stream
            InputStream streamReader = connection.getInputStream();

            int read;
            byte[] buffer = new byte[4096];

            //Output stream in which the data is written into a byte array.
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((read = streamReader.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }

            return baos.toByteArray();

        } catch (IOException ioe) {
            //Print this throwable and its backtrace to the standard error stream.
            ioe.printStackTrace();
        }

        return null;
    }
}
