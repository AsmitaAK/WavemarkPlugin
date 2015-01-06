package org.apache.cordova.wavemark;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.LocalBroadcastManager;

/**
 * uploads & downloads media from the server
 * @author Saghayam
 *
 */

public class Media_Util {

	private static final int DIALOG_DOWNLOAD_PROGRESS = 0;
	public static String MEDIASTATUS = "ngage.MEDIA_STATUS";
	private CustomMultiPartEntity customMultipartEntity;
	long totalSize;
	public Context context;
	public int id;
	long lenghtOfFile;
	public Activity activity;
	public Media_Util(){

	}

	public Media_Util(Context context){

		this.context = context;

	}

	public Media_Util(Activity act){

		this.activity = act;

	}


	


	

	public String media_upload(String subject, String mediaPath){

		if(subject.equalsIgnoreCase("image_wavemark"))
		{
			String str = sendImage(mediaPath);
			return str;
		}
		return null;
	}



	org.apache.cordova.wavemark.CustomMultiPartEntity.ProgressListener progressListener = new org.apache.cordova.wavemark.CustomMultiPartEntity.ProgressListener()
	{
		@Override
		public void transferred(long num)
		{

			System.out.println("Progresss = " + (int) ((num / (float) totalSize) * 100));
			int progress = (int) ((num / (float) totalSize) * 100);
			Intent i = new Intent(MEDIASTATUS);
			Bundle bundle = new Bundle();
			bundle.putInt("progress", progress);
			i.putExtra("type", "upload");
			i.putExtra("progress", progress);
			i.putExtra("totalSize",totalSize);

			LocalBroadcastManager.getInstance(context).sendBroadcast(i);
			//			publishProgress((int) ((num / (float) totalSize) * 100));
		}
	};
	private ProgressDialog mProgressDialog;


	/**
	 * sends image detected in wavemark
	 * 
	 * @param mediaPath
	 * @return
	 */

	public String sendImage(String mediaPath){


		String url = org.apache.cordova.wavemark.Constants.IMAGE_CODE_DETECTION_URL;

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);		


		HttpResponse response;
		HttpEntity entity;


		//		String url = "http://www.yourwellness.com/engage/engage/mobile_connect_api?request_type=upload_attachment_native";
		//
		//		HttpClient httpClient = new DefaultHttpClient();
		//		HttpPost httpPost = new HttpPost(url);

		File file = new File(mediaPath);
		FileBody fileBody = new FileBody(file);

		try {
			CustomMultiPartEntity customMultipartEntity = new CustomMultiPartEntity( HttpMultipartMode.BROWSER_COMPATIBLE,null);

			customMultipartEntity.addPart("image", fileBody);
			totalSize = customMultipartEntity.getContentLength();

			httpPost.setHeader("Authorization",org.apache.cordova.wavemark.Constants.AUTHORIZATION);

			httpPost.setEntity(customMultipartEntity);
			response = httpClient.execute(httpPost);
			entity  = response.getEntity();
			//--------------------------------------------------

			//				if(subject.equalsIgnoreCase("image"))
			//					UtilFunctions.createActivityLogFile(NgageApplication.getInstance().getApplicationContext(),143,"image"+totalSize/1024+"");

			if (entity != null) {

				String responseStr = EntityUtils.toString(entity).trim();
				System.out.println(" response for uploaded image is " + responseStr);
				// System.out.println(" response for uploaded image in to string format "+ entity.getContent().toString());



				return responseStr;
				// you can add an if statement here and do other actions based on the response
			}else{
				System.out.println(" the response for the uploaded image is null");
			}



		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}


}

