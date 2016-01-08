package lw.droid.forms.settings.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lw.droid.forms.settings.PropertyView;
import lw.droid.forms.settings.SettingsActivity;

import org.w3c.dom.Node;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;


public class MetaSettings extends MetaConfigBase {

	List<MetaProperty> mSubProperties = new ArrayList<MetaProperty>();
	
	public MetaSettings(MetaProperty ...props)
	{
		mSubProperties = Arrays.asList(props);
	}
	/**
	 * @return the mSubProperties
	 */
	public List<MetaProperty> getSubProperties() {
		return mSubProperties;
	}
	public void installViews(SettingsActivity ctx,LinearLayout container) {
			for(MetaProperty property: getSubProperties())
			{
			
				PropertyView v = property.getView(ctx);
				
				ctx.AddPropertyView(v);
				container.addView((View)v);
				v.setEnabled(false);
				
			}
			
		
	}
	public void enableViews(boolean enable,LinearLayout container) {
		for(int  i = 0; i< container.getChildCount(); i++)
		{
			View v = container.getChildAt(i);
			v.setEnabled(enable);
		
			
		}
		
	
}

}
