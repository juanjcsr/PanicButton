package com.apb.beacon.sms;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * Class in charge of sending SMS
 *
 * Created by Mikesaurio
 */
public class SMSAdapter {

    private static final String LOG_TAG = SMSAdapter.class.getName();

    /**
     * Method to send the SMS
     *
     * @param phoneNumber (numero al que se envia el mensaje)
     * @param message (mensaje de auxilio)
     * @param levelBattery (nivel de bateria)
     * @return void
     */
    public void sendSMS(String phoneNumber, String message,int levelBattery) {
        SmsManager smsManager = getSmsManager();
        try {
        //enviamos el mensaje
            smsManager.sendTextMessage(phoneNumber, null, message+ " i have "+levelBattery+ " of battery", null, null);
        } catch (Exception exception) {
            Log.e(LOG_TAG, "Sending SMS failed " + exception.getMessage());
        }
    }

    /**
     * Returns the SMSManaer
     * @return (android.telephony.SmsManager)
     */
    SmsManager getSmsManager() {
        return SmsManager.getDefault();
    }
}