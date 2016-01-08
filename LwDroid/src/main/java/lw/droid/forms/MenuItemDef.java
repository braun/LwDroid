package lw.droid.forms;

import android.content.Context;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;

public abstract class MenuItemDef  {

	String label;
	Runnable onFinished;
	int iconRsc = 0;
	
	boolean enabled = true;
	
	public Runnable getOnFinished() {
		return onFinished;
	}


	public MenuItemDef setOnFinished(Runnable onFinished) {
		this.onFinished = onFinished;
		return this;
	}


	public MenuItemDef(String label)
	{
		this(label, 0);
	}
	/**
	 * @return the iconRsc
	 */
	public int getIconRsc() {
		return iconRsc;
	}


	public MenuItemDef(String label,int iconRsc)
	{
		this.label = label;
		this.iconRsc = iconRsc;
	}

	public abstract boolean execute(MenuItem item,Context ctx) ;

	public String getLabel() {
		// TODO Auto-generated method stub
		return label;
	}


	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return enabled;
	}


	/**
	 * @param enables/disables this menu item
	 * used by LwActivity to set enabled flag on MenuItem
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
