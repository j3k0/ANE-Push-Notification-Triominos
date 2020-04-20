package com.freshplanet.ane.AirPushNotification;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class AirFirebaseMessagingService extends FirebaseMessagingService {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is also called
     * when the Instance ID token is initially generated, so this is where
     * you retrieve the token.
     */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.i(Extension.TAG, "[FirebaseMessagingService] onNewToken(): " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        try {
            super.onMessageReceived(remoteMessage);
            Log.i(Extension.TAG, "[FirebaseMessagingService] onMessageReceived() with" +
                    "\n   id: " + remoteMessage.getMessageId() +
                    "\n type: " + remoteMessage.getMessageType() +
                    "\n from: " + remoteMessage.getFrom() +
                    "\n data: " + remoteMessage.getData());
            RemoteMessage.Notification notif = remoteMessage.getNotification();
            if (notif != null) {
                Log.i(Extension.TAG, "[FirebaseMessagingService] RemoteMessage has a Notification with " +
                        "\ntitle: " + notif.getTitle() +
                        "\nbody: " + notif.getBody() +
                        "\nchannelId: " + notif.getChannelId() +
                        "\nclickAction: " + notif.getClickAction() +
                        "\ncolor: " + notif.getColor() +
                        "\neventTime: " + notif.getEventTime() +
                        "\nicon: " + notif.getIcon() +
                        "\nlink: " + notif.getLink() +
                        "\nsound: " + notif.getSound() +
                        "\ntag: " + notif.getTag() +
                        "\nticker: " + notif.getTicker() +
                        "\ntitleLocKey: " + notif.getTitleLocalizationKey() +
                        "\ntitleLocArgs: " + notif.getTitleLocalizationArgs() +
                        "\nbodyLocKey: " + notif.getBodyLocalizationKey() +
                        "\nbodyLocArgs: " + notif.getBodyLocalizationArgs());
            }
        }
        catch (Exception e) {
            Log.w(Extension.TAG, "[FirebaseMessagingService] onMessageReceived() FAILED: " + e.toString());
        }
    }
} 

/*
String 	
getBody()
Gets the body of the notification, or null if not set.
String[] 	
getBodyLocalizationArgs()
Gets the variable string values to be used as format specifiers in the body localization key, or null if not set.
String 	
getBodyLocalizationKey()
Gets the string resource name to use to localize the body of the notification, or null if not set.
String 	
getChannelId()
Gets the channel id from the notification, or null if not set.
String 	
getClickAction()
Gets the action to be performed on the user opening the notification, or null if not set.
String 	
getColor()
Gets the color of the notification, or null if not set.
boolean 	
getDefaultLightSettings()
Gets whether or not the notification uses the default notification light settings.
boolean 	
getDefaultSound()
Gets whether or not the notification uses the default sound.
boolean 	
getDefaultVibrateSettings()
Gets whether or not the notification uses the default vibrate pattern.
Long 	
getEventTime()
Gets the eventTime from the notification.
String 	
getIcon()
Gets the image resource name of the icon of the notification, or null if not set.
Uri 	
getImageUrl()
Gets the image URL from the notification.
int[] 	
getLightSettings()
Gets the lightSettings from the notification.
Uri 	
getLink()
Gets the deep link from the notification, or null if not set.
boolean 	
getLocalOnly()
Gets whether or not this notification is only relevant to the current device.
Integer 	
getNotificationCount()
Gets the notificationCount from the notification.
Integer 	
getNotificationPriority()
Gets the notificationPriority from the notification.
String 	
getSound()
Gets the sound to be played when the notification is shown, or null if not set.
boolean 	
getSticky()
Gets whether or not the notification is considered sticky.
String 	
getTag()
Gets the tag of the notification, or null if not set.
String 	
getTicker()
Gets the ticker text from the notification.
String 	
getTitle()
Gets the title of the notification, or null if not set.
String[] 	
getTitleLocalizationArgs()
Gets the variable string values to be used as format specifiers in the title localization key, or null if not set.
String 	
getTitleLocalizationKey()
Gets the string resource name to use to localize the title of the notification, or null if not set.
long[] 	
getVibrateTimings()
Gets the vibrateTimings from the notification.
Integer 	
getVisibility()
Gets the visibility from the notification.
*/
