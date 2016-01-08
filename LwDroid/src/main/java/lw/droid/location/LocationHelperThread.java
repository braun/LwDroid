package lw.droid.location;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LocationHelperThread  extends Thread{

	Handler handler;
	
	public LocationHelperThread()
	{
		super("LocationHelperThread");
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		
		try {
			Looper.prepare();
			handler = new Handler();
			Looper.loop();
		} catch (Throwable e) {
			Log.e("LocationHelperThread.run TOPLEVEL", e.getLocalizedMessage(), e);
		}
		
	}

	public static LocationHelperThread createAndStart() {
		LocationHelperThread rv = new LocationHelperThread();
		rv.start();
		return rv;
	}

	public void postJob(Runnable runnable) {
		handler.post(runnable);
		
	}
	
	
	
	
}
