package com.apb.beacon.trigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import static android.content.Intent.ACTION_BOOT_COMPLETED;


/**
 * Implements BroadcastREceiver ACTION_BOOT_COMPLETED to start the service after
 * a REBOOT or after the device has been turned on
 */
public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_BOOT_COMPLETED)) {
            context.startService(new Intent(context, HardwareTriggerService.class));
        }
    }
}
