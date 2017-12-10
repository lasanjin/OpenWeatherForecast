package lasanjin.openweatherforecast.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import lasanjin.openweatherforecast.R;

public class WeatherFragment extends Fragment {
    private IController mController;

    public WeatherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_weather_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setWeatherTextViews(view);
        setWeatherImageViews(view);
    }

    private void setWeatherTextViews(View view) {
        List<TextView> views = new ArrayList<>();

        TextView cw = view.findViewById(R.id.textview_current_weather);

        TextView ftime1 = view.findViewById(R.id.textview_forecast_time1);
        TextView ftime2 = view.findViewById(R.id.textview_forecast_time2);
        TextView ftime3 = view.findViewById(R.id.textview_forecast_time3);

        TextView ftemp1 = view.findViewById(R.id.textview_forecast_temp1);
        TextView ftemp2 = view.findViewById(R.id.textview_forecast_temp2);
        TextView ftemp3 = view.findViewById(R.id.textview_forecast_temp3);

        views.add(cw);

        views.add(ftime1);
        views.add(ftime2);
        views.add(ftime3);

        views.add(ftemp1);
        views.add(ftemp2);
        views.add(ftemp3);

        if (isController()) {
            mController.setTextViews(views);
        }
    }

    private void setWeatherImageViews(View view) {
        List<ImageView> views = new ArrayList<>();

        ImageView icw = view.findViewById(R.id.imageview_current_weather);

        ImageView if1 = view.findViewById(R.id.imageview_forecast1);
        ImageView if2 = view.findViewById(R.id.imageview_forecast2);
        ImageView if3 = view.findViewById(R.id.imageview_forecast3);

        views.add(icw);

        views.add(if1);
        views.add(if2);
        views.add(if3);

        if (isController()) {
            mController.setImageViews(views);
        }
    }

    private boolean isController() {
        return mController != null;
    }

    public void setIController(IController controller) {
        mController = controller;
    }
}
