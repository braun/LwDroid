package lw.droid.forms;

/**
 * This callback is called on finish() of LwActivity
 * @author Braun
 *
 */
public interface ActivityFinishedCallback {

	/**
	 * called on finish() of activity
	 * @param activity activity on which the finish() was called
	 * @param model data of this activity
	 */
	 <E> void finished(LwActivity activity,E model);
}
