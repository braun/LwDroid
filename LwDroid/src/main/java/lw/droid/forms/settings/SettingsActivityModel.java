package lw.droid.forms.settings;

import android.content.SharedPreferences;
import lw.droid.forms.LwActivity;
import lw.droid.forms.settings.model.MetaSettings;

/**
 * Data given to SettingsActivity
 * @author Braun
 *
 */
public class SettingsActivityModel extends LwActivity.ModelBase {

	MetaSettings editors;
	
	
	public SettingsActivityModel(MetaSettings editors) {
		super();
		this.editors = editors;
	
	}

	/**
	 * Definition of editors, they will edit the shared preferences
	 * @return
	 */
	public MetaSettings getEditors() {
		return editors;
	}

	
	
	
}
