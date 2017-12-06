package lasanjin.openweatherforecast.model;

/**
 * Position contains current weather in location and weather forecast
 */
public class Position {
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

    public Weather getWeather() {
        return weather;
    }

    public String getCity() {
        return city;
    }
}