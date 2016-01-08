package lw.droid.forms.settings;

import lw.droid.R;
import lw.droid.forms.settings.model.MetaProperty;
import lw.droid.forms.settings.model.Option;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class SelectOptionView extends LinearLayout implements
		OnItemSelectedListener,PropertyView {

	MetaProperty mProperty;
	Context mContext;

	View mRootView;
	TextView mTitle;

	Spinner mSpinner;

	LinearLayout mSubContainer;

	public SelectOptionView(MetaProperty metaProperty, Context ctx) {
		super(ctx);
		mProperty = metaProperty;
		mContext = ctx;

		mRootView = LayoutInflater.from(ctx).inflate(R.layout.meta_property,
				this);
		mTitle = (TextView) mRootView.findViewById(R.id.labelTitle);
		mSpinner = (Spinner) mRootView.findViewById(R.id.spinner_options);
		mSubContainer = (LinearLayout) mRootView
				.findViewById(R.id.layout_sub_properties);

		mSpinner.setOnItemSelectedListener(this);
	}
	/* (non-Javadoc)
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnabled(enabled);
		mSpinner.setEnabled(enabled);
		for(int  i = 0; i< mSubContainer.getChildCount(); i++)
		{
			View v = mSubContainer.getChildAt(i);
			v.setEnabled(enabled);
		
			
		}
	}
	public void Bind() {
		mTitle.setText(mProperty.getDisplay());
		mSpinner.setAdapter(new OptionsAdapter(mProperty, mContext));
		String curval = mProperty.getPropertyValue();
		int i = 0;
		for (Option opt : mProperty.getOptions()) {
			i++;
			if ((opt.getValue() != null && opt.getValue().equals(curval)) || (opt.getValue() == null && (curval == null || curval.equals("")))) {
				mSpinner.setSelection(i - 1);
			}
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Option opt = (Option) mSpinner.getSelectedItem();
		if (opt == null)
			return;

		if (mProperty.getPropertyValue() == null 
				|| opt.getValue() == null || !opt.getValue().equals(mProperty.getPropertyValue()))

			mProperty.setPropertyValue(opt.getValue());

		mSubContainer.removeAllViews();
		if(opt.getSubSettings() != null)
		{
			for (MetaProperty subprop : opt.getSubSettings().getSubProperties()) {
				PropertyView v = subprop.getView(mContext);
				mSubContainer.addView((View)v);
			}
		}
		setEnabled(isEnabled());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleActivityResult(int requestCode, Intent data) {
		// TODO Auto-generated method stub
		
	}
	public void onDestroy()
	{
		
	}
	public static class Factory implements PropertyViewFactory
	{

		@Override
		public PropertyView createView(Context ctx, MetaProperty property) {
			// TODO Auto-generated method stub
			return new SelectOptionView(property, ctx);
		}
		
	}
	public static Factory factory = new Factory();
	
}
