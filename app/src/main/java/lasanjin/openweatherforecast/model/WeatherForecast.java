package lasanjin.openweatherforecast.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WeatherForecast contains days of forecast.
 */
public class WeatherForecast implements IWeather {
    private List<DayForecast> daysForecast;

    public WeatherForecast() {
        daysForecast = new ArrayList<>();
    }

    public boolean addForecast(DayForecast forecast) {
        return daysForecast.add(forecast);
    }

    public List<DayForecast> getDayForecasts() {
        return daysForecast;
    }

    @Override
    public Weather getWeather(Weather w) {
        for (DayForecast df : daysForecast) {
            Weather weather = df.getWeather(null);
            if (w.equals(weather)) {
                return weather;
            }
        }
        return null;
    }

    @Override
    public String getCity() {
        return "Current location";
    }
}