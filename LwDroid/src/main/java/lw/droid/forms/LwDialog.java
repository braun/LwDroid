package lw.droid.forms;

import org.androidannotations.annotations.rest.Post;

import com.google.gson.Gson;

import lw.droid.LwApplication;
import lw.droid.R;
import lw.droid.commons.Helper;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Simple facade over DialogFragment
 * @author Braun
 *
 */

public abstract class LwDialog<T extends LwDialog.DialogModelBase> extends DialogFragment implements LwForm {
	
	   View topView;
	   int layoutResourceId;
	   Handler handler;
	   T model;
	 
	   
	   /**
	    *  bindViews will bind members of concrete class to views.
	    *  It is intended for auto generated bindViews method! 
	    * @param root root view
	    */
	   public abstract void bindViews(View root);
	   
	   
	   /**
	    * initViews is intended for non auto generated initialization of
	    * views, respectively class members bound to views by bindViews.
	    * Things like setting OnClickListenets should be here
	    * 
	    */
	   public abstract void initViews();
	   
	   
	   /**
	    * bindData is intended to populate widgets with data from dialog's model
	    */
	   public abstract void bindData();
	  
	   public LwDialog()
	   {
		   super();
		   handler = new Handler();
	   }
	   /**
		 * Dialog identifier used by LwApplication to lookup activity data
		 * 
		 * @return
		 */
		public String getFormKey() {
			return this.getClass().getName();
		}

	   LinearLayout topcontainer;
	   FrameLayout titlePane;
	   TextView title;
	   FrameLayout childPane;
	   FrameLayout footerPane;

	   void bindContainerViews(View rootView) {
	   	topcontainer = (LinearLayout)rootView.findViewById(R.id.lwdialog_topcontainer);
	   	titlePane = (FrameLayout)rootView.findViewById(R.id.lwdialog_titlePane);
	   	title = (TextView)rootView.findViewById(R.id.lwdialog_title);
	   	childPane = (FrameLayout)rootView.findViewById(R.id.lwdialog_childPane);
	   	footerPane = (FrameLayout)rootView.findViewById(R.id.lwdialog_footerPane);
	   }
	   
	   boolean initializationComplete = false;
	 
//	   /* (non-Javadoc)
//		 * @see android.app.DialogFragment#onCreate(android.os.Bundle)
//		 */
//		@Override
//		public void onCreate(Bundle savedInstanceState) {
//			// TODO Auto-generated method stub
//			super.onCreate(savedInstanceState);
//			if(savedInstanceState.containsKey("dialogmodel"))
//			{
//				String modelJson = savedInstanceState.getString("dialogmodel");
//				Gson gson = new Gson();
//				model  = gson.fromJson(modelJson, getModelClass());
//			}
//		}
		
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
	        
		    View top = inflater.inflate(R.layout.lwdialog, container,false);
		    bindContainerViews(top);
		  //  inflater.inflate(R.layout., root)
		 	View v = inflater.inflate(this.layoutResourceId,childPane, true);
	        
	      
		      bindViews(v);
		    
		      
		  	if(Helper.isStringEmpty(this.getModel().getTitleText()))
			{
				if(this.getModel().getTitleResourceId() == 0)
				  titlePane.setVisibility(View.GONE);
				else
				   title.setText(this.getModel().getTitleResourceId());
			}
			else
				   title.setText(this.getModel().getTitleText());

		  			  	
		  	bindData();
		
	        return top;
	    }

	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onCreateDialog(android.os.Bundle)
	 */
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		  int st = LwApplication.getLwInstance().getStyle();
		Dialog dialog = new Dialog(getActivity(), st);//super.onCreateDialog(savedInstanceState);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
	//	dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
	 
	 //   dialog.getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.lwdialog_title);
		 int titleDividerId = getResources().getIdentifier("titleDivider", "id", "android");
		    View titleDivider = dialog.findViewById(titleDividerId);
		    if (titleDivider != null)
		        titleDivider.setVisibility(View.INVISIBLE);
		  topView = onCreateView(getActivity().getLayoutInflater(), null, savedInstanceState);
		dialog.setContentView(topView);
		 // int st = LwApplication.getLwInstance().getStyle();
	      //  if(st != 0)
	      //  	setStyle(STYLE_NORMAL, st);
	    //   LayoutParams params = dialog.getWindow().getAttributes();
	    //    params.width = LayoutParams.FILL_PARENT;
	     //   dialog.getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
		
		
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(dialog.getWindow().getAttributes());
		
		
		setWindowAttributes(lp);
		//lp.height
	//	if(lp.width <)
	//	lp.width = (int) (mwidth*0.8);
		
	//	if(lp.height)
		//	lp.height = (int) (mheight*0.8);

		//change position of window on screen
		//lp.x = mwidth/2; //set these values to what work for you; probably like I have here at
		//lp.y = mheight/2;        //half the screen width and height so it is in center

		//set the dim level of the background
		lp.dimAmount=0.8f; //change this value for more or less dimming

		dialog.getWindow().setAttributes(lp);

		//add a blur/dim flags
		dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND | WindowManager.LayoutParams.FLAG_DIM_BEHIND);
		
		handler.post(new Runnable() {
			
			@Override
			public void run() {
				initializationComplete = true;
			    initViews();
			}
		});
		return dialog;
	}


	protected void setWindowAttributes(WindowManager.LayoutParams lp) {
		Display display = getActivity().getWindowManager().getDefaultDisplay(); 
		int mwidth = display.getWidth();
		int mheight = display.getHeight();

		int wv = getRootView().getLayoutParams().width;
		int wh = getRootView().getLayoutParams().height;
		int wm = getRootView().getMeasuredWidth();
		int hm = getRootView().getMeasuredHeight();
		int widthMeasureSpec = MeasureSpec.makeMeasureSpec( LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
		int heightMeasureSpec = MeasureSpec.makeMeasureSpec(LayoutParams.WRAP_CONTENT, MeasureSpec.EXACTLY);
		topView.measure(widthMeasureSpec, heightMeasureSpec);
		int h = topView.getMeasuredHeight();
		int w = topView.getMeasuredWidth();
		
		lp.width = (int) (w*2.5);
		if(lp.width > mwidth*0.8)
			lp.width = (int) (mwidth*0.8);
	//	lp.height = h;
		//if(mwidth>mheight)
		//	lp.height =(int) (h*0.8);
		
		
	//	if(lp.height > mheight*0.8)
	//		lp.height = (int) (mheight*0.8);
	}

	
	

	/**
	 * @return the layoutResourceId
	 */
	public int getLayoutResourceId() {
		return layoutResourceId;
	}


	/**
	 * @param layoutResourceId the layoutResourceId to set
	 */
	public void setLayoutResourceId(int layoutResourceId) {
		this.layoutResourceId = layoutResourceId;
	}
	 
	 
	/**
	 * Base class of all dialog models
	 * @author Braun
	 *
	 */
	public static class DialogModelBase extends LwActivity.ModelBase
	{
		   int titleResourceId;
		   String titleText;
		 
		/**
		 * @return the titleResourceId
		 */
		public int getTitleResourceId() {
			return titleResourceId;
		}
		/**
		 * @return the titleText
		 */
		public String getTitleText() {
			return titleText;
		}
		public DialogModelBase(String titleText, int titleResourceId) {
			super();
			this.titleText = titleText;
			this.titleResourceId = titleResourceId;
		}
	
	
	}


	/**
	 * @return the model
	 */
	public T getModel() {
		if(model == null)
		{
			model = LwApplication.getLwInstance().getModel(getFormKey());
		
		
		}	
		return model;
	}


	/**
	 * @param model the model to set
	 */
	public void setModel(T model) {
		this.model = model;
		
	}


	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onSaveInstanceState(android.os.Bundle)
	 */
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
	
		LwApplication.getLwInstance().keepModel(getFormKey(), model, null);
		
	}


	/* (non-Javadoc)
	 * @see android.app.DialogFragment#onDismiss(android.content.DialogInterface)
	 */
	@Override
	public void onDismiss(DialogInterface dialog) {
		// TODO Auto-generated method stub
		super.onDismiss(dialog);
		try {
			getParentForm().dialogDismissedCallback(this);
		} catch (Throwable e) {
			Log.e("LwDialog.onDismiss", e.getLocalizedMessage(), e);
		}
		LwApplication.getLwInstance().removeModel(getFormKey());
	}


	/**
	 * @return the initializationComplete
	 */
	public boolean isInitializationComplete() {
		return initializationComplete;
	}


	public void showDialog(LwDialog dialog)
	{
		dialog.setTargetFragment(this, 1);
		LwDialog.showDialog(getFragmentManager(), dialog);
	}
	
	public LwForm getParentForm()
	{
		if(getTargetFragment() != null)
			return (LwForm) getTargetFragment();
		
		return (LwForm) getActivity();
	}
	
	public View getRootView()
	{
		return topcontainer;	
	}
	
	public void dialogDismissedCallback(LwDialog dialog)
	{
		
	}
	
	public static void showDialog(FragmentManager fragmentManager,
			LwDialog dialog) {
	
			    FragmentTransaction ft = fragmentManager.beginTransaction();
			    Fragment prev = fragmentManager.findFragmentByTag(dialog.getFormKey());
			    if (prev != null) {
			        ft.remove(prev);
			    }
			    ft.addToBackStack(null);

			  
			    //  show the dialog.	   
			  dialog.show(ft, dialog.getFormKey());
			
			
			
		
	}
	

}
