package lw.droid.forms;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public abstract class BindableListAdapterBase<T> extends BaseAdapter {

	int viewResourceId;
	Context context;
	List<T> data;
	
	public BindableListAdapterBase(Context context,List<T> data,int viewResourceId)
	{
		this.data = data;
		this.viewResourceId = viewResourceId;
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return getData().size();
	}
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return getData().get(arg0);
	}
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup root) {
		if(convertView == null)
		{
			convertView = LayoutInflater.from(context).inflate(this.viewResourceId,root,false);
			
		}
		@SuppressWarnings("unchecked")
		T row = (T)getItem(position);
		this.bind(row,convertView);
		return convertView;
	}
	
	public abstract void bind(T row,View view);

	public List<T> getData()
	{
		
		return this.data;
	}
}
