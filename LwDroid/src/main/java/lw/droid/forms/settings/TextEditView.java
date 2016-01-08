package lw.droid.forms.settings;

import lw.droid.R;
import lw.droid.forms.settings.model.MetaProperty;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextEditView extends LinearLayout implements PropertyView {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnabled(enabled);
		btSelect.setEnabled(enabled);
		lblValue.setEnabled(enabled);
		//labelTitle.setEnabled(enabled);
	}

	MetaProperty mProperty;
	Context mContext;

	View mRootView;

	RelativeLayout container;
	TextView labelTitle;
	TextView lblValue;
	Button btSelect;

	void bindViews(View rootView) {
		container = (RelativeLayout) rootView.findViewById(R.id.container);
		labelTitle = (TextView) rootView.findViewById(R.id.labelTitle);
		lblValue = (TextView) rootView.findViewById(R.id.lblValue);
		btSelect = (Button) rootView.findViewById(R.id.bt_select);

	}

	public TextEditView(MetaProperty metaProperty, final Context ctx) {
		super(ctx);
		mProperty = metaProperty;
		mContext = ctx;

		mRootView = LayoutInflater.from(ctx).inflate(
				R.layout.meta_property_edit, this);
		bindViews(mRootView);
		btSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder bld = new Builder(getContext());
				final EditText input = new EditText(getContext());
				input.setText(mProperty.getPropertyValue());
				bld.setView(input);
				bld.setCancelable(false);
				bld.setTitle(mProperty.getDisplay());
				bld.setPositiveButton(getContext().getString(R.string.ok),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								lblValue.setText(input.getText().toString());
								mProperty.setPropertyValue(input.getText()
										.toString());
								dialog.dismiss();
							}
						});
				bld.setNegativeButton(getContext().getString(R.string.back),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();

							}
						});
				bld.create().show();
			}

		});
	}

	public void Bind() {

		String curval = mProperty.getPropertyValue();
		labelTitle.setText(mProperty.getDisplay());

		lblValue.setText(curval);
	}


	public void handleActivityResult(int requestCode, Intent data) {

	}

	public void onDestroy() {

	}
}
