package lasanjin.openweatherforecast.controller;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import lasanjin.openweatherforecast.model.DayForecast;
import lasanjin.openweatherforecast.model.Position;
import lasanjin.openweatherforecast.model.WeatherForecast;
import lasanjin.openweatherforecast.view.IWeather;

/**
 * This class is resposible for controlling data shown in view.
 */
public class MainController implements IWeather {
    private TextView mCurrentWeather;
    private ImageView mCurrentWeatherImage;

    private TextView mForecastTime1;
    private TextView mForecastTime2;
    private TextView mForecastTime3;

    private TextView mForecastTemp1;
    private TextView mForecastTemp2;
    private TextView mForecastTemp3;

    private ImageView mForecastImage1;
    private ImageView mForecastImage2;
    private ImageView mForecastImage3;

    public MainController() {
    }

    public void initForeCast(WeatherForecast forecast) {
        if (forecast != null) {
            List<DayForecast> forecastList = forecast.getDayForecasts();
            DayForecast time1 = forecastList.get(1);
            DayForecast time2 = forecastList.get(2);
            DayForecast time3 = forecastList.get(3);

            mForecastTime1.setText(trimText(time1));
            mForecastTime2.setText(trimText(time2));
            mForecastTime3.setText(trimText(time3));

            mForecastTemp1.setText(getText(time1));
            mForecastTemp2.setText(getText(time2));
            mForecastTemp3.setText(getText(time3));

            mForecastImage1.setImageBitmap(getImage(time1));
            mForecastImage2.setImageBitmap(getImage(time2));
            mForecastImage3.setImageBitmap(getImage(time3));
        }
    }

    public void initCurrentWeather(Position position) {
        if (position != null) {
            byte[] iconData = position.getWeather().getIconData();
            Bitmap img = BitmapFactory.decodeByteArray(iconData, 0, iconData.length);
            mCurrentWeatherImage.setImageBitmap(img);

            mCurrentWeather.setText(position.getCity() + "\n"
                    + position.getWeather().getDescription() + "\n"
                    + (int) position.getWeather().getTemperature().getTemp() + " °C" + "\n"
            );
        }
    }

    @Override
    public void setTextViews(List<TextView> textViews) {
        mCurrentWeather = textViews.get(0);

        mForecastTime1 = textViews.get(1);
        mForecastTime2 = textViews.get(2);
        mForecastTime3 = textViews.get(3);

        mForecastTemp1 = textViews.get(4);
        mForecastTemp2 = textViews.get(5);
        mForecastTemp3 = textViews.get(6);
    }

    @Override
    public void setImageViews(List<ImageView> imageViews) {
        mCurrentWeatherImage = imageViews.get(0);

        mForecastImage1 = imageViews.get(1);
        mForecastImage2 = imageViews.get(2);
        mForecastImage3 = imageViews.get(3);
    }

    private String trimText(DayForecast time) {
        String tmp = time.getDate();
        return "Kl " + tmp.substring(10, tmp.length() - 3);
    }

    private String getText(DayForecast time) {
        return String.valueOf((int) time.getWeather().getTemperature().getTemp() + " °C");
    }

    private Bitmap getImage(DayForecast forecast) {
        byte[] iconData = forecast.getWeather().getIconData();
        return BitmapFactory.decodeByteArray(iconData, 0, iconData.length);
    }
}