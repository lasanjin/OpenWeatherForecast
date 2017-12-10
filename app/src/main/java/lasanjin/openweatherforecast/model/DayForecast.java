package lasanjin.openweatherforecast.model;

/**
 * Day containing weather data with date and time.
 */
public class DayForecast {
    private Weather weather;
    private String date;

    public DayForecast() {
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Weather getWeather(Weather w) {
        return weather;
    }

    public String getDate() {
        return date;
    }
}