package services;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import servicecallbacks.ServerCallback;

public class ServiceA extends Service {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    static OkHttpClient client = new OkHttpClient();

    public static void get(final String endpoint, final ServerCallback callback) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                Request request = new Request.Builder()
                        .url(endpoint)
                        .build();
                try {
                    return client.newCall(request).execute().toString();

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if (s != null) {
                    callback.onGetResponse(s);
                } else {
                    ServiceC.self.publishErrorMessages("An error has occurred");
                }
            }
        }.execute();
    }

    public static void post(final String endpoint, final String json, final ServerCallback callback) {
        new AsyncTask<String, String, String>() {
            @Override
            protected String doInBackground(String... strings) {
                RequestBody body = RequestBody.create(JSON, json);
                Request request = new Request.Builder()
                        .url(endpoint)
                        .post(body)
                        .build();
                Response response = null;
                try {
                    response = client.newCall(request).execute();
                    callback.onGetResponse(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();

                }
                return null;
            }
        }.execute();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
