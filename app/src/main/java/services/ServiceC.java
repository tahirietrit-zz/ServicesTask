package services;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

public class ServiceC extends Service {
    public static ServiceC self;
    int counter = 0;
    TimerTask timerTask;
    IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void initService() {
        timerTask = new TimerTask() {
            @Override
            public void run() {

                counter++;

                if (!isMyServiceRunning(ServiceA.class)) {
                    startService(new Intent(getApplicationContext(), ServiceA.class));
                }
                if (!isMyServiceRunning(ServiceB.class)) {
                    startService(new Intent(getApplicationContext(), ServiceB.class));
                }

            }
        };
        new Timer().scheduleAtFixedRate(timerTask, 1000, 1000);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initService();
        self = this;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void publishErrorMessages(String clientMessages) {
        System.out.println("on Error");
    }

    public class LocalBinder extends Binder {
        public ServiceC getServerInstance() {
            return ServiceC.this;
        }
    }
}
