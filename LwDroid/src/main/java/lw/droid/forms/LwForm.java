package lw.droid.forms;

import android.view.View;

public interface LwForm {

	
	void showDialog(LwDialog<?> dialog);
	String getFormKey();
	
	View getRootView();

	void dialogDismissedCallback(LwDialog dialog);
}
