package lasanjin.openweatherforecast.model;

import java.util.List;

public interface IWeather {
    List<DayForecast> getDayForecasts();
    Weather getWeather(Weather w);
    String getCity();
}
