package lasanjin.openweatherforecast.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Position contains current weather in a location.
 */
public class Position implements IWeather{
    private Weather weather;
    private String city;

    public Position() {
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Weather getWeather(Weather w) {
        return weather;
    }

    public String getCity() {
        return city;
    }

    @Override
    public List<DayForecast> getDayForecasts() {
        List<DayForecast> forecasts = new ArrayList<>();
        DayForecast df = buildDayForecast();
        forecasts.add(df);
        return forecasts;
    }

    private DayForecast buildDayForecast() {
        DayForecast df = new DayForecast();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateandTime = sdf.format(new Date());
        df.setWeather(weather);
        df.setDate(currentDateandTime);
        return df;
    }
}