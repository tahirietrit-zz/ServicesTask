package services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import servicecallbacks.ServerCallback;


public class ServiceB extends Service implements ServerCallback {
    static ServiceB self;
    final String METHOD_KEY = "method";
    final String GET_KEY = "get";
    final String POST_KEY = "post";
    final String URL_KEY = "url";
    final String BODY_KEY = "body";
    String LOG = "Service B: ";

    public static ServiceB sharedInstance() {
        return self;
    }

    public static void getDataFromServer(String url) {
        ServiceA.get(url, sharedInstance());
    }

    public static void postDataToServer(String url, String body) {
        ServiceA.post(url, body, sharedInstance());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getStringExtra(METHOD_KEY).equalsIgnoreCase(GET_KEY)) {
            getDataFromServer(intent.getStringExtra(URL_KEY));
        }
        if (intent.getStringExtra(METHOD_KEY).equalsIgnoreCase(POST_KEY)) {
            String url = intent.getStringExtra(URL_KEY);
            String body = intent.getStringExtra(BODY_KEY);
            postDataToServer(url, body);
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        self = this;
    }

    @Override
    public void onGetResponse(String serverResponse) {
        Log.d(LOG, serverResponse);
    }

    @Override
    public void onPostResponse(String serverResponse) {
        Log.d(LOG, serverResponse);
    }
}
