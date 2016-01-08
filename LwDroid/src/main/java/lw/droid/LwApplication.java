package lw.droid;
import java.util.HashMap;

import lw.droid.forms.ActivityFinishedCallback;
import lw.droid.forms.LwActivity;
import android.R;
import android.app.AlertDialog.Builder;
import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.view.ContextThemeWrapper;

public class LwApplication extends Application {

	static LwApplication lwInstance;
	
	/**
	 * informations concerning runing activities are kept here
	 */
	HashMap<String,ActivityRunRecord> formModels = new HashMap<String, ActivityRunRecord>();
	
	public static LwApplication getLwInstance() {
		return lwInstance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		lwInstance = this;
	}

	int style;

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
	}
	
	/**
	 * get data of actually running activity with identifier formKey
	 * @param formKey identifier of activity (its class name currently)
	 * @return model data if exists null otherwise
	 */
	@SuppressWarnings("unchecked")
	public <M> M getModel(String formKey) {
		// TODO Auto-generated method stub
		if(!this.formModels.containsKey(formKey))
			return null;
		return (M) this.formModels.get(formKey).getModel();
	}
	
	/**
	 * get callback to be called on activity finish {@link ActivityFinishedCallback}
	 * @param formKey  identifier of activity (its class name currently)
	 * @return the callback or null if not set or activity with specified formKey was not found.
	 */
	public ActivityFinishedCallback getFinishedCallback(String formKey) {
		// TODO Auto-generated method stub
		if(!this.formModels.containsKey(formKey))
			return null;
		return this.formModels.get(formKey).getFinishedCallback();
	}

	/**
	 * called by{@link LwActivity.runActivity} to store information needed to
	 * keep state and manage life cycle event of activity
	 * @param formKey identifier of activity (its class name currently)
	 * @param model arbitrary data of activity
	 * @param callback possible callback to be invoked on activiy finish
	 */
	public void keepModel(String formKey, Object model,ActivityFinishedCallback callback) {
		this.formModels.remove(formKey);
		this.formModels.put(formKey,new ActivityRunRecord(model, callback));
		
	}

	/**
	 * remove the ActivityRunRecord from Map, {@link LwActivity} calls this on its finish method
	 * @param formKey identifier of activity (its class name currently)
	 */
	public void removeModel(String formKey)
	{
		this.formModels.remove(formKey);
	}
	
	/**
	 * Auxiliary class used by LwApplication to manage informations concerning running activities
	 * @author Braun
	 *
	 */
	static class ActivityRunRecord
	{
		Object model;
		ActivityFinishedCallback finishedCallback;
		
		public ActivityRunRecord(Object model,
				ActivityFinishedCallback finishedCallback) {
			super();
			this.model = model;
			this.finishedCallback = finishedCallback;
		}

		/**
		 * Data of activity
		 * @return the model
		 */
		public Object getModel() {
			return model;
		}

		/**
		 * Finished callback of activity, {@link LwActivity.finish}
		 * @return the finishedCallback
		 */
		public ActivityFinishedCallback getFinishedCallback() {
			return finishedCallback;
		}
	
		
	
		
	}

	public Builder createDialogBuilder(Context ctx) {
		if(getStyle() == 0)
			return  new AlertDialog.Builder(ctx);
		else
			return  new AlertDialog.Builder(new ContextThemeWrapper(ctx, getStyle()));
	}
}
