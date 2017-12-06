package lasanjin.openweatherforecast.model;

/**
 * Day (date) containing weather data
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

    public Weather getWeather() {
        return weather;
    }

    public String getDate() {
        return date;
    }
}