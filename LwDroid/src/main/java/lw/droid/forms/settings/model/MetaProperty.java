package lw.droid.forms.settings.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lw.droid.forms.settings.PropertyView;
import lw.droid.forms.settings.PropertyViewFactory;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import android.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MetaProperty extends MetaConfigBase {

	String mName;
	String mDisplay;
	PropertyViewFactory mView;
	OptionSource mOptions = null;
	
	
	public MetaProperty(SharedPreferences prefs,String name,String display,PropertyViewFactory view,OptionSource options)
	{
		mName = name;
		mDisplay = display;
		mView = view;
		mOptions = options;
		this.preferences = prefs;
	}
	/**
	 * @param mOptions the mOptions to set
	 */
	public void setOptions(OptionSource mOptions) {
		this.mOptions = mOptions;
	}
	SharedPreferences preferences;
	public SharedPreferences getPreferences() {
		return preferences;
	}

	public void setPreferences(SharedPreferences preferences) {
		this.preferences = preferences;
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * @return the mDisplay
	 */
	public String getDisplay() {
		return mDisplay;
	}
	
	
	/**
	 * @return the mOptions
	 */ 
	public List<Option> getOptions() {
		ArrayList<Option> rv = new ArrayList<Option>();
		rv.add(new OptionImpl("", null));
		rv.addAll(mOptions.getOptions());
		return rv;
	}
	public PropertyView getView(Context ctx) {
		
		PropertyView v = mView.createView(ctx,this);
		v.Bind();
		return v;
		
	}
	
	public void setPropertyValue(String value)
	{
		this.preferences.edit().putString(getName(),value).commit();
	}
	
	public String getPropertyValue()
	{
		return this.preferences.getString(getName(),"");
	}
	
}
