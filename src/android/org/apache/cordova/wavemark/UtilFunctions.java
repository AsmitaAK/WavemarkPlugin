package org.apache.cordova.wavemark;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;




public class UtilFunctions {

private static ProgressDialog mProgressDialog;
public boolean isNetworkAvailable(Context context) {

	ConnectivityManager connectivityManager = (ConnectivityManager) context
			.getSystemService(Context.CONNECTIVITY_SERVICE);
	NetworkInfo activeNetworkInfo = connectivityManager
			.getActiveNetworkInfo();
	return activeNetworkInfo != null && activeNetworkInfo.isConnected();
}
public static void showLoader(Context context) {

	//		dialog1 = new TransparentProgressDialog(context, R.drawable.sp);
	//		dialog1.show();

	mProgressDialog = new ProgressDialog(context);
	mProgressDialog.setMessage("Please wait...");
	mProgressDialog.show();
}

/*
 * Show loading  and set cancelable dialog  
 * */
public static void showLoader(Activity activity, boolean setCancelable) {

	//		dialog1 = new TransparentProgressDialog(activity, R.drawable.sp);
	//		dialog1.setCancelable(setCancelable);
	//		dialog1.setCanceledOnTouchOutside(setCancelable);
	//		dialog1.show();

}

public static void closeLoader() {

	if(mProgressDialog != null) {
		mProgressDialog.cancel();
	}
	//		if(dialog1 != null) {
	//			dialog1.cancel();
	//		}
}
public static UtilFunctions getInstance()
{
	if(utilFunction==null)
	{
		utilFunction = new UtilFunctions();
	}
	return utilFunction;
}
private static UtilFunctions utilFunction;
	
}