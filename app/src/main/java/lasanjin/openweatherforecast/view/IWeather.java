package lasanjin.openweatherforecast.view;

import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Interface for communication between view and controller package
 */
public interface IWeather {
    void setTextViews(List<TextView> textViews);
    void setImageViews(List<ImageView> imageViews);
}
