package lw.droid.forms.settings;

import android.content.Intent;

public interface PropertyView {
	void Bind();

	void handleActivityResult(int requestCode, Intent data);
	void onDestroy();
	
	void setEnabled(boolean enabled);
}
