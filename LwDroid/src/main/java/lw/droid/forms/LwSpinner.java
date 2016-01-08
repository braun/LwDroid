package lw.droid.forms;



import lw.droid.R;
import lw.droid.forms.dialogs.LwSpinnerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class LwSpinner extends RelativeLayout implements View.OnClickListener {

	public interface OnItemSelectedListener
	{
		void itemSelected(LwSpinner spinner);
	}
	ListAdapter adapter;
	View adapterView;

	LwForm hostingForm;
	int selectedItemIndex = 0;
	OnItemSelectedListener listener;
	
	public LwSpinner(Context context) {
		super(context);
		init();
	}

	private void init() {
		setBackgroundResource(R.drawable.spinner_bg);
		//int pad = getContext().getResources().getDimensionPixelSize(
		//		R.dimen.spinner_padding);
		//setPadding(pad, pad, pad, pad);
		setOnClickListener(this);
	}

	public LwSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LwSpinner(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	public void setOnItemSelectedChanged(OnItemSelectedListener listener)
	{
		this.listener = listener;
	}
	/**
	 * @return the adapter
	 */
	public Adapter getAdapter() {
		return adapter;
	}

	/**
	 * @param adapter
	 *            the adapter to set
	 */
	public void setAdapter(ListAdapter adapter) {
		this.adapter = adapter;
		bind();
	}

	private void bind() {

		this.removeAllViews();
		this.adapterView = adapter.getView(getSelectedItemIndex(),
				this.adapterView, null);
		this.adapterView.setDuplicateParentStateEnabled(true);

		this.addView(this.adapterView);
	}

	public static class LwSpinnerChooser extends LwSpinnerDialog {

		public static final String VIEWID = "viewid";

		public static LwSpinnerChooser newInstance(LwSpinner spinner) {
			LwSpinnerChooser chooser = new LwSpinnerChooser();
			Bundle pars = new Bundle();
			pars.putInt(VIEWID, spinner.getId());
			chooser.setArguments(pars);

			return chooser;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see lw.droid.forms.dialogs.LwSpinnerDialog#onItemSelected(int)
		 */
		@Override
		protected void onItemSelected(int itemId) {
			int id = getArguments().getInt(VIEWID);
			LwForm form = getParentForm();
			View root = form.getRootView();
			LwSpinner spinner = (LwSpinner) root.findViewById(id);
			if (spinner != null)
				spinner.setSelectedItemIndex(itemId);
		}

	}

	@Override
	public void onClick(View v) {
		try {

			LwSpinnerDialog.run(LwSpinnerChooser.newInstance(this),
					hostingForm, null, this.adapter, getSelectedItemIndex());
		} catch (Throwable e) {
			Log.e("LwSpinner.onClick", e.getLocalizedMessage(), e);
		}

	}

	private Context unwrapContext(Context ctx) {
		if (ctx.getClass() == ContextThemeWrapper.class)
			return unwrapContext(((ContextThemeWrapper) ctx).getBaseContext());
		else
			return ctx;
	}

	/**
	 * @return the selectedItemIndex
	 */
	public int getSelectedItemIndex() {
		return selectedItemIndex;
	}

	public Object getSelectedItem() {
		Object rv = this.adapter.getItem(getSelectedItemIndex());
		return rv;
	}

	/**
	 * @param selectedItemIndex
	 *            the selectedItemIndex to set
	 */
	public void setSelectedItemIndex(int selectedItemIndex) {
		this.selectedItemIndex = selectedItemIndex;
		bind();
		if(listener != null)
			listener.itemSelected(this);
	}

	/**
	 * @param hostingForm
	 *            the hostingForm to set
	 */
	public void setHostingForm(LwForm hostingForm) {
		this.hostingForm = hostingForm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.RelativeLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// setMeasuredDimension(getMeasuredWidth(), (int)
		// (getMeasuredHeight()*1.5));
	}

}
