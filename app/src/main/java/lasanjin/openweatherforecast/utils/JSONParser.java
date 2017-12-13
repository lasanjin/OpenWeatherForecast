package lasanjin.openweatherforecast.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import lasanjin.openweatherforecast.model.DayForecast;
import lasanjin.openweatherforecast.model.Position;
import lasanjin.openweatherforecast.model.Temperature;
import lasanjin.openweatherforecast.model.Weather;
import lasanjin.openweatherforecast.model.WeatherForecast;

/**
 * This class obtains and saves data from JSON files to the model
 */
public class JSONParser {

    private JSONParser() {
    }

    /**
     * JSON parameters can be found @ https://openweathermap.org/current#one
     *
     * @param data
     * @return Position with data from JSON file.
     * @throws JSONException
     */
    public static Position getWeather(String data) throws JSONException {
        Position position = new Position();
        Weather weather = new Weather();
        Temperature temp = new Temperature();

        JSONObject json = new JSONObject(data);
        JSONObject mainJson = getObject("main", json);
        JSONObject weatherJson = json.getJSONArray("weather").getJSONObject(0);

        position.setCity(getString("name", json));

        weather.setIcon(getString("icon", weatherJson));
        weather.setDescription(getString("description", weatherJson));

        setTemp(temp, mainJson);

        weather.setTemperature(temp);
        position.setWeather(weather);

        return position;
    }

    /**
     * JSON parameters can be found @ https://openweathermap.org/forecast5
     *
     * @param data
     * @param numberOfDays
     * @return WeatherForecast with data from JSON file.
     * @throws JSONException
     */
    public static WeatherForecast getForecastWeather(String data, int numberOfDays) throws JSONException {
        WeatherForecast forecast = new WeatherForecast();

        JSONObject json = new JSONObject(data);
        JSONArray jsonArr = json.getJSONArray("list"); //Forecast data

        //Make sure to stay in range of available forecast.
        int days = numberOfDays > 36 || numberOfDays <= 0 ? jsonArr.length() : numberOfDays;
        for (int i = 0; i < days; i++) {

            JSONObject dayJson = jsonArr.getJSONObject(i);
            JSONObject mainJson = dayJson.getJSONObject("main");//
            JSONObject weatherJson = dayJson.getJSONArray("weather").getJSONObject(0);

            DayForecast df = new DayForecast();
            Weather weather = new Weather();
            Temperature temp = new Temperature();

            df.setDate(getString("dt_txt", dayJson));

            weather.setIcon(getString("icon", weatherJson));
            weather.setDescription(getString("description", weatherJson));
            df.setWeather(weather);

            setTemp(temp, mainJson);

            weather.setTemperature(temp);
            df.setWeather(weather);

            forecast.addForecast(df);
        }

        return forecast;
    }

    private static void setTemp(Temperature temp, JSONObject mainJson) throws JSONException {
        temp.setTemp(getDouble("temp", mainJson));
        temp.setMaxTemp(getDouble("temp_max", mainJson));
        temp.setMinTemp(getDouble("temp_min", mainJson));
    }

    private static JSONObject getObject(String key, JSONObject json) throws JSONException {
        return json.getJSONObject(key);
    }

    private static String getString(String key, JSONObject json) throws JSONException {
        return json.getString(key);
    }

    private static double getDouble(String key, JSONObject json) throws JSONException {
        return json.getDouble(key);
    }
}
