package lw.droid.forms.settings;

import lw.droid.R;
import lw.droid.forms.settings.model.MetaProperty;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SelectContentView extends LinearLayout implements PropertyView  {
	
	MetaProperty mProperty;
	Context mContext;

	View mRootView;
	TextView mTitle;
	TextView mSelected;
	Button mButton;
	Button mBtPlay;
	MediaPlayer mPlayer;
	
	/* (non-Javadoc)
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		// TODO Auto-generated method stub
		super.setEnabled(enabled);
		mButton.setEnabled(enabled);
		mBtPlay.setEnabled(enabled);
		
	}
	public SelectContentView(MetaProperty metaProperty, final Context ctx) {
		super(ctx);
		mProperty = metaProperty;
		mContext = ctx;

		mRootView = LayoutInflater.from(ctx).inflate(R.layout.meta_property_select_content,
				this);
		mTitle = (TextView) mRootView.findViewById(R.id.labelTitle);
		mButton = (Button) mRootView.findViewById(R.id.bt_select);
		mSelected = (TextView) mRootView.findViewById(R.id.lblValue);
		mButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent i = new Intent(Intent.ACTION_GET_CONTENT);
				    i.setType("audio/*");
				    Intent c = Intent.createChooser(i, "Select soundfile");
				    ((Activity)ctx).startActivityForResult(c,11);
				
			}
		});
		
		mBtPlay = (Button) mRootView.findViewById(R.id.bt_replay);
	
		mBtPlay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(mPlayer != null)
				 mPlayer.start();
				
			}
		});
	}

	public void Bind() {
		
		mTitle.setText(mProperty.getDisplay());
		String curval = mProperty.getPropertyValue();
		//AflConfig.AlarmConfig cfg = new AflConfig.AlarmConfig(curval);
		mSelected.setText(curval);//cfg.getName());
		if(mPlayer != null)
			mPlayer.release();
		Uri uri = Uri.parse(curval);
		mPlayer = MediaPlayer.create(mContext,uri);
		
	}

	@Override
	public void handleActivityResult(int requestCode, Intent data) {
		if(requestCode != 11)
			return;
		
		String val = data.getDataString();
	//	AflConfig.AlarmConfig cfg = new AflConfig.AlarmConfig(val,"uri",val);
		mProperty.setPropertyValue(val);
		Bind();
	}
	
	public void onDestroy()
	{
		if(mPlayer != null)
			mPlayer.release();
		mPlayer = null;
	}

}
