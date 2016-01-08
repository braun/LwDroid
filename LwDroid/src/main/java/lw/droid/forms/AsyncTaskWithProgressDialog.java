package lw.droid.forms;

import android.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 *  This is handy combination of progress dialog and AsyncTask
 * @author Braun
 *
 * @param <Params>
 * @param <Progress>
 * @param <Result>
 */
public abstract class AsyncTaskWithProgressDialog<Params, Progress, Result> {

	Context ctx;
	AsyncTask<Params, Progress, Result> task;
	ProgressDialog dlg;
	Params[] params;
    
	Throwable catchedException;
	
	public AsyncTaskWithProgressDialog(Context ctx) {
		this.ctx = ctx;

		this.task = new AsyncTask<Params, Progress, Result>() {

			@Override
			protected void onPreExecute() {				
				super.onPreExecute();
				AsyncTaskWithProgressDialog.this.onPreExecute();
			}

			@Override
			protected void onProgressUpdate(Progress... values) {
				super.onProgressUpdate(values);
				AsyncTaskWithProgressDialog.this.onProgressUpdate(values);
			}

			@Override
			protected Result doInBackground(Params... params) {
				Result rv = AsyncTaskWithProgressDialog.this.doInBackground(params);

				return rv;
			}

			@Override
			protected void onPostExecute(Result result) {
				super.onPostExecute(result);
				
				AsyncTaskWithProgressDialog.this.onPostExecute(result);
				AsyncTaskWithProgressDialog.this.dlg.dismiss();
				AsyncTaskWithProgressDialog.this.dlg = null;
			}

		};

	}

	/**
	 * Code to be executed on background should be placed here
	 * @param params
	 * @return return value of task
	 */
	public abstract Result doInBackground(Params... params);

	/**
	 * Code to be executed upon background work completion
	 * @param result value returned from {@link doInBackground}
	 */
	public void onPostExecute(Result result) {
	}

	/**
	 * Place code to be executed on UiThread right before background work
	 */
	protected void onPreExecute() {
	}

	/**
	 * This method is called on UiThread as result of task progress update
	 * @param values
	 */
	protected void onProgressUpdate(Progress... values) {
	}

	/**
	 * Starts the async task execution
	 * @param p param value to be propagated to {@link doInBackground} 
	 */
	public void execute(Params... p) {
		this.dlg = new ProgressDialog(ctx);
		this.dlg.setMessage(ctx.getString(lw.droid.R.string.working));
		this.dlg.show();
		this.params = p;
		this.task.execute(p);
	}
	
	/**
	 * return in which this isntance was created
	 * @return
	 */
	public Context getContext()
	{
		return this.ctx;
	}
	
	/**
	 * The code executed in {@link doInBackground} 
	 * should called this method when a Throwable is catched
	 * and the code in  {@link onPostExecute} should be informed about the Throwable 
	 * @param e
	 */
	protected void setCatchedException(Throwable e)
	{
		this.catchedException = e;
	}
	
	
	/**
	 * the code in  {@link onPostExecute} should call this method 
	 * to obtain possible exception caught on background execution
	 */
	public Throwable getCatchedException()
	{
		return this.catchedException;
	}
}
