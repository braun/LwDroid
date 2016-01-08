package lw.droid.forms.dialogs;

import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import lw.droid.LwApplication;
import lw.droid.R;
import lw.droid.forms.LwActivity;
import lw.droid.forms.LwActivity.ModelBase;
import lw.droid.forms.LwDialog;
import lw.droid.forms.LwDialog.DialogModelBase;
import lw.droid.forms.LwForm;

public class LwSpinnerDialog extends LwDialog<LwSpinnerDialog.Model> {

	ListView listView;
	
	
	public void bindViews(View rootView) {
		listView = (ListView)rootView.findViewById(R.id.lwspinner_listView);
	}
	
	
	public LwSpinnerDialog()
	{
		setLayoutResourceId(R.layout.lwspinner_dialog);
		
	}
	
	@Override
	public void initViews() {
	
		this.listView.setAdapter(getModel().getAdapter());
		this.listView.setSelection(getModel().getCurrentSelectionPosition());
		this.listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
	
				LwSpinnerDialog.this.onItemSelected(arg2);
				LwSpinnerDialog.this.dismiss();
				
			}
		});
		
//		this.listView.setSelection(//po);

	}
	
	public ListAdapter getAdapter()
	{
		return getModel().getAdapter();
	}

	protected void onItemSelected(int itemId) {		
		
	}


	@Override
	public void bindData() 
	{}
	
	/* (non-Javadoc)
	 * @see lw.droid.forms.LwDialog#setWindowAttributes(android.view.WindowManager.LayoutParams)
	 */
	@Override
	protected void setWindowAttributes(LayoutParams lp) {
		// TODO Auto-generated method stub
	
	}
	public static void run(LwSpinnerDialog dlg,LwForm activity,ModelBase parentModel,int titleResourceId,ListAdapter adapter,int currentSelectionPosition)
	{	
		LwSpinnerDialog.Model model = new Model(adapter, currentSelectionPosition,null,titleResourceId);
		model.setParent(parentModel);
		dlg.setModel(model);
				
	
		activity.showDialog(dlg);
	}
	
	public static void run(LwSpinnerDialog dlg,LwForm parent,ModelBase parentModel,ListAdapter adapter,int currentSelectionPosition)
	{
		run(dlg,parent,parentModel,0,adapter,currentSelectionPosition);
	}
	
	public static class Model extends DialogModelBase
	{
		int currentSelectionPosition;
		
		ListAdapter adapter;
		public Model(ListAdapter adapter, int currentSelectionPosition,
			String titleText,int titleResourceId) {
			super(titleText,titleResourceId);
			this.adapter = adapter;
			this.currentSelectionPosition = currentSelectionPosition;
			
		}
		
		/**
		 * @return the currentSelectionPosition
		 */
		public int getCurrentSelectionPosition() {
			return currentSelectionPosition;
		}
		
		/**
		 * @return the adapter
		 */
		public ListAdapter getAdapter() {
			return adapter;
		}
	}

	


	
	
}
