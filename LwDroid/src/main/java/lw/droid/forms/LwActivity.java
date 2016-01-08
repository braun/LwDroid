package lw.droid.forms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lw.droid.LwApplication;
import lw.droid.commons.Helper;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Activity extension used by LwDroid. Supports more straightforward work with
 * menu, saving activity data (model) in LwApplication
 * 
 * @author Braun
 * 
 */
public class LwActivity<T extends LwActivity.ModelBase> extends Activity implements LwForm {

	public LwActivity() {
		// TODO Auto-generated constructor stub
	}

	   T model;
	   
	List<MenuItemDef> menuItems = new ArrayList<MenuItemDef>();

	public void setMenuItems(MenuItemDef... defs) {
		menuItems = Arrays.asList(defs);
	}

	public void setFullScreen()
	{
		   requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);	      
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		for (final MenuItemDef def : menuItems) {
			MenuItem it = menu.add(def.getLabel());
			if(def.getIconRsc() != 0)
			{
				it.setIcon(def.getIconRsc());
				it.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			}
			it.setEnabled(def.isEnabled());
			it.setOnMenuItemClickListener(new OnMenuItemClickListener() {

				@Override
				public boolean onMenuItemClick(MenuItem mit) {
					try {
						return def.execute(mit, LwActivity.this);
					} catch (Throwable th) {
						Log.e("LwActivity", th.getLocalizedMessage(), th);
						MessageBox.ShowError(LwActivity.this,
								th.getLocalizedMessage());
					}
					return false;
				}
			});
		}
		return super.onCreateOptionsMenu(menu);
	}

	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPrepareOptionsMenu(android.view.Menu)
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		int i = 0;
		for (final MenuItemDef def : menuItems) {
			MenuItem it = menu.getItem(i);
			it.setEnabled(def.isEnabled());
			i++;
		}
		return super.onPrepareOptionsMenu(menu);
	}

	public void showDialog(LwDialog<?> dialog) {
		LwDialog.showDialog(this.getFragmentManager(),dialog);
	}
	
	@Override
	public void finish() {

		try {
			try {
				ActivityFinishedCallback cb = LwApplication.getLwInstance()
						.getFinishedCallback(getFormKey());
				if (cb != null)
					cb.finished(this, getModel());
			} catch (Throwable th) {
				Helper.showError(this, "LwActivity.finish", th);
			}
			super.finish();
		} finally {
			LwApplication.getLwInstance().removeModel(getFormKey());
		}
	}

	/**
	 * Activity identifier used by LwApplication to lookup activity data
	 * 
	 * @return
	 */
	public String getFormKey() {
		return this.getClass().getName();
	}

	/**
	 * Base class of all models
	 * @author Braun
	 *
	 */
	public static class ModelBase
	{
	
		  ModelBase parent;
		 
		   
		
		
		/**
		 * @return the parent
		 */
		public ModelBase getParent() {
			return parent;
		}
		/**
		 * @param parent the parent to set
		 */
		public void setParent(ModelBase parent) {
			this.parent = parent;
		}
		
		  
	}


	
	/**
	 * @return the model
	 */
	public T getModel() {
		if(model == null)
		{
			model = LwApplication.getLwInstance().getModel(getFormKey());
		
		
		}	
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(T model) {
		this.model = model;
	
	}
	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		//model.setMe(null);
		LwApplication.getLwInstance().keepModel(getFormKey(), model, null);
	}

	
	/**
	 * Start activity and store its model in LwApplicatiobn
	 * 
	 * @param ctx
	 *            calling context
	 * @param clazz
	 *            class of activity to be started
	 * @param model
	 *            data of activity to be shown/processed
	 * @param finishCallBack
	 *            callback to be executed on finish
	 */
	public static void runActivity(Context ctx,
			Class<? extends LwActivity<?>> clazz, Object model,
			ActivityFinishedCallback finishCallback) {
		Intent intent = new Intent(ctx, clazz);

		// Store data in application
		if (model != null)
			LwApplication.getLwInstance().keepModel(clazz.getName(), model,
					finishCallback);

		ctx.startActivity(intent);

	}


	@Override
	public View getRootView() {
		// TODO Auto-generated method stub
		return getWindow().getDecorView();
	}

	public void dialogDismissedCallback(LwDialog dialog)
	{
		
	}
}
