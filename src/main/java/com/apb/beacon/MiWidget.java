package com.apb.beacon;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.RemoteViews;

import com.apb.beacon.alert.PanicAlert;

/**
 * Clase que controla el widget, este se activa al presionar 5 veces el reloj
 *
 * Created by mikesaurio on 04/12/13.
 */
public class MiWidget extends AppWidgetProvider {
    private static final String SYNC_CLICKED    = "automaticWidgetSyncButtonClick";
    private static int count = -1;
    private Handler handler = new Handler();
    public static boolean countTimer = true;

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        RemoteViews remoteViews;
        ComponentName watchWidget;

        remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
        watchWidget = new ComponentName(context, MiWidget.class);

        remoteViews.setOnClickPendingIntent(R.id.analogClock, getPendingSelfIntent(context, SYNC_CLICKED));
        appWidgetManager.updateAppWidget(watchWidget, remoteViews);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if (SYNC_CLICKED.equals(intent.getAction())&& count >= 5) {
            count = 0;
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

            RemoteViews remoteViews;
            ComponentName watchWidget;

            remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            watchWidget = new ComponentName(context, MiWidget.class);

            appWidgetManager.updateAppWidget(watchWidget, remoteViews);

            //llamamos el método de panico
            new PanicAlert(context).activate();

        }else{
            count += 1;
            //contamos 10 segundos si no reiniciamos los contadores
            if(countTimer){
                countTimer = false;
                handler.postDelayed(runnable, 10000);//10 segundos de espera
            }
        }
    }

    protected PendingIntent getPendingSelfIntent(Context context, String action) {
        Intent intent = new Intent(context.getApplicationContext(), getClass());
        intent.setAction(action);
        return PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, 0);
    }

    /**
     * hilo que al pasar el tiempo reeinicia los valores
     */
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //reiniciamos los contadores
            count = -1;
            countTimer = true;
        }
    };
}