package lw.droid.location;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.logging.Logger;

import lw.droid.LwApplication;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LocationHelper {

	static LocationHelperThread lhthread;
	
	static LocationHelperThread getLhThread()
	{
		if(lhthread == null)
			lhthread = LocationHelperThread.createAndStart();
		return lhthread;
	}
	static Handler resultHandler;
	
	static Handler getResultHandler()
	{		
		if(resultHandler == null)
			resultHandler= new Handler(Looper.getMainLooper());
		return resultHandler;
	}
	
	public static void init()
	{
		getLhThread();
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	/**
	 * Gets last known location of device intended for occasional location query
	 * (geotaging)
	 * 
	 * @param ctx
	 *            context ;)
	 * @return last known position or null
	 */
	public static Location getLastKnownLocation(Context ctx) {
		LocationManager lm = (LocationManager) ctx
				.getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		Location l = null;

		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}

		return l;
	}
	
	
	public static void postGetLocationInfoForLastKnownPosition(final ResultHandler handler)
	{
		getLhThread().postJob(new Runnable()
		{

			@Override
			public void run() {
				try {
					Location location = getLastKnownLocation(LwApplication.getLwInstance().getApplicationContext());
					LocationInfo info = getLocationInfo(location);
					postHandleResult(handler,info);
				} catch (Throwable e) {
					Log.e("LocationHelper.postGetLocationFromAddress.postJob.run", e.getLocalizedMessage(), e);
					postHandleResult(handler,null);
				}
				
			}
			
		});
	}

	public static void postGetLocationInfo(final Location location,final ResultHandler handler)
	{
		getLhThread().postJob(new Runnable()
		{

			@Override
			public void run() {
				try {
					LocationInfo info = getLocationInfo(location);
					postHandleResult(handler,info);
				} catch (Throwable e) {
					Log.e("LocationHelper.postGetLocationFromAddress.postJob.run", e.getLocalizedMessage(), e);
					postHandleResult(handler,null);
				}
				
			}
			
		});
	}
	

	public static LocationInfo getLocationInfo(Location loc) {

		if(loc == null)
			return null;
		String urlquery = "http://maps.google.com/maps/api/geocode/json?latlng="
				+ loc.getLatitude() + "," + loc.getLongitude() + "&sensor=true";
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
			Log.e("LocationHelper.getLocationInfo",th.getMessage(),th);
			return null;
		} 

		 return parseLocationInfo(stringBuilder);
	}
	
	public interface ResultHandler
	{
		void handleResult(LocationInfo info);
	}
	public static void postGetLocationFromAddress(final String address,final ResultHandler handler)
	{
		getLhThread().postJob(new Runnable()
		{

			@Override
			public void run() {
				try {
					LocationInfo info = getLocationFromAddress(address);
					postHandleResult(handler, info);
				} catch (Throwable e) {
					Log.e("LocationHelper.postGetLocationFromAddress.postJob.run", e.getLocalizedMessage(), e);
					postHandleResult(handler,null);
				}
				
			}
			
		});
	}
	
	public static LocationInfo getLocationFromAddress(String youraddress) {
	    String uri = "http://maps.google.com/maps/api/geocode/json?address=" +
	                  youraddress + "&sensor=false";
	    HttpGet httpGet = new HttpGet(uri);
	    HttpClient client = new DefaultHttpClient();
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
	    } catch (Exception e) {
	    	Log.e("LocationHelper.getLocationFromAddress.queryGeocoder",e.getMessage(),e);
	    }

	   return parseLocationInfo(stringBuilder);

	}

	private static LocationInfo parseLocationInfo(StringBuilder stringBuilder) {
		Gson gson = new Gson();
		   String str = stringBuilder.toString();
		   LocationInfo rv =  gson.fromJson(str, LocationInfo.class);
		   return rv;
	}
	
	protected static void postHandleResult(final ResultHandler handler,final LocationInfo info) {
		if(handler != null)
			getResultHandler().post(new Runnable() {
				
				@Override
				public void run() {
					 try {
						handler.handleResult(info);
					} catch (Throwable e) {
						Log.e("LocationHelper.postHandleResult.run", e.getLocalizedMessage(), e);
					}
					
				}
			});
			
		
	}
}
