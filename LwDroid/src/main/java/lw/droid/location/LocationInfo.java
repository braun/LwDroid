package lw.droid.location;

import java.util.ArrayList;
import java.util.List;

import android.location.Location;

public class LocationInfo {
	String status;
	List<LocationResult> results = new ArrayList<LocationResult>();
	
	public boolean hasResult()
	{
		return results != null && results.size() > 0;
	}
	
	public boolean hasMoreResults()
	{
		return results != null && results.size() > 1;
	}
	
	public List<LocationResult> getResults()
	{
		return results;
	}
	
	public LocationResult firstResult()
	{
		return results.get(0);
	}
	public static class LocationResult
	{
		
		public boolean isLocality()
		{
			return getTypes().contains("locality");
		}
		/**
		 * @return the address_components
		 */
		public List<AddressComponent> getAddressComponents() {
			return address_components;
		}
		/**
		 * @return the formatted_address
		 */
		public String getFormattedAddress() {
			return formatted_address;
		}
		/**
		 * @return the types
		 */
		public List<String> getTypes() {
			return types;
		}
		/**
		 * @return the geometry
		 */
		public Geometry getGeometry() {
			return geometry;
		}
		
		List<AddressComponent> address_components = new ArrayList<LocationInfo.AddressComponent>();
		String formatted_address;
		List<String> types = new ArrayList<String>();
		Geometry geometry;
		
		public String getName()
		{
			return getAddressComponents().get(0).short_name;
		}
		
		public String getLocator()
		{
			StringBuilder bld = new StringBuilder();
			for(int i = 1; i < getAddressComponents().size(); i++)
			{
				AddressComponent c = getAddressComponents().get(i);
				if(bld.length() > 0)
					bld.append(", ");
				String app = c.short_name.replace("District", "").replace("Region", "");
				
				bld.append(app);
			}
			return bld.toString();
		}
	}
	
	public static class AddressComponent
	{
		String long_name;
		String short_name;
		List<String> types = new ArrayList<String>();
	}
	
	public static class Geometry
	{ 
		GeoLocation location;
		String location_type;
		/**
		 * @return the location
		 */
		public GeoLocation getLocation() {
			return location;
		}
		/**
		 * @param location the location to set
		 */
		public void setLocation(GeoLocation location) {
			this.location = location;
		}
		/**
		 * @return the location_type
		 */
		public String getLocation_type() {
			return location_type;
		}
		/**
		 * @param location_type the location_type to set
		 */
		public void setLocation_type(String location_type) {
			this.location_type = location_type;
		}
	}
	
	public static class GeoLocation
	{
		double lat;
		double lng;
		/**
		 * @return the lat
		 */
		public double getLat() {
			return lat;
		}
		/**
		 * @return the lng
		 */
		public double getLng() {
			return lng;
		}
		
		public Location getLocation()
		{
			Location rv = new Location("LocationHelper");
			rv.setLatitude(lat);
			rv.setLongitude(lng);
			return rv;
		}
	}
}
