package lw.droid.location;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import lw.droid.location.LocationHelper.ResultHandler;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.google.gson.Gson;

import android.location.Location;
import android.util.Log;

public class WeatherHelper {

	
	public static void postGetLocationForecast(final Location location,final ForecastHandler handler)
	{
		LocationHelper.getLhThread().postJob(new Runnable()
		{

			@Override
			public void run() {
				try {
					OpenWeatherForecast info = getLocationForecast(location);
					postHandleResult(handler,info);
				} catch (Throwable e) {
					Log.e("WeatherHelper.postGetLocationForecast.postJob.run", e.getLocalizedMessage(), e);
					postHandleResult(handler,null);
				}
				
			}
			
		});
	}
	
	public static OpenWeatherForecast getLocationForecast(Location loc) {

		String urlquery = "http://openweathermap.org/data/2.5/forecast?lat="+loc.getLatitude()+"&lon="+ loc.getLongitude() +"&units=metric&APPID=98dcaaf6ef5e8672f77eddad67f4a3b8";

		HttpGet httpGet = new HttpGet(urlquery);
		
		HttpParams params = new BasicHttpParams();
		
		HttpConnectionParams.setConnectionTimeout(params, 30000);
		HttpConnectionParams.setSoTimeout(params, 30000);
		HttpClient client = new DefaultHttpClient(params);
		HttpResponse response;
		StringBuilder stringBuilder = new StringBuilder();

		try {
			response = client.execute(httpGet);
			HttpEntity entity = response.getEntity();

			InputStream stream = entity.getContent();
			try {
				InputStreamReader r = new InputStreamReader(stream, "UTF-8");
				try {
					BufferedReader rd = new BufferedReader(r);
					try {
						int b;
						while ((b = rd.read()) != -1) {
							stringBuilder.append((char) b);
						}
					} finally {
						rd.close();
					}
				} finally {
					r.close();
				}
			} finally {
				stream.close();
			}

		} catch (Throwable th) {
			Log.e("WeatherHelper.getLocationForecast",th.getMessage(),th);
			return null;
		} 

		 return parseLocationForecast(stringBuilder);
	}

	private static OpenWeatherForecast parseLocationForecast(StringBuilder stringBuilder) {
		Gson gson = new Gson();
		   String str = stringBuilder.toString();
		   OpenWeatherForecast rv =  gson.fromJson(str, OpenWeatherForecast.class);
		   return rv;
	}
	
	protected static void postHandleResult(final ForecastHandler handler,final OpenWeatherForecast info) {
		if(handler != null)
			LocationHelper.getResultHandler().post(new Runnable() {
				
				@Override
				public void run() {
					 try {
						handler.handleResult(info);
					} catch (Throwable e) {
						Log.e("WeatherHelper.postHandleResult.run", e.getLocalizedMessage(), e);
					}
					
				}
			});
			
		
	}
	public interface ForecastHandler
	{
		void handleResult(OpenWeatherForecast info);
	}
}
