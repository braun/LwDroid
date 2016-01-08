package lw.droid.forms;

import lw.droid.LwApplication;
import lw.droid.commons.Helper;
import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.ContextThemeWrapper;

public class MessageBox {

	  static class CancelListener implements OnCancelListener {
			boolean rv = true;
			public void onCancel(DialogInterface dialog) {
				rv = false;
				
			}
			
			public boolean Rv() { return rv; }
	  }
	  
	  /**
	   * Yes/No Query dialog
	   * @param ctx context
	   * @param message the question displayed to user
	   * @param resultCallback delivers the user choice to calling environment
	   */
	  public static void Query(final Context ctx,String message,final QuestionResultCallback resultCallback)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		  builder.setMessage(message)
		         .setCancelable(false)
		         .setPositiveButton(ctx.getString(R.string.yes), new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		            	 try
		            	 {
		                  dialog.dismiss();
		                  if(resultCallback != null)
		                	  resultCallback.onYes();
		            	 }
		            	 catch(Throwable th)
		            	 {
		            		 Helper.showError(ctx, "MessageBox.Query.onYes", th);
		            	 }
		             }
		         })
		         .setNegativeButton(ctx.getString(R.string.no), new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
								try {
									dialog.cancel();
									if (resultCallback != null)
										resultCallback.onNo();
								} catch (Throwable th) {
									Helper.showError(ctx,
											"MessageBox.Query.onNo", th);
								}
							}
		         });
		  AlertDialog alert = builder.create();
		 
	
		  alert.show();
		  
		 
	  }
	
	
	  
	  public static void Show(Context ctx,String message)
	  {
		  AlertDialog.Builder builder = LwApplication.getLwInstance().createDialogBuilder(ctx);
		  builder.setMessage(message)
		         .setCancelable(false)
		         .setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		                  dialog.dismiss();
		             }
		         })
		        ;
		  
		  AlertDialog alert = builder.create();
		 
		
		  alert.show();
		  
		
		 
	  }
	  
	  public static void ShowError(Context ctx,String message)
	  {
		  AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
		  builder.setMessage(message)
		         .setCancelable(false)
		         .setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
		             public void onClick(DialogInterface dialog, int id) {
		                  dialog.dismiss();
		             }
		         })
		           .setTitle(ctx.getString(lw.droid.R.string.title_error));
		        //   .setIcon(R.drawable.icon);
		        ;
		  AlertDialog alert = builder.create();
		 
		
		  alert.show();
		  
		
		 
	  }

	public static void showError(Context ctx, Throwable error) {
		ShowError(ctx,error.getLocalizedMessage());
		
	}
}
