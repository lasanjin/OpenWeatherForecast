package lasanjin.openweatherforecast.activities;

import android.os.AsyncTask;

import org.json.JSONException;

import java.util.List;

import lasanjin.openweatherforecast.controller.MainController;
import lasanjin.openweatherforecast.model.DayForecast;
import lasanjin.openweatherforecast.model.IWeather;
import lasanjin.openweatherforecast.model.Weather;
import lasanjin.openweatherforecast.utils.JSONParser;
import lasanjin.openweatherforecast.utils.OpenWeatherMapClient;

 /**
 * Set forecast to <true> in order to obtain data and set view of weather forecast in the background.
 *
 * Set forecast to <false> in order to obtain data and set view of current weather in the background.
 */
public class WeatherTask extends AsyncTask<String, Void, IWeather> {
    private MainController mController;
    private IWeather weather;
    private boolean forecast;
    int numberOfDays = 10;

    public WeatherTask(MainController controller, IWeather weather, boolean forecast) {
        mController = controller;
        this.weather = weather;
        this.forecast = forecast;
    }

    @Override
    protected IWeather doInBackground(String... params) {
        String data = OpenWeatherMapClient.getWeatherData(params[0], params[1], forecast);
        if (forecast) {
            getForecastWeather(data);
        } else {
            getCurrentWeather(data);
        }
        return weather;
    }

    private void getForecastWeather(String data) {
        try {
            weather = JSONParser.getForecastWeather(data, numberOfDays);
            List<DayForecast> forecasts = weather.getDayForecasts();
            for (DayForecast day : forecasts) {
                Weather w = day.getWeather(null);
                String icon = w.getIcon();
                byte[] iconData = OpenWeatherMapClient.getImage(icon);
                w.setIconData(iconData);
            }
        } catch (JSONException je) {
            //Print this throwable and its backtrace to the standard error stream.
            je.printStackTrace();
        }
    }

    private void getCurrentWeather(String data) {
        try {
            weather = JSONParser.getWeather(data);

            byte[] iconData = OpenWeatherMapClient.getImage(weather.getWeather(null).getIcon());
            weather.getWeather(null).setIconData(iconData);
        } catch (JSONException je) {
            //Print this throwable and its backtrace to the standard error stream.
            je.printStackTrace();
        }
    }

    @Override
    protected void onPostExecute(IWeather weather) {
        super.onPostExecute(weather);
        if (this.forecast) {
            mController.initForeCast(weather);
        } else {
            mController.initCurrentWeather(weather);
        }
    }
}