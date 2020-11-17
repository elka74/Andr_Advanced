package com.example.andr_advanced;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;

import com.example.andr_advanced.model.WeatherRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static com.example.andr_advanced.BuildConfig.WEATHER_API_KEY;

public class Trans {


    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat=55.75&lon=37.62&appid=";
    //private static final String WEATHER_API_KEY = null;
    private EditText city;
    private EditText temperature;
    private EditText pressure;
    private EditText humidity;
    private EditText windSpeed;
    Home_fragment fragment = new Home_fragment();
    Dop_fragment frg = new Dop_fragment();

    public void init() {
        //fragment.getFragmentManager().findFragmentById(R.id.frg_home);
        city = ((EditText) fragment.getView().findViewById(R.id.tx_mos));
        temperature = ((EditText) fragment.getView().findViewById(R.id.textView));
        pressure = ((EditText) fragment.getView().findViewById(R.id.textView7));
        humidity = ((EditText) fragment.getView().findViewById(R.id.textView5));
        windSpeed = ((EditText) fragment.getView().findViewById(R.id.textView3));
       // frg.getFragmentManager().findFragmentById(R.id.dop);
        Button refresh = ((Button) frg.getView().findViewById(R.id.refresh));
        refresh.setOnClickListener(clickListener);
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                        ((EditText) frg.getView().findViewById(R.id.input)).getText(),
                        BuildConfig.WEATHER_API_KEY);
                final URL uri = new URL(url);
                final Handler handler = new Handler(); // Запоминаем основной поток
                new Thread(() -> {
                    HttpsURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpsURLConnection) uri.openConnection();
                        urlConnection.setRequestMethod("GET"); // установка метода получения данных -GET
                        urlConnection.setReadTimeout(10000); // установка таймаута - 10 000 миллисекунд
                        BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream())); // читаем  данные в поток
                        String result = getLines(in);
                        // преобразование данных запроса в модель
                        Gson gson = new Gson();
                        final WeatherRequest weatherRequest = gson.fromJson(result, WeatherRequest.class);
                        // Возвращаемся к основному потоку
                        handler.post(() -> displayWeather(weatherRequest));
                    } catch (Exception e) {
                        Log.e(TAG, "Fail connection", e);
                        e.printStackTrace();
                    } finally {
                        if (null != urlConnection) {
                            urlConnection.disconnect();
                        }
                    }
                }).start();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Fail URI", e);
                e.printStackTrace();
            }
        }


        private String getLines(BufferedReader in) {
            return in.lines().collect(Collectors.joining("\n"));
        }

       @SuppressLint("DefaultLocale")
        private void displayWeather(WeatherRequest weatherRequest) {
            city.setText(weatherRequest.getName());
            temperature.setText(String.format("%f2", weatherRequest.getMain().getTemp()));
            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
            windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
        }
    };
}


