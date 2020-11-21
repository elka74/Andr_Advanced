package com.example.andr_advanced;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.andr_advanced.model.WeatherRequest;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;


public class Home_fragment extends Fragment {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=f370763538b29033cdb879554029b1b5";
    private EditText city;
    private EditText temp;
    private EditText pressure;
    private EditText humidity;
    private EditText windSpeed;
    Button refresh ;
    public static OnDialogListener dialogListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View frgm = inflater.inflate(R.layout.fragment_home_fragment, container, false);
        TextView text = frgm.findViewById(R.id.text);
        ImageView image = frgm.findViewById(R.id.image);
        TableLayout dop_fun = frgm.findViewById(R.id.dop_fun);
        TextView textView1 = frgm.findViewById(R.id.textView2);
        TextView textView3 = frgm.findViewById(R.id.textView4);
        TextView textView5 = frgm.findViewById(R.id.textView6);

        EditText windSpeed = frgm.findViewById(R.id.textView3);
        EditText humidity = frgm.findViewById(R.id.textView5);
        EditText temp = frgm.findViewById(R.id.textView);
        EditText pressure = frgm.findViewById(R.id.textView7);
        EditText city = frgm.findViewById(R.id.tx_mos);
        Button refresh = frgm.findViewById(R.id.city);
        EditText editText = frgm.findViewById(R.id.input );

        refresh.setOnClickListener(clickListener);

        return frgm;

    }


    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            try {
                //final String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s",
                //(editText = (EditText) findViewById(R.id.input)).getText(),
                // BuildConfig.WEATHER_API_KEY);
                final URL uri = new URL(WEATHER_URL);
                final Handler handler = new Handler(); // Запоминаем основной поток
                new Thread(
                        new Runnable() {

                            //@Override
                            public void run() {
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
                            }
                        }
                ).start();
            } catch (MalformedURLException e) {
                Log.e(TAG, "Fail URI", e);
                e.printStackTrace();
            }
        }

        private String getLines(BufferedReader in) {

            return in.lines().collect(Collectors.joining("\n"));

        }
        private void displayWeather(WeatherRequest weatherRequest) {
            city.setText(weatherRequest.getName());
            temp.setText(String.format("%f2", weatherRequest.getMain().getTemp()));
            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
            windSpeed.setText(String.format("%f", weatherRequest.getWind().getSpeed()));
            Log.d ("API", toString());
        }
    };



}






