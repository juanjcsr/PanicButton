package com.apb.beacon.alert;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.BatteryManager;

import com.apb.beacon.location.LocationFormatter;
import com.apb.beacon.model.SMSSettings;
import com.apb.beacon.sms.SMSAdapter;
import com.apb.beacon.twitter.ShortCodeSettings;
import com.apb.beacon.twitter.TwitterSettings;

import static android.telephony.SmsMessage.MAX_USER_DATA_SEPTETS;
import static com.apb.beacon.twitter.TwitterSettings.retrieve;

public class PanicMessage {
    public static final int TWITTER_MAX_LENGTH = 140;
    private Context context;
    private Location location;
    private  int levelBattery;//guarda el nuvel de bateria

    public PanicMessage(Context context) {
        this.context = context;
            this.context.registerReceiver(this.batteryInfoReceiver,	new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    public void send(Location location) {
        this.location = location;
        sendSMS();
        if (TwitterSettings.isEnabled(context)) {
            tweet(retrieve(context));
        }
    }

    private void sendSMS() {
        SMSSettings smsSettings = SMSSettings.retrieve(context);
        SMSAdapter smsAdapter = getSMSAdapter();
        String message = getSMSText(smsSettings.trimmedMessage());


        for (String phoneNumber : smsSettings.validPhoneNumbers()) {
            smsAdapter.sendSMS(phoneNumber, message,levelBattery);
        }
    }

    private void tweet(TwitterSettings twitterSettings) {
        SMSAdapter smsAdapter = getSMSAdapter();
        ShortCodeSettings shortCodeSettings = twitterSettings.getShortCodeSettings();
        String message = getTwitterText(twitterSettings.getMessage());
        smsAdapter.sendSMS(shortCodeSettings.getShortCode(), message,levelBattery);
    }

    private String getSMSText(String message) {
        return trimMessage(message, MAX_USER_DATA_SEPTETS);
    }

    private String getTwitterText(String message) {
        return trimMessage(message, TWITTER_MAX_LENGTH);
    }

    private String trimMessage(String message, int maxLength) {
        String locationString = new LocationFormatter(location).format();
        String smsText = message + locationString;
        if(smsText.length() > maxLength) {
            smsText = message.substring(0, (maxLength - locationString.length()) ) + locationString;
        }
        return smsText;
    }

    SMSAdapter getSMSAdapter() {
        return new SMSAdapter();
    }

    /**
     * BroadcastReceiver que obtiene el nivel de la bateria
     */
    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            levelBattery= intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        }
    };

}