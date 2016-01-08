package lw.droid.location;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Deserialized Forecast from www.openweathermap.org
 * @author Braun
 *
 */
public class OpenWeatherForecast {
	
	int cod;
	double message;
	
	City city;
	
	int cnt;
	List<ForecastItem> list = new ArrayList<OpenWeatherForecast.ForecastItem>();;
	
	/**
	 * @return the message
	 */
	public double getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(double message) {
		this.message = message;
	}
	/**
	 * @return the cod
	 */
	public int getCod() {
		return cod;
	}
	/**
	 * @return the city
	 */
	public City getCity() {
		return city;
	}
	/**
	 * @return the cnt
	 */
	public int getCnt() {
		return cnt;
	}
	/**
	 * @return the list
	 */
	public List<ForecastItem> getList() {
		return list;
	}

	public static class ForecastItem
	{
		long dt;
		ForecastItemMain main;
		List<ForecastItemWeather> weather = new ArrayList<ForecastItemWeather>();
		ForecastItemClouds clouds;
		ForecastItemWind wind;
		ForecastItemRain rain;
		
		public Date getDate()
		{
			Date rv = new Date(getDt()*1000);			
			return rv;
		}
		/**
		 * @return the dt
		 */
		public long getDt() {
			return dt;
		}
		/**
		 * @return the main
		 */
		public ForecastItemMain getMain() {
			return main;
		}
		/**
		 * @return the weather
		 */
		public List<ForecastItemWeather> getWeather() {
			return weather;
		}
		/**
		 * @return the clouds
		 */
		public ForecastItemClouds getClouds() {
			return clouds;
		}
		/**
		 * @return the wind
		 */
		public ForecastItemWind getWind() {
			return wind;
		}
		/**
		 * @return the rain
		 */
		public ForecastItemRain getRain() {
			return rain;
		}
		
		   
	}
	public static class ForecastItemClouds
	{
		int all;

		/**
		 * @return the all
		 */
		public int getAll() {
			return all;
		}
		
		
	}
	public static class ForecastItemWind
	{
		double speed;
		double deg;
		
		/**
		 * @return the speed
		 */
		public double getSpeed() {
			return speed;
		}
		/**
		 * @return the deg
		 */
		public double getDeg() {
			return deg;
		}
		
		
	}
	public static class ForecastItemRain
	{
		 @SerializedName("3h")
		double amount3h;

		/**
		 * @return the amount3h
		 */
		public double getAmount3h() {
			return amount3h;
		}

	}
	public static class ForecastItemWeather
	{
		long id;
		String main;
		String description;
		String icon;
		
		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}
		/**
		 * @return the main
		 */
		public String getMain() {
			return main;
		}
		/**
		 * @return the description
		 */
		public String getDescription() {
			return description;
		}
		/**
		 * @return the icon
		 */
		public String getIcon() {
			return icon;
		}
		
		
	}
	public static class ForecastItemMain
	{
		double temp;
		double temp_min;
		double temp_max;
		double pressure;
		double sea_level;
		double grnd_level;
		double humidity;
		double temp_kf;
		
		/**
		 * @return the temp
		 */
		public double getTemp() {
			return temp;
		}
		/**
		 * @return the temp_min
		 */
		public double getTempMin() {
			return temp_min;
		}
		/**
		 * @return the temp_max
		 */
		public double getTempMax() {
			return temp_max;
		}
		/**
		 * @return the pressure
		 */
		public double getPressure() {
			return pressure;
		}
		/**
		 * @return the sea_level
		 */
		public double getSeaLevel() {
			return sea_level;
		}
		/**
		 * @return the grnd_level
		 */
		public double getGrndLevel() {
			return grnd_level;
		}
		/**
		 * @return the humidity
		 */
		public double getHumidity() {
			return humidity;
		}
		/**
		 * @return the temp_kf
		 */
		public double getTempKf() {
			return temp_kf;
		}
	}
	
	public static class Coord
	{
		double lat;
		double lon;
		
		/**
		 * @return the lat
		 */
		public double getLat() {
			return lat;
		}
		/**
		 * @return the lon
		 */
		public double getLon() {
			return lon;
		}
		
		
	}
	public static class City
	{
		long id;
		String name;
		String country;
		Coord coord;
		int population;
		
		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}
		/**
		 * @return the coord
		 */
		public Coord getCoord() {
			return coord;
		}
		/**
		 * @return the population
		 */
		public int getPopulation() {
			return population;
		}
	}
	public static class SysInfo
	{
		int type;
		long id;
		double message;
		String country;
		long sunrise;
		long sunset;
		
		/**
		 * @return the type
		 */
		public int getType() {
			return type;
		}
		/**
		 * @return the id
		 */
		public long getId() {
			return id;
		}
		/**
		 * @return the message
		 */
		public double getMessage() {
			return message;
		}
		/**
		 * @return the country
		 */
		public String getCountry() {
			return country;
		}
		/**
		 * @return the sunrise
		 */
		public long getSunrise() {
			return sunrise;
		}
		/**
		 * @return the sunset
		 */
		public long getSunset() {
			return sunset;
		}
		
	}
}
