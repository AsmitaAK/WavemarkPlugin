package org.apache.cordova.wavemark;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

/**
 * Helper class for the wavemark activity
 * 
 * @author Saghayam
 *
 */

public class WavemarkHelper {

	public communication communication;
	public CustomMultiPartEntity customMultipartEntity;
	public long totalSize;
	public static WavemarkHelper wavemarkHelper ;

	/**
	 * an interface for the communcation between the wavemark activity & wavemark Helper class
	 * @author Saghayam
	 *
	 */
	public interface communication{

		public void update(ResponseModal[] resp);

		/**
		 * updates the listview in the wavemark activity 
		 * 
		 * @param resp
		 */
		public void updateList(ResponseModal[] resp);

		/**
		 * destroy the activity
		 */
		public void finishActivity();

		/**
		 * displays error dialog box if there was an error in transferring the image
		 * @param error
		 * 
		 */
		public void errorDialog(String error);

		/**
		 * displays a dialog if no match found on the server
		 * 
		 * @param message
		 * 
		 */
		public void noMatchFound(String message);

		/**
		 * checks network availability
		 * @return
		 */
		public boolean isNetworkAvailable();

	}

	public static Context context;
	public boolean isAllowed = false;
	private ArrayList<Long> int_array ;

	public static WavemarkHelper getInstance(Context context){
		if(wavemarkHelper != null){

		}else{
			wavemarkHelper = new WavemarkHelper(context);
		}

		return wavemarkHelper;
	}

	//	public static WavemarkHelper getInstance(){
	//		
	//	}

	private WavemarkHelper(Context context){
		WavemarkHelper.context = context; //NgageApplication.getInstance();
		try{
			communication = (communication)context;
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public communication getProvider(){
		return this.communication;
	}
	//	9821288118

	/**
	 * sends the code or latitude & longitude to the server 
	 * 
	 * @param code
	 * @param lat
	 * @param lon
	 * @return
	 */

	public ResponseModal[] sendCode(String code,String lat,String lon){

		//		"http://mobileapp.yourwellness.com/wavemarc_embed/wavemarc.php?code="+URLEncoder.encode(code)

		//		if(com.isNetworkAvailable())
		//		{
		//			com.errorDialog("no network connection");
		//			return null;
		//		}

		//		String url[] = UtilFunctions.getInstance().getUrl(context);
		//		String url = "http://api.ngageapp.com/index.php";

		String url = "http://yourwellness.com/wemet/api/getting_started.php";
		String tag[] = {"request_type","code","lat","lon"};
		String value[] = {"get_banner_using_code"/*"wavemark"*/,code, lat , lon};

		String api = addParameter(url/*url[0]*/, tag, value);

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(api);

		try {
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();

			InputStream stream = entity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream,"UTF-8"));
			String data = null;
			String result = null;
			while((data = reader.readLine()) != null){
				if(result == null){
					result = data;
				}else{
					result +=data;
				}
			}

			System.out.println(" response in result is " + result);
			//				UtilFunctions.getInstance().invokeRequest(_url);
			return	parseJSON(result);

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

	/**
	 *parse the json response for the image from the third party server
	 * status: it indicates if the request is successfully processed. 1: success, 0: error occurs.
	 * id: the image ID in the database. If no matched image is found, it will be "-1"
	 * 
	 * @param json
	 * @return
	 * @throws  
	 */

	public ResponseModal imageResponse(String json){

		ResponseModal response = new ResponseModal();

		try {
			JSONObject jsonObject = new JSONObject(json);
			String status  = jsonObject.getString("status");
			String metadata = jsonObject.getString("metadata");
			String id = jsonObject.getString("id");

			response.setStatus(status);
			response.setMetadata(metadata);
			response.setImageId(String.valueOf(id));

			return response;

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}


		return null;

	}

	/**
	 * parse the json & adds the result to a responsemodal object
	 * if the error code is 1 in the result it returns null indiating no response from the server 
	 * for a particular code or latitude & longitude
	 * 
	 * @param json
	 * @return
	 */

	public ResponseModal[] parseJSON(String json){

		try {
			JSONObject jsonObject = new JSONObject(json);
			JSONObject responsed = jsonObject.getJSONObject("ResponseDetail");
			String project_id = responsed.getString("project_id");
			String project_title =responsed.getString("project_title");
			String project_desc =responsed.getString("project_desc");
			String campaign_id =responsed.getString("campaign_id");
			String test_count =responsed.getString("test_count");
			String project_start_data =responsed.getString("project_start_data");
			String project_end_data =responsed.getString("project_end_data");
			String project_start_time =responsed.getString("project_start_time");
			String project_end_time =responsed.getString("project_end_time");
			String expiry_msg =responsed.getString("expiry_msg");

			if(responsed.has("ErrorCode")){
				String errorCode = responsed.getString("ErrorCode");

				if(errorCode.equalsIgnoreCase("1"))
					return null;
			}
			if(responsed.has("banner"))
			{

				Object baner = responsed.get("banner");

				if (baner instanceof JSONArray) {
					JSONArray banner = (JSONArray)baner;
					ResponseModal[] respA = new ResponseModal[banner.length()];
				
					for(int i=0;i<banner.length();i++)
					{
						ResponseModal response = new ResponseModal();
						JSONObject jsonO = banner.getJSONObject(i);
						String id = (String)jsonO.getString("id");
						String title = (String) jsonO.getString("Title");
						String imageUrl = (String)jsonO.getString("Image_Url");
						String url = (String)jsonO.getString("Image_Link");
						String desc = (String)jsonO.getString("Description");
						response.setId(Long.valueOf(id));
						response.setTitle(title);
						response.setImageUrl(imageUrl);
						response.setUrl(url);
						response.setDescription(desc);
						//added by Lakshmi
						response.setProject_id(project_id);
						response.setStart_Date(project_start_data);
						response.setEnd_Date(project_end_data);
						response.setStart_time(project_start_time);
						response.setEnd_time(project_end_time);
						response.setExpiry_message(expiry_msg);
						respA[i] = response;

					}
					return respA;
				}else{
					JSONObject jsonO = (JSONObject)baner;
					ResponseModal response = new ResponseModal();
					String id = (String)jsonO.getString("id");
					String title = (String) jsonO.getString("Title");
					String imageUrl = (String)jsonO.getString("Image_Url");
					String url = (String)jsonO.getString("Image_Link");
					String desc = (String)jsonO.getString("Description");
					response.setId(Long.valueOf(id));
					response.setTitle(title);
					response.setImageUrl(imageUrl);
					response.setUrl(url);
					response.setDescription(desc);
					//added by lakshmi
					response.setProject_id(project_id);
					response.setStart_Date(project_start_data);
					response.setEnd_Date(project_end_data);
					response.setStart_time(project_start_time);
					response.setEnd_time(project_end_time);
					response.setExpiry_message(expiry_msg);
					return new ResponseModal[]{response};
				}
			}



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}
	/**
	 * It adds the parameter to the url for get request
	 * @param url
	 * @return
	 */

	public static String addParameter(String url,String[] tag,String[] value){
		if(!url.endsWith("?"))
			url += "?";

		List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
		//		params.add(new BasicNameValuePair("request_type","wavemark"));
		for(int i=0;i<tag.length;i++)
		{
			params.add(new BasicNameValuePair(tag[i],value[i]));
		}
		String paramString = URLEncodedUtils.format(params, "UTF-8");
		url += paramString;

		return url;		
	}

	/**
	 * updates the list to be added to the listview & skips the duplicate response from the server
	 * 
	 * @param it
	 * @param response
	 * @return
	 */

	public List<ResponseModal> populateList(List<ResponseModal> list,ResponseModal[] resp){

		ResponseModal[] response = resp;//sortResponse(resp);
		if(list.size()==0){
			int_array = new ArrayList<Long>();
			for(int i=0;i<response.length;i++){				

				if(!int_array.contains(response[i].getId()))
				{
					int_array.add(response[i].getId());
					list.add(response[i]);
				}
				else
				{
					Log.e("String","error");
				}
			}




		}else{
			//sortResponse(resp);


			//			while(it.hasNext()){
			//				ResponseModal res = it.next();
			//
			//				if(res.getId() == response[i].getId())
			//				{
			//					isAllowed = false;
			//				}else{
			//					isAllowed = true;
			//				}
			//
			//			}

			//			if(!it.hasNext()){
			//				isAllowed = true;
			//			}

			//			if(isAllowed)
			int_array = new ArrayList<Long>();
			for(int j=0;j<response.length;j++){

				if(!int_array.contains(response[j].getId()))
				{
					int_array.add(response[j].getId());
					list.add(response[j]);
				}
				else
				{
					Log.e("String","error");
				}
			}

		}

		return list;
	}

	/**
	 * removes the duplicate entry from the response
	 * 
	 * @param response
	 * @return
	 */

	public ResponseModal[] sortResponse(ResponseModal[] response){
		ResponseModal[] resp = new ResponseModal[]{} ;
		if(response.length>1)
		{
			for(int i=0;i<response.length;i++){

				for(int j=i+1;j<response.length;j++){
					if(j != i && !(response[i].getId() == response[j].getId())){
						resp[i] = response[i];

					}
				}			
			}
		}else{
			resp = response;
		}
		return resp;
	}


	/**
	 * Class for listening the location change using gps provider
	 * 
	 */

	public final LocationListener gpsListener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			double longitude = location.getLongitude();
			double latitude =location.getLatitude();
			//
			//			double longitude = 35.55555;
			//			double latitude =35.55555;

			Toast.makeText(context, "Longitude = "+longitude+"\n"+"Lattitude = "+latitude, Toast.LENGTH_LONG).show();
			new Thread(	new LocationThread(latitude,longitude)).start();

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			switch (status) {
			case LocationProvider.AVAILABLE:
				Toast.makeText(context, "GPS available again\n", Toast.LENGTH_SHORT).show();

				break;
			case LocationProvider.OUT_OF_SERVICE:
				Toast.makeText(context, "GPS out of service\n", Toast.LENGTH_SHORT).show();

				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Toast.makeText(context, "GPS temporarily unavailable\n", Toast.LENGTH_SHORT).show();

				break;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "GPS provider enabled", Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "GPS provider disabled", Toast.LENGTH_SHORT).show();
		}

	};

	/**
	 * Class for listening the location change using network provider
	 * 
	 */

	public final LocationListener networkLocationListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {

			switch (status) {
			case LocationProvider.AVAILABLE:
				Toast.makeText(context, "Network location available again\n", Toast.LENGTH_SHORT).show();

				break;
			case LocationProvider.OUT_OF_SERVICE:
				Toast.makeText(context, "Network location out of service\n", Toast.LENGTH_SHORT).show();

				break;
			case LocationProvider.TEMPORARILY_UNAVAILABLE:
				Toast.makeText(context, "Network location temporarily unavailable\n", Toast.LENGTH_SHORT).show();

				break;
			}
		}

		@Override
		public void onProviderEnabled(String provider) {
			Toast.makeText(context, "Network Provider Enabled\n", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onProviderDisabled(String provider) {
			Toast.makeText(context, "Network Provider Disabled\n", Toast.LENGTH_SHORT).show();

		}

		@Override
		public void onLocationChanged(Location location) {
			double longitude = location.getLongitude();
			double latitude =location.getLatitude();

			//			double longitude = 35.55555;
			//			double latitude =35.55555;


			Toast.makeText(context, "Longitude = "+longitude+"\n"+"Lattitude = "+latitude, Toast.LENGTH_LONG).show();
			new Thread(new LocationThread(latitude,longitude)).start();
		}
	};

	/**
	 * Dialog box called to enable location service if it is disabled 
	 * @param context
	 */

	public void showGPSDialog(Activity activity) {
		
		AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
		dialog.setTitle("Turn GPS ON");
		dialog.setCancelable(false);
		dialog.setPositiveButton("Yes", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				turnGPSOn();
			}
		});

		dialog.setNegativeButton("No", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				dialog.cancel();
				WatermarkDetectorApp.isGPSCheckON = false;
			}
		});

		dialog.show();
	}

	/**
	 * Redirects to the location service activity to enable the gps
	 */

	public void turnGPSOn()
	{

		context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
	}

	/**
	 * A runnable class to send the latitude & longitude to the server
	 * 
	 * @author Saghayam
	 *
	 */

	public class LocationThread implements Runnable{

		double latitude;
		double longitude;

		public LocationThread(double latitude,double longitude){
			this.latitude = latitude;
			this.longitude = longitude;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			ResponseModal[] response = sendCode("",String.valueOf(latitude),String.valueOf(longitude));
			if(response != null)
				communication.updateList(response);	
		}

	}


	public static File getOutputPhotoFile() {

		String root = Environment.getExternalStorageDirectory().toString();

		File directory = new File(root + "/ngage/");

		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				System.out.println( "Failed to create storage directory.");
				return null;
			}
		}

		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US).format(new Date());

		return new File(directory.getPath() + File.separator + "IMG_"
				//		return new File(path + File.separator + "IMG_"
				+ timeStamp + ".jpg");
	}



	CustomMultiPartEntity.ProgressListener progressListener = new CustomMultiPartEntity.ProgressListener()
	{
		@Override
		public void transferred(long num)
		{

		}
	};

	public String sendImage(String mediaPath){


		String url = Constants.IMAGE_CODE_DETECTION_URL;

		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);		


		HttpResponse response;
		HttpEntity entity;

		File file = new File(mediaPath);
		FileBody fileBody = new FileBody(file);

		try {
			customMultipartEntity = new CustomMultiPartEntity( HttpMultipartMode.BROWSER_COMPATIBLE,progressListener);

			customMultipartEntity.addPart("image", fileBody);
			totalSize = customMultipartEntity.getContentLength();

			httpPost.setHeader("Authorization", Constants.AUTHORIZATION);

			httpPost.setEntity(customMultipartEntity);
			response = httpClient.execute(httpPost);
			entity  = response.getEntity();

			if (entity != null) {

				String responseStr = EntityUtils.toString(entity).trim();
				System.out.println(" response for uploaded image is " + responseStr);
				return responseStr;
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
