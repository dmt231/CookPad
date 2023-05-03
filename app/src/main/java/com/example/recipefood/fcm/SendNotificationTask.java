package com.example.recipefood.fcm;

import android.os.AsyncTask;
import android.util.Log;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SendNotificationTask extends AsyncTask<Void, Void, Integer> {
    private static final String FCM_API_URL = "https://fcm.googleapis.com/fcm/send";
    private static final String SERVER_KEY = "AAAAH4BXAuY:APA91bHhfqMFq4TY-EjADuJ3FsHYdbnPX4bQkoyI6CTsW18_rWABTwXjEMcebOZPZnjDvwyfWjrobWDgtWKqNiGVQFq_m8qVSG2UHzatgXf5zbEOWrDeGV6dovJ-Q755ktycBtujUwa6";
    private String topic;
    private String title;
    private String message;

    public SendNotificationTask(String topic, String title, String message) {
        this.topic = topic;
        this.title = title;
        this.message = message;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        try {
            URL url = new URL(FCM_API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Authorization", "key=" + SERVER_KEY);

            conn.setDoOutput(true);
            OutputStream os = conn.getOutputStream();
            String jsonPayload = buildJsonPayload(topic, title, message);
            os.write(jsonPayload.getBytes());
            os.flush();
            os.close();

            Log.d("TAG", "sendNotification: abc ");

            int responseCode = conn.getResponseCode();
            return responseCode;
        } catch (Exception e) {
            Log.e("TAG", e.toString());
            return -1;
        }
    }

    @Override
    protected void onPostExecute(Integer responseCode) {
        Log.d("TAG", "sendNotification: tét ");
        if (responseCode == HttpURLConnection.HTTP_OK) {
            Log.d("TAG", "sendNotification: success ");
        } else {
            Log.e("TAG", "sendNotification: fail ");
        }
    }

    private static String buildJsonPayload(String topic, String title, String message) {
        return "{"
                + "\"to\": \"/topics/" + topic + "\","
                + "\"notification\": {"
                + "\"title\": \"" + title + "\","
                + "\"body\": \"" + message + "\""
                + "}"
                + "}";
    }
}
