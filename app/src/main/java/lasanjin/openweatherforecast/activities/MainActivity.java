package lasanjin.openweatherforecast.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;

import java.util.List;

import lasanjin.openweatherforecast.R;
import lasanjin.openweatherforecast.controller.MainController;
import lasanjin.openweatherforecast.model.DayForecast;
import lasanjin.openweatherforecast.model.Position;
import lasanjin.openweatherforecast.model.WeatherForecast;
import lasanjin.openweatherforecast.utils.JSONParser;
import lasanjin.openweatherforecast.utils.OpenWeatherMapClient;
import lasanjin.openweatherforecast.view.WeatherFragment;

public class MainActivity extends AppCompatActivity {
    private MainController mController;
    private final int REQUEST_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0); //Remove Toolbar shadow

        mController = new MainController();
        WeatherFragment weatherFragment = new WeatherFragment();
        //Set interface to enable communication between view and controller package
        weatherFragment.setIController(mController);

        setWeatherFragment(weatherFragment);

        String[] location = buildLocation();

        CurrentWeatherTask weatherTask = new CurrentWeatherTask();
        weatherTask.execute(location);

        WeatherForecastTask forecastTask = new WeatherForecastTask();
        forecastTask.execute(location);
    }

    private void setWeatherFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_container, fragment);
        ft.commit();
    }

    /**
     * AsyncTask enables proper and easy use of the UI thread.
     * This class allows us to perform background operations
     * and publish results on the UI thread without having to manipulate threads and/or handlers.
     */
    private class CurrentWeatherTask extends AsyncTask<String, Void, Position> {
        @Override
        protected Position doInBackground(String... params) {
            Position position = new Position();
            String data = OpenWeatherMapClient.getWeatherData(params[0], params[1], false);

            try {
                position = JSONParser.getWeather(data);

                byte[] iconData = OpenWeatherMapClient.getImage(position.getWeather().getIcon());
                position.getWeather().setIconData(iconData);
            } catch (JSONException je) {
                //Print this throwable and its backtrace to the standard error stream.
                je.printStackTrace();
            }
            return position;
        }

        @Override
        protected void onPostExecute(Position position) {
            super.onPostExecute(position);
            mController.initCurrentWeather(position);
        }
    }

    private class WeatherForecastTask extends AsyncTask<String, Void, WeatherForecast> {
        int numberOfDays = 10;

        @Override
        protected WeatherForecast doInBackground(String... params) {
            WeatherForecast forecast = new WeatherForecast();
            String data = OpenWeatherMapClient.getWeatherData(params[0], params[1], true);

            try {
                forecast = JSONParser.getForecastWeather(data, numberOfDays);

                List<DayForecast> forecasts = forecast.getDayForecasts();
                for (DayForecast day : forecasts) {
                    String icon = day.getWeather().getIcon();
                    byte[] iconData = OpenWeatherMapClient.getImage(icon);
                    day.getWeather().setIconData(iconData);
                }
            } catch (JSONException je) {
                //Print this throwable and its backtrace to the standard error stream.
                je.printStackTrace();
            }
            return forecast;
        }

        @Override
        protected void onPostExecute(WeatherForecast forecast) {
            super.onPostExecute(forecast);
            mController.initForeCast(forecast);
        }
    }

    /**
     * Higher versions of Android need in app permission.
     * We request in app permission this way
     *
     * @return
     */
    private String[] getLocation() {
        LocationManager locationManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        }

        //Set NETWORK_PROVIDER if running on phone. GPS_PROVIDER if running simulator.
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        if (location != null) {
            return new String[]{
                    String.valueOf(location.getLatitude()),
                    String.valueOf(location.getLongitude())};
        }
        return null;
    }

    private String[] buildLocation() {
        String[] location = getLocation();

        //Use hard coded coordinates for Stockholm if GPS is disabled.
        if (location == null) location = new String[]{"59.3326", "18.0649"};
        return location;
    }
}
