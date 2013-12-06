package com.apb.beacon.sms;

import android.telephony.SmsManager;
import android.util.Log;

/**
 * clase que se encarga del envio de SMS
 *
 * Created by Mikesaurio
 */
public class SMSAdapter {

    private static final String LOG_TAG = SMSAdapter.class.getName();

    /**
     * Metodo para el envio de mensage
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
     * metodo que regresa el SmsManager para el envio de mensaje
     * @return (android.telephony.SmsManager)
     */
    SmsManager getSmsManager() {
        return SmsManager.getDefault();
    }
}