package com.example.ogureje.testweather.model;

public class CachedWeather {
  private String locationName;
  private Integer humidity;
  private Rain rain;
  private Double temp;
  private Wind wind;
  private String icon;
  private String title;
  private String description;

  public String getLocationName() {
	return locationName;
  }

  public void setLocationName(String locationName) {
	this.locationName = locationName;
  }

  public Integer getHumidity() {
	return humidity;
  }

  public void setHumidity(Integer humidity) {
	this.humidity = humidity;
  }

  public Rain getRain() {
	return rain;
  }

  public void setRain(Rain rain) {
	this.rain = rain;
  }

  public Double getTemp() {
	return temp;
  }

  public void setTemp(Double temp) {
	this.temp = temp;
  }

  public Wind getWind() {
	return wind;
  }

  public void setWind(Wind wind) {
	this.wind = wind;
  }

  public String getIcon() {
	return icon;
  }

  public void setIcon(String icon) {
	this.icon = icon;
  }

  public String getTitle() {
	return title;
  }

  public void setTitle(String title) {
	this.title = title;
  }

  public String getDescription() {
	return description;
  }

  public void setDescription(String description) {
	this.description = description;
  }
}
