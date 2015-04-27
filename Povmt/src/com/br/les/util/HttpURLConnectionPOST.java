
package com.br.les.util;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class HttpURLConnectionPOST extends AsyncTask<String, Void, Void> {
    
    private static final String HEADERVALUE = "application/json";

    public final void sendPostJson(String json, String email) {
        try {
            new HttpURLConnectionPOST().execute(json, email).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static HttpResponse makeRequest(final String uri, final String json) {
        try {
            HttpPost httpPost = new HttpPost(uri);
            httpPost.setEntity(new StringEntity(json));
            httpPost.setHeader("Accept", HEADERVALUE);
            httpPost.setHeader("Content-type", HEADERVALUE);
            return new DefaultHttpClient().execute(httpPost);
        } catch (UnsupportedEncodingException e) {
            Log.e("POST", e.getMessage());
        } catch (ClientProtocolException e) {
            Log.e("POST", e.getMessage());
        } catch (IOException e) {
            Log.e("POST", e.getMessage());
        }
        return null;
    }

    @Override
    protected final Void doInBackground(final String... params) {
        String url = "http://les-timeitup.appspot.com/put_user";
        String inJson = params[0];
        String email = params[1];

        Map<String, String> comment = new HashMap<String, String>();
        comment.put("data", inJson);
        comment.put("mail", email);
        String json = new GsonBuilder().create().toJson(comment, Map.class);
        makeRequest(url, json);

        return null;
    }

}
