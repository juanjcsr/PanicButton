package com.apb.beacon.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Configures the settings for the SMS
 */
public class SMSSettings {
    public static final String PHONE_NUMBER = "PHONE_NUMBER_";
    public static final String SMS_MESSAGE = "SMS_MESSAGE";
    private static final int MASK_LIMIT = 2;
    public static final String BLANK = "";

    private List<String> phoneNumbers = new ArrayList<String>();
    private String message;

    public SMSSettings(List<String> phoneNumbers, String message) {
        this.phoneNumbers = phoneNumbers;
        this.message = message;
    }

    public static void save(Context context, SMSSettings smsSettings) {
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = settings.edit();
        int i = 0;
        for (String phoneNumber : smsSettings.phoneNumbers) {
            editor.putString(PHONE_NUMBER + i++, phoneNumber);
        }
        editor.putString(SMS_MESSAGE, smsSettings.message);
        editor.commit();
    }

    public static SMSSettings retrieve(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        Map<String, String> allPreferences = (Map<String, String>) sharedPreferences.getAll();
        List<String> retrievedPhoneNumbers = retrievePhoneNumbers(allPreferences);
        return new SMSSettings(retrievedPhoneNumbers, allPreferences.get(SMS_MESSAGE));
    }

    private static List<String> retrievePhoneNumbers(Map<String, String> allPreferences) {
        List<String> retrievedPhoneNumbers = new ArrayList<String>();
        List<String> sortedKeys = new ArrayList<String>(allPreferences.keySet());
        Collections.sort(sortedKeys);
        for (String preferenceKey : sortedKeys) {
            if (preferenceKey.startsWith(PHONE_NUMBER)) {
                retrievedPhoneNumbers.add(allPreferences.get(preferenceKey));
            }
        }
        return retrievedPhoneNumbers;
    }

    /**
     * Gets the message
     * @return the message
     */
    public String message() {
        return message == null? BLANK : message.trim();
    }

    /**
     * Gets the trimmed message
     * @return the trimmed messate
     */
    public String trimmedMessage() {
        String trimmedMessage = message();
        if(trimmedMessage.endsWith(".")) {
            return trimmedMessage.substring(0, trimmedMessage.length()-1);
        }
        return trimmedMessage;
    }

    /**
     * Gets the phone number at the selected index
     * @param index
     * @return the phone number
     */
    public String phoneNumberAt(int index) {
        return phoneNumbers.size() > index ? phoneNumbers.get(index) : BLANK;
    }

    /**
     * Shows the valid phone numbers
     * @return the list of phone numbers
     */
    public List<String> validPhoneNumbers() {
        List<String> validNumbers = new ArrayList<String>();
        for(String phoneNumber : phoneNumbers) {
            if(!TextUtils.isEmpty(phoneNumber)) validNumbers.add(phoneNumber);
        }
        return validNumbers;
    }


    public String maskedPhoneNumberAt(int index) {
        return mask(phoneNumberAt(index));
    }

    /**
     * Get the mask from a phone number
     * @param phoneNumber
     * @return the masked phone number
     */
    private String mask(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.length() < MASK_LIMIT) return phoneNumber;
        int length = phoneNumber.length();
        String prefix = phoneNumber.substring(0, length - MASK_LIMIT).replaceAll(".", "*");
        return prefix + phoneNumber.substring(length - MASK_LIMIT);
    }

    /**
     * Checks if the app has at least one phone number
     * @return true if the app is configured
     */
    public boolean isConfigured() {
        if (phoneNumbers == null || phoneNumbers.isEmpty()) return false;
        for (String phoneNumber : phoneNumbers) {
            if (!TextUtils.isEmpty(phoneNumber)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SMSSettings that = (SMSSettings) o;

        if (!message.equals(that.message)) return false;
        if (!phoneNumbers.equals(that.phoneNumbers)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = phoneNumbers.hashCode();
        result = 31 * result + message.hashCode();
        return result;
    }
}