
package com.br.les.util;

import android.os.AsyncTask;
import android.util.Log;

import com.br.les.timeitup.User;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpURLConnectionGET extends AsyncTask<String, Void, String> {

    public String requestJson(String mail) {
        try {
            return new HttpURLConnectionGET().execute(mail).get();
        } catch (Exception ex) {
            return null;
        }
    }

    public void sendPostJson(User user) throws ClientProtocolException, IOException {
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost post = new HttpPost("http://localhost:8080/put_user");

            List<NameValuePair> pairs = new ArrayList<NameValuePair>();
            pairs.add(new BasicNameValuePair("data", "mail"));
            pairs.add(new BasicNameValuePair("mail", user.getEmail()));
            pairs.add(new BasicNameValuePair("id", "3"));
            post.setEntity(new UrlEncodedFormEntity(pairs));

            httpClient.execute(post);
        } catch (ClientProtocolException e){
            Log.e("PROTOCOL ERROR", e.getMessage());
        } catch (IOException e) {
            Log.e("IO ERROR", e.getMessage());
        }

    }

    @Override
    protected String doInBackground(String... params) {
        String Url = "http://les-timeitup.appspot.com/get_user?mail="
                + params[0];
        String linha = "";
        try {

            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setHeader("Content-Type", "application/json");
            request.setURI(new URI(Url));
            HttpResponse serverAnswer = client.execute(request);
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    serverAnswer.getEntity().getContent()));
            StringBuffer sb = new StringBuffer("");

            while ((linha = br.readLine()) != null) {
                sb.append(linha);
            }

            br.close();

            linha = sb.toString();
        } catch (Exception e) {
            Log.e("GET" , e.getMessage());
        }

        return linha;
    }

}
