package lasanjin.openweatherforecast.model;

import java.util.ArrayList;
import java.util.List;

/**
 * WeatherForecast contains days of forecast
 */
public class WeatherForecast {
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
}