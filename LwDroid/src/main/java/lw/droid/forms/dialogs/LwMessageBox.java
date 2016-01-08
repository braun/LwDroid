package lw.droid.forms.dialogs;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;
import lw.droid.R;
import lw.droid.commons.Helper;
import lw.droid.forms.LwActivity;
import lw.droid.forms.LwDialog;
import lw.droid.forms.LwForm;

/**
 * Simple message box based on LwDialog
 * @author Braun
 *
 */
public class LwMessageBox extends LwDialog<LwMessageBox.Model> {



	
	public LwMessageBox()
	{
		setLayoutResourceId(R.layout.lwmessagebox);
		
	}
	
	
	TextView lblMessage;
	Button btOk;

	public void bindViews(View rootView) {
		lblMessage = (TextView)rootView.findViewById(R.id.lblMessage);
		btOk = (Button)rootView.findViewById(R.id.btOk);
	}


	@Override
	public void initViews() {
		 btOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dismiss();
				
			}
		});
		
		
		if(Helper.isStringEmpty(this.getModel().getMessage()))
		{
			if(this.getModel().getMessageResourceId() != 0)
				lblMessage.setText(this.getModel().getMessageResourceId());
		}
		else
			lblMessage.setText(this.getModel().getMessage());
			

	}
	@Override
	public void bindData() 
	{}

	
	
	public static void show(LwForm parent,String message)
	{
		LwMessageBox dlg = new LwMessageBox();
		LwMessageBox.Model model = new Model(message, 0,null,0);
		dlg.setModel(model);
		parent.showDialog(dlg);
	}
	public static class Model extends lw.droid.forms.LwDialog.DialogModelBase
	{
		String message;
		int messageResourceId;
		
		public Model(String message, int messageResourceId,String titleText,int titleResourceId) {
			super(titleText,titleResourceId);
			this.message = message;
			this.messageResourceId = messageResourceId;
		}
		
		/**
		 * @return the message
		 */
		public String getMessage() {
			return message;
		}
		
		/**
		 * @return the messageResourceId
		 */
		public int getMessageResourceId() {
			return messageResourceId;
		}
	}

	/* (non-Javadoc)
	 * @see lw.droid.forms.LwDialog#setWindowAttributes(android.view.WindowManager.LayoutParams)
	 */
	@Override
	protected void setWindowAttributes(LayoutParams lp) {
		// TODO Auto-generated method stub
	
	}

	
}
