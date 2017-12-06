package lasanjin.openweatherforecast.model;

/**
 * Location contains weather
 */
public class Position {
    private WeatherForecast weatherForecast;
    private Weather weather;
    private String city;

    public Position() {
    }

    public void setWeatherForecast(WeatherForecast weatherForecast) {
        this.weatherForecast = weatherForecast;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }

    public Weather getWeather() {
        return weather;
    }

    public String getCity() {
        return city;
    }
}