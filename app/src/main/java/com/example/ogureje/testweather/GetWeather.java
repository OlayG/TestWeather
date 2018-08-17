package com.example.ogureje.testweather;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetWeather extends AsyncTask<String, Void, Void> {
  private DownloadCompleteListener listener;
  private ProgressDialog progress;

  public GetWeather(DownloadCompleteListener listener, ProgressDialog progress) {
	this.listener = listener;
	this.progress = progress;
  }

  @Override
  protected void onPreExecute() {
	if (progress != null) {
	  progress.setMessage("Loading");
	  progress.show();
	}
  }

  @Override
  protected Void doInBackground(String... strings) {
	try {
	  // Create URL
	  URL url = new URL(strings[0]);
	  // Create urlConnection
	  HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
	  urlConnection.setRequestMethod("GET");
	  urlConnection.setRequestProperty("Content-Type", "application/json");
	  urlConnection.connect();

	  int status = urlConnection.getResponseCode();

	  switch (status) {
		case 200:
		case 201:
		  BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		  StringBuilder sb = new StringBuilder();
		  String line;
		  while ((line = br.readLine()) != null) {
			sb.append(line + "\n");
		  }
		  br.close();
		  sb.toString();

		  listener.downloadComplete(sb.toString());
	  }

	} catch (MalformedURLException e) {
	  e.printStackTrace();
	} catch (IOException e) {
	  e.printStackTrace();
	}
	return null;
  }

  @Override
  protected void onPostExecute(Void aVoid) {
	if (progress != null && progress.isShowing())
	  progress.dismiss();
  }

  public interface DownloadCompleteListener {
	void downloadComplete(String jsonString);
  }
}

