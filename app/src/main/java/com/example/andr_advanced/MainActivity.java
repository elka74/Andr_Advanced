package com.example.andr_advanced;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.example.andr_advanced.model.WeatherRequest;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

import static com.example.andr_advanced.BuildConfig.WEATHER_API_KEY;
import static com.example.andr_advanced.Home_fragment.dialogListener;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "WEATHER";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=london&appid=f370763538b29033cdb879554029b1b5";
    private static final String WEATHER_API_KEY = "f370763538b29033cdb879554029b1b5";
    private EditText city;
    private EditText temp;
    private EditText pressure;
    private EditText humidity;
    private EditText windSpeed;
    Dop_fragment frg;
    Home_fragment fragment;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        Home_fragment fragment = new Home_fragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frg_home, fragment)
                .commit();
        city = (EditText) findViewById(R.id.tx_mos);
        temp = (EditText) findViewById(R.id.textView);
        windSpeed = (EditText) findViewById(R.id.textView3);
        humidity = (EditText) findViewById(R.id.textView5);
        pressure = (EditText) findViewById(R.id.textView7);
        Button btn = (Button) findViewById(R.id.city);
        btn.setOnClickListener(clickListener);

    }


    View.OnClickListener clickListener = new View.OnClickListener() {
        private WeatherRequest weatherRequest;

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

                            @Override
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
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {

                                            displayWeather(weatherRequest);
                                        }
                                    });

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

        @SuppressLint("DefaultLocale")
        private void displayWeather(WeatherRequest weatherRequest) {
            this.weatherRequest = weatherRequest;
            city.setText(weatherRequest.getName());
            temp.setText(String.format("%f2", weatherRequest.getMain().getTemp()));
            pressure.setText(String.format("%d", weatherRequest.getMain().getPressure()));
            humidity.setText(String.format("%d", weatherRequest.getMain().getHumidity()));
            windSpeed.setText(String.format("%d", weatherRequest.getWind().getSpeed()));
        }
    };


    private Toolbar initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;

    }

    private void initDrawer(Toolbar toolbar) {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        final SearchView searchText = (SearchView) search.getActionView();
        searchText.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Snackbar.make(searchText, query, Snackbar.LENGTH_LONG).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_avatar) {
            return true;

        } else if (id == R.id.naw_share) {
            return true;

        } else if (id == R.id.about_developers) {
            return true;

        } else if (id == R.id.settings) {
            return true;

        } else if (id == R.id.feedback) {
            return true;

        } else if (id == R.id.choose_city_menu) {
            return true;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}

