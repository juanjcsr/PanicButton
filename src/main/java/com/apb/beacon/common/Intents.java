package com.apb.beacon.common;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;


/**
 * The Intents
 */
public class Intents {
    private static final String PREFIX = "com.apb.beacon";
    public static final String LOCATION_UPDATE_ACTION = PREFIX + ".LOCATION_UPDATE_ACTION";
    public static final String SEND_ALERT_ACTION = PREFIX + ".SEND_ALERT_ACTION";
    public static final String ACTIVATE_ALERT_ACTION = PREFIX + ".ACTIVATE_ALERT_ACTION";

    public static PendingIntent alarmPendingIntent(Context context) {
        return PendingIntent.getBroadcast(context, 0, new Intent(SEND_ALERT_ACTION), FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent locationPendingIntent(Context context) {
        return PendingIntent.getBroadcast(context, 0, new Intent(LOCATION_UPDATE_ACTION), FLAG_UPDATE_CURRENT);
    }
}
