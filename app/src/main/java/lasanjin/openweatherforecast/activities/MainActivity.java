package lasanjin.openweatherforecast.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import lasanjin.openweatherforecast.model.IWeather;
import lasanjin.openweatherforecast.model.Position;
import lasanjin.openweatherforecast.model.Weather;
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

        MainController mc = new MainController();
        WeatherFragment weatherFragment = new WeatherFragment();
        //Set interface to enable communication between view and controller package
        weatherFragment.setIController(mc);

        setWeatherFragment(weatherFragment);

        String[] location = buildLocation();

        WeatherTask forecastTask = new WeatherTask(mc, new WeatherForecast(), true);
        forecastTask.execute(location);

        WeatherTask current = new WeatherTask(mc, new WeatherForecast(), false);
        current.execute(location);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ani = cm.getActiveNetworkInfo();
        return ani != null && ani.isConnected();
    }

    private void setWeatherFragment(Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_fragment_container, fragment);
        ft.commit();
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

    /**
     * @return Return array in order of latitude and longitude data.
     */
    private String[] buildLocation() {
        String[] location = getLocation();

        //Use hard coded coordinates for Stockholm if GPS is disabled.
        if (location == null) location = new String[]{"59.3326", "18.0649"};
        return location;
    }
}
