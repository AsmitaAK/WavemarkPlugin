package org.apache.cordova.wavemark;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

public class ImageService extends IntentService {

	Media_Util media ;
	WavemarkHelper wavemarkHelper;
	String jsonString;
	String mediaPath;
	String resp;
	ResponseModal[] response;
	ResponseModal respM;
	WavemarkHelper.communication com;
	public Context context;

	public ImageService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	public ImageService(){
		super("Wavemark_Image");
		wavemarkHelper = WavemarkHelper.getInstance(ImageService.this);
		com = wavemarkHelper.getProvider();
		media = new Media_Util();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		context = this;
		upload(intent);

	}

	public void upload(Intent intent){

		mediaPath = intent.getStringExtra("path");
		jsonString = media.media_upload("image_wavemark", mediaPath);
		respM = wavemarkHelper.imageResponse(jsonString);

		if(respM != null && checkError(respM) == 1)
		{
			System.out.println(" wavemark is " + respM.getMetadata());
			System.out.println(" wavemark id is " + respM.getImageId());
			System.out.println(" wavemark id is " + respM.getId());

			response = wavemarkHelper.sendCode(respM.getMetadata(), "", "");
			if(response !=null){
				ArrayList<ResponseModal> responseList = new ArrayList<ResponseModal>();
				for(int i=0;i<response.length;i++){
					responseList.add(response[i]);
				}

				Intent imgIntent = new Intent(Constants.INTENT_IMAGE_RESPONSE);
				imgIntent.putParcelableArrayListExtra("response", responseList);

				LocalBroadcastManager.getInstance(context).sendBroadcast(imgIntent);
			}else{
				Intent imgIntent = new Intent(Constants.INTENT_IMAGE_RESPONSE);
				imgIntent.putExtra("type", "ErrorNoResponse");

				LocalBroadcastManager.getInstance(context).sendBroadcast(imgIntent);

//				com.errorDialog("No Response from the server");
				return;
			}

			//		com.updateList(response);
		}
		else if(checkError(respM) == 0){
			Intent imgIntent = new Intent(Constants.INTENT_IMAGE_RESPONSE);
			imgIntent.putExtra("type", "NoMatch");

			LocalBroadcastManager.getInstance(context).sendBroadcast(imgIntent);
			return;
		}
		else{
			Intent imgIntent = new Intent(Constants.INTENT_IMAGE_RESPONSE);
			imgIntent.putExtra("type", "Error");

			LocalBroadcastManager.getInstance(context).sendBroadcast(imgIntent);
//			com.errorDialog("Error occured while sending the image");
			return;
		}

	}	




	/**
	 * checks the error in the response
	 * 
	 * @param response
	 * @return
	 *  <b>0</b> error
	 *  <b>1</b> success
	 */

	public int checkError(ResponseModal response){

		if( response != null && response.getStatus().equalsIgnoreCase("0")){
			com.errorDialog("Error occured while sending the image");
			return 0;
		}else if(response != null && response.getImageId().equalsIgnoreCase("-1")){
			com.noMatchFound(response.getMetadata());
			return 0;
		}else if(response != null){
			return 1;
		}

		return 1;

	}
}
