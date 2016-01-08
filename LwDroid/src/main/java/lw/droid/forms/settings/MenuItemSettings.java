package lw.droid.forms.settings;


import android.content.Context;
import android.content.SharedPreferences;
import android.view.MenuItem;
import lw.droid.R;
import lw.droid.forms.MenuItemDef;
import lw.droid.forms.settings.model.MetaSettings;

public class MenuItemSettings extends MenuItemDef {

	SettingsActivityModel model;
	public MenuItemSettings(String label,MetaSettings model) {
		super(label);
		this.model = new SettingsActivityModel(model) ;
	}
	public MenuItemSettings(Context ctx,MetaSettings model) {
		super(ctx.getString(R.string.settings));
		this.model =  new SettingsActivityModel(model) ;
	}

	@Override
	public boolean execute(MenuItem item, Context ctx) {
		SettingsActivity.run(ctx,model,this.getOnFinished());
		return true;
	}

}
