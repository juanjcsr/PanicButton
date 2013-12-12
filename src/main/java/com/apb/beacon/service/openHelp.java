package com.apb.beacon.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import com.apb.beacon.alert.PanicAlert;

/**
 * Created by mikesaurio on 03/12/13.
 */
public class openHelp extends Service {
    private BroadcastReceiver mReceiver;
    private Intent intent;
    private static int countStart = -1;
    private Handler handler = new Handler();
    public static boolean countTimer = true;



    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public void onCreate() {
       //al crear el servicio
        super.onCreate();
        //obtenemos los filtros para el control de la pantalla (apagada-prendida)
        IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        mReceiver = new MyReceiver();
        registerReceiver(mReceiver, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);

    }

    @Override
    public void onStart(Intent intent, int startId) {
        //revisamos si la pantalla esta prendida o apagada y contamos el numero de click al boton de apagado
        boolean screenOn = intent.getBooleanExtra("screen_state", false);
       //si damos más de 4 click al boton de apagado se activa la alarma
        if(countStart >= 4){
          //  Log.i("*********", "mas de 4");
            countStart = -1;
            countTimer = true;
            //activamos el mensaje de auxilio
            new PanicAlert(this.getApplicationContext()).activate();

        }else{
            countStart += 1;
          //  Log.i("*********", "else "+ countStart+"");
          //  incrementamos los click en 1

            //contamos 10 segundos si no reiniciamos los contadores
            if(countTimer){
                countTimer = false;
                handler.postDelayed(runnable, 10000);//10 segundos de espera
            }
        }
    }

    /**
     * hilo que al pasar el tiempo reeinicia los valores
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //reiniciamos los contadores
            countStart = -1;
            countTimer = true;
        }
    };
}
