package org.apache.cordova.wavemark;


import java.io.ByteArrayInputStream;
//import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.ArrayList;
//import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONObject;
import org.apache.cordova.wavemark.DatabaseHandler;
import org.apache.cordova.wavemark.UtilFunctions;

//import yw.wemet.ngageapp.wavemark.databasehandler.DatabaseHandler;
//import yw.wemet.ngageapp.wavemark.json.code_item_list;
//import yw.wemet.ngageapp.wavemark.sound.AudioRecorderMemory;
//import yw.wemet.ngageapp.wavemark.util.LiveLogBuffer;
import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.hardware.Camera;
import android.location.LocationManager;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
//import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView.OnQRCodeReadListener;
import com.google.zxing.client.android.camera.open.CameraManager;
import com.yourwellness.ngage.R;

import de.fraunhofer.sit.watermarking.algorithmmanager.AlgorithmParameter;
import de.fraunhofer.sit.watermarking.algorithmmanager.WatermarkMessage;
import de.fraunhofer.sit.watermarking.algorithmmanager.detector.StreamWatermarkDetector;
import de.fraunhofer.sit.watermarking.algorithmmanager.exception.WatermarkException;
import de.fraunhofer.sit.watermarking.sitmark.audio.SITMarkAudioAnnotationDetector;

public class WatermarkDetectorApp extends Activity implements WavemarkHelper.communication,OnQRCodeReadListener {

	private Button buttonStartRecord;
	private TextView logView;
	Button lbt;
	TextView tve;
	TextView tve1;
	TextView tv1;
	TextView tvimageurl;
	ImageButton btnBack;
	//	static ListView listview;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	String title;
	String description;
	String img_url;
	String img_link;
	Uri fileUri;
	//	static List<code_item_list> listContacts;
	private String SAMPLE_DB_NAME = "wavemark";
	public static Bitmap img;

	public static String lastcode;

	public static String lastbarcode;
	String wavecode;

	String barcode;

	String responsetype;
	//	DatabaseHandler db;

	//ArrayList<HashMap<String, String>> arraylist=new ArrayList<HashMap<String, String>>();

	RecoderThread recThread;

	JSONObject jsonobject;
	JSONObject jsonarray;


	String[] sqltemp = new String[5];

	public static String RANK = "title";
	public static String COUNTRY = "img_link";
	public static String POPULATION = "description";
	public static String FLAG = "img_url";
	public static String CODE = "img_url";

	private int m_interval = 1000; // 1 seconds by default, can be changed later
	private Handler m_handler;
	private QRCodeReaderView mydecoderview;
	//	AudioRecorderMemory mSensor;
	private TextView myTextView;
	public Bitmap bitmap; 
	public ImageView ivImage;
	Camera cam;
	CameraManager camManager;
	boolean safeToTakePicture = false;

	//	private static final String SITMARK_AUDIO_ANNOTATION_ALGO_NAME = "SITMarkAudio2M";
	private static final String AUDIOANN_MESSAGE_LENGTH_PARAM = "NetMessageLength";
	public static final String AUDIOANN_FREQ_MIN_PARAM = "FreqMin";
	public static final String AUDIOANN_FREQ_MAX_PARAM = "FreqMax";
	public static final String AUDIOANN_ECC_TYPE_PARAM = "ErrorCorrectionType";
	public static final String AUDIOANN_WM_REDUNDANCY_PARAM = "WMRedundancy";
	WavemarkAdapter adapter;
	ResponseModal[] response;
	boolean isAllowed = false;

	private AudioRecord mAudioRecord;
	public Context context;

	private static int   sampleRateInHz = 44100;
	// AudioRecord 
	private int mBufSize;

	private byte[] bytes_pkg;

	private static boolean isPaly = true;
	//	private ArrayList<Byte[]> que = new ArrayList<Byte[]>();

	int numberOfFoundMessage = 0 ;
	public ListView lvResponse;
	List<ResponseModal> list;
	WavemarkHelper wavemarkHelper;
	LocationManager locationManager;
	Camera camera;

	SITMarkAudioAnnotationDetector detector;
	private DatabaseHandler db;

	public static boolean isGPSCheckON = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.wavemark_layout);
		context = this;
		lvResponse = (ListView) findViewById(R.id.lvResponse);
		adapter = new WavemarkAdapter(this);
		list = new ArrayList<ResponseModal>();
		lvResponse.setAdapter(adapter);
		db = new DatabaseHandler(context);
		if(db.get_all_wavemark() != null && db.get_all_wavemark().size() > 0){
			list = db.get_all_wavemark();
			adapter.UpdateResponse(list);
		}

		//		wavemarkHelper = new WavemarkHelper(context);
		wavemarkHelper = WavemarkHelper.getInstance(context);
		//		init();
		System.out.println(" wavemark onCreate was called");
		//		ivImage = (ImageView) findViewById(R.id.ivImage);
		m_handler = new Handler();
		//		recThread = new RecoderThread();
		WebView view  =  (WebView) findViewById(R.id.webView1);
		view.setWebChromeClient(new WebChromeClient());
		view.getSettings().setJavaScriptEnabled(true);
		view.getSettings().setAllowFileAccess(true);
		// view.setWebViewClient(new WebViewClient());
		view.getSettings().setLoadWithOverviewMode(true);
		view.getSettings().setUseWideViewPort(true);
		view.loadUrl("file:///android_asset/aswf.html");

		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		boolean statusOfGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if(!statusOfGPS) {
			if(isGPSCheckON)
				wavemarkHelper.showGPSDialog(WatermarkDetectorApp.this);
		}


		myTextView = (TextView) findViewById(R.id.textView3);
		// tve =(TextView)findViewById(R.id.textView1);
		tv1 =(TextView)findViewById(R.id.textView2);
		// TODO Auto-generated method stub
		Log.v("Main act", "Main act1");

		//		AudioRecorderMemory mSensor = new AudioRecorderMemory(WatermarkDetectorApp.this);
		//		mSensor.startLive(WatermarkDetectorApp.this);
		//
		//		startRepeatingTask();

		if(getIntent() != null){
			Intent i = getIntent();
			String action = i.getAction();
			if(action != null && action.equalsIgnoreCase("image_saved")){
				String path = i.getStringExtra("path");
				Intent serviceIntent = new Intent(context,ImageService.class);
				serviceIntent.putExtra("path", path);
				serviceIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startService(serviceIntent);
				UtilFunctions.showLoader(context);
			}
		}

		initCaptureImage();
	}
	private void initCaptureImage() {
		final RelativeLayout rlShowImage = (RelativeLayout)findViewById(R.id.Watermark_rlShowImage);
		final Button btnCaptureImg = (Button)findViewById(R.id.Watermark_btnCaptureImage);
		final ImageView ivShowImg = (ImageView)findViewById(R.id.Watermark_ivShowImage);
		Button btnOk = (Button)findViewById(R.id.Watermark_btnOk);
		Button btnCancel = (Button)findViewById(R.id.Watermark_btnCancel);

		btnCaptureImg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mydecoderview.registerImageCallback(new com.dlazaro66.qrcodereaderview.QRCodeReaderView.imageCallback() {

					@Override
					public void getResult(Bitmap imgBitmap) {
						if(imgBitmap!=null)
						{
							rlShowImage.setVisibility(View.VISIBLE);
							btnCaptureImg.setVisibility(View.GONE);
							Toast.makeText(WatermarkDetectorApp.this,"Image Captured", Toast.LENGTH_LONG).show();
							ivShowImg.setImageBitmap(imgBitmap);
						}else
						{
							Toast.makeText(WatermarkDetectorApp.this,"image not Captured", Toast.LENGTH_LONG).show();
						}

					}
				});
				mydecoderview.captureImage();
			}
		});

		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rlShowImage.setVisibility(View.GONE);
				btnCaptureImg.setVisibility(View.VISIBLE);
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				rlShowImage.setVisibility(View.GONE);
				btnCaptureImg.setVisibility(View.VISIBLE);
			}
		});



	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println(" wavemark onStart was called");
	}

	public boolean isCameraUsebyApp() {
		Camera camera = null;
		try {
			camera = Camera.open();
		} catch (RuntimeException e) {
			return true;
		} finally {
			if (camera != null) camera.release();
		}
		return false;
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println(" wavemark onResume was called");
		init();

		mBufSize = AudioRecord.getMinBufferSize(sampleRateInHz, AudioFormat.CHANNEL_CONFIGURATION_MONO, 
				AudioFormat.ENCODING_PCM_16BIT);
		System.out.println("mBufSize = " + mBufSize);
		mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,sampleRateInHz, AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT, mBufSize);

		//		mydecoderview.getHolder().addCallback(mydecoderview);
		//		camManager.getCamera().setPreviewCallback(mydecoderview);
		//		if(camManager.isOpen()){
		//		camManager.getCamera().release();	
		//		}
		//		isCameraUsebyApp();
		try{
			//			camManager.getCamera().setPreviewCallback(cb)
			camManager.startPreview();
		}catch(Exception e){
			e.printStackTrace();
		}

		//		}

		try {
			isPaly = true;
			//			Toast.makeText(context, outputMessage, Toast.LENGTH_LONG).show();
			//			mResultTextView.setText(outputMessage);

			//			if(recThread != null && !recThread.isAlive())
			recThread = new RecoderThread();
			recThread.start();
		} catch (Exception e) {
			e.printStackTrace();
		}

		locationManager.requestLocationUpdates(
				LocationManager.NETWORK_PROVIDER, 5000, 10,
				wavemarkHelper.networkLocationListener);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000, 10, wavemarkHelper.gpsListener);

		LocalBroadcastManager.getInstance(context).registerReceiver(receiver, new IntentFilter(Constants.INTENT_IMAGE_RESPONSE));
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println(" wavemark onPause was called");
		if(camManager != null)
			camManager.stopPreview();

		mAudioRecord.startRecording();
		recThread = null;
		isPaly = false;
		//		mResultTextView.setText("Ready");
		numberOfFoundMessage = 0;
		detector = null;
		LocalBroadcastManager.getInstance(context).unregisterReceiver(receiver);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		if(mAudioRecord != null)
			mAudioRecord.release();

		locationManager.removeUpdates(wavemarkHelper.networkLocationListener);
		locationManager.removeUpdates(wavemarkHelper.gpsListener);

	}

	private void testDirectly(InputStream stream) {
		try {
			if (detector == null) {
				System.out.println("Testing SITMarkAudio annotation detector *without* AlgorithmManager");
				detector = new SITMarkAudioAnnotationDetector();
				System.out.println("Initializing parameters...");
				detector.initWithoutAlgoman();

				AlgorithmParameter messageLength = detector.getParameter(AUDIOANN_MESSAGE_LENGTH_PARAM);
				AlgorithmParameter minFreq= detector.getParameter(AUDIOANN_FREQ_MIN_PARAM);
				AlgorithmParameter maxFreq= detector.getParameter(AUDIOANN_FREQ_MAX_PARAM);
				AlgorithmParameter ECCMode= detector.getParameter(AUDIOANN_ECC_TYPE_PARAM);
				AlgorithmParameter WatermarkRedundancy= detector.getParameter(AUDIOANN_WM_REDUNDANCY_PARAM);

				messageLength.setValue("24");
				minFreq.setValue("2000");
				maxFreq.setValue("10000");
				ECCMode.setValue("1");
				WatermarkRedundancy.setValue("2");

				detector.setParameter(messageLength);
				detector.setParameter(minFreq);
				detector.setParameter(maxFreq);
				detector.setParameter(ECCMode);
				detector.setParameter(WatermarkRedundancy);
				detector.reinitialize();

				System.out.println("Starting detection from white noise file...");
			}


			detectFromStream(detector, null, stream);
			System.out.println("Finished testing SITMarkAudio annotation detector. All is well.");

		} catch (WatermarkException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void detectFromStream(StreamWatermarkDetector detector,	
			WatermarkMessage expectedMessage, InputStream markedWhiteNoise)	
					throws WatermarkException {
		List<WatermarkMessage> detectedMessages;
		boolean foundMessage = false;

		Set<String> distinctFoundMessages = new TreeSet<String>();

		do {
			detectedMessages = detector.detect(markedWhiteNoise);
			foundMessage = (detectedMessages.size() > 0);
			if (foundMessage) {
				Log.i("Mark", foundMessage + "");
				WatermarkMessage detectedMessage = detectedMessages.get(0);
				double confidence = Double.parseDouble(detectedMessage.getMetaDate(WatermarkMessage.SCORE_METADATA));

				if (confidence > 0.1) {
					distinctFoundMessages.add(detectedMessage.toString());
					String code = detectedMessage.toString();

					response = wavemarkHelper.sendCode(code,"","");
					//					Iterator<ResponseModal> it = list.iterator();
					list = wavemarkHelper.populateList(list, response);
					if(!list.isEmpty())
					{
						for (ResponseModal mod : list){

							String description = mod.getDescription();
							int error = mod.getErrorCode();
							long id = mod.getId();
							String image_id = mod.getImageId();

							String image_url = mod.getImageUrl();
							String meta= mod.getMetadata();
							String status = mod.getStatus();
							String title = mod.getTitle();
							String url = mod.getUrl();
							String project_id = mod.getProject_id();
							String strt_date = mod.getStart_Date();
							String end_date= mod.getEnd_Date();
							String start_time = mod.getStart_time();
							String end_time = mod.getEnd_time();
							String expiry_message = mod.getExpiry_message();


							db.insert_wavemark_data(description,String.valueOf(error),String.valueOf(id),image_id,image_url,meta,
									status,title,url,project_id,strt_date,end_date,start_time,end_time,expiry_message);
						}
					}

					list = db.get_all_wavemark();
					lvResponse.post(new Runnable(){

						@Override
						public void run() {
							// TODO Auto-generated method stub
							adapter.UpdateResponse(list);
						}

					});


					numberOfFoundMessage++;
					System.out.println("++++ " + detectedMessage.toString());

				}
			}
		} while (foundMessage);
		Log.i("Mark", String.format("Found %d messages (%d distinct): %s", numberOfFoundMessage, distinctFoundMessages.size(), distinctFoundMessages));
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		super.onStop();
		System.out.println(" wavemark onStop was called");
		mAudioRecord.release();
		mAudioRecord = null;
	}

	public class RecoderThread extends Thread {


		@Override
		public void run() {
			super.run();
			if(mAudioRecord != null)
				mAudioRecord.startRecording();
			System.out.println(" Recoder THread is running 1");
			while (isPaly)
			{
				System.out.println(" Recoder THread is running ");
				byte[] bytes = new byte[mBufSize];
				mAudioRecord.read(bytes, 0, mBufSize);
				System.out.println("write date to bytes...");
				bytes_pkg = bytes.clone();
				//Log.i("Mark", "........recordSound bytes_pkg==" + bytes_pkg.length);
				//Log.i("Mark", Arrays.toString(bytes_pkg));
				ByteArrayInputStream stream = new ByteArrayInputStream(bytes_pkg);
				testDirectly(stream);
			}
		}


	}



	@Override
	public void QRCodeNotFoundOnCamImage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cameraNotFound() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onQRCodeRead(String text, PointF[] points, Bitmap img1) {
		// TODO Auto-generated method stub

		img=img1;

		myTextView.setText(text);

		barcode=text;

		if(barcode.equals(lastbarcode)){

		}else{
			Log.v("if", "false json got");
			//	 new DownloadJSON().execute();
			//			new DownloadJSON(this,barcode).execute();
			new Thread(){
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					ResponseModal[] res = 	wavemarkHelper.sendCode(barcode, "", "");
					updateList(res);
				}
			}.start();
			lastbarcode=barcode;
		}
	}


	Runnable m_statusChecker = new Runnable()
	{
		@Override 
		public void run() {
			//  updateStatus(); //this function can change value of m_interval.

			//  lbt.performClick();
			//			dosamething();
			m_handler.postDelayed(m_statusChecker, m_interval);
		}
	};

	void startRepeatingTask()
	{
		m_statusChecker.run(); 
	}

	void stopRepeatingTask()
	{
		m_handler.removeCallbacks(m_statusChecker);
	}


	//	public void dosamething(){
	//
	//		String unscode = LiveLogBuffer.getInstance().getLastLogs(1);
	//		String data = unscode ;
	//		String[] items = data.split("x");
	//		for (String item : items)
	//		{
	//			//	tve1.setText("item = " + item);
	//			//	String datain = unscode ;
	//			wavecode=	removeLastChar(item);
	//			String dump="";
	//			tv1.setText(wavecode);
	//			Log.v("d code", wavecode);
	//
	//			if(wavecode.equals(dump)){
	//				Log.v("d code", wavecode);
	//				Log.v("if", "true");
	//			}else{
	//				if(wavecode.length()>1){
	//					if(wavecode.equals(lastcode)){
	//
	//					}else{
	//						Log.v("if", "false json got");
	//						new Thread(){
	//							@Override
	//							public void run() {
	//								// TODO Auto-generated method stub
	//								super.run();
	//								ResponseModal[] res = 	wavemarkHelper.sendCode(barcode, "", "");
	//								updateList(res);
	//							}
	//						}.start();
	//						lastcode=wavecode;
	//					}
	//				}
	//			}
	//		}
	//	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub 

		super.onBackPressed();
	}


	public String removeLastChar(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}

		s=s.replace("(", " ");
		s=s.replace(" ", "0");
		return s.substring(0, Math.min(s.length(), 6)); 


	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println(" wavemark onRestart was called");
		//	mydecoderview.getHolder().addCallback(mydecoderview);
		//	camManager.getCamera().setPreviewCallback(mydecoderview);

		//	camManager.startPreview();
	}

	public void init(){
		isCameraUsebyApp();
		btnBack = (ImageButton) findViewById(R.id.btnBack);
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
			}
		});

		mydecoderview = (QRCodeReaderView) findViewById(R.id.qRCodeReaderView1);
		mydecoderview.setOnQRCodeReadListener(this);
		camManager = mydecoderview.getCameraManager();
		camera = camManager.getCamera();

		mydecoderview.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//				camManager.stopPreview();

				//								camManager.getCamera().takePicture(null, null, new PictureCallback() {
				//									
				//									@Override
				//									public void onPictureTaken(byte[] data, Camera camera) {
				//										// TODO Auto-generated method stub
				//										
				//									}
				//								});
				////								try {					
				////									camManager.getCamera().reconnect();
				////								} catch (IOException e) {
				////									// TODO Auto-generated catch block
				////									e.printStackTrace();
				////								}
				//								mydecoderview.getHolder().removeCallback(mydecoderview);
				//								camManager.getCamera().setPreviewCallback(null);
				//								camManager.getCamera().release();
				//								init();
				////				mydecoderview.getHolder().addCallback(mydecoderview);
				////				camManager.getCamera().setPreviewCallback(mydecoderview);	
				camManager.stopPreview();
				try{
					//				mydecoderview.getHolder().removeCallback(mydecoderview);
					camManager.getCamera().setPreviewCallback(null);
					mydecoderview.getHolder().removeCallback(mydecoderview);
					camManager.getCamera().release();
				}catch(Exception e){
					e.printStackTrace();
				}
				finish();
				//				camera = null;

				Intent i = new Intent(WatermarkDetectorApp.this,DummyActivity.class);
				startActivity(i);

				//				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				//				fileUri = Uri.fromFile(wavemarkHelper.getOutputPhotoFile());
				//				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				//				startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ );

			}

		});



	}



	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		System.out.println("wavemark onActivity result is called 1");
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQ) {
			System.out.println(" wavemark onActivity result is called ");
			if (resultCode == RESULT_OK) {
				isCameraUsebyApp();
				Uri photoUri = null;
				if (data == null) {
					// A known bug here! The image should have saved in fileUri
					Toast.makeText(this, "Image saved successfully", 
							Toast.LENGTH_LONG).show();
					photoUri = fileUri;
					Intent i = new Intent(WatermarkDetectorApp.this,ImageService.class);
					i.putExtra("path", photoUri.getPath());
					startService(i);
					//					new UploadImage(photoUri.getPath()).execute();

				} else {
					photoUri = data.getData();
					Toast.makeText(this, "Image saved successfully in: " + data.getData(), 
							Toast.LENGTH_LONG).show();
					Intent i = new Intent(WatermarkDetectorApp.this,ImageService.class);
					i.putExtra("path", photoUri.getPath());
					startService(i);
				}
				//				showPhoto(photoUri.getPath());
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show();
				isCameraUsebyApp();
			} else {
				Toast.makeText(this, "Callout for image capture failed!", 
						Toast.LENGTH_LONG).show();
				isCameraUsebyApp();
			}
		}
	}

	final Handler uiHandler = new Handler();

	@Override
	public void update(ResponseModal[] resp) {
		// TODO Auto-generated method stub

		//		Iterator<ResponseModal> it = list.iterator();
		if(response != null)
		{
			list = wavemarkHelper.populateList(list, response);
			if(!list.isEmpty())
			{
				for (ResponseModal mod : list){

					String description = mod.getDescription();
					int error = mod.getErrorCode();
					long id = mod.getId();
					String image_id = mod.getImageId();

					String image_url = mod.getImageUrl();
					String meta= mod.getMetadata();
					String status = mod.getStatus();
					String title = mod.getTitle();
					String url = mod.getUrl();
					String project_id = mod.getProject_id();
					String strt_date = mod.getStart_Date();
					String end_date= mod.getEnd_Date();
					String start_time = mod.getStart_time();
					String end_time = mod.getEnd_time();
					String expiry_message = mod.getExpiry_message();

					db.insert_wavemark_data(description,String.valueOf(error),String.valueOf(id),image_id,image_url,
							meta,status,title,url,project_id,strt_date,end_date,start_time,end_time,expiry_message);
				}
			}
			list = db.get_all_wavemark();
			lvResponse.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub

					adapter.UpdateResponse(list);
				}

			});


		}
	}



	@Override
	public void updateList(ResponseModal[] response) {
		// TODO Auto-generated method stub

		//		Iterator<ResponseModal> it = list.iterator();
		if(response != null)
		{
			list = wavemarkHelper.populateList(list, response);
			if(!list.isEmpty())
			{
				for (ResponseModal mod : list) {				

					String description = mod.getDescription();
					int error = mod.getErrorCode();
					long id = mod.getId();
					String image_id = mod.getImageId();

					String image_url = mod.getImageUrl();
					String meta= mod.getMetadata();
					String status = mod.getStatus();
					String title = mod.getTitle();
					String url = mod.getUrl();
					String project_id = mod.getProject_id();
					String strt_date = mod.getStart_Date();
					String end_date= mod.getEnd_Date();
					String start_time = mod.getStart_time();
					String end_time = mod.getEnd_time();
					String expiry_message = mod.getExpiry_message();


					db.insert_wavemark_data(description,String.valueOf(error),String.valueOf(id),image_id,image_url,meta,
							status,title,url,project_id,strt_date,end_date,start_time,end_time,expiry_message);
				}
			}
			list = db.get_all_wavemark();
			lvResponse.post(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub

					adapter.UpdateResponse(list);
				}

			});


		}
	}

	@Override
	public void errorDialog(String err) {
		// TODO Auto-generated method stub
		System.out.println(" wavemark 2" + err);
		//		Toast.makeText(context, err, Toast.LENGTH_LONG);
		//				final String error = err;
		//				runOnUiThread(new Runnable(){
		//		
		//					@Override
		//					public void run() {
		//						// TODO Auto-generated method stub
		//						UtilFunctions.getInstance().showAlert(context, error);					}
		//					
		//				});
		//		UtilFunctions.getInstance().showAlert(context, error);
	}
	@Override
	public void noMatchFound(String msg) {
		// TODO Auto-generated method stub
		//		showDialog("No Match found on the server for the image");
		//		closeLoader();

		System.out.println(" wavemark 1" + msg);
		//		Toast.makeText(context, msg, Toast.LENGTH_LONG);
		//		UtilFunctions.getInstance().showAlert(context, message);
		//		final String message = msg;
		//		runOnUiThread(new Runnable(){
		//
		//			@Override
		//			public void run() {
		//				// TODO Auto-generated method stub
		//				showDialog(message);
		//			}
		//			
		//		});
	}


	@Override
	public void finishActivity() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public boolean isNetworkAvailable() {
		// TODO Auto-generated method stub
		if(UtilFunctions.getInstance().isNetworkAvailable(WatermarkDetectorApp.this)){
			return true;
		}else{
			return false;
		}
	}

	public void	showDialog(String message){
		final Dialog dialog = new Dialog(context);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setContentView(R.layout.alert_dialog);

		Window window = dialog.getWindow();
		window.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		window.setGravity(Gravity.CENTER);

		TextView tvAlertMsg = (TextView) dialog.findViewById(R.id.tvMsg);
		tvAlertMsg.setText(message);

		Button btnOk = (Button) dialog.findViewById(R.id.btnOK);
		btnOk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
			}
		});

		dialog.show();
	}


	public BroadcastReceiver receiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub

			String type = intent.getStringExtra("type");
			if(type != null && type.equalsIgnoreCase("ErrorNoResponse"))
			{
				showDialog("An error occured while sending the image, Please try again");
				closeLoader();
			}else if(type != null && type.equalsIgnoreCase("Error")){

				showDialog("No response from the server for the image");
				closeLoader();
			}else if(type != null && type.equalsIgnoreCase("NoMatch")){
				showDialog("No Match Found on the server, for the image");
				closeLoader();
			}else{

				List<ResponseModal> resp = intent.getParcelableArrayListExtra("response");
				Iterator<ResponseModal> it = resp.iterator();
				ResponseModal[] res = new ResponseModal[resp.size()];
				int i=0;
				while(it.hasNext()){
					res[i] = it.next();
					i++;
				}
				updateList(res);
				closeLoader();
			}
		}

	};

	public void closeLoader(){
		try{
			UtilFunctions.closeLoader();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
