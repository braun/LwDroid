package lw.droid.forms.settings;

import java.util.ArrayList;
import java.util.List;

import lw.droid.R;
import lw.droid.commons.Helper;
import lw.droid.forms.ActivityFinishedCallback;
import lw.droid.forms.LwActivity;
import lw.droid.forms.MessageBox;
import lw.droid.forms.settings.model.MetaSettings;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

@EActivity  
public class SettingsActivity extends LwActivity<SettingsActivityModel> {

	SettingsActivityModel model;

	ScrollView scrollView1;
	LinearLayout settingsContainer;

	void bindViews(View rootView) {
		scrollView1 = (ScrollView)rootView.findViewById(R.id.scrollView1);
		settingsContainer = (LinearLayout)rootView.findViewById(R.id.settingsContainer);
	}


//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		getMenuInflater().inflate(R.menu.activity_afl_config, menu);
//		return true;
//	}

//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		try {
//			switch (item.getItemId()) {
//			case android.R.id.home:
//				NavUtils.navigateUpFromSameTask(this);
//				return true;
//			case R.id.item_admin:
//				setAdminMode();
//				return true;
//			}
//		} catch (Throwable th) {
//			Logger.e(th);
//		}
//		return super.onOptionsItemSelected(item);
//	}

//	private void setAdminMode() {
//		MessageBox.input(this, "Heslo:", new InputCallback() {
//
//			@Override
//			public void onInput(String value) {
//				try {
//					String pwd = AflConfig.getInstance().getEntry(
//							"SETTINGS_PIN", "3971");
//					boolean enabled = value.equals(pwd);
//					enableEdits(enabled);
//				} catch (Throwable th) {
//					Logger.e(th);
//				}
//
//			}
//
//			@Override
//			public void onCancel() {
//				// TODO Auto-generated method stub
//
//			}
//		}, true);
//	}

	protected void enableEdits(boolean enabled) {
		MetaSettings ms = model.getEditors();
		ms.enableViews(enabled, SettingsActivity.this.settingsContainer);

	}

	



	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == RESULT_OK) {
			for (PropertyView v : mPviews)
				v.handleActivityResult(requestCode, data);
		}
		if (resultCode == RESULT_CANCELED) {
			// Write your code if there's no result
		}

	}// onActivityResult

	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.settings_activityl);
		super.onCreate(savedInstanceState);
		
		init();
	}


	void init() {
		model = getModel();
		bindViews(getWindow().getDecorView());
		MetaSettings ms = model.getEditors();
		ms.installViews(this, this.settingsContainer);
	enableEdits(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		try {
			for (PropertyView v : mPviews)
				v.onDestroy();

		} catch (Throwable th) {
			Log.e("ActivitySettings.onDestroy",th.getLocalizedMessage(),th);
		}
		super.onDestroy();
	}

	public static void run(Context ctx, SettingsActivityModel model, final Runnable runnable) {

		LwActivity.runActivity(ctx,SettingsActivity_.class,model,
				new ActivityFinishedCallback() {
					
					@Override
					public <E> void finished(LwActivity activity, E model) {
						if(runnable != null)
							runnable.run();
						
					}
				});
	}

	

	List<PropertyView> mPviews = new ArrayList<PropertyView>();

	public void AddPropertyView(PropertyView v) {
		mPviews.add(v);

	}
}
