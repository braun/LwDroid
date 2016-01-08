package lw.droid.forms.settings;


import lw.droid.R;
import lw.droid.forms.settings.model.MetaProperty;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

public class CheckboxView extends LinearLayout implements PropertyView  {
	
	/* (non-Javadoc)
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnabled(enabled);
		mCheckBox.setEnabled(enabled);
	}
	MetaProperty mProperty;
	Context mContext;

	View mRootView;

	
	CheckBox mCheckBox;
	
	public CheckboxView(MetaProperty metaProperty, final Context ctx) {
		super(ctx);
		mProperty = metaProperty;
		mContext = ctx;

		mRootView = LayoutInflater.from(ctx).inflate(R.layout.meta_property_checkbox,
				this);
		
		mCheckBox = (CheckBox) mRootView.findViewById(R.id.checkbox);
	
		mCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mProperty.setPropertyValue(Boolean.toString(isChecked));
				
			}
		});
	}

	public void Bind() {
		
		
		String curval = mProperty.getPropertyValue();
		mCheckBox.setText(mProperty.getDisplay());
		boolean checked = Boolean.parseBoolean(curval);
		mCheckBox.setChecked(checked);
	}

	
	public void handleActivityResult(int requestCode, Intent data) {
	
	}
	public void onDestroy()
	{
		
	}
}

