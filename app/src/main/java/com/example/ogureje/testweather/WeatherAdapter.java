package com.example.ogureje.testweather;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.ogureje.testweather.model.CachedWeather;

import java.util.ArrayList;
import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.MyViewHolder> implements Filterable {
  private static final String TAG = "WeatherAdapter";
  private WeatherAdapterListener listener;
  private Context context;
  private List<CachedWeather> weatherList;
  private List<CachedWeather> weatherListFiltered;

  class MyViewHolder extends RecyclerView.ViewHolder {
	TextView latitude, longitude;

	MyViewHolder(View itemView) {
	  super(itemView);
	  latitude = itemView.findViewById(R.id.latitude);
	  longitude = itemView.findViewById(R.id.longitude);
	  itemView.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
		  listener.onLocationClicked(weatherListFiltered.get(getAdapterPosition()));
		}
	  });
	}
  }

  public WeatherAdapter(WeatherAdapterListener listener, Context context) {
	this.listener = listener;
	this.context = context;
  }

  @NonNull
  @Override
  public WeatherAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
	View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_location_weather, parent, false);
	return new MyViewHolder(itemView);
  }

  @Override
  public void onBindViewHolder(@NonNull WeatherAdapter.MyViewHolder holder, int position) {
	holder.latitude.setText("");
	holder.longitude.setText("");
  }

  @Override
  public int getItemCount() {
	return weatherListFiltered.size();
  }

  @Override
  public Filter getFilter() {
	return new Filter() {
	  @Override
	  protected FilterResults performFiltering(CharSequence charSequence) {
		String charString = charSequence.toString();
		if (charString.isEmpty()) {
		  weatherListFiltered = weatherList;
		} else {
		  List<CachedWeather> filteredList = new ArrayList<>();
		  for (CachedWeather weather : weatherList) {

			// name match condition. this might differ depending on your requirement
			// here we are looking for name or phone number match
			if (weather.getLocationName().toLowerCase().contains(charString.toLowerCase())) {
			  filteredList.add(weather);
			}
		  }

		  weatherListFiltered = filteredList;
		}

		FilterResults filterResults = new FilterResults();
		filterResults.values = weatherListFiltered;
		return filterResults;
	  }

	  @Override
	  protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
		weatherListFiltered = (ArrayList<CachedWeather>) filterResults.values;
		notifyDataSetChanged();
	  }
	};
  }

  public interface WeatherAdapterListener {
	void onLocationClicked(CachedWeather weather);
  }
}
