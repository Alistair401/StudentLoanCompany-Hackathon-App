package guts.carpaltunnel.mobileapp.util;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class FormManager extends AsyncTask<URL, Integer, Long> {
    private JSONObject json = new JSONObject();

    public void submit() throws IOException {
        System.out.println(json.toString());
        this.execute();
    }

    public void setField(String key, Object value) {
        try {
            json.put(key, value);
            System.out.println("key: " + key + " value: " + value);
            System.out.println(json.toString());
        } catch (JSONException ignored) {
        }
    }

    public void clearForm() {
        json = new JSONObject();
    }

    @Override
    protected Long doInBackground(URL... urls) {
        try {
            URL endpoint;

            endpoint = new URL("http://default-environment.xnjdwwknda.us-west-2.elasticbeanstalk.com/submit");

            HttpURLConnection connection;

            connection = (HttpURLConnection) endpoint.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            OutputStream os;
            OutputStreamWriter osw;

            os = connection.getOutputStream();
            osw = new OutputStreamWriter(os, "UTF-8");
            osw.write(json.toString());
            osw.flush();
            osw.close();

            Log.d("DEBUG",Integer.toString(connection.getResponseCode()));
        } catch (IOException ignored){

        }
        return null;
    }
}
