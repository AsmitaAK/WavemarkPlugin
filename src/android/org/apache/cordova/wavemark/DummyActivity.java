package org.apache.cordova.wavemark;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

public class DummyActivity extends Activity {

	Uri fileUri;
	public WavemarkHelper wavemarkHelper;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		wavemarkHelper = WavemarkHelper.getInstance(this);		
		
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		fileUri = Uri.fromFile(WavemarkHelper.getOutputPhotoFile());
		i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );
		
	}
	
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
			if (resultCode == RESULT_OK) {
				Uri photoUri = null;
				if (data == null) {
					// A known bug here! The image should have saved in fileUri
					Toast.makeText(this, "Image saved successfully", 
							Toast.LENGTH_LONG).show();
					photoUri = fileUri;
					Intent i = new Intent(DummyActivity.this,WatermarkDetectorApp.class);
					i.setAction("image_saved");
					i.putExtra("path",photoUri.getPath());
					startActivity(i);
					finish();
//					Intent i = new Intent(DummyActivity.this,ImageService.class);
//					i.putExtra("path", photoUri.getPath());
//					startService(i);

				} else {
					photoUri = data.getData();
					Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
							Toast.LENGTH_LONG).show();
					Intent i = new Intent(DummyActivity.this,WatermarkDetectorApp.class);
					i.setAction("image_saved");
					i.putExtra("path",photoUri.getPath());
					startActivity(i);
					finish();
//					Intent i = new Intent(DummyActivity.this,ImageService.class);
//					i.putExtra("path", photoUri.getPath());
//					startService(i);
				}
			
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
				Intent i = new Intent(DummyActivity.this,WatermarkDetectorApp.class);
				i.setAction("image_cancelled");
				startActivity(i);
				finish();
			} else {
				Toast.makeText(this, "Callout for image capture failed!", 
						Toast.LENGTH_LONG).show();
				Intent i = new Intent(DummyActivity.this,WatermarkDetectorApp.class);
				i.setAction("image_timeout");
				startActivity(i);
				finish();
			}
		}
	}
	
}
