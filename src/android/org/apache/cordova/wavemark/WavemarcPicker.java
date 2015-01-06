/*
       Licensed to the Apache Software Foundation (ASF) under one
       or more contributor license agreements.  See the NOTICE file
       distributed with this work for additional information
       regarding copyright ownership.  The ASF licenses this file
       to you under the Apache License, Version 2.0 (the
       "License"); you may not use this file except in compliance
       with the License.  You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing,
       software distributed under the License is distributed on an
       "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
       KIND, either express or implied.  See the License for the
       specific language governing permissions and limitations
       under the License.
 */
package org.apache.cordova.wavemark;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.media.RingtoneManager;

import org.apache.cordova.PluginResult;

import android.util.Log;
import android.content.Intent;
import android.provider.Settings;
import android.net.Uri;
import android.app.Activity;
import android.media.Ringtone;

import org.json.JSONObject;

import de.fraunhofer.sit.watermarking.algorithmmanager.detector.WatermarkDetector;
/**
 * This class provides access to vibration on the device.
 */
public class WavemarcPicker extends CordovaPlugin {
	public String notification_uri;
	private CallbackContext callbackContext = null;
	/**
	 * Constructor.
	 */
	public WavemarcPicker() {
	}

	@Override
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		this.callbackContext = callbackContext;
		if (action.equals("getRingtone")) { 
			Log.d("customPlugin", " getRingtone ");

			
			
			
			Runnable getWavmarc = new Runnable() {

				@Override
				public void run() {
					Intent intent = new Intent(cordova.getActivity() , WatermarkDetector.class); 
					cordova.setActivityResultCallback(WavemarcPicker.this);
					cordova.getActivity().startActivityForResult(intent, 1);
				}
			};
			this.cordova.getActivity().runOnUiThread(getWavmarc);
			
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
	}

}
