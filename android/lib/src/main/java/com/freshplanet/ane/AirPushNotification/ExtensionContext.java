/**
 * Copyright 2017 FreshPlanet
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.freshplanet.ane.AirPushNotification;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.adobe.fre.FREContext;
import com.adobe.fre.FREFunction;
import com.adobe.fre.FREObject;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.FirebaseApp;

import java.util.HashMap;
import java.util.Map;

public class ExtensionContext extends FREContext {

	public static FCMMessagingService messagingService;
    public static AirFirebaseMessagingService messagingService2;

	public ExtensionContext() {
    }
	
	@Override
	public void dispose() {
		Extension.context = null;
	}

	@Override
	public Map<String, FREFunction> getFunctions() {

		Map<String, FREFunction> functionMap = new HashMap<String, FREFunction>();
		
		functionMap.put("registerPush", regPushFunc);
		functionMap.put("setBadgeNb", new SetBadgeValueFunction());
		functionMap.put("sendLocalNotification", new LocalNotificationFunction());
		functionMap.put("setIsAppInForeground", new SetIsAppInForegroundFunction());
		functionMap.put("fetchStarterNotification", new FetchStarterNotificationFunction());
		functionMap.put("cancelLocalNotification", new CancelLocalNotificationFunction());
		functionMap.put("cancelAllLocalNotifications", new CancelLocalNotificationFunction());
		functionMap.put("getNotificationsEnabled", new GetNotificationsEnabledFunction());
		functionMap.put("openDeviceNotificationSettings", new GoToNotifSettingsFunction());
		functionMap.put("getCanSendUserToSettings", new GetCanSendUserToSettings());
		functionMap.put("storeNotifTrackingInfo", new StoreNotifTrackingInfo());
		functionMap.put("createNotificationChannel", new CreateNotificationChannel());
		functionMap.put("checkNotificationChannelEnabled", new CheckNotificationChannelEnabled());
		functionMap.put("log", new LogFunction());

		return functionMap;
	}

	private final FREFunction regPushFunc = new FREFunction() {
		@Override
		public FREObject call(FREContext freContext, FREObject[] freObjects) {
		try {
                // Log.d(Extension.TAG, "registerPush()");
                Extension.logToAIR("registerPush()");
				Context appContext = freContext.getActivity().getApplicationContext();
				if(NotificationManagerCompat.from(appContext).areNotificationsEnabled()) {
					Extension.context.dispatchStatusEventAsync("NOTIFICATION_SETTINGS_ENABLED", "");
				}
				else {
					Extension.context.dispatchStatusEventAsync("NOTIFICATION_SETTINGS_DISABLED", "");
				}
                // Extension.logToAIR("initializing FirebaseApp");
				// FirebaseApp.initializeApp(appContext);

                if (FirebaseApp.getInstance() == null) {
                    Extension.logToAIR("WARNING: Firebase.App is null!");
                }
                if (FirebaseInstanceId.getInstance(FirebaseApp.getInstance()) == null) {
                    Extension.logToAIR("WARNING: Firebase.InstanceId is null!");
                    return null;
                }

                Extension.logToAIR("Firebase.InstanceId.addOnSuccessListener()");
                FirebaseInstanceId
                    .getInstance()
                    .getInstanceId()
                    .addOnSuccessListener(freContext.getActivity(), new OnSuccessListener<InstanceIdResult>() {

                    @Override
                    public void onSuccess(InstanceIdResult instanceIdResult) {
                        String newToken = instanceIdResult.getToken();
                        Extension.logToAIR("Firebase.InstanceId.onSuccess() token: " + newToken);
                    }
                });

				// Task<InstanceIdResult> task =
                Extension.logToAIR("FirebaseInstanceId.addOnCompleteListener()");
                FirebaseInstanceId
                    .getInstance()
                    .getInstanceId()
                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {

					@Override
					public void onComplete(@NonNull Task<InstanceIdResult> task) {
						if (!task.isSuccessful()) {
							Exception e = task.getException();
							Extension.logToAIR("Firebase.InstanceId.onComplete() FAILED with error: " + e);
							Extension.context.dispatchStatusEventAsync("TOKEN_FAIL", e.getMessage());
							return;
						}
						// Get new Instance ID token
						String token = task.getResult().getToken();
						if (token != null) {
                            Extension.logToAIR("Firebase.InstanceId.onComplete() token: " + token);
							Extension.context.dispatchStatusEventAsync("TOKEN_SUCCESS", token);
						} else {
                            Log.d(Extension.TAG, "Firebase.InstanceId.onComplete() NO TOKEN");
							Extension.context.dispatchStatusEventAsync("TOKEN_FAIL", "NULL_TOKEN");
						}
					}
				});
				// Boolean complete = task.isComplete();
				// Boolean canceled = task.isCanceled();
				// Boolean successful = task.isSuccessful();
			} catch (Exception e) {
				Log.e(Extension.TAG, "Firebase ERROR", e);
                Extension.logToAIR("Firebase ERROR: " + e);
			}
			return null;
		}
	};
}

