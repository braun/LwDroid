package lw.droid.forms.settings;

import lw.droid.R;
import lw.droid.forms.settings.model.MetaProperty;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OptionsAdapter extends BaseAdapter {

	MetaProperty mProperty;
	Context mContext;
	public OptionsAdapter(MetaProperty mProperty, Context mContext) {
		super();
		this.mProperty = mProperty;
		this.mContext = mContext;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mProperty.getOptions().size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mProperty.getOptions().get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			convertView = LayoutInflater.from(mContext).inflate(R.layout.spinner_item, null);
		
		}
		
		TextView tv = (TextView)convertView;
	
		tv.setText(mProperty.getOptions().get(position).getDisplay());
		return convertView;
	
	}

}
