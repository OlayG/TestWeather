package com.example.ogureje.testweather;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ogureje.testweather.model.CachedWeather;
import com.example.ogureje.testweather.model.CurrentWeather;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener, GetWeather.DownloadCompleteListener, WeatherAdapter.WeatherAdapterListener {
  public static final String SHARED_PREF_NAME = "saved_location";
  public static final String SHARED_PREF_KEY = "latlong";
  FloatingActionButton fab;
  RecyclerView recyclerView;
  private CurrentWeather weather;
  private SharedPreferences preferences;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_home_screen);
	initViews();
	new GetWeather(this, new ProgressDialog(this)).execute("http://api.openweathermap.org/data/2.5/weather?lat=0&lon=0&appid=c6e381d8c7ff98f0fee43775817cf6ad&units=metric");
  }

  private void initViews() {
	// Toolbar Init
	Toolbar toolbar = findViewById(R.id.toolbar);
	setSupportActionBar(toolbar);
	// Floating Action Button Init
	fab = findViewById(R.id.fab);
	fab.setOnClickListener(this);
	// RecyclerView Init
	recyclerView = findViewById(R.id.rv);
	recyclerView.setHasFixedSize(true);
	recyclerView.setLayoutManager(new LinearLayoutManager(this));
	recyclerView.setAdapter(null);

	preferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
	super.onCreateOptionsMenu(menu);
	getMenuInflater().inflate(R.menu.toolbar_menu, menu);
	return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
	switch (item.getItemId()) {
	  case R.id.edit:
		Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show();
		return true;
	  case R.id.search:
		Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
		return true;
	}
	return false;
  }

  @Override
  public void onClick(View view) {
	switch (view.getId()) {
	  case R.id.fab:
		Toast.makeText(this, "FAB", Toast.LENGTH_SHORT).show();
		break;
	}
  }

  @Override
  public void downloadComplete(final String jsonString) {
	runOnUiThread(new Runnable() {
	  @Override
	  public void run() {
		weather = new Gson().fromJson(jsonString, CurrentWeather.class);
		if (weather != null) {
		  parseWeatherData();
		}
	  }
	});
  }

  private void parseWeatherData() {
	CachedWeather cachedWeather = new CachedWeather();
	cachedWeather.setHumidity(weather.getMain().getHumidity());
	cachedWeather.setLocationName(weather.getName());
	cachedWeather.setTemp(weather.getMain().getTemp());
	cachedWeather.setWind(weather.getWind());
	cachedWeather.setIcon(weather.getWeather().get(0).getIcon());
	cachedWeather.setTitle(weather.getWeather().get(0).getMain());
	cachedWeather.setDescription(weather.getWeather().get(0).getDescription());
	HashMap<String, CachedWeather> savedLocations = new HashMap<>();

	if (preferences.contains(SHARED_PREF_KEY)) {
		String jsonString = preferences.getString(SHARED_PREF_KEY, null);
		if (jsonString == null) {
		  saveWeather(cachedWeather, savedLocations);
		} else {
		  Type type = new TypeToken<Map<String, CachedWeather>>() {}.getType();
		  savedLocations = new Gson().fromJson(jsonString, type);
		  saveWeather(cachedWeather, savedLocations);
		}
	} else {
	  saveWeather(cachedWeather, savedLocations);
	}
  }

  private void saveWeather(CachedWeather cachedWeather, HashMap<String, CachedWeather> savedLocations) {
	savedLocations.put("latlong", cachedWeather);
	preferences.edit().putString(SHARED_PREF_KEY, new Gson().toJson(savedLocations)).apply();
  }

  @Override
  public void onLocationClicked(CachedWeather weather) {

  }

}


// Icon    https://openweathermap.org/weather-conditions
// Filter https://openweathermap.org/weather-conditions